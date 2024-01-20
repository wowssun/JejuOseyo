<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전체 숙소목록</title>
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
			전체 숙소 목록 </h1></div></div>	
	<form action="/jejuOseyo/Room/AllRoomList.do" method="post">
			<input type="hidden" name="pageNum" value=1>
<c:if test="${empty allRoomList}">
    <div class="row">
       &nbsp;&nbsp;&nbsp; 등록된 숙소가 없습니다.
    </div>
</c:if>

<c:if test="${not empty allRoomList}">
		<div class="row mb-3">
			<div class="col-8"> 
				<input type="text" name="keyword" 
					   value="${!empty keyword ? keyword : ''}"  style="margin-left: 10px;">
			<input type="button" class="btn btn-info btn-sm" value="검색" onclick="submitSearchForm()">	
				</div>
			<div class="col-4 text-right"> 
				<span class="btn btn-secondary">
					전체 게시물 ${totalCnt }건</span></div></div>
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
            <c:forEach items="${allRoomList}" var="all">
            
			<c:set var="pageNum" value="${pageNum}"/>
			<c:set var="keyword" value="${param.keyword }"/>
			
                <tr>
                    <td>${all.rmNo}</td>
                    <td style="width: 20%;"><img src="../resources/images/${all.img }" style="width:30%"></td>
                 <td>
    ⭐<c:set var="average" value="${all.rvCnt != 0 ? all.starTotal / all.rvCnt : 0}" />
    <fmt:formatNumber value="${average}" maxFractionDigits="1" />
</td>

                    <td><a href="/jejuOseyo/Room/RoomInfo.do?rmNo=${all.rmNo }&pageNum=${pageNum}">${all.rmName}</a></td>
                    <td><fmt:formatNumber value="${all.price}" type="currency"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <%-- 페이징 --%>
			<ul class="pagination justify-content-center">
				<%-- 이전 버튼 --%>
				<c:if test="${prev }">
				<li class="page-item">
					<a href="/Room/AllRoomList.do?pageNum=${start - 1 }&keyword=${keyword}" 
					   class="page-link">&laquo; Previous</a></c:if>
				
				<%-- 페이지 번호 버튼 --%>
				<c:forEach begin="${start }" end="${end }" var="i">
				<c:url var="link" value="/Room/AllRoomList.do?pageNum=${i}&keyword=${param.keyword}"/>
				<li class="page-item ${pageNum == i ? 'active' : '' }">
					<a href="${pageNum == i ? '#' : link }" 
					   class="page-link">${i }</a>
				</c:forEach>
				
				<%-- 다음 버튼 --%>
				<c:if test="${next }">
				<li class="page-item">
					<a href="/Room/AllRoomList.do?pageNum=${end+1 }&keyword=${keyword}"  
					   class="page-link">Next &raquo;</a></c:if>
			</ul>
</c:if>

			<div class="text-right">
		</div>
</form>
<script>
function submitSearchForm() {
    document.getElementById("searchForm").submit();
}
</script>
<%@ include file="../include/footer.jsp" %>
</body>
</html>