angular.module('app')
    .controller('ChannelsCtrl',
        [ '$scope', 'appService', 'appDialog', '$http', '$timeout',
        function($scope, appService, appDialog, $http, $timeout) {
            function initData() {
                appDialog.loading();
                appService.ajaxGet('/api/cms/channels/query?all=true').then(function(d) {
                    $scope.channels = d;
                    appDialog.loading(false);
                });
            }

            $scope.add = function(item) {
                console.debug(item);
            };
            $scope.edit = function(item) {

            };
            $scope.toggleVisible = function(item) {
                appService.ajaxPut('/api/cms/channels/' + item.id + '/toggleVisible').then(function() {
                    initData();
                    appDialog.success();
                });
            };
            $scope.remove = function(item) {
                appDialog.confirmDeletion(function() {
                    appService.ajaxDelete('/api/cms/channels/' + item.id).then(function(d) {
                        initData();
                        appDialog.success();
                    });
                });
            };


            initData();
        }
    ]);