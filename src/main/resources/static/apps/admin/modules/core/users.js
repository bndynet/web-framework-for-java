app.controller('UsersCtrl',
    [ '$scope', 'appDialog', '$http', '$timeout',
    function($scope, appDialog, $http, $timeout) {
        $scope.data = [];
        $scope.roles = null;

        function pageUsers(page) {
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
                        $('table > tbody > tr > td, table > tbody > tr > td > span').highlightText($scope.searchKeywords);
                    }, 100);
                }
            });
        };

        function initData() {
            $http.get('/api/core/roles').then(function(res) {
                $scope.roles = res.data;
            });
            pageUsers(1);
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
                $http.put('/api/core/users/' + $scope.userModel.id + '/changeRole', {values: ids})
                    .then(function(res) {
                        appDialog.success();
                        var curItem = _.find($scope.data, function(item) { return item.id == $scope.userModel.id});
                        if (curItem) {
                            curItem.roles.length = 0;
                            curItem.roleNames.length = 0;
                            _.each(ids, function(id) {
                                var role = _.find($scope.roles, function(r) {return r.id === id; });
                                curItem.roles.push(role);
                                curItem.roleNames.push(role.name);
                            });
                        } else {
                            pageUsers($scope.pager.currentPage);
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
            pageUsers(1);
        };

        $scope.cancelSearch = function() {
            $scope.searchKeywords = null;
            pageUsers(1);
        };

        $scope.userHasRole = function(role) {
            if ($scope.userModel && _.find($scope.userModel.roles||[], function(r) { return r.name === role.name; })) {
                return true;
            }
            return false;
        };

        initData();
    }]);
