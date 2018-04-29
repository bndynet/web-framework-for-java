angular.module('app')
    .controller('ApplicationsCtrl',
    /* @ngInject */
    function($scope, appService, appDialog) {
        $scope.data = [];
        $scope.init = function () {
            appService.ajaxGet('/api/core/clients').then(function(res) {
                $scope.data = res;

                appService.ajaxGet('/api/core/clients/myapprovals').then(function(res){
                    for(var idx in $scope.data) {
                        $scope.data[idx].approvals = [];
                        for(var idx1 in res) {
                            if(res[idx1].clientId === $scope.data[idx].details.clientId) {
                                $scope.data[idx].approvals.push(res[idx1]);
                            }
                        }
                    }
                });
            });
        };
        $scope.add = function() {
            angular.resetForm($scope.form);
            $scope.formModel = null;
            $('#dialogForm').modal('show');
        };
        $scope.edit = function(item) {
            angular.resetForm($scope.form);
            $scope.formModel = angular.copy(item);
            $('#dialogForm').modal('show');
        };
        $scope.save = function() {
            if ($scope.formModel.id) {
                appService.ajaxPut('/api/core/clients/' + $scope.formModel.id, $scope.formModel).then(function(res){
                    $scope.formModel = null;
                    $('#dialogForm').modal('hide');
                    $scope.init();
                });
            } else {
                appService.ajaxPost('/api/core/clients', $scope.formModel).then(function(res){
                    $scope.formModel = null;
                    $('#dialogForm').modal('hide');
                    $scope.init();
                });
            }
        };
        $scope.remove = function(item) {
            appDialog.confirmDeletion(function(){
                appService.ajaxDelete('/api/core/clients/' + item.id).then(function(){
                    $scope.data.splice($scope.data.indexOf(item), 1);
                    appDialog.success();
                }, function() {
                    appDialog.error();
                });
            });
        };

        $scope.init();
    });