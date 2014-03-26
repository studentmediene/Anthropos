<!DOCTYPE html>
<html class="no-js">
<head>
    <title>Medlemsdatabase</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset = "UTF-8">


    <script src='https://ajax.googleapis.com/ajax/libs/angularjs/1.2.12/angular.min.js' type="text/javascript"></script>
    <script src='//ajax.googleapis.com/ajax/libs/angularjs/1.2.12/angular-route.js' type="text/javascript"></script>
    <script src="javascript/angular-resource.min.js" type="text/javascript"></script>
    <script src="javascript/app.js" type = "text/javascript"></script>
    <script src="javascript/personController.js" type = "text/javascript"></script>
    <script src="javascript/userController.js" type = "text/javascript"></script>
    <script src="javascript/locationCtrl.js" type = "text/javascript"></script>
    <script src="javascript/testCtrl.js" type = "text/javascript"></script>

    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/lodash.js/0.10.0/lodash.min.js"></script>

    <link rel="shortcut icon" href="favicon.png">

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/bootstrap.css">


</head>
<body  ng-app="mdbApp">
    <div class="banner" >
        <div>
            <div class="pull-right">
                <a href="#"> <b>Medlemmer</b></a>
                <a href="#/user"> <b>Behandle bruker </b></a>
                <a href="#/logout"> <b>Logg ut </b></a>
            </div>
        </div>
        <div class="container pagination-centered">
            <h2>Medlemsdatabase for Studentmediene</h2>
        </div>
        <a class="banner-icon" href="#" ng-mouseover="count=count+1" ng-init="count=0"></a>
            <!-- <a href="{{easter(count)}}"><b><div style="color: black;">{{egg}}</div></b></a> -->

    </div>


    <div ng-view></div>
</body>
</html>