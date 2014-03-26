/**
 * Created by Kristian on 12/02/14.
 */
app.controller("MainController", function($scope) {

    $scope.avdelinger = [
        {
            id: 0,
            avdeling: 'IT'
        },
        {
            id:1,
            avdeling:'Radio'
        },
        {
            id:1,
            avdeling: 'other'
        }
    ];
    $scope.newAvdeling = null;
    $scope.addNew = function(){
        if ($scope.newAvdeling != null && $scope.newAvdeling != "") {
            $scope.avdelinger.push({
                id: $scope.avdelinger.length,
                avdeling: $scope.newAvdeling,
            })
        }
    }
});