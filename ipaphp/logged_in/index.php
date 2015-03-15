<!DOCKTYPE html>
<html>
<head>
	<title>Anthropos</title>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
	<!-- CSS -->
	<link rel="stylesheet" href="css/styles.css">
</head>
<body>
	<form method="post" action="useradd.php" onsubmit="return validateForm();" name="userForm">
		<div class="input-group">
	  		<input type="text" placeholder="Fornavn*" name="firstname"><br/><br/>
	  		<input type="text" placeholder="Etternavn*" name="lastname"><br/><br/>
	  		<input type="text" placeholder="Epost*" name="email"><br/><br/>
	  		<input type="text" placeholder="Mobilnummer" name="phone"><br/><br/>
	  		<button type="submit" class="btn btn-default">Submit</button>
	  	</div>
	</form>

	<script>
		function validateForm() {
		    var firstname = document.forms["userForm"]["firstname"].value;
		    var lastname = document.forms["userForm"]["lastname"].value;
		    var email = document.forms["userForm"]["email"].value;

		    if (firstname == "") {
		        alert("Fornavn kan ikke vær tom.");
		        return false;
		    } else if (lastname == "") {
		        alert("Etternavn kan ikke vær tom.");
		        return false;
		    } else if (email == "") {
		        alert("Epost kan ikke vær tom.");
		        return false;
		    } else {
		    	return true;
		    }
		}
	</script>
</body>
</html>