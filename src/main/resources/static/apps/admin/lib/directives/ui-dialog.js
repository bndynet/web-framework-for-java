angular.module('app')
    .directive('uiDialog', [
        '$translate',
        function($translate) {
            return {
                restrict: 'E',
                replace: true,
                transclude: {
                    footer: '?uiDialogFooter'
                },
                scope: true,
                templateUrl: '/static/apps/admin/lib/directives/ui-dialog.html',
                link: function(scope, elem, attrs, ctrl, $transclude) {
                    scope.hasFooter = $transclude.isSlotFilled('footer');
                }
            }
        }
    ]);