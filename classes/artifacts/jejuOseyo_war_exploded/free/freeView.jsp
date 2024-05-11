<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freeView.jsp</title>
</head>

<script>
function delChek() {
    if (confirm("정말 삭제하시겠습니까?")) {
        location.href = "/jejuOseyo/Free/FreeRemove.do?freeNo=${fvo.freeNo}";
    }
}

function modChek() {
    if (confirm("정말 수정하시겠습니까?")) { // 취소했을 때 돌아가야함..
    }
}
</script>

<body>

	<%@ include file="../include/header.jsp"%>

	<div class="jumbotron" style="background-color: #FFFBC9">
		<div class="container">
			<h1 class="display-4"
				style="text-align: center; font-weight: bold; color: #649E77">
				자유게시판 게시글 상세조회</h1>
		</div>
	</div>

	<main role="main">
		<div class="container">
			<form action="/jejuOseyo/Free/FreeModifyForm.do" method="post"
				class="form-horizontal">

				<div class="form-group row">
					<label class="col-sm-2">작성자</label>
					<div class="col-sm-3">
						<input type="text" name="mid" id="mid" class="form-control"
							readonly value="${fvo.mid }">
						<!--모든 항목에 value가 들어가야 한다.  -->
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2">제목</label>
					<div class="col-sm-8">
						<input type="text" name="freeTitle" id="freeTitle"
							class="form-control" readonly value="${fvo.freeTitle }">
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2">내용</label>
					<div class="col-sm-8">
						<textarea name="freeContent" id="freeContent" class="form-control"
							rows="5" cols="50" readonly>${fvo.freeContent }</textarea>
						<!-- 여기는 내용을 태그 안에 -->
					</div>
				</div>

				<div class="form-group row mt-4">
					<div class="col text-right">

						<!-- 관리자 삭제 기능 -->
						<c:if test="${sid eq 'admin'}">
							<input type="button" class="btn btn-danger regBtn" value="삭제"
								onclick="delChek()" />
							<a
								href="./FreeList.do?pageNum=${pageNum }&type=${type }&keyword=${keyword }"
								class="btn btn-secondary">목록</a>
						</c:if>
						<hr>

						<c:if test="${fvo.mid eq sid }">
							<!-- fvo.mid가 sid와 같은 경우(일반적으로 로그인한 사용자의 SID와 게시물 작성자의 mid를 비교할 때 사용될 것입니다), 수정 버튼과 삭제 버튼이 나타나고, 그렇지 않은 경우에는 목록으로 돌아가는 버튼만 나타난다. -->
							<input type="submit" class="btn btn-info" onclick="modChek()" value="수정" />
							<input type="button" class="btn btn-danger" onclick="delChek()"
								value="삭제" />
							<a
								href="./FreeList.do?pageNum=${pageNum }&type=${type }&keyword=${keyword }"
								class="btn btn-secondary">목록</a>
						</c:if>
					</div>
				</div>
				<input type="hidden" name="freeNo" value="${fvo.freeNo }">
				<!-- hidden으로 번호 넘기기 -->

			</form>
		</div>
	</main>




	<%@ include file="../include/footer.jsp"%>



</body>
</html>