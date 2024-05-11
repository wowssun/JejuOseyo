<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="/jejuOseyo/resources/js/script.js"></script>
<!--폰트 적용 GmarketSansMedium  -->
<style type="text/css">
@font-face {
	font-family: 'GmarketSansMedium';
	src:
		url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}

body {
	--bs-font-sans-serif: margin:0;
	padding: 0;
	font-size: 18px;
	line-height: 1.6;
	font-family: 'GmarketSansMedium', 'Apple SD Gothic Neo', '돋움', Dotum,
		Arial, Sans-serif;
	color: #464646;
	letter-spacing: 0;
	-webkit-text-size-adjust: none;
	font-weight: 400;
}
}
}
</style>

<!-- String sid = (String)session.getAttribute("sid");
       String mid = (String)session.getAttribute("mid");
       String hid = (String)session.getAttribute("hid");; -->

<nav class="navbar navbar-expand navbar-dark"
	style="background-color: #F5F5DC;">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="/jejuOseyo" style="color: #000000;">Home</a>
		</div>
		<div>
			<ul class="navbar-nav mr-auto">

				<c:choose>
					<c:when test="${empty sid}">
						<!-- 로그인하지 않은 경우 -->

						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="/jejuOseyo/common/memLogin.jsp">회원
								로그인</a></li>

						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="/jejuOseyo/common/hostLogin.jsp">호스트
								로그인</a></li>

						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="/jejuOseyo/member/memJoin.jsp">회원가입</a></li>

						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="/jejuOseyo/host/hostJoin.jsp">호스트
								회원가입</a></li>

						<li class="nav-item"><a class="nav-link" href="/jejuOseyo/Free/FreeList.do?pageNum=1&type=&keyword"
							style="color: #000000;">자유게시판</a></li>
							
						<li class="nav-item"><a class="nav-link" 
							style="color: #000000;"
							href="/jejuOseyo/Mate/List.do">여행메이트 게시판</a></li>	

					</c:when>

					<c:when test="${sid eq 'admin'}">
						<!-- 관리자로 로그인한 경우 -->

						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="#">[${sid }]님(관리자)</a></li>

						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="/jejuOseyo/common/logout.jsp">로그아웃</a></li>

						<li class="nav-item"><a class="nav-link" style="color: #000000;"
							href="/jejuOseyo/Admin/AdminView.do?mid=${sessionScope.sid}">관리자마이페이지</a></li>

						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="/jejuOseyo/Admin/MemList.do?pageNum=1&type=&keyword=">회원관리</a></li>

						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="/jejuOseyo/admin/hostMng.jsp">호스트관리</a></li>

						<li class="nav-item"><a class="nav-link"
							style="color: #000000;"
							href="/jejuOseyo/Room/AllRoomList.do?pageNum=1&keyword=">전체숙소목록</a></li>


						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="/jejuOseyo/Free/FreeList.do?pageNum=1&type=&keyword">자유게시판</a></li>
							

					</c:when>


					<c:when test="${sid eq hid}">
						<!-- 호스트로 로그인한 경우 -->
						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="#">[${sid }]님(호스트)</a></li>

						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="/jejuOseyo/common/logout.jsp">로그아웃</a></li>

						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="/jejuOseyo/host/hostMypage.jsp">마이페이지</a></li>

						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="/jejuOseyo/room/roomAdd.jsp">숙소등록</a>
						</li>
						<li class="nav-item"><a class="nav-link"
							style="color: #000000;"
							href="/jejuOseyo/Room/MyRoomList.do?hid=${hid }&pageNum=1">나의 숙소</a></li>

					</c:when>


					<c:when test="${sid eq mid}">
						<!-- 회원으로 로그인한 경우 -->
						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="#">[${sid }]님(회원)</a></li>

						<li><a class="nav-link" style="color: #000000;"
							href="/jejuOseyo/common/logout.jsp">로그아웃</a></li>
							
						<li><a class="nav-link" style="color: #000000;"
							href="/jejuOseyo/member/memMypage.jsp">마이페이지</a></li>
							
						<li class="nav-item"><a class="nav-link"
							style="color: #000000;"
							href="/jejuOseyo/Cart/cartList.do?pageNum=1&mid=${sid }">장바구니</a></li>
							
							
						<li class="nav-item"><a class="nav-link"
							style="color: #000000;" href="/jejuOseyo/Free/FreeList.do?pageNum=1&type=&keyword">자유게시판</a></li>
							
							
						<li class="nav-item"><a class="nav-link" 
							style="color: #000000;"
							href="/jejuOseyo/Mate/List.do">여행메이트 게시판</a></li>	
						
					</c:when>

				</c:choose>

			</ul>
		</div>
	</div>
</nav>

<%-- else {%>
			[<%=sid %>님]
			 <li><a class="nav-link active" aria-current="page" href="/jejuOseyo/common/logout.jsp">로그아웃</a></li>
			<li><a class="nav-link active" aria-current="page" href="/jejuOseyo/member/memMypage.jsp">마이페이지</a></li>

		<% }%>  --%>

</body>
</html>