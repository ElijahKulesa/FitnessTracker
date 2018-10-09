<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Fitness Tracker Login</title>
<style>
.error {
	color: #ff0000;
}
.center {
	margin: 0px auto;
	align: center;
	text-align: center;
}
</style>
</head>
<body>
	
	<form:form modelAttribute="accountLogin" action="login" method="POST" class="center">
		<h2 class="center">Fitness Tracker Login</h2>
		<table class="center">
			<tr>
				<td><form:label path="username">Username:</form:label></td>
				<td><form:input path="username"/></td>
			</tr>
			
			<tr>
				<td><form:label path="password">Password:</form:label></td>
				<td><form:password path="password"/></td>
			</tr>
			<tr>
				<td><form:button type="submit">Login</form:button></td>
				<td><a href="register">Create an account.</a></td>
			</tr>		
		</table>
	</form:form>
	
	<spring:hasBindErrors name="accountLogin">
			<div class="center"><div class="error">Invalid username or password.</div></div>
	</spring:hasBindErrors>
</body>
</html>