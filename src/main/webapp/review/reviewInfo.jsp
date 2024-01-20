<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
#myform fieldset{
    display: inline-block;
    direction: rtl;
    border:0;
}
#myform fieldset legend{
    text-align: right;
}
#myform input[type=radio]{
    display: none;
}
#myform label{
    font-size: 2em;
    color: transparent;
    text-shadow: 0 0 0 #f0f0f0;
    margin-left: 30px;
}
#myform label:hover{
    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
}
#myform label:hover ~ label{
    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
}
#myform input[type=radio]:checked ~ label{
    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
}
#content {
    width: 100%;
    height: 150px;
    padding: 10px;
    box-sizing: border-box;
    border: solid 1.5px #D3D3D3;
    border-radius: 5px;
    font-size: 16px;
    resize: none;
}
</style>
<title>리뷰작성</title>

</head>

<body>
<%@ include file="../include/header.jsp" %>


<main role="main">
<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			리뷰 상세보기 </h1></div></div>	
  
   	<form  action="/jejuOseyo/Review/ReviewModify.do?rvNo=${param.rvNo }&pageNum=${param.pageNum}&people=${param.people}&keyword=${param.keyword}&checkIn=${param.checkIn}&checkOut=${param.checkOut}" class="mb-3" name="myform" id="myform" method="post">
      <c:set var="sbMsg" value="제목은 필수 입력 항목입니다."/>
      <c:set var="ctMsg" value="내용은 필수 입력 항목입니다."/>
      <input type="hidden" name="rmNo" id="rmNo" value="${reviewInfo.rmNo }">
      <input type="hidden" name="pageNum" value="${pageNum }">
      <div class="form-group row"> 
        <label class="col-sm-2" style="color:black; font-size:1.5em;">
          작성자
        </label>
        <div class="col-sm-3">
          <input type="text" name="mid" id="mid" class="form-control" required
              readonly value="${reviewInfo.mid }">
        </div>
      </div>
      <div class="form-group row"> 
        <label class="col-sm-2" style="color:black; font-size:1.5em;">
          작성일자
        </label>
        <div class="col-sm-3">
          <input type="text" name="regDate" id="regDate" class="form-control" required
              readonly value="${reviewInfo.regDate }">
        </div>
      </div>
  <div ><span class="text-bold" style="color:black; font-size:1.5em; margin-left:30px;">별점　　　　　　　　　　</span>
 <fieldset>
	<input type="radio" name="star" value=5 id="rate1" ${reviewInfo.star == 5 ? 'checked' : ''}><label
		for="rate1">★</label>
	<input type="radio" name="star" value=4 id="rate2" ${reviewInfo.star == 4 ? 'checked' : ''}><label
		for="rate2">★</label>
	<input type="radio" name="star" value=3 id="rate3" ${reviewInfo.star == 3 ? 'checked' : ''}><label
		for="rate3">★</label>
	<input type="radio" name="star" value=2 id="rate4" ${reviewInfo.star == 2 ? 'checked' : ''}><label
		for="rate4">★</label>
	<input type="radio" name="star" value=1 id="rate5" ${reviewInfo.star == 1 ? 'checked' : ''}><label
		for="rate5">★</label>
</fieldset>
</div>
      <div class="form-group row"> 
        <label class="col-sm-2"  style="color:black; font-size:1.5em;">
          리뷰제목
        </label>
        <div class="col-sm-3">
          <input type="text" name="title" id="title" class="form-control" required value="${reviewInfo.title }"
                 oninput="setCustomValidity('')" oninvalid="this.setCustomValidity('${sbMsg }')">
        </div>
      </div>
  
   
  
     <div class="form-group row"> 
				<label class="col-sm-2"  style="color:black; font-size:1.5em;">
					리뷰내용 </label>
				<div class="col-sm-5">
					<textarea class="form-control" name="content"  id="content" 
							  cols="50" rows="2" oninput="setCustomValidity('')" oninvalid="this.setCustomValidity('${ctMsg }')" required >${reviewInfo.content }</textarea>
				</div></div>

      <div class="form-group row"> 
        <div class="col-sm-offset-2 col-sm-10">
          <input type="button" class="btn btn-secondary" value="이전" onclick="goBack()">
           	<c:if test="${sid eq reviewInfo.mid}">
          <input type="submit" class="btn btn-info" value="수정" >
          <input type="button" class= "btn btn-danger regBtn" value="삭제" onclick="delChk();"/>
       	  </c:if>
       	   <c:if test="${sid eq 'admin'}"> 
       	    <input type="button" class= "btn btn-danger regBtn" value="삭제" onclick="delChk();"/>
       	    </c:if>
        </div>
      </div>
    </form>

</main>

<%@ include file="../include/footer.jsp" %>
<script>
function goBack() {
  history.back();
}

function delChk(){
	if(confirm('이 리뷰를 정말 삭제하시겠습니까? ')){
		 $('form').attr('action', './ReviewRemove.do?rvNo=${param.rvNo }&pageNum=${param.pageNum}&keyword=${param.keyword}&checkIn=${param.checkIn}&checkOut=${param.checkOut}&people=${param.people}').submit();
	}
}

</script>
</body>
</html>