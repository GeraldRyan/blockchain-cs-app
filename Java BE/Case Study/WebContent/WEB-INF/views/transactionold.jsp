<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="privblock.gerald.ryan.entity.Transaction"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Index Page</title>
</head>
<body>
	<h1>Transact on the blockchain</h1>
	<a href="/CaseStudy/">Home</a>
	<br>
	<h2>You made a transaction</h2>
	${transaction.toJSONtheTransaction() }

	<br>
	<a href="/CaseStudy/blockchain/">See our version of the blockchain</a>
	<br>
	<a href="/CaseStudy/blockchaindesc/">Same as above but with
		description</a>

	<form action="/CaseStudy/wallet/transact">
		Address to send money to <input type="text" name="address"><br>
		<br> Amount to send<input type="text" name="amount"><br>
		<br> <input type="submit" name="submit">
	</form>

</body>
</html>