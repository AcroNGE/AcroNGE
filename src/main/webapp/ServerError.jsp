<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Server error</title>
</head>
<body>
	<a href="index.jsp">Back to main page</a>
	<div>
		<% 
			Cookie[] cookies = request.getCookies();
			for(int i = cookies.length - 1; i >= 0; --i){
				if(cookies[i].getName() == "error"){
					out.print(cookies[i].getValue());
				}
			}
		%>
	</div>
	<div>
		<% out.print(session.getAttribute("error").toString()); %>
	</div>
</body>
</html>