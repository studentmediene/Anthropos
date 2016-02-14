/**
 * Created by Kristian on 12/02/14.
 */
var app = angular.module('mdbApp',["ngResource", "ngRoute","ui.bootstrap","smart-table"]);

app.filter('startFrom', function() {
    return function(input, start) {
        console.log("FILTERING");
        start = +start; //parse to int
        return input.slice(start);
    };
});

    app.config(['$routeProvider',
        function($routeProvider) {
            $routeProvider
                .when('/user/:id', {
                    templateUrl: 'views/user.html',
                    controller: 'UserCtrl'
                })
                .when('/', {
                    templateUrl: 'views/members.html',
                    controller: 'PersonCtrl'
                })
                .when('/login', {
                    templateUrl: 'views/login.html',
                    controller: 'LoginCtrl'
                 })
                .when('/memberPw', {
                    templateUrl: 'views/editMemberPW.html',
                    controller: 'EditPwCtrl'
                })
                .when('/register', {
                    templateUrl: 'views/newUser.html',
                    controller: 'RegisterCtrl'
                })
                .otherwise({
                    templateUrl: 'views/404.html',
                    controller: 'pageNotFoundCtrl'
                });
        }
    ]).
    config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push('HttpInterceptor');
    }]);

angular.module('mdbApp')
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