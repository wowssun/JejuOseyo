<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mateView.jsp</title>
</head>
<body>
	
<%@ include file="../include/header.jsp" %>

<div class="jumbotron">
	<div class="container">
		<h1 class="display-4">여행메이트 상세조회</h1></div></div>

<main role="main">
	<div class="container">
		<form action="./mateModify.do" method="post" class="form-horizontal">
		
		
			<!-- 필수 입력 항목 안내 메시지 -->
			<c:set var="mtitleMsg" value="제목을 입력해주세요."/>
			<c:set var="mtextMsg" value="내용을 입력해주세요."/>
			
			
			
			<div class="form-group row"> 
				<label class="col-sm-2">제목</label>
				<div class="col-sm-3">
					<input type="text" name="mtitle" id="mtitle" class="form-control" 
						   required
						   oninput="setCustomValidity('')"
						   oninvalid="this.setCustomValidity('${mtitleMsg }')"							<%-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 --%>
							value="${mvo.mtitle}">
				</div></div>
			
			
			<div class="form-group row"> 
				<label class="col-sm-2">닉네임</label>
				<div class="col-sm-3">
					<input type="text" name="mnick" id="mnick" class="form-control" 
						   required readonly value="${mvo.mnick }">	
				</div></div>
		
			
			<div class="form-group row"> 
				<label class="col-sm-2">계획한 여행기간</label>
				<div class="col-sm-3">
					<input type="text" name="depart" id="depart" class="form-control" required value="${mvo.depart}">		<!-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 -->
					~
					<input type="text" name="fin" id="fin" class="form-control" required value="${mvo.fin}">		<!-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 -->
				</div></div>
				
				
			<div class="form-group row"> 
				<label class="col-sm-2">계획한 여행장소</label>
				<div class="col-sm-3">
					<input type="text" name="mplace" id="mplace" class="form-control" required value="${mvo.mplace}">		<!-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 -->
				</div></div>
				
				
			<div class="form-group row"> 
				<label class="col-sm-2">참가인원</label>
				<div class="col-sm-3">
					<input type="text" name="mnum" id="mnum" class="form-control" required value="${mvo.mnum}">		<!-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 -->
				</div></div>
			
				
			<div class="form-group row"> 
				<label class="col-sm-2">내용</label>
				<div class="col-sm-8">
					<textarea name="mtext" id="mtext" class="form-control" rows="5" cols="50" 
						   required
						   oninput="setCustomValidity('')"
						   oninvalid="this.setCustomValidity('${mtextMsg }')">${mvo.mtext }</textarea>							<!-- 값을 입력하지 않고 등록버튼 눌렀을 때 표시 -->
				</div></div>
					
		
			<div class="form-group row mt-4"> 
				<div class="col text-right">
					<a href="./mateList.do?pageNum=${pageNum }&type=${param.type}&keyword=${param.keyword}" class="btn btn-secondary cancelBtn">목록</a>
					<!-- 자신이 작성한 글인 경우 수정/삭제 버튼 표시 -->
					<c:if test="${mvo.mnick eq sid}">
							<input type="button" class="btn btn-danger" onclick="delChk()" value="삭제"/> 
							<input type="submit" class="btn btn-info" value="수정"/> 
					</c:if>
				</div></div>
				<input type="hidden" name="mno" value="${mvo.mno }">
				<input type="hidden" name="pageNum" value="${pageNum }">
				<input type="hidden" name="type" value="${param.type }">
				<input type="hidden" name="keyword" value="${param.keyword }">
		</form></div></main>

<%@ include file="matecmList.jsp" %>
<%@ include file="../include/footer.jsp" %>

</body>
</html>