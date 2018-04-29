angular.module('app')
    .controller('ChannelsCtrl',
        /* @ngInject */
        function($scope, appService, appDialog, $timeout, $filter, $state) {
            $scope.boTypes = [];
            $scope.sameTypeChannels = [];
            $scope.exchangeTitle = null;
            $scope.isSaving = false;

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

            var dialogForm = appDialog.getModal('dialogEdit');
            var dialogExchange = appDialog.getModal('dialogExchange');
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
            $scope.view = function(item) {
                switch(item.boType.name) {
                    case 'Page':
                        $state.go('cms-page', {obj: {id: item.id, channel: item}});
                        break;
                    case 'Article':
                        $state.go('cms-articles', {obj: {id: item.id, channel: item}});
                        break;
                    case 'Resource':
                        $state.go('cms-resources', {obj: {id: item.id, channel: item}});
                        break;
                }
            };
            $scope.exchange = function(item) {
                $scope.formModel = angular.copy(item);
                $scope.exchangeTitle = $filter('translate')('admin.modules.cms.channels.exchangeTitle', [$scope.formModel.name]);
                appService.ajaxGet('/api/cms/channels/sameTypeChannels?id=' + item.id).then(function(d) {
                    $scope.sameTypeChannels = d;
                });
                dialogExchange.show();
            };
            $scope.exchangeSave = function() {
                if ($scope.formModel && $scope.formModel.transferTo) {
                    appService.ajaxPut('/api/cms/channels/' + $scope.formModel.id + '/transfer?to=' + $scope.formModel.transferTo.id).then(function() {
                        initData();
                        dialogExchange.close();
                        appDialog.success();
                    });
                }
            };
            $scope.edit = function(item) {
                angular.resetForm($scope.form);
                $scope.formModel = angular.copy(item);
                dialogForm.show();
            };
            $scope.save = function() {
                $scope.isSaving = true;
                appDialog.loading();
                appService.ajaxSave('/api/cms/channels', $scope.formModel).then(function() {
                    initData();
                    appDialog.success();
                    dialogForm.close();
                }).finally(function() {
                    $scope.isSaving = false;
                    appDialog.loading(false);
                });
            }
            $scope.toggleVisible = function(item) {
                appService.ajaxPut('/api/cms/channels/' + item.id + '/toggleVisible').then(function() {
                    initData();
                    appDialog.success();
                });
            };
            $scope.remove = function(item, force) {
                appDialog.confirmDeletion(function() {
                    url = '/api/cms/channels/' + item.id;
                    if (force) {
                        url = '/api/cms/channels/' + item.id + '/forceDelete';
                    }
                    appService.ajaxDelete(url).then(function(d) {
                        initData();
                        appDialog.success();
                    });
                });
            };
            $scope.forceRemove = function() {
                $scope.remove($scope.formModel, true);
            };


            initData();
        }
    );