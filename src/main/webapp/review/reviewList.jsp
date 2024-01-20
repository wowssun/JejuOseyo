<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	           <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰목록</title>
</head>
<body>
<!-- 숙소상세조회에 include 시켜야됨 -->

<c:if test="${!empty msg }">
	<script>
		alert('${msg}');
	</script>
	<%session.removeAttribute("msg"); %>
	</c:if>
    <h3 >
  	⭐리뷰목록
    </h3>

<form action="/jejuOseyo/Review/ReviewInfo.do" method="post">
		<input type="hidden" name="pageNum" value="${pageNum }">
<c:if test="${empty rvList}">
    <div class="row">
       &nbsp;&nbsp; 등록된 리뷰가 없습니다.
    </div>
</c:if>

<c:if test="${not empty rvList}">
<div class="col-4 ml-auto text-right"> 
    <span class="btn btn-secondary">
        전체 게시물 ${totalCnt }건</span>
</div>

    <table class="table table-hover">
        <thead>
            <tr>
                <th>리뷰번호</th>
                <th>별점</th>
                <th>리뷰제목</th>
                <th>작성자</th>
                <th>작성일자</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${rvList}" var="rv">
            
			<c:set var="pageNum" value="${pageNum}"/>
			
                <tr>
                    <td>${rv.rvNo}</td>
                    <td>${rv.star }</td>
                    <td><a href="/jejuOseyo/Review/ReviewInfo.do?rmNo=${rv.rmNo }&rvNo=${rv.rvNo }&pageNum=${pageNum}&keyword=${param.keyword}&checkIn=${param.checkIn}&checkOut=${param.checkOut}&people=${param.people}">${rv.title}</a></td>
                    <td>${rv.mid}</td>
                    <td>${rv.regDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
   <%-- 페이징 --%>
			<ul class="pagination justify-content-center">
				<%-- 이전 버튼 --%>
				<c:if test="${prev }">
				<li class="page-item">
					<a href="/Review/RoomInfo.do?rmNo=${param.rmNo }&pageNum=${start - 1 }" 
					   class="page-link">&laquo; Previous</a></c:if>
				
				<%-- 페이지 번호 버튼 --%>
				<c:forEach begin="${start }" end="${end }" var="i">
				<c:url var="link" value="/Review/RoomInfo.do?rmNo=${param.rmNo }&pageNum=${i }"/>
				<li class="page-item ${pageNum == i ? 'active' : '' }">
					<a href="${pageNum == i ? '#' : link }" 
					   class="page-link">${i }</a>
				</c:forEach>
				
				<%-- 다음 버튼 --%>
				<c:if test="${next }">
				<li class="page-item">
					<a href="/Review/RoomInfo.do?rmNo=${param.rmNo }&pageNum=${end+1 }"  
					   class="page-link">Next &raquo;</a></c:if>
			</ul>
</c:if>
</form>

</body>
</html>