<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이재훈 게시판</title>
</head>
<body>
	<form role="form" method="post" autocomplete="off">
		<p>
			<label for="userId">아이디</label>
			<input type="text" id="userId" name="userId" value="${member.userId}">
		</p>
		<p>
			<label for="userPass">비밀번호</label>
			<input type="password" id="userPass" name="userPass">
		</p>
		<p>
			<button type="submit">회원탈퇴</button>
		</p>
		<p>
			<a href="/">처음으로</a>
		</p>
	</form>
	<c:if test="${msg == false}">
		<p>비밀번호가 일치하지 않습니다.</p>
	</c:if>
</body>
</html>