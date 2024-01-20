<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>숙소상세조회</title>
  <style>
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



<main role="main">
<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			숙소 상세보기 </h1></div></div>	
  <div class="container">
    <div class="text-right">
    </div>
  
    <form action="/jejuOseyo/Room/RoomModifyForm.do?rmNo=${param.rmNo }"  method="post" class="form-horizontal">
 	<input type="hidden" name="pageNum" value="${pageNum }">
 	<input type="hidden" name="hid" value="${view.hid }">

 	<div class="form-group row"> 
        <label class="col-sm-2">
          숙소 이미지
        </label>
        <div class="main-view" >
         <ul>
            <li><img src="../resources/images/${view.img }" width="100%" height="300px" /></li>
            <li><img src="../resources/images/${view.img2 }" width="100%" height="300px"/></li>
           <c:if test="${not empty view.img3}">
    <li><img src="../resources/images/${view.img3}" width="100%" height="300px"/></li>
</c:if>

          </ul>
        </div>
      </div>
      <div class="form-group row"> 
        <label class="col-sm-2">
          호스트 ID
        </label>
        <div class="col-sm-3">
          ${view.hid }
        </div>
      </div>
      
<div class="form-group row"> 
    <label class="col-sm-2">
        숙소명
    </label>
    <div class="col-sm-3">
        ${view.rmName}
    </div>
    <div class="col-sm-7"> <!-- 추가한 내용을 보여주는 열 -->
        별점 ⭐ <c:set var="average" value="${view.rvCnt != 0 ? view.starTotal / view.rvCnt : 0}" />
    <fmt:formatNumber value="${average}" maxFractionDigits="1" />
    </div>
</div>

  
      <div class="form-group row"> 
        <label class="col-sm-2">
          가격
        </label>
        <div class="col-sm-3">
                 ${view.price }원
        </div>
      </div>
      <div class="form-group row"> 
        <label class="col-sm-2">
          위치
        </label>
        <div class="col-sm-3">
                 ${view.addr1 }  ${view.addr2 } 
        </div>
      </div>
      <div class="form-group row"> 
        <label class="col-sm-2">
          최대 수용 인원 수 
        </label>
        <div class="col-sm-3">
                 ${view.people }명
        </div>
      </div>
      <div class="form-group row"> 
        <label class="col-sm-2">
          숙소 소개글
        </label>
        <div class="col-sm-3">
                 ${view.memo }
        </div>
      </div>

     <div class="form-group row"> 
				<label class="col-sm-2">
					공지사항 </label>
				<div class="col-sm-5">
					
						${view.notice }
				</div></div>
				<%-- 목록으로 돌아가기 위해 저장된 pageNum 전송--%>
		<input type="hidden" name="pageNum" id="pageNum" value="${pageNum }">
			<c:if test="${not empty sid and sid eq mid and sid ne 'admin' and not empty param.keyword}"> 
			<a href="#" class="btn btn-info wishAddBtn">위시리스트 담기&raquo;</a>
		
             <a href="javascript:cartDamgi('${param.rmNo }','${param.checkIn }','${param.checkOut }',${param.people },${pageNum},'${param.keyword }','${view.rmName }')" class="btn btn-danger cartAddBtn" >장바구니 담기&raquo;</a>
           	<a class="btn btn-secondary" href="/jejuOseyo/Room/SearchRoomList.do?pageNum=${pageNum}&keyword=${param.keyword }&people=${param.people }&checkIn=${param.checkIn }&checkOut=${param.checkOut }" >이전</a>          
            <hr>
           
            </c:if>
            <c:if test="${empty param.keyword and sid ne 'admin' and sid ne view.hid}">
            <a href="../index.jsp" class="btn btn-info">숙소 검색하기</a>
            </c:if>
            <c:if test="${empty sid }">
                	<a class="btn btn-secondary" href="/jejuOseyo/Room/SearchRoomList.do?pageNum=${pageNum}&keyword=${param.keyword }&people=${param.people }&checkIn=${param.checkIn }&checkOut=${param.checkOut }" >이전</a>          
            <hr>
            </c:if>
        <c:if test="${sid eq 'admin'}"> 
             
             <input type="button" class= "btn btn-danger regBtn" value="삭제" onclick="delChkAd();"/>
             <a href="javascript:void(0);" onclick="goBack()" class="btn btn-secondary">이전으로</a>
             </c:if>
             <hr>
		<!-- 자신이 작성한 글인 경우 수정/삭제 버튼 표시 -->
	<c:if test="${sid eq view.hid}"> 
		<input type="submit" class= "btn btn-info regBtn" value="수정"/>
		<input type="button" class= "btn btn-danger regBtn" value="삭제" onclick="delChk();"/>
		 <a href="/jejuOseyo/Room/MyRoomList.do?hid=${sid }&pageNum=${pageNum }" class="btn btn-secondary">나의 숙소 목록 &raquo;</a>
		</c:if>
		<hr>
<%@ include file="../review/reviewList.jsp" %>

    </form>
  </div>
</main>
<script>
function goBack() {
    history.back();
}
function delChk(){
	if(confirm('이 숙소를 정말 삭제하시겠습니까? ')){
		 $('form').attr('action', '/jejuOseyo/Room/RoomRemove.do?rmNo=${param.rmNo }').submit();
		 
	}
}
function delChkAd(){
	if(confirm('이 숙소를 정말 삭제하시겠습니까? ')){
		 $('form').attr('action', '/jejuOseyo/Room/RoomRemoveAd.do?rmNo=${param.rmNo }').submit();
		 
	}
}

  $(document).ready(function() {
      $('.wishAddBtn').click(function(e) {    
    	  $('form').attr('action', '/jejuOseyo/Rgg/RggAdd.do?mid=${sid}&rmNo=${param.rmNo }&pageNum=${param.pageNum}&keyword=${param.keyword}&checkIn=${param.checkIn}&checkOut=${param.checkOut}&people=${param.people}').submit();  
      });
    });


</script>

</body>
<%@ include file="../include/footer.jsp" %>
</html>
