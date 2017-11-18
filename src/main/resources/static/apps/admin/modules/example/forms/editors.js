app.controller('ExampleFormsEditorsCtrl', ['$timeout', function($timeout){
    $timeout(function() {
        CKEDITOR.replace('editor1')
        $('.textarea').wysihtml5()
    }, 0);
}]);