<!DOCKTYPE html>
<html>
<head>
	<title>Anthropos</title>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
	<!-- CSS -->
	<link rel="stylesheet" href="css/styles.css">
</head>
<body>

<?php

	require_once('ipacurl.php');
	
	function getRandomPassword($length = 10) {
	    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	    $randomString = '';
	    for ($i = 0; $i < $length; $i++) {
	        $randomString .= $characters[rand(0, strlen($characters) - 1)];
	    }
	    return $randomString;
	}

	function removeSpecials($string) {
		//Removes æ, ø and å from usernames
		$string = mb_strtolower($string, 'utf-8');
		$string = str_replace("æ", "ae", $string);
		$string = str_replace("ø", "o", $string);
		$string = str_replace("å", "a", $string);
		//Remove any other special character
		$string = preg_replace('/[^a-z\.]/', '', $string);
		return $string;
	}

	function spamcheck($field) {
		// Sanitize e-mail address
		$field = filter_var($field, FILTER_SANITIZE_EMAIL);
		// Validate e-mail address
		if(filter_var($field, FILTER_VALIDATE_EMAIL)) {
	    	return true;
		} else {
			return false;
		}
	}

	$firstname = $_POST['firstname'];
	$lastname = $_POST['lastname'];
	$email = $_POST['email'];
	$phone = $_POST['phone'];

	$space_pos = strpos($firstname, " ");
	if ($space_pos):
		$fname = substr($firstname, 0, $space_pos);
	else:
		$fname = $firstname;
	endif;

	$space_pos = strrpos($lastname, " ");
	if ($space_pos):
		$lname = substr($lastname, $space_pos);
	else:
		$lname = $lastname;
	endif;

	$username = removeSpecials($fname . '.' . $lname);
	$password = getRandomPassword();
	$mailcheck = spamcheck($email);

	echo add_user($username, $password, $firstname, $lastname, $email, $phone);

	if ($mailcheck == false) {
    	echo 'Ugyldig epost.';
    } else {
    	$subject = "Bruker opprettet i Studentmedienesmedlemsdatabase";
    	$message = "Hei!<br/>Du har blitt lagt til i Studentmedienesmedlemsdatabase med følgende informasjon:<br/>";
    	$message += "Brukernavn: " . $username . "<br/>Passord: " . $password . "<br/>Fornavn: " . $firstname;
    	$message += "<br/>Etternavn: " . $lastname . "<br/>Epost: " . $email . "<br/>Mobilnummer: " . $phone;
    	$message += "<br/><br/>Skulle det være noe galt, eller noe du lurer på, ikke nøl med å kontakte IT-Drift ved å svar på denne eposten.";
    	$message += "<br/></br/>Med vennlig hilsen,<br/>IT-Drift, Studentmediene.";
    	$headers = 'From: bruker@studentmediene.no' . "\r\n" . 'Reply-To: bruker@studentmediene.no' . "\r\n" . 'X-Mailer: PHP/' . phpversion();
		
		// send mail
		echo mail($email, $subject, $message, $headers);

		echo 'Epost sendt!';
    }

    echo '<br/><a href="index.php">&larr; Tilbake</a>';
	
?>

</body>
</html>