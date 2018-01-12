app.controller('UserProfileCtrl', [ '$scope', 'appDialog', '$http', '$timeout', 'Upload',
    function($scope, appDialog, $http, $timeout, Upload) {
        $http.get('/api/core/users/me').then(function(res) {
            $scope.user = res.data;
        });
        $http.get('/api/core/users/profile').then(function(res) {
            if (res.data) {
                $scope.viewModel = res.data;
            } else {
                $scope.viewModel = {};
            }

            if ($scope.viewModel.birthday) {
                $scope.viewModel.birthday = moment($scope.viewModel.birthday).format('YYYY-MM-DD');
            }

            $timeout(function(){
                initUI();
                $('input[name=gender]').each(function(){
                    $(this).on('ifChecked', function(event) {
                        $scope.viewModel.gender = event.target.value;
                    });
                    if ($(this).val() == $scope.viewModel.gender) {
                        $(this).iCheck('check');
                    }
                })
            });
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

        $scope.updateProfile = function() {
            if ($scope.viewModel.id) {
                $http.put('/api/core/userProfiles/' + $scope.viewModel.id, $scope.viewModel).then(function(res) {
                    appDialog.success();
                }, function() {
                    appDialog.error();
                });
            } else {
                $http.post('/api/core/userProfiles', $scope.viewModel).then(function(res) {
                    appDialog.success();
                }, function() {
                    appDialog.error();
                });
            }
        };

        $scope.changePassword = function() {
            if ($scope.pwdModel.oldPassword && $scope.pwdModel.newPassword) {
                $http.post('/api/core/users/changePassword', {
                    oldPassword: $scope.pwdModel.oldPassword,
                    newPassword: $scope.pwdModel.newPassword
                }).then(function() {
                    $scope.pwdModel = null;
                    $scope.pwdForm.$setPristine();
                    appDialog.success();
                });
            }
        };
    }
]);