angular.module('app')
    .controller('ArticlesCtrl',
        /* @ngInject */
        function($scope, $timeout, $stateParams, appService, appDialog) {

            if (!$stateParams.obj || !$stateParams.obj.id) {
                var error = 'You should specify the linkParams like {id: channelId}.';
                appDialog.error(error);
                throw error;
            }

            var channelId = $stateParams.obj.id;
            $scope.pager = {};
            $scope.channel = null;
            $scope.isSaving = false;

            function initData(page) {
                if (!page) {
                    page = 1;
                }
                if (!$scope.channel) {
                    appService.ajaxGet('/api/cms/channels/' + channelId).then(function(d){
                        $scope.channel = d;
                    });
                }

                var url = '/api/cms/articles/search?channel=' + $stateParams.obj.id + '&page=' + (page - 1);
                if ($scope.searchKeywords) {
                    url += '&keywords=' + $scope.searchKeywords;
                }
                appService.ajaxGet(url).then(function(d) {
                    $scope.pager = d;

                    if ($scope.searchKeywords) {
                        $timeout(function() {
                            $('table .title').highlightText($scope.searchKeywords);
                        }, 200);
                    }
                });
            };

            var dialogForm = appDialog.getModal('dialogForm');
            $scope.add = function() {
                dialogForm.show();
            };
            $scope.edit = function(item) {
                if (!item) {
                    item = {};
                }
                angular.resetForm($scope.form);
                $scope.formModel = angular.copy(item);
                dialogForm.show();
            };
            $scope.save = function() {
                $scope.formModel.channelId = $scope.channel.id;
                $scope.isSaving = true;
                appDialog.loading();
                appService.ajaxSave('/api/cms/articles', $scope.formModel).then(function() {
                    initData();
                    appDialog.success();
                }).finally(function() {
                    $scope.isSaving = false;
                    dialogForm.close();
                    appDialog.loading(false);
                });
            };
            $scope.remove = function(item) {
                appDialog.confirmDeletion(function(){
                    appService.ajaxDelete('/api/cms/articles/' + item.id).then(function() {
                        $scope.pager.content.splice($scope.pager.content.indexOf(item), 1)
                        appDialog.success();
                        $scope.pageUsers($scope.pager.currentPage);
                    });
                });
            };

            $scope.search = function(keywords) {
                $scope.searchKeywords = keywords;
                initData(1);
            };

            $scope.cancelSearch = function() {
                $scope.searchKeywords = null;
                initData(1);
            };

            $scope.removeAttachment = function(item) {
                appDialog.confirmDeletion(function() {
                    $scope.formModel.attachments.splice($scope.formModel.attachments.indexOf(item), 1);
                });
            };

            $scope.uploaded = function(result) {
                if (!$scope.formModel.attachments) $scope.formModel.attachments = [];
                $scope.formModel.attachments.push(result);
            };

            initData(1);
        }
    );
