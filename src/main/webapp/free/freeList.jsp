<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freeList.jsp</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>
<!-- 게시물 등록/수정/삭제 완료 메시지가 있는 경우 알림창으로 출력 -->
<c:if test="${!empty msg }">
	<script>
		alert('${msg}');
	</script>
	<%session.removeAttribute("msg"); %>
</c:if>

<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
		자유게시판 게시글 전체 목록</h1></div></div>

<main role="main">
	<div class="container">
		<form action="/jejuOseyo/Free/FreeList.do" method="post">
		<input type="hidden" name="pageNum" value="${pageNum }">
		<!-- 게시물이 없는 경우 -->
		<c:if test="${empty freeList }">
		<div class="row">
			등록된 게시물이 없습니다.</div>
		</c:if>
		
		<!-- 그렇지 않은 경우 -->
		<c:if test="${!empty freeList }">
		<div class="row mb-3">
			<div class="col-8"> 
				<select name="type">
					<option value="free_Title" ${type eq 'free_Title' ? 'selected' :'' }>제목에서</option>
					<option value="free_Content" ${type eq 'free_Content' ? 'selected' :'' }>본문에서</option>
					<option value="mid" ${type eq 'mid' ? 'selected' :'' }>작성자에서</option>
				</select>
				<input type="text" name="keyword" value="${!empty keyword ? keyword : '' }">
				<input type="submit" class="btn btn-info btn-sm" value="검색">	
				</div>
			<div class="col-4 text-right"> 
				<span class="badge badge-secondary">
					전체 게시물 ${totalCnt }건</span></div></div>
					
		<div>
			<table class="table table-hover">
			<thead>
				<tr><th>번호</th>
					<th>제목</th>
					<th>작성일</th>
					<th>조회수</th>
					<th>작성자</th></tr></thead>
				
			<tbody>
			<c:forEach items="${freeList }" var="fvo">
			<c:url var="viewlink" value="./FreeView.do">
				<c:param name="freeNo" value="${fvo.freeNo  }"/>
				<c:param name="mid" value="${fvo.mid }"/>
				<c:param name="pageNum" value="${pageNum }"/>
				<c:param name="type" value="${type }"/>
				<c:param name="keyword" value="${keyword  }"/>
			</c:url>
			
		<%-- 이렇게 처리했던 것을 위에처럼 파라미터로 저장해서 밑에 누르는 링크 길이를 줄일 수 있다.
		 	<c:set var="freeNo" value="${fvo.freeNo }"/>   <!--주소창에 바로 들어가면 길어져서 여기에서 변수 설정하고  -->
			<c:set var="mid" value="${fvo.mid }"/>
			<c:set var="pageNum" value="${pageNum }"/>
			<c:set var="type" value="${type }"/>
			<c:set var="keyword" value="${keyword }"/> --%>
			
			
			<tr><td>${fvo.freeNo }</td>
				<td><a href="${viewlink }" >${fvo.freeTitle }</a></td> <!-- 여기서 현재페이지 번호도 보내야함, 위에서 param  처리함 -->
				<td>${fvo.regDate }</td>
				<td>${fvo.freeHit }</td>
				<td>${fvo.mid }</td></tr>				
			</c:forEach>
			</tbody>
			</table>
			
			<%-- 페이징 --%>
			<ul class="pagination justify-content-center">
			
				<%-- 이전 버튼 --%>
				
				<c:if test="${prev }">
				<li class="page-item">
					<a href="./FreeList.do?pageNum=${start - 1 }&type=${type }&keyword=${keyword }" 
					   class="page-link">&laquo; Previous</a></c:if>
				
				<%--페이지 번호 버튼 --%>
				
				<c:forEach begin="${start }" end="${end }" var="i">
				<c:url var="link" value="./FreeList.do?pageNum=${i }">
					<c:param name="type" value="${type }"/>
					<c:param name="keyword" value="${keyword  }"/>				
				</c:url>
				<li class="page-item ${pageNum == i ? 'active' : '' }">
					<a href="${pageNum == i ? '#' : link }" class="page-link">${i }</a>
				</c:forEach>
				
				<%-- 다음 버튼 --%>
				
				<c:if test="${next }">
				<li class="page-item">
					<a href="./FreeList.do?pageNum=${end + 1 }&type=${type }&keyword=${keyword }" 
					   class="page-link">Next &raquo;</a></c:if>
			</ul>
					
			
		</div>
		</c:if>
		
		<div class="text-right">
		<c:if test="${!empty sid && sid != 'admin'}">
			<a href="./FreeWriteForm.do" class="btn btn-info btn-sm">
				글쓰기</a>
		</c:if>
		</div>
		</form>
	</div></main>

<%@ include file="../include/footer.jsp" %>
	
</body>
</html>

