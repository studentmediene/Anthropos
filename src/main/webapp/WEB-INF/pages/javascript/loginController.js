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

    $scope.resetPassword = function() {
        $scope.modalInstance = $modal.open({
            templateUrl: 'resetPw.html',
            controller: 'LoginCtrl'
        });
        $scope.modalInstance.result.then(function (confirm) {
            $scope.selected = 0;
        }, function () {});
    }

    $scope.cancel = function () {
        $scope.modalInstance.$dismiss('cancel');
    };

});