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
                showAll: '=?',
                canEdit: '=?',
                onItemEdit: '&?',
                onItemRemove: '&?',
                onItemAddSubmenu: '&?',
                onItemToggleVisible: '&?',
                onItemToggleSelect: '&?',
            },
            templateUrl: getPath( '/static/apps/admin/lib/directives/ui-menu-tree.html'),
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

                var dicMenus = {};
                scope.initMapping = function(item) {
                    if (item.id) {
                        dicMenus[item.id] = item;
                    }
                };

                scope.toggleSelect = function(item) {
                    item.__selected = !item.__selected;

                    var parent = dicMenus[item.parentId];
                    while(parent) {
                        if (item.__selected) {
                            parent.__selected = true;
                        } else {
                            if (parent && parent.children.length === _.filter(parent.children, function(c) { return !c.__selected;} ).length) {
                                parent.__selected = false;
                            }
                        }
                        parent = dicMenus[parent.parentId];
                    }
                    toggleChildren(item);

                    if (scope.onItemToggleSelect) {
                        scope.onItemToggleSelect({item: item});
                    }
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