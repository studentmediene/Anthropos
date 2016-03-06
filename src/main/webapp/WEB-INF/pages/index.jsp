<!DOCTYPE html>
<html class="no-js" >
<head>
    <title>Medlemsdatabase</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset = "UTF-8">

    <script src='https://ajax.googleapis.com/ajax/libs/angularjs/1.2.15/angular.min.js' type="text/javascript"></script>
    <script src='https://ajax.googleapis.com/ajax/libs/angularjs/1.2.12/angular-route.js' type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-smart-table/2.1.7/smart-table.js" type="text/javascript"></script>
    <script src="javascript/angular-resource.min.js" type="text/javascript"></script>
    <script src="javascript/app.js" type = "text/javascript"></script>
    <script src="javascript/personController.js" type = "text/javascript"></script>
    <script src="javascript/userController.js" type = "text/javascript"></script>
    <script src="javascript/locationCtrl.js" type = "text/javascript"></script>
    <script src="javascript/testCtrl.js" type = "text/javascript"></script>
    <script src="javascript/loginController.js" type = "text/javascript"></script>
    <script src="javascript/registerController.js" type = "text/javascript"></script>
    <script src="javascript/maincontroller.js" type = "text/javascript"></script>
    <script src="javascript/httpInterceptor.js" type = "text/javascript"></script>


    <script src="javascript/ui-bootstrap-tpls-0.11.2.min.js" type = "text/javascript"></script>

    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/lodash.js/0.10.0/lodash.min.js"></script>



    <%--<link rel="stylesheet" href="css/bootstrap-theme.css">--%>
    <link rel="icon" type="image/png" href="favicon.png">

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/bootstrap.css">



</head>
<body ng-app="mdbApp" >
<div id="wrap" >
<div class="banner" ng-controller="mainCtrl">
    <span class="header-title largeScreen"> Medlemsdatabase for Studentmediene </span>
    <img href="#"  src="../img/SM-logo.png" class="banner-icon" style="float:left" ng-mouseover="count=count+1" ng-init="count=0">
    <span class="header-title mediumScreen">MDB</span>
    <div class="pull-right" style="margin-top: 30px;">
      <!--  <button class="btn" ng-click="ting=!ting">{{ting}}</button> -->
        <a href="#/login" data-ng-click="logout()"> <b>Logg ut </b></a>

    </div>
    <!-- <a href="https://www.youtube.com/watch?v=PGNiXGX2nLU" ng-show="easter(count)"><b><div style="color: black;  ">{{egg}}</div></b></a>
     -->
</div>


<div ng-view style="margin-bottom: 100px;"></div>
    <div class="panel-footer" style=" position: relative; left: 0; right: 0; padding-top: 60px; bottom:0; text-align: center;">Copyright © 2016 Studentmediene i Trondheim AS. All Rights Reserved </div> <!-- TODO: Make real footer -->
</div>

</body>

</html>