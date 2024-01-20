<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freeModify.jsp</title>
</head>

<body>

	<%@ include file="../include/header.jsp" %>

<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
		자유게시판 게시글 수정</h1></div></div>

<main role="main">
	<div class="container">
		<form action="/jejuOseyo/Free/FreeModify.do?pageNum=1&type=&keyword&freeNo=${param.freeNo }" method="post" class="form-horizontal" >
			<!-- 필수 입력 항목 안내 메시지 -->
			<c:set var="subMsg" value="제목을 입력해 주세요."/>
			<c:set var="conMsg" value="내용을 입력해 주세요."/>
			
			<div class="form-group row"> 
				<label class="col-sm-2">작성자</label>
				<div class="col-sm-3">
					<input type="text" name="mid" id="mid" class="form-control" 
						   readonly value="${sid }">   <!--모든 항목에 value가 들어가야 한다.  -->
					
			
				</div></div>
		
			<div class="form-group row"> 
				<label class="col-sm-2">제목</label>
				<div class="col-sm-8">
					<input type="text" name="freeTitle" id="freeTitle" class="form-control" 
						   required oninput="setCustomValidity('')"
						   oninvalid="this.setCustomValidity('${subMsg }')" value="${fvo.freeTitle }">
				</div></div>
		
			<div class="form-group row"> 
				<label class="col-sm-2">내용</label>
				<div class="col-sm-8">
					<textarea name="freeContent" id="freeContent" class="form-control"
							rows="5"  cols="50" required oninput="setCustomValidity('')"
						   oninvalid="this.setCustomValidity('${conMsg }')">${fvo.freeContent }</textarea>  <!-- 여기는 내용을 태그 안에 -->
				</div></div>
				
			<div class="form-group row mt-4"> 
				<div class="col text-right">
					 <!-- fvo.mid가 sid와 같은 경우(일반적으로 로그인한 사용자의 SID와 게시물 작성자의 mid를 비교할 때 사용될 것입니다), 수정 버튼과 삭제 버튼이 나타나고, 그렇지 않은 경우에는 목록으로 돌아가는 버튼만 나타난다. -->
						<input type="submit" class="btn btn-info" value="수정완료"/> 
						<input type="button" class="btn btn-secondary cancelBtn" value="취소"
					   onclick="back()"/>
				
				</div></div>
				<input type="hidden" name="freeNo" value="${fvo.freeNo }">            <!-- hidden으로 번호 넘기기 -->
		</form></div></main>
		
 
		

<%@ include file="../include/footer.jsp" %>

</body>
<script>
function back() {
  history.back();
}

</script>

</html>