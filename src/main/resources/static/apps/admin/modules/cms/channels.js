angular.module('app')
    .controller('ChannelsCtrl',
        [ '$scope', 'appService', 'appDialog', '$http', '$timeout',
        function($scope, appService, appDialog, $http, $timeout) {
            $scope.boTypes = [];

            function initData() {
                appDialog.loading();
                appService.ajaxGet('/api/cms/channels/query?all=true').then(function(d) {
                    $scope.channels = d;
                    appDialog.loading(false);
                });
                appService.ajaxGet('/api/cms/config/boTypes').then(function(d) {
                    $scope.boTypes = d;
                });
            }

            var dialogForm = appDialog.getModal();
            $scope.add = function(item) {
                angular.resetForm($scope.form);
                if (item) {
                    $scope.formModel = {
                        visible: true,
                        path: item.path + item.id + '/',
                        parent: item,
                    };
                } else {
                    $scope.formModel = {
                        visible: true,
                    };
                }
                dialogForm.show();
            };
            $scope.edit = function(item) {
                angular.resetForm($scope.form);
                $scope.formModel = angular.copy(item);
                dialogForm.show();
            };
            $scope.save = function() {
                appService.ajaxSave('/api/cms/channels', $scope.formModel).then(function() {
                    initData();
                    appDialog.success();
                    dialogForm.close();
                });
            }
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