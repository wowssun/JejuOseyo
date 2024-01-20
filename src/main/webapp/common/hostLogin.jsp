<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hostLogin.jsp</title>
<style>
.container {
	display: flex;
	justify-content: center;
	align-items: center;
}

.form-group.row {
	text-align: center;
}

.button-container {
	margin-top: 10px;
}

.button-container .btn {
	border: none;
	outline: none;
}

  
  .caption {
  margin-top: 20px;
  text-align: center;
}

.caption a {
  margin: 0 20px;
  font-size: 15px;
  color: black;
  text-decoration: none;
}
</style>
<script>
//호스트로그인
function hostloginChk(){
	
	var hid = document.getElementById("hid").value;
	if (hid.length < 5 || hid.length > 10) {
		alert("아이디는 5자에서 10자 사이로 입력하세요.");
		hid.focus();
		return false;
		}
	
	var hpw = document.getElementById("hpw").value;
	
	var passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/\-='])[A-Za-z\d!@#$%^&*()_+{}\[\]:;<>,.?~\\/\-=']{8,}$/;

	if (!passwordPattern.test(hpw)) {
		alert("비밀번호는 알파벳, 숫자, 특수문자를 포함 8글자 이상이어야 합니다.");
		hpw.focus();
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
				호스트 로그인</h1>
		</div>
	</div>


	<main role="main">
		<div class="container">
			<form action="/jejuOseyo/Common/HostLogin.do" method="post"
				class="form-horizontal">

				<div class="form-group row">
					<label class="col-sm-4">아이디</label>
					<div class="col-sm-8">
						<input type="text" name="hid" id="hid" class="form-control"
							placeholder="5~10글자 이상" required>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-4">비밀번호</label>
					<div class="col-sm-8">
						<input type="password" name="hpw" id="hpw" class="form-control"
							placeholder="알파벳,숫자,특수문자 포함 8글자 이상" required>
					</div>
				</div>

				<div class="button-container">
					<input type="submit" class="btn btn-lg btn-block btn-warning"
						style="background-color: #FFFBC9" value="호스트 로그인"
						onclick="return hostloginChk()">
				</div>


				<div class="caption">
					<a href="../common/hidSearch.jsp">아이디 찾기</a> <a
						href="../common/hpwSearch.jsp">비밀번호 찾기</a>
				</div>





			</form>
		</div>
	</main>

	<%
	// 호스트로그인실패 //아이디찾기성공
	String msg = (String) session.getAttribute("msg");
	if (msg != null) {
		out.println("<script>alert('" + msg + "');</script>");
		session.removeAttribute("msg");
	}
	%>


	<%@ include file="../include/footer.jsp"%>

</body>
</html>