<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hostRJList.jsp</title>
<style>
.button-container {
	display: flex;
	justify-content: flex-end;
	margin-top: 10px;
}

.button-container .btn {
	margin-right: 8px;
}
</style>
</head>
<body>


	<%@ include file="../include/header.jsp"%>


	<div class="jumbotron" style="background-color: #FFFBC9">
		<div class="container">
			<h1 class="display-4"
				style="text-align: center; font-weight: bold; color: #649E77">
				호스트 거절 목록</h1>
		</div>
	</div>

	<main role="main">
		<div class="container">
			<form action="/jejuOseyo/Admin/AdHostRJList.do" method="post">
				<input type="hidden" name=pageNum id="pageNum" value="${pageNum }">
				<!-- 게시물이 없는 경우 -->
				<c:if test="${empty hostRJList}">
					<div class="row">호스트 정보를 찾을 수 없습니다.</div>
				</c:if>


				<!-- 그렇지 않은 경우 -->
				<c:if test="${!empty hostRJList}">

					<div class="row mb-3">
						<div class="col-8">
							<select name="type">
								<option value="rep" ${type eq 'rep' ? 'selected' : '' }>대표자명</option>
								<option value="hid" ${type eq 'hid' ? 'selected' : '' }>아이디</option>
								<option value="hphone" ${type eq 'hphone' ? 'selected' : '' }>전화번호</option>
							</select> <input type="text" name="keyword"
								value="${!empty keyword ? keyword : '' }"> <input
								type="submit" class="btn btn-info btn-sm" value="검색">
						</div>
						<div class="col-4 text-right">
							<span class="badge badge-secondary"> 호스트 거절 수 ${totalCnt }건</span>
						</div>

					</div>

					<div>
						<table class="table table-hover">
							<thead>
								<tr>
									<th>대표자명</th>
									<th>아이디</th>
									<th>전화번호</th>
									<th>이메일</th>
									<th>사업자번호</th>
									<th>가입거절일</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${hostRJList }" var="host">
									<c:set var="rep" value="${host.rep }" />
									<c:set var="hid" value="${host.hid }" />
									<c:set var="hphone" value="${host.hphone }" />
									<c:set var="hemail" value="${host.hemail }" />
									<c:set var="crnum" value="${host.crnum }" />
									<c:set var="procDate" value="${host.procDate }" />
									<c:set var="pageNum" value="${pageNum }" />
									<c:set var="type" value="${type }" />
									<c:set var="keyword" value="${keyword }" />
									<tr>
										<td>${host.rep }</td>
										<td>${host.hid }</td>
										<td>${host.hphone }</td>
										<td>${host.hemail }</td>
										<td>${host.crnum }</td>
										<td>${host.procDate }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<%-- 페이징 --%>
						<ul class="pagination justify-content-center">
							<%-- 이전 버튼 --%>
							<c:if test="${prev }">
								<li class="page-item"><a
									href="/jejuOseyo/Admin/AdHostRJList.do?pageNum=${start - 1 }&type=${type }&keyword=${keyword }"
									class="page-link">&laquo; Pre</a>
							</c:if>

							<%-- 페이지 번호 버튼 --%>
							<c:forEach begin="${start }" end="${end }" var="i">
								<c:url var="link"
									value="/Admin/AdHostRJList.do?pageNum=${i }&type=${type }&keyword=${keyword }" />
								<li class="page-item ${pageNum == i ? 'active' : '' }"><a
									href="${pageNum == i ? '#' : link }" class="page-link">${i }</a>
							</c:forEach>

							<%-- 다음 버튼 --%>
							<c:if test="${next }">
								<li class="page-item"><a
									href="/jejuOseyo/Admin/AdHostRJList.do?pageNum=${end + 1 }&type=${type }&keyword=${keyword }"
									class="page-link">Next &raquo;</a>
							</c:if>
						</ul>
					</div>
				</c:if>
				
				<div class="button-container">
					<a class="btn btn-secondary cancelBtn" onclick="history.back()">뒤로가기</a>
				</div>

			</form>
		</div>
	</main>

	<%
	// 호스트 거절성공
	String msg = (String) session.getAttribute("msg");
	if (msg != null) {
		out.println("<script>alert('" + msg + "');</script>");
		session.removeAttribute("msg");
	}
	%>


	<%@ include file="../include/footer.jsp"%>



</body>
</html>