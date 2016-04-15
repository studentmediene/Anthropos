/**
 * Created by Wiker on 10/04/16.
 */
'use strict';
//Adds pagenumber to the paginatior
angular.module('mdbApp.directives', []);

angular.module('mdbApp.directives')
    .directive('pageSelect', function() {
        return {
            restrict: 'E',
            template: '<input type="text" class="select-page" ng-model="inputPage" ng-change="selectPage(inputPage)" style=" text-align: center; width: 30%; line-height: 0.1em; padding: 0;">',
            link: function(scope, element, attrs) {
                scope.$watch('currentPage', function(c) {
                    scope.inputPage = c;
                });
            }
        }
    });