app.controller('MenusCtrl',
    [ '$scope', 'appDialog', '$http',
    function($scope, appDialog, $http) {

        function initData() {
            $http.get('/api/core/menus/tree?all=true').then(function(res) {
                $scope.data = res.data;
            });
        }

        $scope.add = function(parent) {
            if (parent) {
                $scope.formModel = { parentId: parent.id };
            } else {
                $scope.formModel = null;
            }
            $('#dialogForm').modal('show');
        };

        $scope.edit = function(menu) {
            $scope.formModel = angular.copy(menu);
            $('#dialogForm').modal('show');
        };

        $scope.save = function() {
            if ($scope.formModel.id) {
                $http.put('/api/core/menus/' + $scope.formModel.id, $scope.formModel).then(function() {
                    $('#dialogForm').modal('hide');
                    appDialog.success();
                    initData();
                });
            } else {
                $http.post('/api/core/menus', $scope.formModel).then(function() {
                    $('#dialogForm').modal('hide');
                    appDialog.success();
                    initData();
                });
            }
        };

        $scope.toggleVisible = function(menu) {
            $http.put('/api/core/menus/' + menu.id + '/toggleVisible').then(function() {
                appDialog.success();
                initData();
            });
        };

        $scope.remove = function(menu) {
            appDialog.confirmDeletion(function() {
                $http.delete('/api/core/menus/' + menu.id).then(function() {
                    appDialog.success();
                    initData();
                });
            });
        };

        initData();
    }]
);