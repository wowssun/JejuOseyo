<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myMateApply.jsp</title>
</head>
<body>


<%@ include file="../include/header.jsp" %>

<div class="jumbotron">
	<div class="container">
		<h1 class="display-4">
			여행메이트 참여신청 내역 조회</h1></div></div>

<main role="main">
	<div class="container">

		<!-- 신청한 게시물이 없는 경우 -->
		<c:if test="${empty mateapplyList }">
		<div class="row">
			신청한 내역이 없습니다.</div>
		</c:if>
		
		<!-- 그렇지 않은 경우 -->
		<c:if test="${!empty mateapplyList }">
	
		<div>
			<table class="table table-hover">
			<thead>
				<tr><th>번호</th>
					<th>신청한 게시글</th>
					<th>상태</th></tr></thead>
				
			<tbody>
			<c:forEach items="${mymateapplyList }" var="mateapplyvo">
			<c:url var="viewLink" value="./MymateApply.do">
					<c:param name="mno" value="${mateapplyvo.mno }"/>
					<c:param name="mnick" value="${mateapplyvo.mnick }"/>
					<c:param name="pageNum" value="${pageNum }"/>
					<c:param name="type" value="${type }"/>
					<c:param name="keyword" value="${keyword }"/>
			</c:url>
			
			
			<tr>
				<td>${mateapplyvo.ano }</td>				
				<td><a href="${viewLink }">${mateapplyvo.mtitle }</a></td>
				<td>${mateapplyvo.mnow }</td></tr>
			</c:forEach>
			</tbody>
			</table>
			
		</div>
		</c:if>
		
	</div></main>

<%@ include file="../include/footer.jsp" %>
	
</body>
</html>