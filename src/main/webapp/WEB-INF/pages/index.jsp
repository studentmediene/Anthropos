<!DOCTYPE html>
<html class="no-js">
<head>
    <title>Medlemsdatabase</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset = "UTF-8">

    <script src='https://ajax.googleapis.com/ajax/libs/angularjs/1.2.15/angular.min.js' type="text/javascript"></script>
    <script src='//ajax.googleapis.com/ajax/libs/angularjs/1.2.12/angular-route.js' type="text/javascript"></script>
    <script src="javascript/angular-resource.min.js" type="text/javascript"></script>
    <script src="javascript/app.js" type = "text/javascript"></script>
    <script src="javascript/personController.js" type = "text/javascript"></script>
    <script src="javascript/userController.js" type = "text/javascript"></script>
    <script src="javascript/locationCtrl.js" type = "text/javascript"></script>
    <script src="javascript/testCtrl.js" type = "text/javascript"></script>
    <script src="javascript/loginController.js" type = "text/javascript"></script>
    <script src="javascript/registerController.js" type = "text/javascript"></script>
    <script src="javascript/maincontroller.js" type = "text/javascript"></script>

    <script src="javascript/ui-bootstrap-tpls-0.11.2.min.js" type = "text/javascript"></script>

    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/lodash.js/0.10.0/lodash.min.js"></script>

    <link rel="shortcut icon" type="image/png" href="favicon.png">

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/bootstrap.css">


</head>
<body  ng-app="mdbApp">
<div class="banner" ng-controller="mainCtrl">
    <img src="../img/SM-logo.png" class="banner-icon" href="#" ng-mouseover="count=count+1" ng-init="count=0">
    <span class="header-title largeScreen"> Medlemsdatabase for Studentmediene </span>
    <span class="header-title mediumScreen">MDB</span>
    <div class="pull-right">
      <!--  <button class="btn" ng-click="ting=!ting">{{ting}}</button> -->
        <a href="#"> <b>Medlemmer</b></a>
        <a href="#/user/1"> <b>Behandle bruker </b></a>  <!-- TODO go to the id retrievable from user credentials -->
        <a href="#/login"> <b>Logg inn </b></a>
        <a href="#/register" style="border-left:1px solid #FFF;height: 30px" ng-show="ting"> <b>Opprett bruker </b></a>
    </div>
    <!-- <a href="https://www.youtube.com/watch?v=PGNiXGX2nLU" ng-show="easter(count)"><b><div style="color: black;  ">{{egg}}</div></b></a>
     -->
</div>


<div ng-view></div>
</body>
</html>