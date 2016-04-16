/*
 * Copyright 2016 Studentmediene i Trondheim AS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Created by Kristian on 12/02/14.
 */

'use strict';

angular.module('mdbApp.controllers', []);
angular.module('mdbApp.filters', []);
angular.module('mdbApp.services', []);
angular.module('mdbApp.directives', []);


var app = angular.module('mdbApp',
    [
        "ngResource",
        "ngRoute",
        "ui.bootstrap",
        "smart-table",
        "mdbApp.controllers",
        "mdbApp.services",
        'mdbApp.directives'
    ]);

/*app.filter('startFrom', function() {
    return function(input, start) {
        console.log("FILTERING");
        start = +start; //parse to int
        return input.slice(start);
    };
});*/


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





