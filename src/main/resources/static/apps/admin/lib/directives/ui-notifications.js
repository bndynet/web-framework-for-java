app.directive('uiNotifications', ['$http', '$translate', function($http, $translate) {

    return {
        restrict: 'E',
        replace: true,
        scope: {
            theme: '@theme',
            menuIcon: '@menuIcon',
            itemClick: '&itemClick',
            moreText: '@moreText',
            moreClick: '&moreClick',
            data: '=data'
        },
        templateUrl: getPath('/static/apps/admin/lib/directives/ui-notifications.html'),
        link: function(scope, element, attrs) {
        }
    };
}]);