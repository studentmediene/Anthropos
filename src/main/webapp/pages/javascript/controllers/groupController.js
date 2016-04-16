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
 * Created by Kristian on 19/02/14.
 */

app.controller('groupCtrl', [$scope, function($scope) {


    $scope.selectedGroup = [];
    $scope.setSelectedPerson = function () {
        var groupName = this.person.groups;
        if (_.contains($scope.selectedGroup, groupName)) {
            $scope.selectedGroup = _.without($scope.selectedGroup, groupName);
        } else {
            $scope.selectedGroup.push(groupName);
        }
        return false;
    };

    $scope.isChecked = function (groupName) {
        if (_.contains($scope.selectedGroup, groupName)) {
            return 'icon-ok pull-right';
        }
        return false;
    };

    $scope.checkAll = function () {
        $scope.selectedGroup = _.pluck($scope.persons, 'groupName');
        console.log("HEI");
    }

}]);



angular.module('App.filters', []).filter('groupFilter:selectedGroup', [function () {
    return function(persons, selectedGroup) {
        if (!angular.isUndefined(persons) && !angular.isUndefined(selectedGroup) && selectedGroup.length > 0) {
            var tmpPersons = [];
            angular.forEach(selectedGroup, function(groupName) {
                angular.forEach(persons, function(person) {
                    if (angular.equals(person.group, groupName)) {
                        tmpPersons.push(person);
                    }
                });
            });
            return tmpPersons;
        } else {
            return persons;
        }
    };
}]);