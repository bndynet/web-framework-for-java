angular.module('app')
    .directive('uiPageContent', [
        function() {
            return {
                restrict: 'E',
                replace: true,
                transclude: {
                    'headerLeft': '?uiPageContentHeaderLeft',
                    'headerRight': '?uiPageContentHeaderRight',
                    'headerCenter': '?uiPageContentHeaderCenter',
                    'footer': '?uiPageContentFooter'
                },
                templateUrl: getPath('/static/apps/admin/lib/directives/ui-page-content.html'),
                link: function(scope, elem, attrs, ctrl, $transclude) {
                    scope.hasHeaderLeft = $transclude.isSlotFilled('headerLeft');
                    scope.hasHeaderRight = $transclude.isSlotFilled('headerRight');
                    scope.hasHeaderCenter = $transclude.isSlotFilled('headerCenter');
                    scope.hasFooter = $transclude.isSlotFilled('footer');
                    scope.hasHeader = scope.hasHeaderLeft || scope.hasHeaderRight || scope.hasHeaderCenter;
                }
            };
        }
    ]);