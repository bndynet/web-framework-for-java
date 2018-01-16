app.directive('uiNoData', ['$translate', function($translate){
    return {
        restrict: 'E',
        replace: true,
        scope: {
            message: '@'
        },
        templateUrl: '/static/apps/admin/lib/directives/ui-no-data.html',
    };
}]);