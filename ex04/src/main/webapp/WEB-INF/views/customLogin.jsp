<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Custom Login</h1>
	<h2><c:out value="${error}"/></h2>
	<h2><c:out value="${logout}"/></h2>
	
	<!-- /login을 만든적이없고 /customLogin의 파라미터 String error, logout에 값이 저절로 입력되네? -->
	<form method="post" action="/login">
	
	<div>
		<input type="text" name="username" value="admin">
	</div>
	
	<div>
		<input type="password" name="password" value="admin">
	</div>
	
	<div>
		<input type="checkbox" name="remember-me"> Remember Me
	</div>
	
	<div>
		<input type="submit">
	</div>
	
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	
	</form>
</body>
</html>