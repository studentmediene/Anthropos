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