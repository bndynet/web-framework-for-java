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
                   url: '/api/core/users/upload',
                   data: {file: file}
                });

                file.upload.then(function (response) {
                   $timeout(function () {
                       file.result = response.data;
                       $http.get('/api/core/users/updateavatar?name=' + file.result.relativePath).then(function() {
                           $scope.user.avatar = file.result.relativePath;
                           appDialog.success();
                       });
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