<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hostRQInfo.jsp</title>
<style>
    .button-container {
        display: flex;
        justify-content: flex-end;
        margin-top:10px;
    }
    .button-container .btn {
        margin-right: 8px;
    }
</style>
</head>
<body>


	<%@ include file="../include/header.jsp"%>


	
	<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			호스트 회원가입 신청 정보</h1></div></div>



	<main role="main">
		<div class="container">
			<div class="text-right"></div>

			<form action="/jejuOseyo/Admin/AdHostRQView.do" method="post"
				class="form-horizontal">
<input type="hidden" name=pageNum id="pageNum" value="${pageNum }">
				<c:choose>
					<c:when test="${empty view}">
						<p>호스트 정보를 찾을 수 없습니다.</p>
					</c:when>
					<c:otherwise>

						<div class="form-group row">
							<label class="col-sm-2"> 대표자명 </label>
							<div class="col-sm-3">${view.rep }</div>
						</div>


						<div class="form-group row">
							<label class="col-sm-2"> 아이디 </label>
							<div class="col-sm-3">${view.hid }</div>
						</div>


						<div class="form-group row">
							<label class="col-sm-2"> 닉네임 </label>
							<div class="col-sm-3">${view.hnick }</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2"> 전화번호 </label>
							<div class="col-sm-3">${view.hphone }</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2"> 대표번호 </label>
							<div class="col-sm-3">${view.dnumber }</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2"> 이메일 </label>
							<div class="col-sm-3">${view.hemail }</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2"> 사업자번호 </label>
							<div class="col-sm-3">${view.crnum }</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2"> 사업자등록증 사본(jpg/*) </label>
							<div class="col-sm-3">
								<img src="../resources/images/${view.photo}" alt="호스트 사진">

							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2"> 가입신청일 </label>
							<div class="col-sm-3">${view.regDate}</div>
						</div>
						


					</c:otherwise>
				</c:choose>
				
				<script>
function rjChk() {
    // 호스트 거절
    if (confirm('거절하시겠습니까?')) {
        $('form').attr('action', '/jejuOseyo/Admin/AdHostReject.do').submit();
    }
}

function acChk() {
    // 호스트 승인
    if (confirm('승인하시겠습니까?')) {
        $('form').attr('action', '/jejuOseyo/Admin/AdHostAccept.do').submit();
    }
}




</script>

<div class="button-container">
				<a href="/jejuOseyo/Admin/HostRQList.do?pageNum=1&type=&keyword=" class="btn btn-secondary cancelBtn"
					>뒤로가기</a> 
					<input type="hidden" name="hid" value="${hid }">
					<input type="hidden" name="email" value="${view.hemail }"> 
					 <input type="button"
					class="btn btn-danger regBtn" value="호스트 거절"
					onclick="rjChk();" /> 
					 <input type="button"
					class="btn btn-info regBtn" value="호스트 승인"
					onclick="acChk();" />
</div>

			</form>
		</div>
	</main>





	<%@ include file="../include/footer.jsp"%>




</body>
</html>