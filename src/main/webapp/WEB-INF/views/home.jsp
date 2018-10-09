<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
	<title>Fitness Tracker Home</title>
</head>
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
<body>
	<div class="center" Style="height=auto;margin=auto">
		<h1 Style="width=auto;">Welcome home, ${firstName} ${lastName}</h1>
		<a href="logout" Style="align=left">Logout (${username})</a>
	</div>
	<form:form class="center" modelAttribute="home" action="home" method="POST">
		<table class="center">
			<tr>
				<td><form:label path="exerciseDate">Date</form:label></td>
				<td><form:label path="exerciseMinutes">Time Exercised (Minutes)</form:label></td>
				<td><form:label path="goalMinutes">Goal (Minutes)</form:label></td>
			</tr>
			<tr>
				<td><form:input path="exerciseDate" type="date"/></td>
				<td><form:input path="exerciseMinutes" type="number"/></td>
				<td><form:input path="goalMinutes" type="number"/></td>
			</tr>
		</table>
		<form:button type="submit">Submit Exercise</form:button>
		<div><form:errors path="*" class="error"></form:errors></div>
	</form:form>
</body>
</html>
