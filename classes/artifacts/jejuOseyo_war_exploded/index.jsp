
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main</title>
<style>
/* 기본 스타일 설정 */
 body {
      margin: 0;
      padding: 0;
      display: flex;
      flex-direction: column;
      min-height: 100vh; /* 최소 뷰포트 높이만큼 설정 */
    }

    .content {
      flex: 1; /* 내용이 footer 위로 미치지 않도록 flex 속성 활용 */
      padding: 20px;
    }

    footer {
      background-color: #333;
      color: black;
      text-align: center;
      padding: 20px;
    }
  .main {
    display: block;
    margin: 0 auto;
    margin-top: 20px;
    margin-bottom: 20px; 

  }

  .search-form {
    text-align: center;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  .img{
  margin-top: 20px;
  height:500px;
  }
    .main-view{
      margin:  auto;
      margin-bottom: 20px;
      margin-top: 50px;
    text-align: center;
}
.main-view{height:500px;overflow:hidden;}
    .main-view ul{width:calc(100% * 3);display:flex;animation:slide 6s infinite;} /* slide를 8초동안 진행하며 무한반복 함 */
    .main-view li{width:calc(100% / 3);height:300px;}
    
    @keyframes slide {
      0% {margin-left:0;} /* 0 ~ 10  : 정지 */
      10% {margin-left:0;} /* 10 ~ 25 : 변이 */
      25% {margin-left:-100%;} /* 25 ~ 35 : 정지 */
      35% {margin-left:-100%;} /* 35 ~ 50 : 변이 */
      50% {margin-left:-200%;}
      60% {margin-left:-200%;}
      100% {margin-left:0;}
    }
</style>
</head>      
<body>
<%@ include file="../include/header.jsp" %>

	<%
	// 호스트회원가입신청성공 // 일반회원로그인성공 //회원탈퇴성공 //호스트탈퇴성공
	String msg = (String) session.getAttribute("msg");
	if (msg != null) {
		out.println("<script>alert('" + msg + "');</script>");
		session.removeAttribute("msg");
	}
	%>
 <div class="main-view content" >
	 <ul>
			<li><img src="/jejuOseyo/resources/images/001.jpg" width="100%" height="300px"/></li>
            <li><img src="/jejuOseyo/resources/images/003.jpg" width="100%" height="300px"/></li>
    		
            <li><img src="/jejuOseyo/resources/images/002.jpg" width="100%" height="300px"/></li>
     </ul>
     
  <form class="search-form" action="./Room/SearchRoomList.do?pageNum=1">
  <h3 style="margin-top: 100px;">🍊제주도의 숙소를 검색해보세요~!🍊</h3>
    <input type="hidden" name="pageNum" value="1">
    <input type="text" name="keyword" required placeholder="지역/숙소명">
    <input type="number" name="people" required placeholder="인원 수"><br>
    체크인/아웃 날짜 
    <input type="date" name="checkIn" required style="margin-top: 20px;">
    <input type="date" name="checkOut" required style="margin-top: 20px; margin-bottom: 100px">
    <input type="submit" class="btn btn-info btn-sm" value="검색">
  </form>

</div>

</body>
<%@ include file="../include/footer.jsp" %>
</html>