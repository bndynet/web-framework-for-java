angular.module('app')
    .directive('uiPageContent', [
        function() {
            return {
                restrict: 'E',
                replace: true,
                transclude: {
                    'headerLeft': '?uiPageContentHeaderLeft',
                    'headerRight': '?uiPageContentHeaderRight',
                    'footer': '?uiPageContentFooter'
                },
                templateUrl: '/static/apps/admin/lib/directives/ui-page-content.html',
                link: function(scope, elem, attrs, ctrl, $transclude) {
                    console.debug($transclude.isSlotFilled);
                    scope.hasHeaderLeft = $transclude.isSlotFilled('headerLeft');
                    scope.hasHeaderRight = $transclude.isSlotFilled('headerRight');
                    scope.hasFooter = $transclude.isSlotFilled('footer');
                    scope.hasHeader = scope.hasHeaderLeft || scope.hasHeaderRight;
                }
            };
        }
    ]);