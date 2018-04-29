app.directive('uiPageHeader', ['$translate', function($translate){
    return {
        restrict: 'E',
        replace: true,
        scope: {
            title: '=',
            subtitle: '=',
            breadcrumbs: '='
        },
        templateUrl: getPath('/static/apps/admin/lib/directives/ui-page-header.html'),
        link: function(scope, element, attrs) {
        }
    };
}]);