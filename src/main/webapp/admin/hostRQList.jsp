<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hostRQList.jsp</title>
<style>
    .button-container {
        display: flex;
        justify-content: flex-end;
        margin-top:10px;
    }
    .button-container .btn {
        margin-right: 8px;
    }
</style>
</head>
<body>

	<%@ include file="../include/header.jsp"%>



	
	<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			호스트 회원가입 신청 내역</h1></div></div>

	<main role="main">
		<div class="container">
			<form action="/jejuOseyo/Admin/HostRQList.do" method="post">
				<input type="hidden" name=pageNum id="pageNum" value="${pageNum }">
				<!-- 게시물이 없는 경우 -->
				<c:if test="${empty hostRQList}">
					<div class="row">호스트 정보를 찾을 수 없습니다.</div>
				</c:if>

				<!-- 그렇지 않은 경우 -->
				<c:if test="${!empty hostRQList}">
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
							<span class="badge badge-secondary"> 호스트 신청 수 ${totalCnt }건</span>
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
									<th>처리상태</th>
									<th>가입신청일</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${hostRQList }" var="hostrq">
									<c:set var="rep" value="${hostrq.rep }" />
									<c:set var="hid" value="${hostrq.hid }" />
									<c:set var="hphone" value="${hostrq.hphone }" />
									<c:set var="hemail" value="${hostrq.hemail }" />
									<c:set var="crnum" value="${hostrq.crnum }" />
									<c:set var="procState" value="${hostrq.procState }" />
									<c:set var="regDate" value="${hostrq.regDate }" />
									<c:set var="pageNum" value="${pageNum }" />
									<c:set var="type" value="${type }" />
									<c:set var="keyword" value="${keyword }" />
									<tr>
										<td>${hostrq.rep }</td>
										<td><a
											href="/jejuOseyo/Admin/AdHostRQView.do?hid=${hostrq.hid }&pageNum=${pageNum}&type=${type }&keyword=${keyword}">
												${hostrq.hid }</a></td>
										<td>${hostrq.hphone }</td>
										<td>${hostrq.hemail }</td>
										<td>${hostrq.crnum }</td>
										<td>${hostrq.procState }</td>
										<td>${hostrq.regDate }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<%-- 페이징 --%>
						<ul class="pagination justify-content-center">
							<%-- 이전 버튼 --%>
							<c:if test="${prev }">
								<li class="page-item"><a
									href="/jejuOseyo/Admin/HostRQList.do?pageNum=${start - 1 }&type=${type }&keyword=${keyword }" 
									class="page-link">&laquo; pre</a>
							</c:if>

							<%-- 페이지 번호 버튼 --%>
							<c:forEach begin="${start }" end="${end }" var="i">
								<c:url var="link" value="/Admin/HostRQList.do?pageNum=${i }&type=${type }&keyword=${keyword }" />
								<li class="page-item ${pageNum == i ? 'active' : '' }"><a
									href="${pageNum == i ? '#' : link }" class="page-link">${i }</a>
							</c:forEach>

							<%-- 다음 버튼 --%>
							<c:if test="${next }">
								<li class="page-item"><a
									href="/jejuOseyo/Admin/HostRQList.do?pageNum=${end + 1 }&type=${type }&keyword=${keyword }" class="page-link">Next
										&raquo;</a>
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


	<%@ include file="../include/footer.jsp"%>





</body>
</html>