angular.module('app')
    .directive('uiWaitOn',
    /* @ngInject */
    function ($compile) {
        return {
            restrict: 'A',
            link: function(scope, elem, attr) {
	            var children = $compile(elem.contents())(scope);
                scope.$watch(attr.uiWaitOn, function (value) {
                    elem.attr('disabled', value);
                    if (value) {
                        elem.prepend('<span class="in-process"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></span>');
                        angular.forEach(children, function(child) {
                            angular.element(child).css('visibility', 'hidden');
                        });
                    } else {
                        angular.element(document.querySelector('.in-process')).remove();
                        angular.forEach(children, function(child) {
                            angular.element(child).css('visibility', 'visible');
                            elem.append(child);
                        });
                    }
                });
            },
        };
    });