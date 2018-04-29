angular.module('app')
    .directive('uiUpload',
    /* @ngInject */
    function(FileUploader, appDialog) {
        return {
            restrict: 'E',
            scope: {
                url: '@',
                multiple: '@?',
                label: '@?',
                onItemSuccess: '&?',
            },
            templateUrl: getPath('/static/apps/admin/lib/directives/ui-upload.html'),
            compile: function(elem, attrs) {
                return {
                    pre: function(scope, element, attrs) {
                        scope.uploader = new FileUploader({
                            url: getPath(scope.url),
                            removeAfterUpload: true,
                            onSuccessItem: function(item, response, status, headers) {
                                if (scope.onItemSuccess) {
                                    scope.onItemSuccess({
                                        file: item,
                                        result: response,
                                        });
                                }
                            },
                            onErrorItem: function(item, response, status) {
                                appDialog.error(item.file.name, response.message);
                            }
                        });
                        if (attrs.multiple || attrs.multiple === '') {
                            element.find('[type=file]').attr('multiple', attrs.multiple);
                        }
                        attrs.$observe('reloadOn', function(data) {
                            scope.uploader.clearQueue();
                        }, true);
                    },
                };
            },
            link: function() {
                console.log('LINK');
            },
        };
    });