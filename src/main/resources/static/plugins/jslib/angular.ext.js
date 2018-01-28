/**
 * Utils for AngularJS v1.x
 * @author Bendy Zhang <zb@bndy.net>
 * @copyright BNDY.NET 2017
 * @see {@link http://bndy.net|Home Page}
 * @external angular
 */

'use strict';

(function (angular) {
    /**
     * Starts angular application.
     * @function external:angular.start
     * @param {string} appName  - The angular application module name.
     * @param {object=} options  - The options injected.
     * @param {function} options.run - The fun function.
     * @param {function} options.config  - The config function.
     * @param {object} options.httpInterceptor - The httpIntercaptor.
     * @param {function} options.httpInterceptor.request - The intercaptor about request.
     * @param {function} options.httpInterceptor.requestError - The intercaptor about requestError.
     * @param {function} options.httpInterceptor.response - The intercaptor about response.
     * @param {function} options.httpInterceptor.responseError - The intercaptor about responseError.
     *
     * @example <caption>Usage</caption>
     * angular.start('ngApp', {
     *      request: function(config) {},
     *      requestError: function(rejection) {},
     *      response: function(response) {},
     *      responseError: function(rejection) {},
     *      ...
     * });
     *
     */
    angular.start = function (appName, options) {
        options = options || {};
        var app = angular.module(appName);

        if (angular.isFunction(options.config)) {
            app.config(options.config);
        }

        if (angular.isFunction(options.run)) {
            app.run(options.run);
        }

        if (angular.isObject(options.httpInterceptor)) {
            app.config([
                '$provide', '$qProvider', '$httpProvider',
                function ($provide, $qProvider, $httpProvider) {
                    $qProvider.errorOnUnhandledRejections(false);

                    // http interceptor
                    $provide.factory('appHttpInterceptor', [
                        '$q', '$injector', '$timeout',
                        function ($q, $injector, $timeout) {
                            return {
                                'request': function (config) {
                                    if (angular.isFunction(options.httpInterceptor.request)) {
                                        options.httpInterceptor.request(config);
                                    }
                                    return config;
                                },
                                'requestError': function (rejection) {
                                    if (angular.isFunction(options.httpInterceptor.requestError)) {
                                        options.httpInterceptor.requestError(rejection);
                                    } else {
                                        console.error(rejection);
                                    }
                                    return $q.reject(rejection);
                                },
                                'response': function (response) {
                                    if (angular.isFunction(options.httpInterceptor.response)) {
                                        options.httpInterceptor.response(response);
                                    }
                                    return response;
                                },
                                'responseError': function (rejection) {
                                    if (angular.isFunction(options.httpInterceptor.responseError)) {
                                        options.httpInterceptor.responseError(rejection);
                                    } else {
                                        console.error(rejection);
                                    }
                                    return $q.reject(rejection);
                                }
                            };
                        }
                    ]);
                    $httpProvider.interceptors.push('appHttpInterceptor');
                }
            ]);
        }

        angular.element(document).ready(function () {
            angular.bootstrap(document, [appName]);
        });
    }

    /**
     * Resets Form validation status.
     * @function external:angular.resetForm
     * @param {object} scopeDotFormName - The object of angular Form.
     * @example
     * angular.resetForm($scope.formName);
     */
    angular.resetForm = function (scopeDotFormName) {
        var ngForm = scopeDotFormName;
        ngForm.$setPristine();
        ngForm.$setUntouched();
        ngForm.$error = {};
        for (var item in ngForm) {
            if (item.indexOf('$') < 0) {
                if (ngForm[item]) {
                    ngForm[item].$error = {};
                }
            }
        }
    };

    /**
     * Gets a single Promise that resolves all $http functions.
     * @function external:angular.ajaxAll
     * @param {$http} argument - A $http such as $http.get()
     * @param {$http} ... - more
     * @returns {Promise} A single promise.
     * @example
     * angular.ajaxAll($http.get(...), $http.post(...)).then(function(values){}, function(rejections){});
     * angular.ajaxAll($http.get(...));
     */
    angular.ajaxAll = function () {
        var promises = [];
        for (var idx = 0; idx < arguments.length; idx++) {
            var ajax = arguments[idx];
            promises.push(new Promise(function (resolve, reject) {
                ajax.then(resolve, reject);
            }));
        }
        return Promise.all(promises);
    }

})(angular);