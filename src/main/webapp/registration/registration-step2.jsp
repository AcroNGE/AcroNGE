<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration - Step 2/4</title>
</head>
<body>
	<h1>Code verification</h1>
	<form action="Registration">
		<input type="hidden" value="2" name="step">
		Check your e-mail and put code that you received.<br>
		e-mail: 
		<input type="text" id="email" name="email">
		<br>
		code: 
		<input type="text" id="code" name="code">
		<br>
		<input type="submit" value="submit" name="submit">
	</form>
	<script type="text/javascript">
		//load default parameters
		var s = window.location.search.substring(1);
		var ss = s.split("&");
		var k = ss.length;
		for(var i = 0; i < k; ++i){
			s = ss[i].split("=");
			var elem = document.getElementById(s[0]);
			elem.value = s[1];
		}
	</script>
</body>
</html>