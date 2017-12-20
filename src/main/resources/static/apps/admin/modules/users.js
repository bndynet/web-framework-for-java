app.controller('UsersCtrl',
    [ '$scope', 'appDialog', '$http',
    function($scope, appDialog, $http) {
        $scope.data = [];
        $scope.roles = null;
        $scope.pageUsers = function (page) {
            $http.get('/api/app/users/?page=' + (page - 1)).then(function(res) {
                $scope.data = res.data.content;
                $scope.pager = {
                    currentPage: page,
                    pageSize:  res.data.size,
                    recordCount: res.data.totalElements,
                };
            });
        };

        $scope.init = function() {
            $http.get('/api/app/config/roles').then(function(res) {
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
                $http.put('/api/app/users/' + $scope.userModel.id + '/changerole?roleId=' + $scope.userModel.currentRole.id)
                    .then(function(res) {
                        $scope.pageUsers($scope.pager.currentPage);
                        $('#rolesForm').modal('hide');
                    });
            }
        };

        $scope.remove = function(item) {
            appDialog.confirmDeletion(function(){
                $http.delete('/api/app/users/' + item.id).then(function(res) {
                    $scope.data.splice($scope.data.indexOf(item), 1)
                    appDialog.success();
                });
            });
        };

        $scope.toggleEnabled = function(item) {
            $http.put('/api/app/users/' + item.id + '/toggleenabled').then(function(res) {
                item.enabled = !item.enabled;
                appDialog.success();
            });
        };


        $scope.init();
    }]);
