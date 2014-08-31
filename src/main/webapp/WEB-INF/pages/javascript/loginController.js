/**
 * Created by Kristian on 23/04/14.
 */
app.controller("LoginCtrl", function($scope, $modal) {

    $scope.login = function() {
        var userName = document.getElementById('username').value;
        var password = document.getElementById('pass').value;

        console.log(userName);
        console.log(password);

        $http.post('login', '{ "userName": "'+userName+'"}');
    }

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
            $scope.modalInstance = undefined  // <--- This fixes
        });
    }

    $scope.cancel = function () {
        $scope.modalInstance.$dismiss('cancel');
    };

});