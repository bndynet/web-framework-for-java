angular.module('app')
    .controller('PageCtrl',
        /* @ngInject */
        function($scope, $stateParams, appService, appDialog) {
            if (!$stateParams.obj) {
                var error = 'You should specify the linkParams like {id: channelId}.';
                appDialog.error(error);
                throw error;
            }
            var params = $stateParams.obj || {};

            $scope.viewModel = { };

            function initData() {
                appDialog.loading();
                appService.ajaxGet('/api/cms/pages/' + params.id).then(function(d) {
                    $scope.viewModel = d;
                }).finally(function() {
                    appDialog.loading(false);
                });
            }

            initData();

            $scope.save = function() {
                appDialog.loading();
                appService.ajaxSave('/api/cms/pages', $scope.viewModel).then(function(d) {
                    $scope.viewModel = d;
                    appDialog.loading(false);
                    appDialog.success();
                });
            };
        });