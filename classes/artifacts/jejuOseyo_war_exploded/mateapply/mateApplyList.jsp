<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mateApplyList.jsp</title>
</head>
<body>


<%@ include file="../include/header.jsp" %>

<div class="jumbotron">
	<div class="container">
		<h1 class="display-4">
			여행메이트 참여자 내역 조회</h1></div></div>

<main role="main">
	<div class="container">

		<!-- 참여자가 없는 경우 -->
		<c:if test="${empty mateapplyList }">
		<div class="row">
			신청이 없습니다.</div>
		</c:if>
		
		<!-- 그렇지 않은 경우 -->
		<c:if test="${!empty mateapplyList }">
	
		<div>
			<table class="table table-hover">
			<thead>
				<tr><th>닉네임</th>
					<th>성별</th>
					<th>전화번호</th>
					<th>요청내역</th></tr></thead>
				
			<tbody>
			<c:forEach items="${mateApplyList }" var="mateappyvo">
			
			<tr>
				<td>${mateapplyvo.mnick }</td>				
				<td>${mateapplyvo.mphone }</td>
				<td> <!-- 버튼 열 시작 -->
                        <input type="submit" class="btn btn-primary btn-sm" value="수락">
                        <input type="submit" class="btn btn-danger btn-sm" value="거절">
                </td> <!-- 버튼 열 끝 -->
            </tr>
			</c:forEach>
			</tbody>
			</table>
			
		</div>
		</c:if>
		
	</div></main>

<%@ include file="../include/footer.jsp" %>
	

</body>
</html>