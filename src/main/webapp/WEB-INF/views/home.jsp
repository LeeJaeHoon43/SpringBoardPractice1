<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>게시판</title>
</head>
<body>
	<a href="/board/write">글 작성</a>
	<a href="/board/list">글 목록</a>
	<a href="/board/listPage">글 목록 + 페이지</a>
	<a href="/board/listSearch">글 목록 + 페이지 + 검색</a>
	
	<c:if test="${member == null}">
		<form role="form" method="post" autocomplete="off" action="/member/login"> 
			<p>
				<label for="userId">아이디</label>
				<input type="text" id="userId" name="userId">
			</p>
			<p>
				<label for="userPass">비밀번호</label>
				<input type="password" id="userPass" name="userPass">
			</p>
			<p><button type="submit">로그인</button></p>
			<p><a href="/member/register">회원가입</a></p>
		</form>
	</c:if>
	<c:if test="${msg == false}">
		<p style="color: #f00;">로그인에 실패하였습니다. 아이디 또는 비밀번호를 다시 입력해주세요.</p>
	</c:if>
	<c:if test="${member != null}">
		<p>${member.userName}님 환영합니다.</p>
		<a href="member/modify">회원정보 수정</a>,<a href="member/withdrawal">회원탈퇴</a><br>
		<a href="member/logout">로그아웃</a>
	</c:if>
</body>
</html>
