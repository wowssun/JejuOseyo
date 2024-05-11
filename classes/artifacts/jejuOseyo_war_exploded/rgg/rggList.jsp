<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>숙소 위시리스트 목록</title>

<style>
td:not(:first-child) { text-align: center;}
</style>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			숙소 위시리스트 </h1></div></div>		

	<main role="main">
	<div class="container">
		<!-- 위시리스트가 비어 있는 경우 -->
		<c:if test="${empty rggList}">
			<div class="row">
				<div class="col" align="center"> 
					<p class="alert alert-danger p-5">
						위시리스트에 담긴 숙소가 없습니다.</p>
				</div>
			</div>
		</c:if>
		
		<!-- 그렇지 않은 경우 -->
		<c:if test="${not empty rggList}">
<div class="row">
    <div class="col-8 text-left">
        <a href="/jejuOseyo/Rgg/RggClear.do?mid=${sid }&pageNum=${pageNum }" class="btn btn-danger">위시리스트 비우기</a>
    </div>
    <div class="col-4 text-right">
       <span class="btn btn-secondary">
            전체 게시물 ${totalCnt }건
        </span>
    </div>
</div>

			
			<div style="padding-top:50px">
				<table class="table table-hover">
					<thead>
						<tr>
				<th>위시리스트 번호</th>
                <th>숙소 이미지</th>
                <th>별점</th>
                <th>숙소이름</th>
                <th>가격</th>
                <th>비고</th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${rggList}" var="rgg">
            <c:url var="roomListLink" value="/Room/SearchRoomList.do">
					<!-- pageNum=1&keyword=&people=1&checkIn=2023-08-01&checkOut=2023-08-02 -->
						<c:param name="pageNum" value="1"/>
						<c:param name="people" value="${rgg.people }"/>
						<c:param name="checkIn" value="${rgg.checkIn }"/>
						<c:param name="checkOut" value="${rgg.checkOut }"/>
						<c:param name="keyword" value="${rgg.rmvo.rmName }"/>		
					</c:url>
			<c:set var="pageNum" value="${pageNum}"/>
			<c:set var="keyword" value="${param.keyword }"/>
			
                <tr>
                    <td>${rgg.rggNo}</td>
                    <td style="width: 20%;"><img src="../resources/images/${rgg.rmvo.img }" style="width:30%"></td>
                    <td>
   			 ⭐<c:set var="average" value="${rgg.rmvo.rvCnt != 0 ? rgg.rmvo.starTotal / rgg.rmvo.rvCnt : 0}" />
    				<fmt:formatNumber value="${average}" maxFractionDigits="1" />
				</td>
                    <td><a href="${roomListLink }" >${rgg.rmvo.rmName}</a></td>
                    <td><fmt:formatNumber value="${rgg.rmvo.price}" type="currency"/></td>
                    <td><a href="/jejuOseyo/Rgg/RggRemove.do?rggNo=${rgg.rggNo }&pageNum=${pageNum}&mid=${sid}" class="badge badge-danger">삭제</a></td>
                </tr>
            </c:forEach>
					
					</tbody>
				</table>
			</div>
		</c:if>
		
		<!-- 쇼핑 계속하기 -->
		<div class="row">
			<div class="col-8"> 
				<a href="../index.jsp" class="btn btn-secondary">숙소 검색하기</a>
			</div>
		</div>
	</div>
	  <%-- 페이징 --%>
			<ul class="pagination justify-content-center">
				<%-- 이전 버튼 --%>
				<c:if test="${prev }">
				<li class="page-item">
					<a href="/Rgg/RggList.do?pageNum=${start - 1 }&mid=${sid }" 
					   class="page-link">&laquo; Previous</a></c:if>
				
				<%-- 페이지 번호 버튼 --%>
				<c:forEach begin="${start }" end="${end }" var="i">
				<c:url var="link" value="/Rgg/RggList.do?pageNum=${i}&mid=${sid }"/>
				<li class="page-item ${pageNum == i ? 'active' : '' }">
					<a href="${pageNum == i ? '#' : link }" 
					   class="page-link">${i }</a>
				</c:forEach>
				
				<%-- 다음 버튼 --%>
				<c:if test="${next }">
				<li class="page-item">
					<a href="/Rgg/RggList.do?pageNum=${end+1 }&mid=${sid }"  
					   class="page-link">Next &raquo;</a></c:if>
			</ul>
</main>


<%@ include file="../include/footer.jsp" %>
	
</body>
</html>

