angular.module('app')
    .directive('uiMenuTree', ['$translate',
    function($translate) {
        return {
            restrict: 'E',
            replace: true,
            require: ['ngModel'],
            scope: {
                model: '=ngModel',
                multiSelect: '=?',
                canEdit: '=?',
                onItemEdit: '&',
                onItemRemove: '&',
                onItemAddSubmenu: '&',
                onItemToggleVisible: '&',
            },
            templateUrl: '/static/apps/admin/lib/directives/ui-menu-tree.html',
            link: function(scope, elem, attrs) {

                function toggleChildren(item) {
                    if (item.children) {
                        for (var idx = 0; idx < item.children.length; idx++) {
                            var child = item.children[idx];
                            child.__selected = item.__selected;

                            toggleChildren(child);
                        }
                    }
                }

                scope.toggleSelect = function(item, parent) {
                    item.__selected = !item.__selected;
                    if (parent) {
                        if (item.__selected) {
                            parent.__selected = true;
                        } else {
                            if (parent.children.length === _.filter(parent.children, function(c) { return !c.__selected;} ).length) {
                                parent.__selected = false;
                            }
                        }
                    }
                    toggleChildren(item);
                };

                scope.addSubmenu = function(item) {
                    scope.onItemAddSubmenu({item: item});
                };
                scope.remove = function(item) {
                    scope.onItemRemove({item: item});
                };
                scope.edit = function(item) {
                    scope.onItemEdit({item: item});
                };
                scope.toggleVisible = function(item) {
                    scope.onItemToggleVisible({item: item});
                };
            }
        };
    }
]);