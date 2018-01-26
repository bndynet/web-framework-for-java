app.controller('RolesCtrl',
    [ '$scope', 'appDialog', '$http', '$timeout',
    function($scope, appDialog, $http, $timeout) {
        $scope.data = [];
        $scope.roles = null;
        $scope.init = function() {
            $http.get('/api/core/roles').then(function(res) {
                $scope.data = res.data;
            });
        };
/*
        $scope.editUsers = function(item) {
            $scope.roleModel = angular.copy(item);
            $('#rolesForm').modal('show');
        };
        $scope.toggleUser = function(user) {
            var existedUser = _.find($scope.roleModel.users, function(r) { return r.name === user.username; });
            if (existedRole) {
                _.remove($scope.roleModel.users, function(r) {
                    return r.name === user.username;
                });
            } else {
                $scope.roleModel.users.push(user);
            }
        };
        $scope.saveUsers = function() {
            if ($scope.roleModel.id) {
                var ids = _.map($scope.roleModel.users, 'id');
                $http.put('/api/core/roles/' + $scope.roleModel.id + '/changeUser', {values: ids})
                    .then(function(res) {
                        appDialog.success();
                        var curItem = _.find($scope.data, function(item) { return item.id == $scope.roleModel.id});
                        if (curItem) {
                            curItem.users.length = 0;
                            curItem.userNames.length = 0;
                            _.each(ids, function(id) {
                                var user = _.find($scope.users, function(r) {return r.id === id; });
                                curItem.roles.push(user);
                                curItem.roleNames.push(user.username);
                            });
                        } else {
                            $scope.pageRoles($scope.pager.currentPage);
                        }
                        $scope.roleModel = null
                        $('#usersForm').modal('hide');
                    });
            }
        };

*/
        $scope.remove = function(item) {
            appDialog.confirmDeletion(function(){
                $http.delete('/api/core/roles/' + item.id).then(function(res) {
                    $scope.data.splice($scope.data.indexOf(item), 1)
                    appDialog.success();
                });
            });
        };

        $scope.init();
    }]);
