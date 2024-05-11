<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hostInfo.jsp</title>
<script>
function delChk(){
	
	//게시물 삭제 확인
	
	if(confirm('정말 삭제하시겠습니까?')){
		$('form').attr('action','/jejuOseyo/Admin/AdHostRemove.do').submit();
	}
}

</script>	
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
			호스트 정보</h1></div></div>



	<main role="main">
		<div class="container">
			<div class="text-right"></div>

			<form action="/jejuOseyo/Admin/AdHostView.do" method="post" class="form-horizontal">
			<input type="hidden" name=pageNum id="pageNum" value="${pageNum }">
			
		
		<c:choose>
    <c:when test="${empty view}">
        <p>회원 정보를 찾을 수 없습니다.</p>
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
					<label class="col-sm-2"> 가입승인일 </label>
					<div class="col-sm-3">${view.procDate}</div>
				</div>
   </c:otherwise>
</c:choose>

<div class="button-container">
			<a class="btn btn-secondary cancelBtn" onclick="history.back()">뒤로가기</a>
					<input type="hidden" name="hid" value="${view.hid }">
					<input type="button"
					class="btn btn-danger regBtn" value="삭제" onclick="delChk();" />
</div>


			</form>
		</div>
	</main>





	<%@ include file="../include/footer.jsp"%>




</body>
</html>