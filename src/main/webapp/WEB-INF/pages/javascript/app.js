/**
 * Created by Kristian on 12/02/14.
 */
var app = angular.module('mdbApp',["ngResource", "ngRoute"]);

    app.config(['$routeProvider',
        function($routeProvider) {
            $routeProvider
                .when('/user', {
                    templateUrl: 'views/user.html',
                    controller: 'UserCtrl'
                })
                .when('/', {
                    templateUrl: 'views/person.html',
                    controller: 'PersonCtrl'
                })
                .otherwise({
                    redirectTo: '/'
                });
        }
    ]);