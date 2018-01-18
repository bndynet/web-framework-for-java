app.controller('UsersCtrl',
    [ '$scope', 'appDialog', '$http', '$timeout',
    function($scope, appDialog, $http, $timeout) {
        $scope.data = [];
        $scope.roles = null;
        $scope.pageUsers = function (page) {
            var url = '/api/core/users/search?page=' + (page - 1);
            if ($scope.searchKeywords) {
                url += '&keywords=' + $scope.searchKeywords;
            }
            $http.get(url).then(function(res) {
                $scope.data = res.data.content;
                $scope.pager = {
                    currentPage: page,
                    pageSize:  res.data.size,
                    recordCount: res.data.totalElements,
                };

                if ($scope.searchKeywords) {
                    $timeout(function() {
                        highlightText($scope.searchKeywords, 'table > tbody > tr > td, table > tbody > tr > td > span');
                    }, 100);
                }
            });
        };

        $scope.init = function() {
            $http.get('/api/core/config/roles').then(function(res) {
                $scope.roles = res.data;
            });
            $scope.pageUsers(1);
        };

        $scope.editRoles = function(item) {
            $scope.userModel = angular.copy(item);
            if ($scope.userModel.roles && $scope.userModel.roles.length > 0) {
                for (var idx in $scope.roles) {
                    if ($scope.roles[idx].name == $scope.userModel.roles[0].name) {
                        $scope.userModel.currentRole = $scope.roles[idx];
                    }
                }
            }
            $('#rolesForm').modal('show');
        };
        $scope.changeRole = function(role) {
            $scope.userModel.currentRole = role;
        };
        $scope.saveRoles = function() {
            if ($scope.userModel.id) {
                var roleId = $scope.userModel.currentRole.id;
                $http.put('/api/core/users/' + $scope.userModel.id + '/changeRole?roleId=' + $scope.userModel.currentRole.id)
                    .then(function(res) {
                        appDialog.success();
                        var curItem = _.find($scope.data, function(item) { return item.id == $scope.userModel.id});
                        if (curItem) {
                            curItem.roles.length = 0;
                            curItem.roleNames.length = 0;
                            curItem.roles.push($scope.userModel.currentRole);
                            curItem.roleNames.push($scope.userModel.currentRole.name);
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
                $http.delete('/api/core/users/' + item.id).then(function(res) {
                    $scope.data.splice($scope.data.indexOf(item), 1)
                    appDialog.success();
                });
            });
        };

        $scope.toggleEnabled = function(item) {
            $http.put('/api/core/users/' + item.id + '/toggleEnabled').then(function(res) {
                item.enabled = !item.enabled;
                appDialog.success();
            });
        };

        $scope.search = function() {
            if ($scope.searchKeywords) {
                $scope.pageUsers(1);
            }
        };

        $scope.cancelSearch = function() {
            $scope.searchKeywords = null;
            $scope.pageUsers(1);
        };

        $scope.init();
    }]);
