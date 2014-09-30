/**
 * Created by Kristian on 12/02/14.
 */
app.controller("mainCtrl", function($scope, $window, $resource, $http) {


    $scope.ting=true;
    $scope.titleName = 'Medlemsdatabase for Studentmediene';
    $scope.easter = function(count) {
        if (count >= 10) {

            $scope.egg = "X";
            return true;
        }
    };

    if($scope.detectMobile){console.log("is a mobile")} /* TODO make something incredible for mobile platforms or some shit like that */
    else {console.log("is desktop")}

    $scope.$watch( function() {
        return $window.innerWidth;
    },
   $scope.title = function(width) {
        var lg = 1180;
        console.log(width);
        var md =730;
        if(width >= lg) {
            $scope.titleName = 'Medlemsdatabase for Studentmediene';
        }else if(width <lg && width >= md) {
            $scope.titleName = 'MDB SM';
        }
        else {$scope.titleName = '';}
    });

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
    }

});