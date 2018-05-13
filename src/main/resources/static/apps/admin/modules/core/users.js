angular.module('app')
    .controller('UsersCtrl',
    /* @ngInject */
    function($scope, appService, appDialog, $timeout) {
        $scope.pager = {};
        $scope.roles = null;

        function initData() {
            appService.ajaxGet('/api/core/roles').then(function(d) {
                $scope.roles = d;
            });
            $scope.pageUsers(1);
        };

        $scope.pageUsers = function(page) {
            var url = '/api/core/users/search?page=' + (page - 1);
            if ($scope.searchKeywords) {
                url += '&keywords=' + $scope.searchKeywords;
            }
            appService.ajaxGet(url).then(function(d) {
                $scope.pager = d;

                if ($scope.searchKeywords) {
                    $timeout(function() {
                        $('table > tbody > tr > td, table > tbody > tr > td > span').highlightText($scope.searchKeywords);
                    }, 100);
                }
            });
        };

        $scope.editRoles = function(item) {
            $scope.userModel = angular.copy(item);
            $('#rolesForm').modal('show');
        };
        $scope.toggleRole = function(role) {
            var existedRole = _.find($scope.userModel.roles, function(r) { return r.name === role.name; });
            if (existedRole) {
                _.remove($scope.userModel.roles, function(r) {
                    return r.name === role.name;
                });
            } else {
                $scope.userModel.roles.push(role);
            }
        };
        $scope.saveRoles = function() {
            if ($scope.userModel.id) {
                var ids = _.map($scope.userModel.roles, 'id');
                appService.ajaxPut('/api/core/users/' + $scope.userModel.id + '/changeRole', {values: ids})
                    .then(function() {
                        appDialog.success();
                        var curItem = _.find($scope.pager.content, function(item) { return item.id == $scope.userModel.id});
                        if (curItem) {
                            curItem.roles.length = 0;
                            curItem.roleNames.length = 0;
                            _.each(ids, function(id) {
                                var role = _.find($scope.roles, function(r) {return r.id === id; });
                                curItem.roles.push(role);
                                curItem.roleNames.push(role.name);
                            });
                        } else {
                            $scope.pageUsers($scope.pager.currentPage);
                        }
                        $scope.userModel = null
                        $('#rolesForm').modal('hide');
                    });
            }
        };

        $scope.remove = function(item) {
            appDialog.confirmDeletion(function(){
                appService.ajaxDelete('/api/core/users/' + item.id).then(function() {
                    $scope.pager.content.splice($scope.pager.content.indexOf(item), 1)
                    appDialog.success();
                    $scope.pageUsers($scope.pager.currentPage);
                });
            });
        };

        $scope.toggleEnabled = function(item) {
            appService.ajaxPut('/api/core/users/' + item.id + '/toggleEnabled').then(function(res) {
                item.enabled = !item.enabled;
                appDialog.success();
            });
        };

        var userDialog = appDialog.getModal('dialogUser');
        $scope.openUserDialog = function(user) {
            angular.resetForm($scope.formUser);
            $scope.userModel = angular.copy(user) || {};
            $scope.userModel.__origin = user;
            userDialog.show();
        };
        $scope.saveUser = function() {
            $scope.savingUser = true;
            appService.ajaxSave('/api/core/users', $scope.userModel).then(function(res) {
                $scope.pageUsers(1);
                userDialog.close();
            }).finally(function() {
                $scope.savingUser = false;
            });
        };
        $scope.checkUsername = function(username) {
            if ($scope.userModel.__origin && $scope.userModel.username === $scope.userModel.__origin.username) {
                $scope.formUser.username.$error = {};
                return;
            }

            $scope.userModel.canSave = false;
            $scope.userModel.checkingUsername = true;
            appService.ajaxGet('/api/core/users/exist?username=' + username).then(function(res) {
                if (!res) {
                    $scope.userModel.canSave = true;
                    $scope.formUser.username.$setValidity('existed', true);
                } else {
                    $scope.formUser.username.$setValidity('existed', false);
                }
            }).finally(function() {
                $scope.userModel.checkingUsername = false;
            });

        };
        $scope.checkRepassword = function() {
            if ($scope.userModel.repassword !== $scope.userModel.password) {
                $scope.formUser.repassword.$setValidity('same', false);
            } else {
                $scope.formUser.repassword.$setValidity('same', true);
            }
        };

        $scope.search = function(keywords) {
            $scope.searchKeywords = keywords;
            $scope.pageUsers(1);
        };

        $scope.cancelSearch = function() {
            $scope.searchKeywords = null;
            $scope.pageUsers(1);
        };

        $scope.userHasRole = function(role) {
            if ($scope.userModel && _.find($scope.userModel.roles||[], function(r) { return r.name === role.name; })) {
                return true;
            }
            return false;
        };

        initData();
    });
