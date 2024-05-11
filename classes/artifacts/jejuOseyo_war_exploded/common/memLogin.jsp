<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memLogin.jsp</title>
<style>
    .container {
        display: flex;
        justify-content: center;
        align-items: center;}
        
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
 	<!-- <script>
	Kakao.init('63aacca1ad716f53299d44eff3ea39fb'); //발급받은 키 중 javascript키를 사용해준다.
	console.log(Kakao.isInitialized()); // sdk초기화여부판단
	// 카카오로그인
	function kakaoLogin() {
	    Kakao.Auth.login({
	        success: function (response) {
	            Kakao.API.request({
	                url: '/v2/user/me',
	                success: function (response) {
	                    // 받은 사용자 정보를 쿠키에 저장 (또는 다른 방식으로 저장)
	                    var userId = response.id;
	                    var nickname = response.properties.nickname;


	                    // 쿠키에 사용자 정보 저장
	                    document.cookie = "userId=" + userId + "; path=/";
	                    document.cookie = "nickname=" + nickname + "; path=/";
	  

	                    // 페이지 리로딩
	                    location.reload();
	                },
	                fail: function (error) {
	                    console.log(error)
	                },
	            });
	        },
	        fail: function (error) {
	            console.log(error)
	        },
	    });
	}
	</script> -->
<script>

	function memloginChk(){
	
	var mid = document.getElementById("mid").value;
	if (mid.length < 5 || mid.length > 10) {
		alert("아이디는 5자에서 10자 사이로 입력하세요.");
		return false;
		}
		
	var mpw = document.getElementById("mpw").value;
	
	var passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/\-='])[A-Za-z\d!@#$%^&*()_+{}\[\]:;<>,.?~\\/\-=']{8,}$/;


	if (!passwordPattern.test(mpw)) {
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
		로그인</h1></div></div>


	<main role="main">
		<div class="container">
			<form action="/jejuOseyo/Common/MemLogin.do" method="post" class="form-horizontal">
				<div class="form-group row">
					<label class="col-sm-4">아이디</label>
					<div class="col-sm-8">
						<input type="text" name="mid" id="mid" class="form-control"
							placeholder="5~10글자 이상" required>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-4">비밀번호</label>
					<div class="col-sm-8">
						<input type="password" name="mpw" id="mpw" class="form-control"
							placeholder="알파벳,숫자,특수문자 포함 8글자 이상" required>
					</div>
				</div>


				<div class="button-container">
				<input type="submit" class="btn btn-lg btn-block btn-warning"
					style="background-color:#FFFBC9" value="로그인" onclick="return memloginChk()">
<!-- 
				<a href="javascript:void(0)" onclick="kakaoLogin();"
					class="btn btn-lg btn-block btn-warning " > <span>카카오 로그인</span></a> -->
				</div>

				
				  <div class="caption">
            		<a href="../common/midSearch.jsp">아이디 찾기</a>
            		<a href="../common/mpwSearch.jsp">비밀번호 찾기</a>
        				</div>
				
			</form>
			
		</div>
	</main>


	<%
	// 회원가입성공 // 로그인실패 //아이디찾기성공
	String msg = (String) session.getAttribute("msg");
	if (msg != null) {
		out.println("<script>alert('" + msg + "');</script>");
		session.removeAttribute("msg");
	}
	%>





	<%@ include file="../include/footer.jsp"%>


</body>
</html>