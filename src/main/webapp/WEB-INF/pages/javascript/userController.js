/**
 * Created by Kristian on 05/03/14.
 */
app.controller("UserCtrl", function($scope, $resource, $http, $modal, $routeParams, $log) {

    var tmpObj = $resource("/"+$routeParams.id, {}, {
            get:{
                isArray:false,
                method:"GET"
            }
        }
    );
    $scope.allGroups = tmpObj.get();

    $scope.showPasswordError = false;
    var user = tmpObj.get(
        function() {
            $scope.firstName=user.firstName;
            $scope.lastName = user.lastName;
            $scope.mobile = user.mobile;
            $scope.email = user.email;
            $scope.canAddGroups = false;
            $scope.id = user.id;
            // TODO Find out the level of authority current user has

            var level = 3;// Using test var temporarily

            if(level == 3) { // Admin or logged in user's profile
                console.log("Admin")
                $scope.editProfile = true;
                $scope.canAddGroups = true;

                document.getElementById("name").disabled = false;
                document.getElementById("surname").disabled = false;

                $scope.authString = "ADMIN"
            }
            else if (level == 1) { // PL for this user
                console.log("The user's PL")
                $scope.editProfile = false;
                $scope.canAddGroups = true;
                document.getElementById("name").disabled = true;
                document.getElementById("surname").disabled = true;
                document.getElementById("email").disabled = true;
                document.getElementById("mobile").disabled = true;
                document.getElementById("pwBtn").style.display = "none";

                $scope.authString = "pl"
            }
            else if (level == 0){ // Ordinary user
                console.log("User")
                $scope.editProfile = false;
                $scope.canAddGroups = false;
                document.getElementById("name").disabled = true;
                document.getElementById("surname").disabled = true;
                document.getElementById("email").disabled = true;
                document.getElementById("mobile").disabled = true;
                document.getElementById("pwBtn").style.display = "none";
                document.getElementById("saveBtn").style.display = "none";
                document.getElementById("groupBtn").style.display = "none";

                $scope.authString = "user";
            }
            $scope.groups = [];
            $scope.myGroups = [];
            $scope.permGroups = [];
            var active = false;
           angular.forEach(user.groups, function(group){

               var str = group.substring(group.indexOf("cn=")+3, group.indexOf(","));
               str = str.charAt(0).toLocaleUpperCase() + str.slice(1);
               $scope.groups.push(str);
               if(_.contains(group, "sections") && !_.contains(group, "active")) {
                   $scope.myGroups.push(str);
               } else{
                   $scope.permGroups.push(str);
               }
            });
            // $scope.myGroups = $scope.permGroups.concat();
        }
    );

    $scope.editStyle = function(editable) {
        if(editable) {
            return "tableSelection"
        }return "notEditable";
    }


    $scope.addGroupList = [];

    $scope.mailsSelected = [];

    console.log($routeParams);

    //TODO: mailinglist





    /** MAILING LIST **/
    $scope.checkAll = function() {
        $scope.mailsSelected = $scope.mailingList.concat();
    };
    $scope.uncheck = function() {
        $scope.mailsSelected = [];
    };
    $scope.isChecked = function(mail) {

        if (_.contains($scope.mailsSelected, mail)) {
            console.log("HEI");
            return 'glyphicon glyphicon-ok pull-right';
        }
        return false;
    };

    $scope.mailSelection = function(mail) {
        if(!_.contains($scope.mailsSelected, mail)) {
            $scope.mailsSelected.push(mail);
        }
        else {
            var index = $scope.mailsSelected.indexOf(mail);
            $scope.mailsSelected.splice(index, 1);
        }
    }


    /** ADD GROUPS **/

    $scope.groupIsChecked = function(group) {
        if (!_.contains($scope.myGroups, group) && _.contains($scope.addGroupList, group)) {
            return 'glyphicon glyphicon-ok pull-right';
        }
        return false;
    };

    $scope.groupSelection = function(group) {
        if(!_.contains($scope.myGroups, group) && !_.contains($scope.addGroupList, group)) {
            $scope.addGroupList.push(group);
        }
        else {
            var index = $scope.addGroupList.indexOf(group);
            $scope.addGroupList.splice(index, 1);
    }
    }

    $scope.addGroups = function() {
        angular.forEach($scope.addGroupList, function(group){
            $scope.myGroups.push(group);
        });
        dropdownGroups();
        $scope.addGroupList = [];
    }

    $scope.removeMyGroup = function(myGroup, editable) {
        if(editable) {
            var index = $scope.myGroups.indexOf(myGroup);
            $scope.myGroups.splice(index, 1);
        }
    }

    dropdownGroups = function() {
        $scope.permGroups = [];
        angular.forEach($scope.groups, function(group){
           if(!_.contains($scope.myGroups, group))
                $scope.permGroups.push(group);
        });
    }

    $scope.hoverable = false;

    $scope.editGroups = function() {
        if(!$scope.edit) {
            $scope.edit = true;
            $scope.hoverable = true;
            return;
        }
        $scope.edit = false;
        $scope.hoverable = false;
    }

    $scope.createHover = function() {
        if($scope.hoverable) {
            return'tableSelection'
        }
        return null;
    }

    $scope.editable = function() {
        if($scope.edit) {
            return "icon-remove-sign pull-right";
        }
        return null;
    }


    $scope.validateNumber = function() {
        console.log("val");
        var fieldInput = document.getElementById('mobile').value;
        console.log(fieldInput);
        for (var i = 0; i < fieldInput.length; i++) {
            if (fieldInput.charCodeAt(i) < 47 || fieldInput.charCodeAt(i) > 58) {
                console.log("Ikke gyldig input");
                document.getElementById('mobile').value = document.getElementById('mobile').value.substring(0, fieldInput.length-1);
                return false;
            }
            else {

            }
        }
    }


    $scope.endringerLagret = "";

    $scope.explanation = "Vennligst fyll inn: "
    $scope.showExplanation = "";
    $scope.save = function() {
        $scope.insufficientList = [];
        if ( document.getElementById('email').value.length < 6 ) {
            $scope.insufficientList.push('Email');
        }
        if ( document.getElementById('name').value.length < 2 ) {
            $scope.insufficientList.push('Fornavn');
        }
        if ( document.getElementById('surname').value.length < 2 ) {
            $scope.insufficientList.push('Etternavn');
        }
        /*if ( document.getElementById('mobile').value.length < 8 ) {
            $scope.insufficientList.push('Mobilnummer');
        }*/
        if($scope.insufficientList.length > 0) {
            $scope.showExplanation = $scope.explanation;
        }
        else {
            $scope.showExplanation = "";
            <!-- TODO: create json and send to backend -->
            if(confirm("Lagre endringer?")) {
                console.log("JA");
                $scope.endringerLagret = "Endringer lagret"
                var userRet =  {
                    "firstName":document.getElementById('name').value,
                    "lastName":document.getElementById('surname').value,
                    "uid":user.uid,
                    "id":user.id,
                    "email":document.getElementById('email').value,
                    "mobile":document.getElementById('mobile').value,
                    "groups":$scope.myGroups
                   // "mailingList":$scope.mailsSelected
                };
                <!-- TODO: send dette til backend -->
                return $http({
                    method : 'POST',
                    data : userRet,
                    url : 'add'
                });
            }
        }
    }

    $scope.items = ['item1', 'item2', 'item3'];
    $scope.editPassword = function() {
        var modalInstance = $modal.open({
            templateUrl: 'editPw.html',
            controller: 'ModalInstanceCtrl',
            size: 'lg',
            resolve: {
                items: function () {
                    return $scope.items;
                }
            }
        });
        modalInstance.result.then(function (status) {
            console.log(status);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    }

    $scope.remove = function(group) {
        if($scope.edit) {
            var index = $scope.groups.indexOf(group);
            $scope.groups.splice(index, 1);
        }
    }

    mailSort = function(mailingList) {
        mailingList.sort(function(a, b){   /** By first sorting by forname, people with same lastname will get sorted automatically #latskap **/
            if(a.name < b.name) return -1;
            if(a.name > b.name) return 1;
            return 0;
        });
    };



});

app.controller('ModalInstanceCtrl', function ($scope, $modalInstance, items) {
    $scope.showPasswordError = false;
    $scope.items = items;
    $scope.selected = {
        item: $scope.items[0]
    };

    $scope.changePassword = function() {
        console.log("changing password");
        var oldpass = document.getElementById('oldpass').value;
        var newpass = document.getElementById('newpass').value;
        var confpass = document.getElementById('confpass').value;

        if ( newpass == confpass && newpass.length >7) {
            document.getElementById("passErr").hidden = true;
            console.log("Equals and greater than 7");
            $scope.ok();
        }
        else {
            console.log("error");
            document.getElementById("passErr").hidden = false;
        }
    }


    $scope.ok = function () {
        $modalInstance.close('ok');
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});