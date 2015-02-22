/**
 * Created by Kristian on 12/02/14.
 */
var app = angular.module('mdbApp',["ngResource", "ngRoute","ui.bootstrap"]);

    app.config(['$routeProvider',
        function($routeProvider) {
            $routeProvider
                .when('/api/user/:id', {
                    templateUrl: 'views/user.html',
                    controller: 'UserCtrl'
                })
                .when('/api/', {
                    templateUrl: 'views/members.html',
                    controller: 'PersonCtrl'
                })
                .when('/login', {
                    templateUrl: 'views/login.html',
                    controller: 'LoginCtrl'
                 })
                .when('/api/memberPw', {
                    templateUrl: 'views/editMemberPW.html',
                    controller: 'EditPwCtrl'
                })
                .when('/api/register', {
                    templateUrl: 'views/newUser.html',
                    controller: 'RegisterCtrl'
                })
                .otherwise({
                    templateUrl: 'views/404.html',
                    controller: 'pageNotFoundCtrl'
                });
        }
    ]);