angular.module('app')
    .directive('uiActions', [
        function() {
            return {
                restrict: 'E',
                replace: true,
                scope: {
                    model: '=ngModel',
                    onView: '&?',
                    showView: '=?',
                    onAdd: '&?',
                    showAdd: '=?',
                    onEdit: '&?',
                    showEdit: '=?',
                    onRemove: '&?',
                    showRemove: '=?',
                    onToggleVisible: '&?',
                    showToggleVisible: '=?',
                    onExchange: '&?',
                    showExchange: '=?',
                },
                templateUrl: getPath('/static/apps/admin/lib/directives/ui-actions.html'),
                link: function(scope, elem, attrs) {
                    if (typeof scope.showView === 'undefined') {
                        scope.showView = !!scope.onView;
                    }
                    if (typeof scope.showAdd === 'undefined') {
                        scope.showAdd = !!scope.onAdd;
                    }
                    if (typeof scope.showEdit === 'undefined') {
                        scope.showEdit = !!scope.onEdit;
                    }
                    if (typeof scope.showRemove === 'undefined') {
                        scope.showRemove = !!scope.onRemove;
                    }
                    if (typeof scope.showToggleVisible === 'undefined') {
                        scope.showToggleVisible = !!scope.onToggleVisible;
                    }
                    if (typeof scope.showExchange === 'undefined') {
                        scope.showExchange = !!scope.onExchange;
                    }
                },
            };
        }
    ]);