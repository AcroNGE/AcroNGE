<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration - Step 3/4</title>
</head>
<body>
	<h1>Input you data</h1>
	<form action="Registration" method="post">
		<input name="step" value="3" type="hidden">
		e-mail:
		<input name="email" type="text" readonly="readonly" value="<%= session.getAttribute("email")%>">
		<br>
		user name:
		<input name="login" type="text">
		<br>
		password:
		<input name="pass" type="password">
		<br>
		repeat password:
		<input name="pass2" type="password">
		<br>
		<input name="submit" type="submit" value="submit">
	</form>
</body>
</html>