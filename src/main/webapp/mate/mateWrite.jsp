<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mateWrite.jsp</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

<div class="jumbotron">
	<div class="container">
		<h1 class="display-4">여행메이트 글 작성하기</h1></div></div>

<main role="main">
	<div class="container">
		<form action="./mateWrite.do" method="post" class="form-horizontal">
		
			<!-- 필수 입력 항목 안내 메시지 -->
			<c:set var="mtitleMsg" value="제목을 입력해주세요."/>
			<c:set var="mtextMsg" value="내용을 입력해주세요."/>
			

		
			<div class="form-group row"> 
				<label class="col-sm-2">제목</label>
				<div class="col-sm-3">
					<input type="text" name="mtitle" id="mtitle" class="form-control" 
						   required
						   oninput="setCustomValidity('')"
						   oninvalid="this.setCustomValidity('${mtitleMsg }')">							<!-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 -->
				</div></div>
				
				
			<div class="form-group row"> 
				<label class="col-sm-2">닉네임</label>
				<div class="col-sm-3">
					<input type="text" name="mnick" id="mnick" class="form-control" 
						   required readonly value="${sid }">	
				</div></div>
				
				
			<div class="form-group row"> 
				<label class="col-sm-2">계획한 여행기간</label>
				<div class="col-sm-3">
					<input type="text" name="depart" id="depart" class="form-control" required>		<!-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 -->
					~
					<input type="text" name="fin" id="fin" class="form-control" required>		<!-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 -->
				</div></div>
				
				
			<div class="form-group row"> 
				<label class="col-sm-2">계획한 여행장소</label>
				<div class="col-sm-3">
					<input type="text" name="mplace" id="mplace" class="form-control" required>		<!-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 -->
				</div></div>
				
				
			<div class="form-group row"> 
				<label class="col-sm-2">참가인원</label>
				<div class="col-sm-3">
					<input type="text" name="mnum" id="mnum" class="form-control" required>		<!-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 -->
				</div></div>
				
				
			<div class="form-group row"> 
				<label class="col-sm-2">내용</label>
				<div class="col-sm-3">
					<input type="text" name="mtext" id="mtext" class="form-control"
						   required
						   oninput="setCustomValidity('')"
						   oninvalid="this.setCustomValidity('${mtextMsg }')">							<!-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 -->
				</div></div>
					
		
			<div class="form-group row mt-4"> 
				<div class="col text-right">
					<a href="#" class="btn btn-secondary cancelBtn" onclick="history.back()">취소</a>
					<input type="submit" class="btn btn-info regBtn" value="등록"/> 
				</div></div>
		</form></div></main>

<%@ include file="../include/footer.jsp" %>

</body>
</html>