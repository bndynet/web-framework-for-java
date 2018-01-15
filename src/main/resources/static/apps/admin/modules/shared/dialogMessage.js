function DialogMessageCtrl($scope, $translate, $mdDialog, vm, options) {
    $scope.vm = vm;
    $scope.options = options;

    $scope.closeDialog = function() {
        $mdDialog.cancel();
    };
};
