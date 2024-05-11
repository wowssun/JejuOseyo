<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 숙소목록</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<!-- 게시물 등록/수정/삭제 완료 메시지가 있는 경우 알림창으로 출력 -->
<c:if test="${!empty msg }">
	<script>
		alert('${msg}');
	</script>
	<%session.removeAttribute("msg"); %>
	</c:if>
	<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			나의 숙소 목록 </h1></div></div>	
		<form action="/jejuOseyo/Room/AllRoomList.do" method="post">
		<input type="hidden" name="pageNum" value="${pageNum }">
<c:if test="${empty myRoomList}">
    <div class="row">
        &nbsp; &nbsp;&nbsp;등록된 숙소가 없습니다.
    </div>
</c:if>

<c:if test="${not empty myRoomList}">
<div class="col-4 ml-auto text-right"> 
    <span class="btn btn-secondary">
        전체 게시물 ${totalCnt }건</span>
</div>
    <table class="table table-hover">
        <thead>
            <tr>
                <th>숙소번호</th>
                <th>숙소 이미지</th>
                <th>별점</th>
                <th>숙소이름</th>
                <th>가격</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${myRoomList}" var="my">
            
			<c:set var="pageNum" value="${pageNum}"/>
			
                <tr>
                    <td>${my.rmNo}</td>
                    <td style="width: 20%;"><img src="../resources/images/${my.img }" style="width:30%"></td>
                <td>
    ⭐<c:set var="average" value="${my.rvCnt != 0 ? my.starTotal / my.rvCnt : 0}" />
    <fmt:formatNumber value="${average}" maxFractionDigits="1" />
</td>
                    <td><a href="/jejuOseyo/Room/RoomInfo.do?rmNo=${my.rmNo }&pageNum=${pageNum}">${my.rmName}</a></td>
                    <td><fmt:formatNumber value="${my.price}" type="currency"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
   <%-- 페이징 --%>
			<ul class="pagination justify-content-center">
				<%-- 이전 버튼 --%>
				<c:if test="${prev }">
				<li class="page-item">
				<%--로그인한 호스트 아이디로 나중에 변경 --%>
					<a href="/Room/MyRoomList.do?hid=hid&pageNum=${start - 1 }" 
					   class="page-link">&laquo; Previous</a></c:if>
				
				<%-- 페이지 번호 버튼 --%>
				<c:forEach begin="${start }" end="${end }" var="i">
				<%--로그인한 호스트 아이디로 나중에 변경 --%>
				<c:url var="link" value="/Room/MyRoomList.do?hid=${sid}&pageNum=${i }"/>
				<li class="page-item ${pageNum == i ? 'active' : '' }">
					<a href="${pageNum == i ? '#' : link }" 
					   class="page-link">${i }</a>
				</c:forEach>
				
				<%-- 다음 버튼 --%>
				<c:if test="${next }">
				<li class="page-item">
				<%--로그인한 호스트 아이디로 나중에 변경 --%>
					<a href="/Room/MyRoomList.do?hid=hid&pageNum=${end+1 }"  
					   class="page-link">Next &raquo;</a></c:if>
			</ul>
</c:if>
</form>
			<div class="text-right">
			<a href="/jejuOseyo/Room/RoomAddForm.do" class="btn btn-info">
				숙소등록</a>
		</div>
<%@ include file="../include/footer.jsp" %>
</body>
</html>