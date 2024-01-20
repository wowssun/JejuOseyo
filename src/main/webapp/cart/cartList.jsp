<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cartList.jsp</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

<!-- 장바구니 담기/삭제/비우기 메시지가 있는 경우 알림창으로 출력 -->
<c:if test="${!empty msg }">
	<script>
		alert('${msg}');
	</script>
	<%session.removeAttribute("msg"); %>
</c:if>
	
	<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			장바구니</h1></div></div>
		
	<main role="main">
		<div class="container">
		<form action="/jejuOseyo/Cart/cartList.do" method="post">
		<input type="hidden" name="pageNum" value="${pageNum }">
		
		<!-- 장바구니가 비어 있는 경우 / 여기 다시 처리하기 (노션에 적어둠)-->
		<c:if test="${empty cartList }">
			<div class="row">
				<div class="col" align="center"> 
					<p class="alert alert-danger p-5">
						장바구니에 담긴 숙소가 없습니다.</p>
				</div>
			</div>
		</c:if>
		
	<!-- 그렇지 않은 경우 -->
		<c:if test="${!empty cartList}">	
	<div class="row">
		<div class="col-8"> 
			<a href="javascript:cartRemoveAll()" class="btn" style="background-color:#C7EDB9">
			장바구니 비우기</a>
					</div></div>
					
					<br>
					<br>
					<br>
					
					<c:forEach items="${cartList }" var="cvo">
					<c:url var="roomListLink" value="/Room/SearchRoomList.do">
					<!-- pageNum=1&keyword=&people=1&checkIn=2023-08-01&checkOut=2023-08-02 -->
						<c:param name="pageNum" value="1"/>
						<c:param name="people" value="${cvo.guest }"/>
						<c:param name="checkIn" value="${cvo.checkIn }"/>
						<c:param name="checkOut" value="${cvo.checkOut }"/>
						<c:param name="keyword" value="${cvo.rvo.rmName }"/>		
					</c:url>
					<c:set var="pageNum" value="${pageNum }"/>
					<div class="container col-9 border p-3 shadow-sm mt-3">
						 <div class="row">
							<div class="col-md-4">
								<img src="../resources/images/${cvo.rvo.img }" style="width:100%"></div>
							 	<div class="col-md-8">				
									<h4 style="margin-top: 50px"><a href="${roomListLink }" >${cvo.rvo.rmName }</a></h4>
									<p>게스트 ${cvo.guest } 명 </p>
									<p><span>${cvo.checkIn } </span> ~ <span>${cvo.checkOut } </span></p>
									<p><fmt:formatNumber value="${cvo.rvo.price }" type="currency"/></p>
								</div>	
					</div>
					
				     <a href="javascript:yeyak('${cvo.rvo.rmName }',${cvo.cno },${pageNum })" class="btn btn-info" >
						예약</a>
						<div class="float-right">
					      <a href="javascript:cartRemove(${cvo.cno })" class="btn btn-danger">
							삭제</a>  
			  			</div> 
			  				 
					
					</div>
				</c:forEach>
								
			
				
				<%-- 페이징 --%>
			<ul class="pagination justify-content-center mt-5">
			
				<%-- 이전 버튼 --%>
				
				<c:if test="${prev }">
				<li class="page-item">
					<a href=" /Cart/cartList.do?pageNum=${start - 1 }" 
					   class="page-link">&laquo; Previous</a></c:if>
				
				<%--페이지 번호 버튼 --%>
				
				<c:forEach begin="${start }" end="${end }" var="i">
				<c:url var="link" value="/Cart/cartList.do?pageNum=${i }">			
				</c:url>
				<li class="page-item ${pageNum == i ? 'active' : '' }">
					<a href="${pageNum == i ? '#' : link }" class="page-link">${i }</a>
				</c:forEach>
				
				<%-- 다음 버튼 --%>
				
				<c:if test="${next }">
				<li class="page-item">
					<a href="/Cart/cartList.do?pageNum=${end + 1 }" 
					   class="page-link">Next &raquo;</a></c:if>
			
			</ul>
				</c:if>	
				</form>
				</div>
		</main>
		
		<%@ include file="../include/footer.jsp" %>
</body>
</html>