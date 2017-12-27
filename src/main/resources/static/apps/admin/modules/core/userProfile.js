app.controller('UserProfileCtrl', [ '$scope', 'appDialog', '$http',
    function($scope, appDialog, $http) {
        $http.get('/api/core/users/profile').then(function(res) {
            $scope.viewModel = res;
        });
    }
]);