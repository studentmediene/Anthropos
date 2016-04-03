<%--
  Created by IntelliJ IDEA.
  User: adrianh
  Date: 12.02.14
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Spring MVC - Ajax</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <style>
        body { background-color: #eee; font: helvetica; }
        #container { width: 500px; background-color: #fff; margin: 30px auto; padding: 30px; border-radius: 5px; box-shadow: 5px; }
        .green { font-weight: bold; color: green; }
        .message { margin-bottom: 10px; }
        label { width:70px; display:inline-block;}
        .hide { display: none; }
        .error { color: red; font-size: 0.8em; }
    </style>
</head>
<body>

<div id="container">

    <h1>Person Page</h1>
    <p>This page demonstrates Spring MVC's powerful Ajax functionality. Retrieve a
        person by ID.
    </p>

    <h2>Get By ID</h2>
    <form id="idForm">
        <div class="error hide" id="idError">Please enter a valid ID in range 0-3</div>
        <label for="personId">ID (0-3): </label><input firstName="id" id="personId" value="0" type="number" />
        <input type="submit" value="Get Person By ID" /> <br /><br/>
        <div id="personIdResponse"> </div>
    </form>

    <hr/>

</div>


<script type="text/javascript">

    $(document).ready(function() {

        // Request Person by ID AJAX
        $('#idForm').submit(function(e) {
            var personId = +$('#personId').val();
            if(!validatePersonId(personId))
                return false;
            $.get('${pageContext.request.contextPath}/' + personId, function(person) {
                $('#personIdResponse').text('Name: ' + person.firstName + ', Email: ' + person.email + ', Id: ' + person.id + ', Medlemskap: ' + person.groups);
            });
            e.preventDefault(); // prevent actual form submit
        });

    });

    function validatePersonId(personId) {
        console.log(personId);
        if(personId === undefined || personId < 0 || personId > 3) {
            $('#idError').show();
            return false;
        }
        else {
            $('#idError').hide();
            return true;
        }
    }


</script>

</body>
</html>
