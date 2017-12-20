app.controller('ApplicationsCtrl', [
    '$scope', 'appDialog', '$http',
    function($scope, appDialog, $http) {
        $scope.data = [];
        $scope.init = function () {
            $http.get('/api/app/clients').then(function(res) {
                $scope.data = res.data;

                $http.get('/api/app/users/clients').then(function(res){
                    for(var idx in res.data) {
                        for(var idx1 in $scope.data) {
                            if(res.data[idx].clientId === $scope.data[idx1].clientId) {
                                $scope.data[idx1].isAuthorized = true;
                            }
                        }
                    }
                });
            });
        };
        $scope.add = function() {
            $scope.formModel = null;
            $('#dialogForm').modal('show');
        };
        $scope.edit = function(item) {
            $scope.formModel = angular.copy(item);
            $('#dialogForm').modal('show');
        };
        $scope.save = function() {
            if ($scope.formModel.id) {
                $http.put('/api/app/clients/' + $scope.formModel.id, $scope.formModel).then(function(res){
                    $scope.formModel = null;
                    $('#dialogForm').modal('hide');
                    $scope.init();
                });
            } else {
                $http.post('/api/app/clients', $scope.formModel).then(function(res){
                    $scope.formModel = null;
                    $('#dialogForm').modal('hide');
                    $scope.init();
                });
            }
        };
        $scope.remove = function(item) {
            appDialog.confirmDeletion(function(){
                $http.delete('/api/app/clients/' + item.id).then(function(){
                    $scope.data.splice($scope.data.indexOf(item), 1);
                    appDialog.success();
                }, function() {
                    appDialog.error();
                });
            });
        };
        $scope.unauthorize = function(item) {
            appDialog.confirm('Cancel Authorization', 'Are you sure to cancel authorization for this application?', function() {
                $http.put('/api/app/users/removeapp?clientId=' + item.clientId).then(function(res) {
                    item.isAuthorized = false;
                });
            });
        };

        $scope.init();
} ]);