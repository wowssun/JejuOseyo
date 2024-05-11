<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hidSearch.jsp</title>
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
</head>
<body>

<%@ include file="../include/header.jsp"%>

	
	<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			호스트 아이디 찾기</h1></div></div>
	



	<main role="main">
		<div class="container">
			<form action="/jejuOseyo/Common/HidFind.do" method="post" class="form-horizontal">
			
			
			
			<div class="form-group row">
					<label class="col-sm-4">대표자명</label>
					<div class="col-sm-8">
						<input type="text" name="rep" id="rep" class="form-control"
							required>
					</div>
				</div>
				
				
				
			<div class="form-group row">
					<label class="col-sm-4">전화번호</label>
					<div class="col-sm-8">
						<input type="text" name="hphone" id="hphone" class="form-control"
							placeholder="'-'를 제외하고 입력하세요." required maxlength="11">
					</div>
				</div>
				
				<div class="button-container">
				<input type="submit" class="btn btn-info" style ="background-color: #649E77" value="아이디 찾기">
</div>

			</form>
		</div>
	</main>
	
	
	<%
	//아이디찾기실패
	String msg = (String) session.getAttribute("msg");
	if (msg != null) {
		out.println("<script>alert('" + msg + "');</script>");
		session.removeAttribute("msg");
	}
	%>





	<%@ include file="../include/footer.jsp"%>

</body>
</html>