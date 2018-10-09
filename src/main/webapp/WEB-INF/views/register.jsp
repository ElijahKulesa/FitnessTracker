<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Account</title>
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
	<form:form modelAttribute="account" action="register" method="POST">
		<table class="center">
			<tr><td colspan=2><h2>Create an Account</h2>
			<spring:hasBindErrors name="account">
				<div class="error">There were problems creating your account:</div>
			</spring:hasBindErrors>
			</td></tr>
			
			<tr>
				<td><form:label path="firstName">First Name</form:label></td>
				<td><form:input path="firstName"/></td>
			</tr>
			<tr><td colspan=2><form:errors class="error" path="firstName"/></td></tr>
			<tr>
				<td><form:label path="lastName">Last Name</form:label></td>
				<td><form:input path="lastName"/></td>
			</tr>
			<tr><td colspan=2><form:errors class="error" path="lastName"/></td></tr>
			<tr>
				<td><form:label path="username">Username</form:label></td>
				<td><form:input path="username"/></td>
			</tr>
			<tr><td colspan=2><form:errors class="error" path="username"/></td></tr>
			<tr>
				<td><form:label path="password">Password</form:label></td>
				<td><form:password path="password"/></td>
			</tr>
			<tr>
				<td><form:label path="confirmPassword">Confirm Password</form:label></td>
				<td><form:password path="confirmPassword"/></td>
			</tr>
			<tr><td colspan=2><form:errors class="error" path="password"/></td></tr>
			<tr>
				<td><form:button type="submit">Create Account</form:button></td>
				<td><a href="login">Return to login.</a>
		</table>
	</form:form>
</body>
</html>