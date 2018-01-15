var app = angular.module('app', ['ngCookies', 'pascalprecht.translate', 'ngAnimate', 'ngMaterial', 'ui.router', 'toaster', 'bn.ui', 'ngFileUpload']);

app.config(['$provide', '$qProvider', '$httpProvider', '$stateProvider', '$translateProvider', function ($provide, $qProvider, $httpProvider, $stateProvider, $translateProvider) {
    $qProvider.errorOnUnhandledRejections(false);

    var $cookies;
    angular.injector(['ngCookies']).invoke(['$cookies', function(_$cookies_) {
        $cookies = _$cookies_;
    }]);

    $translateProvider.useUrlLoader('/api/core/config/lang');
    $translateProvider.preferredLanguage($cookies.get("LOCALE")||'en');

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

                var title = rejection.data.error || rejection.data.title;
                var message = rejection.data.message;

                switch(rejection.status) {
                    case 401:
                    $timeout(function() {
                        location.href = '/sso/login';
                    }, 3000);
                    title = translate.instant('error.msgUnauthorized');
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
			templateUrl: '/static/apps/admin/modules/' + path + '.html',
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

app.factory('appDialog', [
    '$translate',
	'$mdDialog',
	'toaster',
	function ($translate, $mdDialog, toaster) {
		var service = {};

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
            if (!msg) msg = 'error.description';
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
            $mdDialog.show(alert).finally(function() {
                alert = null;
            });
		};

		service.confirm = function (title, msg, fnOK, fnCancel) {
            if (title) title = $translate.instant(title);
            if (msg) msg = $translate.instant(msg);
			var confirm = $mdDialog.confirm().title(title).textContent(msg)
				.ok($translate.instant('common.ok')).cancel($translate.instant('common.cancel'));

			$mdDialog.show(confirm).then(function () {
				if (fnOK)
					fnOK();
			}, function () {
				if (fnCancel)
					fnCancel();
			});
		};

		service.confirmDeletion = function (fnOK) {
			service.confirm($translate.instant('common.confirmDeleteTitle'), $translate.instant('common.confirmDeleteDescription'), fnOK);
		};

		service.showWin = function (data, controller, templateUrl, fnOK, fnCancel, options) {
			$mdDialog.show({
				locals: {
					vm: data,
					options: options,
				},
				controller: controller,
				templateUrl: templateUrl,
				parent: angular.element(document.body),
				clickOutsideToClose: false
			}).then(function (result) {
				if (fnOK) fnOK(result);
			}, function () {
				if (fnCancel) fnCancel();
			});
		};

		return service;
	}
]);

app.controller('LayoutCtrl', ['$http', '$scope', 'appDialog',
    function($http, $scope, appDialog) {
        // menus
        $scope.menus = [];
        $http.get('/static/apps/admin/mockdata/menus.json').then(function(res) {
            $scope.menus = res.data;
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
        $http.get('/static/apps/admin/mockdata/messages.json').then(function(res) {
            $scope.messages = res.data;
            $scope.messages1 = angular.copy(res.data);
            for (var idx = 0; idx < $scope.messages1.length; idx++) {
                $scope.messages1[idx].img = null;
            }
            $scope.messages2 = angular.copy(res.data);
            for (var idx = 0; idx < $scope.messages2.length; idx++) {
                $scope.messages2[idx].img = null;
                $scope.messages2[idx].description = null;
            }
        });
        $scope.messageClick = function(item) {
            // TODO: remove item from message bar
            appDialog.showWin(item, DialogMessageCtrl, '/static/apps/admin/modules/shared/dialogMessage.html',
                null, null, {
                    icon: 'fa fa-fw fa-user'
                });
        };
        $scope.messageMoreClick = function() {
            // TODO
            appDialog.alert('TODO', 'Link to more messages.');
        };

        // lock screen
        $scope.lockScreen = function() {
            $http.get('/api/core/users/logout').then(function(){
                $('.lockscreen').css('top', $(document).scrollTop()).show();
                $('body').addClass('no-scroll');
            });
        };

        $scope.unlockScreenError = false;
        $scope.unlockScreen = function() {
            if (!$scope.password) {
                return;
            }

            $scope.unlockScreenError = false;
            if ($scope.username && $scope.password) {
                $http.post('/api/core/users/login', { username: $scope.username, password: $scope.password }).then(function(res) {
                    $scope.password = '';
                    if (res.data === true) {
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


// MISC
function initUI() {
    $('.icheck, .iradio').iCheck({ checkboxClass: 'icheckbox_flat-green', radioClass   : 'iradio_flat-green'});
    $('.colorpicker').colorpicker();
    $('.timepicker').timepicker({ showInputs: false });
    $('.datepicker').datepicker({ autoclose: true, format: 'yyyy-mm-dd' });
}

$(function () {
    initUI();

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
	    var url = location.href;
	    var newUrl = ''
	    if (url.indexOf('locale=') > 0) {
	        newUrl = url.replace(/locale=[^#]*/g, 'locale=' + lang);
	    } else if (url.indexOf('#') > 0) {
	        newUrl = url.substring(0, url.indexOf('#'))
	            + (url.indexOf('?') > 0 ? '&' : '?') + 'locale=' + lang + url.substring(url.indexOf('#'));
	    } else {
	        newUrl = url + (url.indexOf('?') > 0 ? '&' : '?') + 'locale=' + lang;
	    }
	    location.href = newUrl;
	});
});
