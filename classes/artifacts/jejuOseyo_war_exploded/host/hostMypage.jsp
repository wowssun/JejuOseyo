<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
.container {
	display: flex;
	justify-content: center;
	align-items: center;
}

.rounded {
	text-align: center;
	width: 160px;
	height: 160px;
	background-color: rgb(198, 217, 132);
/* 	line-height: 1rem; */
	padding: 10px;
	margin: 5px;
	border: none;
	outline: none;

}
.button-container {
	display: flex;
	justify-content: center;
}
</style>
<title>hostMypage.jsp</title>
</head>
<body>


	<%@ include file="../include/header.jsp"%>


	
		<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
		MY PAGE</h1></div></div>
		

			
	<div class="container">
	
		
		<button class="rounded"
			onclick="location.href='/jejuOseyo/Host/HostView.do?hid=${sessionScope.sid}'">
			개인정보</button>
		
		<button class="rounded"
			onclick="location.href='/jejuOseyo/Yeyak/hoyeyakList.do?pageNum=1&hid=${hid}'">예약자 목록</button>
		
		
	</div>


	<%@ include file="../include/footer.jsp"%>


</body>
</html>