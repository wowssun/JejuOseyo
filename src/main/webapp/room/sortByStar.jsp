<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>검색 숙소목록</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

	<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			검색 숙소 목록 </h1></div></div>	
	<form action="/jejuOseyo/Room/SearchRoomList.do?pageNum=${pageNum }" method="post">
<div class="row mb-3">
			<div class="col-8"> 
				<input type="text" name="keyword" required placeholder="지역/숙소명" style="margin-left: 20px;">
				<input type="number" name="people" required placeholder="인원 수"><br>
					　 &nbsp;체크인/아웃 날짜 
				<input type="date" name="checkIn" required  style="margin-left: 10px;  margin-top: 10px;">
				<input type="date" name="checkOut" required style="margin-top: 10px;" >
				<input type="submit" class="btn btn-info btn-sm" value="검색">	
				</div>
			<div class="col-4 text-right"> 
				<span class="btn btn-secondary">
					전체 게시물 ${totalCnt }건</span>
					 <a class="btn btn-primary" href="/jejuOseyo/Room/SearchRoomList.do?pageNum=${1}&keyword=${keyword }&people=${people }&checkIn=${param.checkIn }&checkOut=${param.checkOut }">ㄱㄴㄷ순 정렬</a></div></div>
<c:if test="${empty searchRoomList}">
    <div class="row">
       		 <p style="font-size:30px;">&nbsp;&nbsp;&nbsp;&nbsp;검색된 숙소가 없습니다.
    </div>
</c:if>

<c:if test="${not empty searchRoomList}">
	
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
            <c:forEach items="${searchRoomList}" var="search">
            
			<c:set var="pageNum" value="${pageNum}"/>
			<c:set var="keyword" value="${param.keyword }"/>
			<c:set var="checkIn" value="${param.checkIn }"/>
			<c:set var="checkOut" value="${param.checkOut }"/>
			<c:set var="people" value="${param.people }"/>
			
			
			
                <tr>
                    <td>${search.rmNo}</td>
                    <td style="width: 20%;"><img src="../resources/images/${search.img }" style="width:30%"></td>
                    <td>
    ⭐<c:set var="average" value="${search.rvCnt != 0 ? search.starTotal / search.rvCnt : 0}" />
    <fmt:formatNumber value="${average}" maxFractionDigits="1" />
</td>
 <td><a href="/jejuOseyo/Room/RoomInfo.do?rmNo=${search.rmNo }&checkIn=${checkIn}&checkOut=${checkOut}&people=${people}&keyword=${keyword }&pageNum=${pageNum}">${search.rmName}</a></td>                    <td><fmt:formatNumber value="${search.price}" type="currency"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <%-- 페이징 --%>
			<ul class="pagination justify-content-center">
				<%-- 이전 버튼 --%>
				<c:if test="${prev }">
				<li class="page-item">
					<a href="/Room/SortByStar.do?pageNum=${start - 1 }&keyword=${keyword }&people=${people }&checkIn=${checkIn }&checkOut=${checkOut }" 
					   class="page-link">&laquo; Previous</a></c:if>
				
				<%-- 페이지 번호 버튼 --%>
				<c:forEach begin="${start }" end="${end }" var="i">
				<c:url var="link" value="/Room/SortByStar.do?pageNum=${i}&keyword=${keyword }&people=${people }&checkIn=${checkIn }&checkOut=${checkOut }"/>
				<li class="page-item ${pageNum == i ? 'active' : '' }">
					<a href="${pageNum == i ? '#' : link }" 
					   class="page-link">${i }</a>
				</c:forEach>
				
				<%-- 다음 버튼 --%>
				<c:if test="${next }">
				<li class="page-item">
					<a href="/Room/SortByStar.do?pageNum=${end+1 }&keyword=${keyword }&people=${people }&checkIn=${checkIn }&checkOut=${checkOut }"  
					   class="page-link">Next &raquo;</a></c:if>
			</ul>
</c:if>

			<div class="text-right">
		</div>
</form>
</body>
<%@ include file="../include/footer.jsp" %>
</html>