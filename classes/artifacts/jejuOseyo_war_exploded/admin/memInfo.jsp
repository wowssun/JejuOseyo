<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memInfo.jsp</title>
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
	
<script>
function delChk(){
	
	//게시물 삭제 확인
	
	if(confirm('정말 삭제하시겠습니까?')){
		$('form').attr('action','/jejuOseyo/Admin/AdMemRemove.do').submit();
	}
}

</script>	
	


	<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			회원 정보</h1></div></div>


	<main role="main">
		<div class="container">
			<div class="text-right"></div>

			<form action="/jejuOseyo/Admin/AdMemView.do" method="post" class="form-horizontal">
			
			
		
		<c:choose>
    <c:when test="${empty view}">
        <p>회원 정보를 찾을 수 없습니다.</p>
    </c:when>
    <c:otherwise>

				<div class="form-group row">
					<label class="col-sm-2"> 이름 </label>
					<div class="col-sm-3">${view.name }</div>
				</div>


				<div class="form-group row">
					<label class="col-sm-2"> 아이디 </label>
					<div class="col-sm-3">${view.mid }</div>
				</div>


				<div class="form-group row">
					<label class="col-sm-2"> 닉네임 </label>
					<div class="col-sm-3">${view.mnick }</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2"> 전화번호 </label>
					<div class="col-sm-3">${view.mphone }</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2"> 이메일 </label>
					<div class="col-sm-3">${view.memail }</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2"> 가입일자 </label>
					<div class="col-sm-3">${view.regDate}</div>
				</div>
   </c:otherwise>
</c:choose>




<div class="button-container">
				<a class="btn btn-secondary cancelBtn" onclick="history.back()">뒤로가기</a>
					<input type="hidden" name="mid" value="${mid}">
					<input type="button"
					class="btn btn-danger regBtn" value="삭제" onclick="delChk();" />
</div>


			</form>
		</div>
	</main>




	<%@ include file="../include/footer.jsp"%>




</body>
</html>