/**
 * Created by Kristian on 12/02/14.
 */
app.controller("mainCtrl", function($scope, $resource, $http) {


    $scope.ting=true;


    $scope.easter = function(count) {
        if (count >= 10) {

            $scope.egg = "X";
            return true;
        }
    };

});