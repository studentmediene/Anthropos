/*
 * Copyright 2016 Studentmediene i Trondheim AS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Created by Kristian on 30/04/14.
 */
app.controller("RegisterCtrl", function($scope, $resource, $http) {


    $scope.mailsSelected = [];
    $scope.myGroups = [];
    $scope.explanation = "Følgende felter er ikke utfylt: ";
    $scope.showExplanation = "";


    $scope.showUserContainer = false;
    $scope.showFileContainer = false;
    $scope.dropbox = document.getElementById("dropbox");
    $scope.dropText = "Slipp fil her...";
    $scope.csvFile = '';

    var tmpObj = $resource("../mailingLists.json", {}, {
            get:{
                isArray:true,
                method:"GET"
            }
        }
    );
    $scope.mailingList = tmpObj.get();

    var tmpObj = $resource("../groups.json", {}, {
            get:{
                isArray:true,
                method:"GET"
            }
        }
    );
    $scope.allGroups = tmpObj.get();



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
            return 'icon-ok pull-right';
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
    };


    /** ADD GROUPS **/

    $scope.groupIsChecked = function(group) {
        if (_.contains($scope.myGroups, group)) {
            return 'icon-ok pull-right';
        }
        return false;
    };

    $scope.groupSelection = function(group) {
        if(!_.contains($scope.myGroups, group)) {
            $scope.myGroups.push(group);
        }
        else {
            var index = $scope.myGroups.indexOf(group);
            $scope.myGroups.splice(index, 1);
        }
    };

    $scope.hoverable = false;
    $scope.test = "HEI";

    $scope.editGroups = function() {
        if(!$scope.edit) {
            $scope.edit = true;
            $scope.hoverable = true;
            return;
        }
        $scope.edit = false;
        $scope.hoverable = false;
    };

    $scope.createHover = function() {
        if($scope.hoverable) {
            return'tableSelection'
        }
        return null;
    };

    $scope.editable = function() {
        if($scope.edit) {
            return "icon-remove-sign pull-right";
        }
        return null;
    };


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
    };



    $scope.save = function() {
        $scope.insufficientList = [];
        if ( document.getElementById('name').value.length < 2 ) {
            $scope.insufficientList.push('Fornavn');
        }
        if ( document.getElementById('lastname').value.length < 2 ) {
            $scope.insufficientList.push('Etternavn');
        }
        if ( document.getElementById('email').value.length < 6 ) {
            $scope.insufficientList.push('Email');
        }
        if ( document.getElementById('mobile').value.length < 8 ) {
            $scope.insufficientList.push('Mobilnummer');
        }
        if($scope.insufficientList.length > 0) {
            $scope.showExplanation = $scope.explanation;
        }
        else {
            $scope.showExplanation = "";
            <!-- TODO: create json and send to backend -->
            if(confirm("Er du sikker på at du vil lagre endringer?")) {
                console.log("JA");
                var user =  {
                    "firstName":document.getElementById('name').value,
                    "lastName":document.getElementById('lastname').value,
                    "email":document.getElementById('email').value,
                    "mobil e":document.getElementById('mobile').value,
                };
                <!-- TODO: send dette til backend -->
                return $http({
                    method : 'POST',
                    data : user,
                    url : 'api/add'
                });

            }
        }
    };

    $scope.remove = function(group) {
        if($scope.edit) {
            var index = $scope.groups.indexOf(group);
            $scope.groups.splice(index, 1);
        }
    };


    mailSort = function(mailingList) {
        mailingList.sort(function(a, b){   /** By first sorting by forname, people with same surname will get sorted automatically #latskap **/
            if(a.name < b.name) return -1;
            if(a.name > b.name) return 1;
            return 0;
        });
    };


    $scope.selectCreateUser = function(){
        $scope.showUserContainer = true;
        $scope.showFileContainer = false;
       // document.getElementById("simpleUserContainer").refresh;
    };
    $scope.selectCreateCSV = function() {
        $scope.showFileContainer = true;
        $scope.showUserContainer = false;
    };

    $scope.filetypeCheck = true;
    $scope.setFile = function(element) {
        $scope.$apply(function($scope) {
            console.log('files:', element.files.item(0).type);
            $scope.file = element.files.item(0);
            if($scope.file.type !== "text/csv"){
                $scope.filetypeCheck = false;

            }
            else{
                $scope.filetypeCheck = true;
                $scope.csvFile = $scope.file;
            }
        });
    };

    $scope.selectFile = function(){
        console.log("heisann");
    }

    function dragEnterLeave(evt) {
        evt.stopPropagation();
        evt.preventDefault();
        console.log("enter/leave");
        $scope.$apply(function(){
            $scope.dropText = "Slipp fil her";
            $scope.dropClass = ''
        })
    }
    $scope.dropbox.addEventListener("dragenter", dragEnterLeave, false);
    $scope.dropbox.addEventListener("dragleave", dragEnterLeave, false);
    $scope.dropbox.addEventListener("dragover", function(evt) {
        evt.stopPropagation();
        evt.preventDefault();
        var clazz = 'not-available';
        console.log(evt.dataTransfer.types);
        var ok = evt.dataTransfer && evt.dataTransfer.types && evt.dataTransfer.types.indexOf('Files') > 0;
        $scope.$apply(function(){
            $scope.dropText = ok ? 'Slipp fil her...' : 'Kun .csv-filer tillatt';
            $scope.dropClass = ok ? 'over' : 'not-available'
        })
    }, false);
    $scope.dropbox.addEventListener("drop", function(evt) {
        console.log('drop evt:', JSON.parse(JSON.stringify(evt.dataTransfer)));
        evt.stopPropagation();
        evt.preventDefault();
        $scope.$apply(function(){
            $scope.dropText = 'Drop files here...';
            $scope.dropClass = ''
        });
        $scope.file = evt.dataTransfer.files

    }, false)

});
//<!-- http://jsfiddle.net/danielzen/utp7j/ -->