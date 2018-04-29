angular.module('app')
    .directive('uiTree', [
        function() {
            return {
                restrict: 'E',
                replace: true,
                require: ['ngModel'],
                scope: {
                    model: '=ngModel',
                    itemTemplate: '=',
                },
                templateUrl: getPath('/static/apps/admin/lib/directives/ui-tree.html'),
                link: function(scope, elem, attrs) {
                    scope.id = attrs.id;
                    if (!scope.id) {
                        scope.id = 'tree';
                    }
                }
            };
        }
    ]);