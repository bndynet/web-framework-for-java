app.controller('UserProfileCtrl', [ '$scope', 'appDialog', '$http', '$timeout', 'Upload',
    function($scope, appDialog, $http, $timeout, Upload) {
        $http.get('/api/core/users/me').then(function(res) {
            $scope.user = res.data;
        });
        $http.get('/api/core/users/profile').then(function(res) {
            $scope.viewModel = res;
        });

        $scope.uploadAvatar = function(file, errFiles) {
            $scope.avatarFile = file;
            $scope.errFile = errFiles && errFiles[0];
            if (file) {
                file.upload = Upload.upload({
                   url: '/api/core/users/updateAvatar',
                   data: {file: file}
                });

                file.upload.then(function (response) {
                   $timeout(function () {
                       file.result = response.data;
                       $scope.user.avatar = $scope.user.avatar.substring(0, $scope.user.avatar.lastIndexOf('/') + 1) + file.result.uuid;
                       // update all user avatar on page
                       $('img.user-avatar').attr('src', $scope.user.avatar);
                       appDialog.success();
                   });
                }, function (response) {
                   if (response.status > 0)
                       appDialog.error(response.data)
                }, function (evt) {
                   file.progress = Math.min(100, parseInt(100.0 *
                                            evt.loaded / evt.total));
                });
            }
        };

        $timeout(function(){
            initUI();
        });
    }
]);