<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>hoyeyakList.jsp</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			숙소 예약 관리</h1></div></div>

	<main role="main">
		<div class="container">
		<form action="/jejuOseyo/Yeyak/hoyeyakList.do" method="post">	
		<!-- 예약목록이 비어 있는 경우-->
		<c:if test="${empty hoyeyakList }">
			<div class="row">
				<div class="col" align="center"> 
					<p class="alert alert-danger p-5">
						예약목록이 비어있습니다.</p>
				</div>
			</div>
		</c:if>
		
		<!-- 그렇지 않은 경우 -->
		<c:if test="${!empty hoyeyakList}">
		<br>
		<br>
		<br>
		
	<c:forEach items="${hoyeyakList }" var="yvo">
		<c:url var="hoyeviewlink" value="/Yeyak/hoyeyakView.do">
				<c:param name="yno" value="${yvo.yno  }"/>
				<c:param name="pageNum" value="${pageNum }"/>
		</c:url>			
		<c:set var="pageNum" value="${pageNum }"/>
		<div class="container col-9 border p-3 shadow-sm mt-3">
			<c:set var = "yeyakDate" value ='${yvo.pvo.payDate }' />
 			<c:set var = "yeyakDt" value = "${fn:substring(yeyakDate, 0, 10)}" />
 		<div class="text-right">${yeyakDt }</div>
		 	<div class="row">
				<div class="col-md-4">
				<img src="../resources/images/${yvo.rvo.img }" style="width:100%"></div>
					<div class="col-md-8">
						<h4 style="margin-top: 50px"><a href="${hoyeviewlink }" >${yvo.rvo.rmName }</a></h4>
						<p>게스트 ${yvo.yeGuest } 명 </p>
						<p><span>${yvo.yeCheckIn } </span> ~ <span>${yvo.yeCheckOut } </span></p>
						<p><fmt:formatNumber value="${yvo.rvo.price }" type="currency"/></p>
				</div>	
			</div>
		</div>
		
		</c:forEach>
		
			<%-- 페이징 --%>
			<ul class="pagination justify-content-center mt-5">
			
				<%-- 이전 버튼 --%>
				
				<c:if test="${prev }">
				<li class="page-item">
					<a href="/Yeyak/hoyeyakList.do?pageNum=${start - 1 }" 
					   class="page-link">&laquo; Previous</a></c:if>
				
				<%--페이지 번호 버튼 --%>
				
				<c:forEach begin="${start }" end="${end }" var="i">
				<c:url var="link" value="/Yeyak/hoyeyakList.do?pageNum=${i }">			
				</c:url>
				<li class="page-item ${pageNum == i ? 'active' : '' }">
					<a href="${pageNum == i ? '#' : link }" class="page-link">${i }</a>
				</c:forEach>
				
				<%-- 다음 버튼 --%>
				
				<c:if test="${next }">
				<li class="page-item">
					<a href="/Yeyak/hoyeyakList.do?pageNum=${end + 1 }" 
					   class="page-link">Next &raquo;</a></c:if>
			
			</ul>
				</c:if>	
				</form>
				</div>
		</main>		
							


<%@ include file="../include/footer.jsp" %>
		
		
		
		
		



</body>
</html>