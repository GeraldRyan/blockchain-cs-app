<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="privblock.gerald.ryan.entity.Blockchain"%>

<!-- This is not working. can remove if it doens't work as well as the jar file from lib folder -->
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Index Page</title>
<link rel="stylesheet" href="./blockchain.css" type="text/css"></link>
</head>
</body>
<body>
	<pre style="word-wrap: break-word; white-space: pre-wrap;">${blockchain.toJSONtheChain()}</pre>
</body>
</html>