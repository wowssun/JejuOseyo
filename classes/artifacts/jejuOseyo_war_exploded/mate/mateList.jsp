<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mateList.jsp</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>


<!-- 게시물 등록/수정/삭제 완료 메시지가 있는 경우 알림창으로 출력 -->
<c:if test="${!empty msg}">
<script>
		alert('${msg}');
//		if(msg != empty){ alert("게시물이 등록되었습니다."); }
</script>
<%session.removeAttribute("msg"); %>
</c:if>


<div class="jumbotron">
	<div class="container">
		<h1 class="display-4">
			여행메이트 게시판</h1></div></div>

<main role="main">
	<div class="container">
		<form action="/mate/list.do" method="post">
		<input type="hidden" name="pageNum" value="${pageNum }">
				
		<!-- 게시물이 없는 경우 -->
		<c:if test="${empty mateList }">
		<div class="row">
			등록된 게시물이 없습니다.</div>
		</c:if>
		
		<!-- 그렇지 않은 경우 -->
		<c:if test="${!empty mateList }">
		<div class="row mb-3">
			<div class="col-8"> 
				<select name="type">
					<option value="mnick" ${type eq 'mnick' ? 'selected' : '' }>닉네임</option>
					<option value="mtitle" ${type eq 'mtitle' ? 'selected' : '' }>제목</option>
					<option value="mtext" ${type eq 'mtext' ? 'selected' : '' }>내용</option>
				</select>
				<input type="text" name="keyword" value="${!empty keyword ? keyword : '' }">
				<input type="submit" class="btn btn-info btn-sm" value="검색">	
				</div>
			<div class="col-4 text-right"> 
				<span class="badge badge-secondary">
					전체 게시물 ${totalCnt }건</span></div></div>
					
		<div>
			<table class="table table-hover">
			<thead>
				<tr><th>번호</th>
					<th>제목</th>
					<th>닉네임</th>
					<th>작성일</th>
					<th>조회수</th></tr></thead>
				
			<tbody>
			<c:forEach items="${mateList }" var="mateMvo">
			<c:url var="viewLink" value="./MateView.do">
					<c:param name="mno" value="${mateMvo.mno }"/>
					<c:param name="mnick" value="${mateMvo.mnick }"/>
			</c:url>
			
			
			<tr>
				<td>${mateMvo.mno }</td>				
				<td><a href="${viewLink }">${mateMvo.mtitle }</a></td>
				<td>${mateMvo.mnick }</td>
				<td>${mateMvo.mdate }</td>
				<td>${mateMvo.mhit }</td></tr>				
			</c:forEach>
			</tbody>
			</table>
			

		</div>
		</c:if>
		
		<div class="text-right">
			<a href="./mateWriteForm.do" class="btn btn-info btn-sm">
				글 작성하기</a>
		</div>
		</form>
	</div></main>


<%@ include file="../include/footer.jsp" %>
	
</body>
</html>