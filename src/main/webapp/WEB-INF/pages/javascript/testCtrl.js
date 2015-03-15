/**
 * Created by Kristian on 23/02/14.
 */

app.controller("TestCtrl", function($scope) {

    $scope.allChecked = false;

    $scope.groupList = $scope.tmpGroupList;

    $scope.groupsSelected = []; /* Selected groups are id'ed with namestrings, since no groups can have the same name */
    $scope.shownPeople = [];
    $scope.isInGroup = function() {
        console.log("hei");
        for (person in $scope.persons) {
            console.log(person);
            for (group in person.groups) {

                if (_.contains($scope.groupsSelected, group)) {
                    $scope.shownPeople.push(person);
                    break;
                }
            }
        }
    };

    $scope.checkAll = function() {
        $scope.allChecked = !$scope.allChecked;
        console.log($scope.allChecked);
        $scope.shownPeople=$scope.persons;
        if($scope.allChecked) {
            return 'icon-ok pull-right'
        }
        else {
            return false;
        }
    };

    $scope.isChecked = function(group) {
        if (_.contains($scope.selectedGroup, group)) {
            return 'icon-ok pull-right';
        }
        return false;
    };


});