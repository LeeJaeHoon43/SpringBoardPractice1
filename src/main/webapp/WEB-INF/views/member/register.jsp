<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
	<form role="form" method="post" autocomplete="off">
		<p>
			<label for="userId">아이디</label>
			<input type="text" id="userId" name="userId">
			<button type="button" class="idCheck">아이디 확인</button>
		</p>
		<p class="result">
			<span class="msg">이미 존재하는 ID입니다.</span>
		</p>
		<p>
			<label for="userPass">비밀번호</label>
			<input type="password" id="userPass" name="userPass">
		</p>
		<p>
			<label for="userName">이름</label>
			<input type="text" id="userName" name="userName">
		</p>
		<p><button type="submit" id="submit" disabled="disabled">가입하기</button></p>
		<p>
			<a href="/"></a>
		</p>
	</form>
</body>
<script type="text/javascript">
	$(".idCheck").click(function() {
		var query = {userId : $("#userId").val()}; 
		
		$.ajax({
			url : "/member/idCheck",
			type : "post",
			data : query,
			success : function(data) {
				if (data == 1) {
					$(".result .msg").text("이미 존재하는 ID입니다.");
					$(".result .msg").attr("style", "color: #f00");
					// 아이디가 중복될 경우, id="submit" 속성을 가진 요소는 비활성화.
					$("#submit").attr("disabled", "disabled");
				}else {
					$(".result .msg").text("사용 가능한 ID입니다.");
					$(".result .msg").attr("style", "color: #00f");
					// 아이디가 중복되지 않을 경우, id="submit" 속성을 가진 요소의 비활성화 속성을 제거. 
					$("#submit").removeAttr("disabled");
				}
			}
		});
	});
	
	// ID Input박스에서 입력이 발생하게 되면 가입 버튼이 비활성화된다.
	// 이러면 아이디가 중복되는 여러 현상도 막을 수 있다.
	$("#userId").keyup(function() {
		$(".result .msg").text("아이디를 확인하세요.");
		$(".result .msg").attr("style", "color: #000");
		$("#submit").attr("disabled", "disabled");
	});
</script>
</html>