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
 * Created by Kristian on 31/08/14.
 */

app.controller("EditPwCtrl", function($scope, $resource, $http) {
    $scope.userList =  {
        "username":"perknu",
        "username":"trineline",
        "username":"kristhus",
        "username":"anlu",
        "username":"test1",
        "username":"person",
        "username":"olano",
        "username":"karino",
        "username":"ottonorm"
    };

    console.log('test2');
    $scope.convert = function() {
        console.log('test');
        angular.forEach($scope.userList, function(user) {
        console.log(user);
        });
    }

});