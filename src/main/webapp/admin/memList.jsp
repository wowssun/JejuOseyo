<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memList.jsp</title>
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
				회원 목록</h1>
		</div>
	</div>


	<main role="main">
		<div class="container">
			<form action="/jejuOseyo/Admin/MemList.do" method="post">
				<input type="hidden" name=pageNum id="pageNum" value="${pageNum }">

				<!-- 회원이 없는 경우 -->
				<c:if test="${empty memList}">
					<div class="row">회원 정보를 찾을 수 없습니다.</div>
				</c:if>


				<c:if test="${!empty memList}">
					<div class="row mb-3">
						<div class="col-8">
							<select name="type">
								<option value="name" ${type eq 'name' ? 'selected' : '' }>이름</option>
								<option value="mid" ${type eq 'mid' ? 'selected' : '' }>아이디</option>
								<option value="mphone" ${type eq 'mphone' ? 'selected' : '' }>전화번호</option>
							</select> <input type="text" name="keyword"
								value="${!empty keyword ? keyword : '' }"> <input
								type="submit" class="btn btn-info btn-sm" value="검색">
						</div>
						<div class="col-4 text-right">
							<span class="badge badge-secondary"> 전체 회원 수 ${totalCnt }건</span>
						</div>
					</div>

					<div>
						<table class="table table-hover">
							<thead>
								<tr>
									<th>이름</th>
									<th>아이디</th>
									<th>전화번호</th>
									<th>이메일</th>
									<th>가입일자</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${memList }" var="mem">
									<c:set var="name" value="${mem.name }" />
									<c:set var="mid" value="${mem.mid }" />
									<c:set var="mphone" value="${mem.mphone }" />
									<c:set var="memail" value="${mem.memail }" />
									<c:set var="regDate" value="${mem.regDate }" />
									<c:set var="pageNum" value="${pageNum }" />
									<c:set var="type" value="${type }" />
									<c:set var="keyword" value="${keyword }" />
									<tr>
										<td>${mem.name }</td>
										<td><a
											href="/jejuOseyo/Admin/AdMemView.do?mid=${mem.mid }&pageNum=${pageNum}&type=${type }&keyword=${keyword}">
												${mem.mid }</a></td>
										<td>${mem.mphone }</td>
										<td>${mem.memail }</td>
										<td>${mem.regDate }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<%-- 페이징 --%>
						<ul class="pagination justify-content-center">
							<%-- 이전 버튼 --%>
							<c:if test="${prev }">
								<li class="page-item">
								<a href="/jejuOseyo/Admin/MemList.do?pageNum=${start - 1 }&type=${type }&keyword=${keyword}"
									class="page-link">&laquo; pre</a>
							</c:if>

							<%-- 페이지 번호 버튼 --%>
							<c:forEach begin="${start }" end="${end }" var="i">
								<c:url var="link"
									value="/Admin/MemList.do?pageNum=${i }&type=${type }&keyword=${keyword}" />
								<li class="page-item ${pageNum == i ? 'active' : '' }">
								<a href="${pageNum == i ? '#' : link }" class="page-link">${i }</a>
							</c:forEach>

							<%-- 다음 버튼 --%>
							<c:if test="${next }">
								<li class="page-item"><a
									href="/jejuOseyo/Admin/MemList.do?pageNum=${end + 1 }&type=${type }&keyword=${keyword}"
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
	// 회원삭제 성공/실패
	String msg = (String) session.getAttribute("msg");
	if (msg != null) {
		out.println("<script>alert('" + msg + "');</script>");
		session.removeAttribute("msg");
	}
	%>

	<%@ include file="../include/footer.jsp"%>





</body>
</html>