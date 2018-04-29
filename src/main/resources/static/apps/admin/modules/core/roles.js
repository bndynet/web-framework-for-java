angular.module('app')
    .controller('RolesCtrl',
    /* @ngInject */
    function($scope, appService, appDialog, $timeout) {

        function initData() {
            appDialog.loading();
            appService.ajaxGet('/api/core/roles').then(function(data) {
                $scope.data = data;
                appDialog.loading(false);
            });
            appService.ajaxGet('/api/core/menus/tree').then(function(data) {
                $scope.menus = data;
            });
        };

        $scope.data = [];
        $scope.roles = null;

        $scope.add = function(parent) {
            angular.resetForm($scope.form);
            if (parent) {
                $scope.formModel = { parentId: parent.id, visible: true };
            } else {
                $scope.formModel = { visible: true };
            }
            $('#dialogForm').modal('show');
        };

        $scope.edit = function(role) {
            angular.resetForm($scope.form);
            $scope.formModel = angular.copy(role);
            $('#dialogForm').modal('show');
        };

        $scope.save = function() {
            appService.ajaxSave('/api/core/roles', $scope.formModel).then(function(data) {
                initData();
                appDialog.success();
                $('#dialogForm').modal('hide');
            });
        };

        $scope.remove = function(item) {
            appDialog.confirmDeletion(function(){
                appService.ajaxDelete('/api/core/roles/' + item.id).then(function() {
                    $scope.data.splice($scope.data.indexOf(item), 1)
                    appDialog.success();
                });
            });
        };

        // assign menus
        function initMenus() {
            for (var idx = 0; idx < $scope.menus.length; idx++) {
                initMenu($scope.menus[idx]);
            }
        }
        function initMenu(menu) {
            var needToCheck = $scope.formModel && $scope.formModel.menuIds;
            var menuExisted = needToCheck ? eval('[' + $scope.formModel.menuIds + ']').indexOf(menu.id) !== -1 : false;
            menu.__selected =  menuExisted;
            menu.visible = menuExisted;
            for (var idx = 0; idx < menu.children.length; idx++) {
                var child = menu.children[idx];
                var existed = needToCheck ? eval('[' + $scope.formModel.menuIds + ']').indexOf(child.id) !== -1 : false;
                child.__selected = existed;
                child.visible = existed;
                initMenu(child);
            }
        }


        function getSelectedMenus(parent) {
            var selectedMenus = [];
            var menuList = parent ? parent.children : $scope.menus;
            for(var idx = 0; idx < menuList.length; idx++) {
                var m = menuList[idx];
                if (m.__selected) {
                    selectedMenus.push(m);
                    selectedMenus = selectedMenus.concat(getSelectedMenus(m));
                }
            }
            return selectedMenus;
        }
        $scope.showMenus = function(role) {
            $scope.canEditMenus = false;
            angular.resetForm($scope.form);
            $scope.formModel = angular.copy(role);
            initMenus();
            $('#menusForm').modal('show');
        };
        $scope.toggleMenuSelect = function(menu) {
            $scope.canResetForm = true;
        };
        $scope.resetMenus = function() {
            initMenus();
            $scope.canResetForm = false;
        };
        $scope.assignMenus = function() {
            var selectedMenus = getSelectedMenus();
            var menuIds = _.map(selectedMenus, function(m) {
                return m.id;
            });
            appService.ajaxPut('/api/core/roles/' + $scope.formModel.id + '/assignMenus', {values: menuIds}).then(function() {
                initData();
                appDialog.success();
                $('#menusForm').modal('hide');
            });
        };

        initData();
    });
