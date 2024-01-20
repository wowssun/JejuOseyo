<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hostMng.jsp</title>
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
</head>
<body>

	<%@ include file="../include/header.jsp"%>

	<div class="jumbotron" style="background-color: #FFFBC9">
		<div class="container">
			<h1 class="display-4"
				style="text-align: center; font-weight: bold; color: #649E77">
				호스트 관리</h1>
		</div>
	</div>

	<div class="container">
		<button class="rounded"
			onclick="location.href='/jejuOseyo/Admin/HostList.do?pageNum=1&type=&keyword='">
			호스트 목록</button>

		<button class="rounded"
			onclick="location.href='/jejuOseyo/Admin/HostRQList.do?pageNum=1&type=&keyword='">
			호스트 회원가입 신청목록</button>

		<button class="rounded"
			onclick="location.href='/jejuOseyo/Admin/AdHostRJList.do?pageNum=1&type=&keyword='">
			호스트 거절내역</button>

	</div>

	<%@ include file="../include/footer.jsp"%>

</body>
</html>
