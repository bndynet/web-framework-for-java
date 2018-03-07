angular.module('app')
    .controller('FilesCtrl',
    /* @ngInject */
    function($scope, appService, appDialog) {

        function initData() {
            appDialog.loading();
            appService.ajaxGet('/api/core/files').then(function(res) {
                $scope.files = res;
            }).finally(function() {
                appDialog.loading(false);
            });
        }

        initData();
    });