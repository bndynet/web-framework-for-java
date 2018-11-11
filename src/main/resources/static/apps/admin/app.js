var app = angular.module('app', ['ngCookies', 'ngMessages', 'ngclipboard', 'pascalprecht.translate', 'ngAnimate', 'ngMaterial', 'ui.router', 'toaster', 'bn.ui', 'ngFileUpload', 'angularFileUpload']);

app.config(['$provide', '$qProvider', '$httpProvider', '$stateProvider', '$translateProvider', function ($provide, $qProvider, $httpProvider, $stateProvider, $translateProvider) {
    $qProvider.errorOnUnhandledRejections(false);

    var $cookies;
    angular.injector(['ngCookies']).invoke(['$cookies', function(_$cookies_) {
        $cookies = _$cookies_;
    }]);

    var lang = $cookies.get("LOCALE") || defaultLang;
    $.get(getPath('/api/core/config/lang?lang=' + lang), function(res) {
    	$translateProvider.translations(lang, JSON.parse(res));
		$translateProvider.preferredLanguage(lang);
		$translateProvider.use(lang);
    });

    // override translateFilter for supporting java i18n format
    $provide.decorator('translateFilter', ['$delegate', function($delegate) {
        var srcFilter = $delegate;
        return function() {
            var lang = srcFilter.apply(this, arguments);
            // arguments[0] is the KEY
            if (angular.isArray(arguments[1])) {
                for (var idx = 0; idx < arguments[1].length; idx++){
                   lang = lang.replace('{' + idx + '}', arguments[1][idx]);
                }
            }
            return lang;
        };
    }]);


    // http interceptor
    $provide.factory('appHttpInterceptor', ['$q', '$injector', '$timeout', function($q, $injector, $timeout) {
        var toaster = $injector.get('toaster');
        var translate = $injector.get('$translate');
        return {
            'request': function(config) {
                return config;
            },
            'requestError': function(rejection) {
                toaster.pop({
                    type: 'error',
                    body: JSON.stringify(rejection)
                });
                return $q.reject(rejection);
            },
            'response': function(response) {
                return response;
            },
           'responseError': function(rejection) {
                console.error(rejection);

                var title = null;
                var message = null;

                if (rejection.data) {
                    var message = rejection.data.error || rejection.data.message;
                }

                switch(rejection.status) {
                    // if server unavailable
                    case -1:
                        title = translate.instant('error.serverUnavailable');
                        break;
                    case 401:
                        $timeout(function() {
                            location.href = getPath('/sso/login');
                        }, 3000);
                        title = translate.instant('error.unauthorizedAccess');
                        message = translate.instant('common.msgRedirectToLogin');
                    default:
                }

                toaster.pop({
                    type: 'error',
                    title: title,
                    body: message,
                });
                return $q.reject(rejection);
            }
        };
    }]);
    $httpProvider.interceptors.push('appHttpInterceptor');


    // register ui-states
	function registerState(name) {
		var path = name.replace(/-/g, '/');
		$stateProvider.state(name, {
			url: '/' + path,
			templateUrl: getPath('/static/apps/admin/modules/' + path + '.html'),
			params: { obj: null },
		});
	}
	var pages = appModules || [];   // appModules is from backend
	$stateProvider.state('default', {
		url: '',
		redirectTo: 'example-dashboard'
	});
	for (var idx in pages) {
		var p = pages[idx];
		registerState(p);
	}
}]);

app.factory('appService', ['$rootScope', '$http', '$translate', function($rootScope, $http, $translate) {
    var service = {};

    service.ajaxGet = function(apiUrl) {
        return $http.get(getPath(apiUrl)).then(function(res) {
            var result = res.data;
            // for paging
            if (typeof result.totalElements !== 'undefined') {
                result.currentPage = result.number + 1;
                result.pageSize = result.size;
                result.recordCount = result.totalElements;
            }
            return result;
        });
    };
    service.ajaxPut = function(apiUrl, data) {
        return $http.put(getPath(apiUrl), data).then(function(res) {
            return res.data;
        });
    };
    service.ajaxPost = function(apiUrl, data) {
        return $http.post(getPath(apiUrl), data).then(function(res) {
            return res.data;
        });
    };
    service.ajaxDelete = function(apiUrl, data) {
        return $http.delete(getPath(apiUrl)).then(function(res) {
            return res.data;
        });
    };
    service.ajaxSave = function(apiUrl, data) {
        if (data.id) {
            apiUrl += '/' + data.id;
            return service.ajaxPut(apiUrl, data);
        }

        return service.ajaxPost(apiUrl, data);
    };
    service.ajaxAll = function() {
        return angular.ajaxAll.apply(this, arguments);
    };


    return service;
}]);

app.factory('appDialog', [
    '$rootScope',
    '$timeout',
    '$translate',
	'$mdDialog',
	'$mdSelect',
	'toaster',
	function ($rootScope, $timeout, $translate, $mdDialog, $mdSelect, toaster) {
		var service = {};

        service.loading = function(showLoading) {
            if (typeof showLoading === 'undefined') showLoading = true;
            $timeout(function() {
                $rootScope.$broadcast('loading', showLoading);
                $rootScope.$emit('loading', showLoading);
            });
        }

		service.info = function (title, msg) {
			toaster.pop({
				type: 'info',
				title: title,
				body: msg,
				timeout: 5000
			});
		};
		service.wait = function (title, msg) {
			var id = 'wait-toastid-' + Math.random();
			toaster.pop({
				type: 'wait',
				title: title,
				body: msg,
				showCloseButton: false,
				toastId: id,
				timeout: 0
			});
			return id;
		};
		service.clearWait = function (wait) {
			toaster.clear(null, wait);
		};
		service.warning = function (title, msg) {
            if (title) title = $translate.instant(title);
            if (msg) msg = $translate.instant(msg);
			toaster.pop({
				type: 'warning',
				title: title,
				body: msg,
				timeout: 0
			});
		};
		service.success = function (title, msg) {
			if (!title) title = 'common.msgSuccess';
            if (title) title = $translate.instant(title);
            if (msg) msg = $translate.instant(msg);
			toaster.pop({
				type: 'success',
				title: title,
				body: msg,
				timeout: 3000
			});
		};
		service.error = function (title, msg) {
			if (!title) title = 'error.title';
            if (title) title = $translate.instant(title);
            if (msg) msg = $translate.instant(msg);
			toaster.pop({
				type: 'error',
				title: title,
				body: msg,
				timeout: 0
			});
		};

		service.alert = function (title, msg) {
            if (title) title = $translate.instant(title);
            if (msg) msg = $translate.instant(msg);
            var alert = $mdDialog.alert({
                title: title,
                textContent: msg,
                ok: $translate.instant('common.ok'),
                clickOutsideToClose: false
            });
            angular.element(document.querySelector('body')).attr('style', 'overflow-y: hidden');
            $mdDialog.show(alert).finally(function() {
                alert = null;
                angular.element(document.querySelector('body')).attr('style', 'overflow-y: auto');
            });
		};

		service.confirm = function (title, msg, fnOK, fnCancel) {
            if (title) title = $translate.instant(title);
            if (msg) msg = $translate.instant(msg);
			var confirm = $mdDialog.confirm().title(title).textContent(msg)
				.ok($translate.instant('common.ok')).cancel($translate.instant('common.cancel'));

            angular.element(document.querySelector('body')).attr('style', 'overflow-y: hidden');
			$mdDialog.show(confirm).then(function () {
				if (fnOK)
					fnOK();
			}, function () {
				if (fnCancel)
					fnCancel();
			}).finally(function() {
                angular.element(document.querySelector('body')).attr('style', 'overflow-y: auto');
			});
		};

		service.confirmDeletion = function (fnOK) {
			service.confirm($translate.instant('common.confirmDeleteTitle'), $translate.instant('common.confirmDeleteDescription'), fnOK);
		};

		service.show = function(selector, ev) {
            angular.element(document.querySelector('body')).attr('style', 'overflow-y: hidden');
            $mdDialog.show({
                contentElement: selector,
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true
            }).finally(function() {
                angular.element(document.querySelector('body')).attr('style', 'overflow-y: auto');
            });
		};

		service.getModal = function(name) {
		    return {
		        name: name,
		        selector: function() {
		            if (this.name) {
                        return '#' + name;
		            } else {
		                return '.modal';
		            }
		        },
		        show: function() {
                    $(this.selector()).on('hide.bs.modal', function (e) {
                        $mdSelect.hide();
                    });
		            $(this.selector()).modal('show');
		        },
		        close: function() {
		            $(this.selector()).modal('hide');
		        }
		    };
		};

		service.showWin = function (data, controller, templateUrl, fnOK, fnCancel, options) {
            angular.element(document.querySelector('body')).attr('style', 'overflow-y: hidden');
			$mdDialog.show({
				locals: {
					vm: data,
					options: options,
				},
				controller: controller,
				templateUrl: templateUrl,
				parent: angular.element(document.body),
				disableParentScroll: true,
				clickOutsideToClose: false
			}).then(function (result) {
				if (fnOK) fnOK(result);
			}, function () {
				if (fnCancel) fnCancel();
			}).finally(function() {
                angular.element(document.querySelector('body')).attr('style', 'overflow-y: auto');
			});
		};

		return service;
	}
]);

// filters
angular.module('app').
    filter('path', function() {
        return function(url) {
            return getPath(url);
        };
    });

app.controller('LayoutCtrl', ['$http', '$scope', '$state', 'appService', 'appDialog',
    function($http, $scope, $state, appService, appDialog) {

        $scope.$on('loading', function(event, showLoading) {
            $scope.showLoading = showLoading;
        });

        // menus
        $scope.menus = [];
        appService.ajaxGet('/api/core/menus/tree').then(function(res) {
            $scope.menus = res;
        });

        // search
        $scope.searchOptions = {
            value: '',
            search: function() {
                if ($scope.searchOptions.value) {
                    // TODO
                    appDialog.alert('TODO', 'search ' + $scope.searchOptions.value);
                }
            }
        };

        // messages
        // TODO: get messages from backend
        $scope.messages = [];
        appService.ajaxGet('/static/apps/admin/mockdata/messages.json').then(function(res) {
            _.forEach(res, function(item) {
                item.img = getPath(item.img);
            });
            $scope.messages = res;
            $scope.messages1 = angular.copy(res);
            for (var idx = 0; idx < $scope.messages1.length; idx++) {
                $scope.messages1[idx].img = null;
            }
            $scope.messages2 = angular.copy(res);
            for (var idx = 0; idx < $scope.messages2.length; idx++) {
                $scope.messages2[idx].img = null;
                $scope.messages2[idx].description = null;
            }
        });
        $scope.messageClick = function(item) {
            // TODO: remove item from message bar
            appDialog.showWin(item, DialogMessageCtrl, getPath('/static/apps/admin/modules/shared/dialogMessage.html'),
                null, null, {
                    icon: 'fa fa-fw fa-user'
                });
        };
        $scope.messageMoreClick = function() {
            // TODO
            appDialog.alert('TODO', 'Link to more messages.');
        };
        $scope.goTo = function(menu) {
            // {id: 1}  -> ({id: 1}) -> {"id": 1} -> json object
            var args = JSON.parse(JSON.stringify(eval('(' + menu.linkParams + ')')));
            $state.go(menu.link, {obj: args});
        };

        // lock screen
        $scope.lockScreen = function() {
            appService.ajaxGet('/api/core/users/logout').then(function(){
                $('.lockscreen').css('top', $(document).scrollTop()).show();
                $('body').addClass('no-scroll');
            });
        };

        $scope.unlockScreenError = false;
        $scope.unlockScreen = function() {
            if (!$scope.password) {
                $scope.unlockScreenError = true;
                return;
            }

            if ($scope.username && $scope.password) {
                appService.ajaxPost('/api/core/users/login', { username: $scope.username, password: $scope.password }).then(function(res) {
                    $scope.password = '';
                    if (res) {
                        $scope.unlockScreenError = false;
                        $('.lockscreen').css('top', 0);
                        $('body').removeClass('no-scroll');
                        $('.lockscreen').hide();
                    } else {
                        $scope.unlockScreenError = true;
                    }
                });
            }
        };
    }
]);

angular.element(document).ready(function () {
	angular.bootstrap(document, ['app']);
});


// misc functions
function getPath(url) {
    if (url && url.toLowerCase().indexOf('http') === 0) {
        return url;
    }
    if (url && url.indexOf('/') === 0) {
        url = url.substring(1);
    }
    return contextPath + url||'';
}

// overwrite global settings

// disable modal dialog closing when clicking outside dialog or pressing ESC
$.fn.modal.prototype.constructor.Constructor.DEFAULTS.backdrop = 'static';
$.fn.modal.prototype.constructor.Constructor.DEFAULTS.keyboard =  false;

$(function () {
	// fix the actions of AdminLTE in AngularJS
	$('body').on('click', '[data-widget=collapse]', function (event) {
		var iconTag = $(this).find('i');
		if (iconTag.hasClass('fa-minus')) {
			iconTag.removeClass('fa-minus');
			iconTag.addClass('fa-plus');
		} else {
			iconTag.addClass('fa-minus');
			iconTag.removeClass('fa-plus');
		}
		$(this).closest('.box').find('.box-body').slideToggle();
		$(this).closest('.box').find('.box-footer').slideToggle();
		event.preventDefault();
	});
	$('body').on('click', '[data-widget=remove]', function (event) {
		$(this).closest('.box').slideToggle();
		event.preventDefault();
	});

	// language
	$('#LanguageOptions').change(function(){
	    var lang = $(this).val();
	    document.cookie = 'LOCALE=' + lang + ';path=/';
	    location.href = getPath('/admin/');
	});
});
