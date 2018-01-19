angular.module('app')
    .directive('uiSearch', ['$translate', function($translate) {
        return {
            restrict: 'E',
            replace: true,
            require: ['ngModel'],
            scope: {
                model: '=ngModel',
                onCancel: '&',
                onSearch: '&'
            },
            templateUrl: '/static/apps/admin/lib/directives/ui-search.html',
            link: function(scope, elem, attrs) {

                scope.cancel = function() {
                    scope.model = null;
                    if (scope.onCancel) {
                        scope.onCancel();
                    }
                };

                scope.search = function() {
                    if (scope.onSearch) {
                        scope.onSearch();
                    }
                };
            }
        };
    }]);