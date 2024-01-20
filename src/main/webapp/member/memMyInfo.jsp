<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="jejuOseyo.vo.MemberVO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memMyInfo.jsp</title>
<style>
.button-container {
	display: flex;
	justify-content: flex-end;
	margin-top: 10px;
}

.button-container .btn {
	margin-right: 8px;
}

.container {
	display: flex;
	justify-content: center;
	align-items: center;
}
</style>
<script>
	function delChk() {

		//회원 탈퇴 확인

		if (confirm('정말 탈퇴하시겠습니까?')) {
			$('form').attr('action', '/jejuOseyo/Member/MemRemove.do').submit();
		}
	}
	btn.onclick = function() {//submit 폼 전송
		event.preventDefault(); //기본 이벤트 막기
	}
</script>
</head>
<body>

	<%@ include file="../include/header.jsp"%>



	<div class="jumbotron" style="background-color: #FFFBC9">
		<div class="container">
			<h1 class="display-4"
				style="text-align: center; font-weight: bold; color: #649E77">
				개인정보</h1>
		</div>
	</div>



	<main role="main">
		<div class="container">
			<div class="text-right"></div>

			<form action="/jejuOseyo/Member/MemModify.do" method="post"
				class="form-horizontal">



				<c:choose>
					<c:when test="${empty view}">
						<p>회원 정보를 찾을 수 없습니다.</p>
					</c:when>
					<c:otherwise>


						<div class="form-group row">
							<label class="col-sm-2"> 이름 </label>
							<div class="col-sm-3">
								<input type="text" name="name" id="name" class="form-control"
									required readonly value="${view.name }">
							</div>
						</div>


						<div class="form-group row">
							<label class="col-sm-2"> 아이디 </label>
							<div class="col-sm-3">
								<input type="text" name="mid" id="mid" class="form-control"
									required readonly value="${view.mid }">
							</div>
						</div>



						<div class="form-group row">
							<label class="col-sm-2"> 닉네임 </label>
							<div class="col-sm-3">
								<input type="text" name="mnick" id="mnick" class="form-control"
									required readonly value="${view.mnick }">
							</div>
						</div>



						<div class="form-group row">
							<label class="col-sm-2"> 전화번호 </label>
							<div class="col-sm-3">
								<input type="text" name="mphone" id="mphone"
									class="form-control" required value="${view.mphone }"
									maxlength="11">
							</div>
						</div>

						<script>
							// 전화번호 유효성 검사
							var mphoneInput = document.getElementById("mphone");

							mphoneInput
									.addEventListener(
											"input",
											function() {
												var koreanPhoneNumberPattern = /^(010|011|016|017|018|019)[0-9]{7,8}$/;
												var mphone = mphoneInput.value;

												if (!koreanPhoneNumberPattern
														.test(mphone)) {
													mphoneInput
															.setCustomValidity("유효하지 않은 전화번호입니다.");
												} else {
													mphoneInput
															.setCustomValidity("");
												}
											});
						</script>


						<!-- 이메일 필드 설정 부분 -->
						<div class="form-group row">
							<label class="col-sm-2">이메일</label> <input type="hidden"
								name="email" id="email">
							<div class="col-sm-3">
								<input type="text" name="email1" id="email1"
									class="form-control" value="${emailArray[0]}" required>
							</div>
							@
							<div class="col-sm-3">
								<input type="text" name="email2" id="email2"
									class="form-control" value="${emailArray[1]}" readonly>
							</div>

							<div class="col-sm-3">
								<select class="form-control" name="email3" id="email3">
									<option>직접입력</option>
									<option ${emailArray[2].equals("gmail.com") ? "selected" : "" }>gmail.com</option>
									<option ${emailArray[2].equals("naver.com") ? "selected" : "" }>naver.com</option>
									<option ${emailArray[2].equals("daum.com") ? "selected" : "" }>daum.com</option>
								</select>
							</div>
						</div>

						<script>
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
								setEmailValue();
							}

							function setEmailValue() {
								if (email2.value === '' || email1.value === '') {
									alert("이메일 주소를 모두 입력해 주세요");
									email2.focus();
								} else {
									document.getElementById('email').value = email1.value
											+ '@' + email2.value;
								}
							}

							function splitEmail(email) {
								var splitEmail = email.split("@");
								email1.value = splitEmail[0];
								email2.value = splitEmail[1];
								email3.value = "직접입력"; // or set the appropriate option value
								setEmailValue();
							}

							// 페이지 로드 시 이메일 값 설정 함수 호출
							splitEmail("${view.memail}");
						</script>


						<div class="form-group row">
							<label class="col-sm-2"> 가입일자 </label>
							<div class="col-sm-3">${view.regDate}</div>
						</div>
					</c:otherwise>
				</c:choose>

				<div class="button-container">

					<a class="btn btn-secondary cancelBtn"
						href="../member/memMypage.jsp">뒤로가기</a> <input type="hidden"
						name="mid" id="mid" value="${view.mid}">
					<button type="submit" class="btn btn-info regBtn"
						onclick="setEmailValue();">수정</button>
					<a class="btn btn-success" href="../member/mypagePWModify.jsp">비밀번호
						변경</a> <input type="button" class="btn btn-danger regBtn" value="탈퇴"
						onclick="delChk();" />
				</div>

			</form>
		</div>
	</main>

	<%
	//회원탈퇴실패 //정보수정성공
	String msg = (String) session.getAttribute("msg");
	if (msg != null) {
		out.println("<script>alert('" + msg + "');</script>");
		session.removeAttribute("msg");
	}
	%>



	<%@ include file="../include/footer.jsp"%>

</body>
</html>