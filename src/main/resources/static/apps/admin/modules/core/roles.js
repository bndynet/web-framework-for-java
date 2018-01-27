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
            if ($scope.formModel.id) {
                $http.put('/api/core/roles/' + $scope.formModel.id, $scope.formModel).then(function() {
                    $('#dialogForm').modal('hide');
                    appDialog.success();
                    $scope.init();
                });
            } else {
                $http.post('/api/core/roles', $scope.formModel).then(function() {
                    $('#dialogForm').modal('hide');
                    appDialog.success();
                    $scope.init();
                });
            }
        };

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
