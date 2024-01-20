<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>matecmWrite.jsp</title>
</head>
<body>

<main role="main">
	<div class="container">
		<form action="./matecmWrite.do" method="post" class="form-horizontal">
		
			
			<div class="form-group row"> 
				<label class="col-sm-2">닉네임</label>
				<div class="col-sm-3">
					<input type="text" name="mnick" id="mnick" class="form-control" 
						   required readonly value="${sid }">	
				</div></div>
				
				
				
			<div class="form-group row"> 
				<label class="col-sm-2">내용</label>
				<div class="col-sm-3">
					<input type="text" name="mcmtext" id="mcmtext" class="form-control"
						   required>							<!-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 -->
				</div></div>
					
		
			<div class="form-group row mt-4"> 
				<div class="col text-right">
					<a href="#" class="btn btn-secondary cancelBtn" onclick="history.back()">취소</a>
					<input type="submit" class="btn btn-info regBtn" value="등록"/> 
				</div></div>
		</form></div></main>


</body>
</html>