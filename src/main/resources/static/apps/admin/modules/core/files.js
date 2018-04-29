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

        $scope.uploaded = function(result) {
            $scope.files.splice(0, 0, result);
        };

        $scope.getFileIcon = function(file) {
            switch(file.type) {
                case 'IMAGE':
                    return getPath(file.url);
                default:
                    return getPath('/static/assets/img/fileTypes/' + file.extName + '.png');
            }

            return getPath('/static/assets/img/fileTypes/_blank.png');
        };

        $scope.remove = function(file) {
            appDialog.confirmDeletion(function() {
                appService.ajaxDelete('/api/core/files/' + file.id).then(function() {
                    $scope.files.splice($scope.files.indexOf(file), 1);
                    appDialog.success();
                });
            });
        };

        $scope.copied = function(file) {
            appDialog.success('common.msgCopied');
        };

        $scope.search = function(keywords) {
            if (keywords) {
                $scope.keywords = keywords;
            }
        };

        $scope.cancelSearch = function() {
            $scope.keywords = '';
        };

        initData();
    });