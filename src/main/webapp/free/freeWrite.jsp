<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freeWrite.jsp</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
		자유게시판 게시글 작성</h1></div></div>

<main role="main">
	<div class="container">
		<form action="/jejuOseyo/Free/FreeWrite.do" method="post" class="form-horizontal" >
			<!-- 필수 입력 항목 안내 메시지 -->
			<c:set var="subMsg" value="제목을 입력해 주세요."/>
			<c:set var="conMsg" value="내용을 입력해 주세요."/>
			
			<div class="form-group row"> 
				<label class="col-sm-2">작성자</label>
				<div class="col-sm-3">
					<input type="text" name="mid" id="mid" class="form-control" 
						   readonly value="${sid }"> <!-- value="${sid }" -->
				</div></div>
		
			<div class="form-group row"> 
				<label class="col-sm-2">제목</label>
				<div class="col-sm-8">
					<input type="text" name="freeTitle" id="freeTitle" class="form-control" 
						   required oninput="setCustomValidity('')"
						   oninvalid="this.setCustomValidity('${subMsg }')">
				</div></div>
		
			<div class="form-group row"> 
				<label class="col-sm-2">내용</label>
				<div class="col-sm-8">
					<textarea name="freeContent" id="freeContent" class="form-control"
							rows="5"  cols="50" required oninput="setCustomValidity('')"
						   oninvalid="this.setCustomValidity('${conMsg }')"></textarea>
				</div></div>
				
			<div class="form-group row mt-4"> 
				<div class="col text-right">
					<input type="button" class="btn btn-secondary cancelBtn" value="취소"
					   onclick="back()"/>
					<input type="submit" class="btn btn-info regBtn" 
					       value="등록"/> 
				</div></div>
		</form></div></main>


<%@ include file="../include/footer.jsp" %>

</body>
<script>
function back() {
  history.back();
}

</script>

</html>