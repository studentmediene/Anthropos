/**
 * Created by Kristian on 23/02/14.
 */

app.controller("LocationCtrl", function($scope) {
   $scope.go = function( path ) {
        console.log("hei");
        $location.path(path);
    };
});