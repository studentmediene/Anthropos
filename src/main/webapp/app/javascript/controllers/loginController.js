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
 * Created by Kristian on 23/04/14.
 */
app.controller("LoginCtrl", function($scope, $location, $rootScope, $modal, $http, LoginService) {

    $scope.login = function() {

        var username = document.getElementById('username').value;
        var password = document.getElementById('pass').value;

        $scope.errorDuringLogin = "";

        var credentials = {
            "username":username,
            "password":password
        };

        /*return $http({
            method : 'POST',
            data : credentials,
            contentType: 'application/json',
            url : '/api/auth/login'
        })*/
        LoginService.login(credentials).success(function(data, status, headers, config) {

            window.location.href="/";

            //You logged inn successfully

        }).error(function(data, status, headers, config) {
            $scope.errorDuringLogin = "Feil brukernavn eller passord";
            //Display error message during login
        });



    };



    $scope.modalInstance;
    $scope.resetPassword = function() {
        modalInstance = $modal.open({
            templateUrl: 'resetPw.html',
            controller: 'LoginCtrl'
        });
        $scope.modalInstance.result.then(function() {
            console.log('Success');
        }, function() {
            console.log('Cancelled');
        })['finally'](function(){
            $scope.modalInstance = undefined;  // <--- This fixes
        });
    };

    $scope.cancel = function () {
        $scope.modalInstance.$dismiss('cancel');
    };

});

