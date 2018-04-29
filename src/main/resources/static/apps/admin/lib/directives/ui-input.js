app.directive('uiInput', ['$translate', function($translate){
    return {
        restrict: 'E',
        replace: true,
        require: ['ngModel'],
        scope: {
            name: '@',
            label: '@',
            type: '@',
            placeholder: '@',
            tips: '@',
            prefix: '@',
            suffix: '@',
            model: '=ngModel',
            required: '=?',
            hasSuccess: '=?',
            successMessage: '@',
            hasWarning: '=?',
            warningMessage: '@',
            hasError: '=?',
            errorMessage: '@',
        },
        templateUrl: getPath('/static/apps/admin/lib/directives/ui-input.html'),
        link: function(scope, elem, attrs) {
            if (scope.required) {
                if (!scope.errorMessage) {
                    scope.errorMessage = 'common.msgRequired';
                }
            }

            if (scope.prefix && scope.prefix.indexOf('class:') === 0) {
                scope.prefixIcon = scope.prefix.replace('class:', '').trim();
            }
            if (scope.suffix && scope.suffix.indexOf('class:') === 0) {
                scope.suffixIcon = scope.suffix.replace('class:', '').trim();
            }

            scope.isFormInvalid = function () {
                return scope.fieldForm[scope.name].$dirty && scope.fieldForm[scope.name].$invalid;
            };
        }
    };
}]);