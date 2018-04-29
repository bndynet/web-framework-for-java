angular.module('app')
    .directive('uiSearch', ['$translate', function($translate) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                placeholder: '@?',
                onCancel: '&?',
                onSearch: '&'
            },
            templateUrl: getPath('/static/apps/admin/lib/directives/ui-search.html'),
            link: function(scope, elem, attrs) {
                scope.model = null;

                scope.cancel = function() {
                    scope.model = null;
                    if (scope.onCancel) {
                        scope.onCancel();
                    }
                };

                scope.search = function() {
                    scope.onSearch({__model: scope.model});
                };
            }
        };
    }]);