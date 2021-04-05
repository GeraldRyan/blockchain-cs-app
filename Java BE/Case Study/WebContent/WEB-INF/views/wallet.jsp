<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="privblock.gerald.ryan.entity.Blockchain"%>


<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title></title>
<link rel="stylesheet" href="./blockchain.css" type="text/css"></link>
</head>
<body>
	<h1>Have a wallet</h1>

	Address: ${wallet.getAddress() }
	<br> Balance: ${wallet.getBalance() }
	<br> public key: ${wallet.getPublickey().toString() }
	<br>
	<br>
	<a href="/CaseStudy/wallet/transact/">Make a transaction</a>
	<br>
	<p>Or send a post request to (/wallet/transact") in the following
		JSON format</p>
	<p>{ "address": "foo", "amount": 500 }</p>

</body>


</html>