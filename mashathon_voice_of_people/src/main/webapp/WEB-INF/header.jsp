<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Voice Of People</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="css/bootstrap.css" type="text/css" rel="stylesheet">
	<link href="css/mashathon.css" type="text/css" rel="stylesheet">
	<!-- use local jquery -->
	<script src="js/jquery.min-2.1.1.js"></script>
	<script src="js/bootstrap.js"></script>
	<!-- the submission entry point -->
	<script src="js/script.js"></script>
</head>

<body>
	<div class="row bg-primary" id="header">
		<div class="col-md-4 col-md-offset-4">
			<h1>VOICE OF PEOPLE</h1>
		</div>
		<div class="col-md-2">
			<p class="logOutButton"><a href="${logoutURL}" >Logout</a></p>
		</div>
	</div>
	