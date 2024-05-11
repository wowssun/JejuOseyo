<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memJoin.jsp</title>
<style>
.button-container {
	display: flex;
	justify-content: flex-end;
	margin-top: 10px;
}

.button-container .btn {
	margin-right: 8px;
}
</style>
<script>


//일반회원 회원가입
function mjoinChk() {
	
	//아이디
	var mid = document.getElementById("mid").value;
	
	if (mid.length < 5 || mid.length > 10) {
		alert("아이디는 5자에서 10자 사이로 입력하세요.");
		return false;
	}
	
	//비밀번호
	var mpw = document.getElementById("mpw").value;
	var mpw2 = document.getElementById("mpw2").value;

	if (mpw !== mpw2) {
		alert("비밀번호가 일치하지 않습니다.");
		return false;
	}
	
	var mpw = document.getElementById("mpw").value;

	var passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/\-=])[A-Za-z\d!@#$%^&*()_+{}\[\]:;<>,.?~\\/\-=']{8,}$/;

	if (!passwordPattern.test(mpw)) {
		alert("비밀번호는 알파벳, 숫자, 특수문자를 포함 8글자 이상이어야 합니다.");
		return false;
	}
	
	//전화번호
	var koreanPhoneNumberPattern = /^(010|011|016|017|018|019)[0-9]{7,8}$/;

	var mphone = document.getElementById("mphone").value;
	
	if (!koreanPhoneNumberPattern.test(mphone)) {
		alert("유효하지 않은 전화번호입니다.");
		return false;
	}
	
    var email1 = document.getElementById('email1').value;
	var email2 = document.getElementById('email2').value;
	
	if (email2 === '' || email1 === '') {
		alert("이메일 주소를 모두 입력해 주세요");
		return false;
	}

    return true;


}
</script>
</head>
<body>
	<%@ include file="../include/header.jsp"%>



	<div class="jumbotron" style="background-color: #FFFBC9">
		<div class="container">
			<h1 class="display-4"
				style="text-align: center; font-weight: bold; color: #649E77">
				회원가입</h1>
		</div>
	</div>



	<main role="main">
		<div class="container">
			<form action="/jejuOseyo/Member/MemJoin.do" method="post"
				class="form-horizontal">


				<div class="form-group row">
					<label class="col-sm-2">이름</label>
					<div class="col-sm-3">
						<input type="text" name="name" id="name" class="form-control"
							required>
					</div>
				</div>



				<div class="form-group row">
					<label class="col-sm-2">아이디</label>
					<div class="col-sm-3">
						<input type="text" name="mid" id="mid" class="form-control"
							placeholder="5~10글자 이상" required>

					</div>
				</div>


				<div class="form-group row">
					<label class="col-sm-2">비밀번호</label>
					<div class="col-sm-3">
						<input type="password" name="mpw" id="mpw" class="form-control"
							placeholder="알파벳,숫자,특수문자 포함 8글자 이상" required>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2">비밀번호 확인</label>
					<div class="col-sm-3">
						<input type="password" name="mpw2" id="mpw2" class="form-control"
							required>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2">닉네임</label>
					<div class="col-sm-3">
						<input type="text" name="mnick" id="mnick" class="form-control"
							required>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2">전화번호</label>
					<div class="col-sm-3">
						<input type="text" name="mphone" id="mphone" class="form-control"
							placeholder="'-'를 제외하고 입력하세요." required maxlength="11">
					</div>
				</div>

			



				<div class="form-group row">
					<label class="col-sm-2">이메일</label> <input type="hidden"
						name="email" id="email">
					<div class="col-sm-3">
						<input type="text" name="email1" id="email1" class="form-control">
					</div>
					@
					<div class="col-sm-3">
						<input type="text" name="email2" id="email2" class="form-control">
					</div>

					<div class="col-sm-3">
						<select class="form-control" name="email3" id="email3">
							<option selected>직접입력</option>
							<option>gmail.com</option>
							<option>naver.com</option>
							<option>daum.com</option>
						</select>
					</div>
				</div>
			
			<script>
				
				//이메일
				var email1 = document.getElementById('email1');
			    var email2 = document.getElementById('email2');
			    var email3 = document.getElementById('email3');

			    email3.onchange = function() {
			        if (email3.value === '직접입력') {
			            email2.value = '';
			            email2.readOnly = false;
			        } else {
			            email2.value = email3.value;
			            email2.readOnly = true;
			        }
			        setEmailValue(); // 이메일 값 설정 함수 호출 추가
			    }

			    function setEmailValue() {

	 document.getElementById('email').value = email1.value + '@' + email2.value;
			    }

		        // 페이지 로드 시 이메일 값 설정 함수 호출
			    setEmailValue();

				</script>



				<div class="button-container">
					<div class="form-group row mt-4">
						<div class="col text-right">
							<a class="btn btn-secondary cancelBtn" href="../index.jsp">뒤로가기</a>
							<input type="submit" class="btn btn-info regBtn" value="회원가입"
								onclick="return mjoinChk()" style="background-color: #649E77" />

						</div>
					</div>
				</div>



			</form>
		</div>
	</main>

	<%
	// 회원가입실패알림
	String msg = (String) session.getAttribute("msg");
	if (msg != null) {
		out.println("<script>alert('" + msg + "');</script>");
		session.removeAttribute("msg");
	}
	%>




	<%@ include file="../include/footer.jsp"%>

</body>
</html>