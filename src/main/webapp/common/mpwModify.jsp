<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mpwModify.jsp</title>
<style>
    .button-container {
        display: flex;
        justify-content: flex-end;
        margin-top:10px;
    }
    .button-container .btn {
        margin-right: 8px;
      	
    }
        .container {
        display: flex;
        justify-content: center;
        align-items: center;}
</style>
<script>
					//비밀번호 변경
					function newPWChk() {

						var newpw = document.getElementById("newpw").value;
						var newpw2 = document.getElementById("newpw2").value;

						if (newpw !== newpw2) {
							alert("비밀번호가 일치하지 않습니다.");
							return false;
						}
						
						

						var passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/\-=])[A-Za-z\d!@#$%^&*()_+{}\[\]:;<>,.?~\\/\-=']{8,}$/;

						
						if (!passwordPattern.test(newpw)) {
							alert("비밀번호는 알파벳, 숫자, 특수문자를 포함 8글자 이상이어야 합니다.");
							return false;
						}

						return true;
					}
				</script>
				
</head>
<body>



	<%@ include file="../include/header.jsp"%>

		<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
		회원 비밀번호 변경</h1></div></div>



	<main role="main">
		<div class="container">
			<form action="/jejuOseyo/Common/MpwModify.do" method="post" class="form-horizontal">
			
				<div class="form-group row">
					<label class="col-sm-4">새 비밀번호</label>
					<div class="col-sm-8">
						<input type="password" name="newpw" id="newpw" class="form-control"
							placeholder="알파벳,숫자,특수문자 포함 8글자 이상" required>
					</div>	</div>
<div class="form-group row">
					<label class="col-sm-4">새 비밀번호 확인</label>
					<div class="col-sm-8">
						<input type="password" name="newpw2" id="newpw2" class="form-control"
							required>

					</div>
				</div>

			
				<input type="hidden" name="mid" value="${mpw_mid }">

<div class="button-container">
				<button type="submit" class="btn btn-info regBtn" style ="background-color: #649E77" onclick="return newPWChk()">비밀번호 변경</button>
</div>
			</form>
		</div>
	</main>


	<%
	//비밀번호변경실패
	String msg = (String) session.getAttribute("msg");
	if (msg != null) {
		out.println("<script>alert('" + msg + "');</script>");
		session.removeAttribute("msg");
	}
	%>





	<%@ include file="../include/footer.jsp"%>


</body>
</html>