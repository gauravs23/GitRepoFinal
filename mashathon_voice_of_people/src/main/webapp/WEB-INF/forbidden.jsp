<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<html>
<head>
<title>Access Denied</title>

<!-- use local jquery -->
<script src="js/jquery.min-2.1.1.js"></script>

<!-- the submission entry point -->
<script src="js/script.js"></script>
</head>

<body>

<h2>Oops, looks like you do not have access to this tool. This is only accessible to admins of the domain.</h2>

<p>Got Another Account?? Try again by logging out : <a href="${logoutURL}" >Logout and Login Again</a>

</html>