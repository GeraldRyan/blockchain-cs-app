<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Welcome to the club ${user.getUsername() }</h3>

	<br>
	<h4>Would you like to create your wallet now?</h4>
	<a href="./createwallet">Yes:: create a Wallet</a>
	<br>
	<br>
	<a href="index.jsp">Home</a>
</body>
</html>