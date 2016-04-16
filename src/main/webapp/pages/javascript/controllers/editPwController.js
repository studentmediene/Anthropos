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