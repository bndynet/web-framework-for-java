angular.module('app')
    .controller('UserProfileCtrl',
    /* @ngInject */
    function($scope, appService, appDialog, $timeout, Upload) {
        appService.ajaxGet('/api/core/users/me').then(function(res) {
            $scope.user = res;
        });
        
        function initData() {
        	appService.ajaxGet('/api/core/users/profile').then(function(res) {
        		if (res) {
        			$scope.viewModel = res;
        		} else {
        			$scope.viewModel = {};
        		}

        		if ($scope.viewModel.birthday) {
        			$scope.viewModel.birthday = moment($scope.viewModel.birthday).format('YYYY-MM-DD');
        		}

        		$timeout(function(){
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
        }
        
        initData();
       
        $scope.uploadAvatar = function(file, errFiles) {
            $scope.avatarFile = file;
            $scope.errFile = errFiles && errFiles[0];
            if (file) {
                file.upload = Upload.upload({
                   url: getPath('/api/core/users/updateAvatar'),
                   data: {file: file}
                });

                file.upload.then(function (response) {
                   $timeout(function () {
                       file.result = response.data;
                       $scope.user.avatar = file.result.url;
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
            $scope.isUpdatingProfile = true;
            if ($scope.viewModel.id) {
                appService.ajaxPut('/api/core/userProfiles/' + $scope.viewModel.id, $scope.viewModel).then(function(res) {
                	initData();
                    appDialog.success();
                }, function() {
                    appDialog.error();
                }).finally(function() {
                    $scope.isUpdatingProfile = false;
                });
            } else {
                appService.ajaxPost('/api/core/userProfiles', $scope.viewModel).then(function(res) {
                	initData();
                    appDialog.success();
                }, function() {
                    appDialog.error();
                }).finally(function() {
                    $scope.isUpdatingProfile = false;
                });
            }
        };

        $scope.changePassword = function() {
            if ($scope.pwdModel.oldPassword && $scope.pwdModel.newPassword) {
                $scope.isChangingPassword = true;
                appService.ajaxPost('/api/core/users/changePassword', {
                    oldPassword: $scope.pwdModel.oldPassword,
                    newPassword: $scope.pwdModel.newPassword
                }).then(function() {
                    $scope.pwdModel = null;
                    $scope.pwdForm.$setPristine();
                    appDialog.success();
                }).finally(function() {
                    $scope.isChangingPassword = false;
                });
            }
        };
    });