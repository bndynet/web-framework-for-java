app.directive('uiMenuMessages', ['$http', '$translate', function($http, $translate) {

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
        templateUrl: '/static/apps/admin/lib/directives/ui-menu-messages.html',
        link: function(scope, element, attrs) {
        }
    };
}]);