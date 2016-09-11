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
app.controller("mainCtrl", function($scope, $location, $rootScope, $resource, $http, PersonService) {




    $scope.ting=true;
    $scope.easter = function(count) {
        if (count >= 10) {

            $scope.egg = "X";
            return true;
        }
    };


    if($scope.detectMobile){console.log("is a mobile")} /* TODO make something incredible for mobile platforms or some shit like that */
    else {console.log("is desktop")}

    $scope.detectMobile = function() {
        if( navigator.userAgent.match(/Android/i)
            || navigator.userAgent.match(/webOS/i)
            || navigator.userAgent.match(/iPhone/i)
            || navigator.userAgent.match(/iPad/i)
            || navigator.userAgent.match(/iPod/i)
            || navigator.userAgent.match(/BlackBerry/i)
            || navigator.userAgent.match(/Windows Phone/i)
            ){
            return true;
        }
        else {
            return false;
        }
    };

    $scope.logout = function() {
        window.location.href = "/";

        return $http({
            url : '/api/auth/logout'

        });
    };

    PersonService.getCurrentUser().success(function(user) {
        console.log(user);
        $scope.user = user;
    });



    $scope.profile = function(){
            localStorage.setItem("currentPage", 2);
            $location.url("/user/" + $scope.user.uidNumber);


    }

});