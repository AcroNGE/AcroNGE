<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration - Step 1/4</title>
</head>
<body>
    <script type="text/jscript">
        function CheckCond() {
            if (document.getElementById("condition").checked) {
                document.getElementById("submit").disabled = false;
            }
            else {
                document.getElementById("submit").disabled = true;
            }
        }
    </script>

	<h1>Step 1. E-mail validation.</h1>
	<form action="Registration" method="post">
		<input name="step" value="1" type="hidden">
		e-mail: 
		<input name="email" type="text">
		<br>
		Are you accept all Site politics
        <input name="condition" id="condition" type="checkbox" onchange="CheckCond();" value="accept" />
        <br>
        <input id="submit" type="submit" value="Register" disabled="disabled">
	</form>
</body>
</html>