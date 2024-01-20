<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>숙소수정</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<main role="main">
<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			숙소 수정 </h1></div></div>	
	
		<form action="/jejuOseyo/Room/RoomModify.do?rmNo=${param.rmNo }" method="post" enctype="multipart/form-data"  class="form-horizontal">
			  <c:set var="sbMsg" value="필수 입력 항목입니다."/>
			  	<div class="form-group row"> 
			<label class="col-sm-2">
					　　　　호스트아이디
				</label>
				<div class="col-sm-3">
					<input type="text" name="hid" id="hid" class="form-control" readonly value="${sid }">
				</div></div>
				 	
			  	<div class="form-group row"> 
			<label class="col-sm-2">
						　　　　숙소명
				</label>
				<div class="col-sm-3">
					<input type="text" name="rmName" id="rmName" class="form-control" value="${view.rmName }" required 
                 oninput="setCustomValidity('')" oninvalid="this.setCustomValidity('${sbMsg }')">
				</div></div>
		
				<div class="form-group row"> 
				<label class="col-sm-2">	　　　　우편번호</label>
				<div class="col-sm-3">
					<input type="text" name="zipNo" id="zipNo" readonly class="form-control" required 
                 oninput="setCustomValidity('')" oninvalid="this.setCustomValidity('${sbMsg }')">
				</div> 
				<div class="col-sm-2">
					<input type="button" class="btn btn-outline-secondary"
						   value="검색" onclick="goPopup();">
				</div></div>
				
			<div class="form-group row"> 
				<label class="col-sm-2">	　　　　도로명 주소</label>
				<div class="col-sm-6">
					<input type="text" name="addr1" id="addr1" class="form-control" value="${view.addr1 }" required 
                 oninput="setCustomValidity('')" oninvalid="this.setCustomValidity('${sbMsg }')" >
				</div></div>
		
			<div class="form-group row"> 
				<label class="col-sm-2">	　　　　상세 주소</label>
				<div class="col-sm-3">
					<input type="text" name="addr2" id="addr2" class="form-control" value="${view.addr2 }" required 
                 oninput="setCustomValidity('')" oninvalid="this.setCustomValidity('${sbMsg }')"></div>
				</div>
		
			<div class="form-group row"> 
				<label class="col-sm-2">
						　　　　최대 수용 인원
					</label>
				<div class="col-sm-3">
					<input type="number" name="people" id="people" class="form-control"
						   min="0"  value="${view.people }" required 
                 oninput="setCustomValidity('')" oninvalid="this.setCustomValidity('${sbMsg }')">
				</div></div>
				
				<div class="form-group row"> 
				<label class="col-sm-2">
						　　　　가격
					</label>
				<div class="col-sm-3">
					<input type="number" name="price" id="price" class="form-control"
						   min="0" step="10000"  value="${view.price }" required 
                 oninput="setCustomValidity('')" oninvalid="this.setCustomValidity('${sbMsg }')">
				</div></div>
		
		<div class="form-group row"> 
				<label class="col-sm-2">
					 	　　　　이미지 
					</label>
				<div class="col-sm-5">
					<input type="file" name="img" class="form-control"  multiple required 
                 oninput="setCustomValidity('')" oninvalid="this.setCustomValidity('${sbMsg }')">
				</div></div>
		<div class="form-group row"> 
				<label class="col-sm-2">
					 	　　　　이미지 2
					</label>
				<div class="col-sm-5">
					<input type="file" name="img2" class="form-control"  multiple>
				</div></div>
		<div class="form-group row"> 
				<label class="col-sm-2">
					 	　　　　이미지 3
					</label>
				<div class="col-sm-5">
					<input type="file" name="img3" class="form-control"  multiple>
				</div></div>
			<div class="form-group row"> 
				<label class="col-sm-2">
						　　　　소개글
					</label>
				<div class="col-sm-5">
					<textarea class="form-control" name="memo"  
							  cols="50" rows="2"> ${view.memo } </textarea>
				</div></div>
				
				<div class="form-group row"> 
				<label class="col-sm-2">
						　　　　공지사항
					</label>
				<div class="col-sm-5">
					<textarea class="form-control" name="notice"  
							  cols="50" rows="2">${view.notice }</textarea>
				</div></div>
		
			<div class="form-group row"> 
				<div class="col-sm-offset-2 col-sm-10">
					　　<input type="submit" class="btn btn-info" 
						   value="수정">
					<input type="button" class="btn btn-secondary"
						   value="취소" onclick="goBack()">
				</div></div>
					<input type="hidden" name="location" id="location">		
		</form></main>
<%@ include file="../include/footer.jsp" %>
</body>
</html>


<script>
function goPopup(){
    var pop = window.open("jusoPopup.jsp","pop",
    	"width=570,height=420, left=500, top=200, scrollbars=yes, resizable=yes"); 
};

function jusoCallBack(roadAddrPart1,addrDetail,roadAddrPart2,zipNo){
	// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
	$('#zipNo').val(zipNo);
	$('#addr1').val(roadAddrPart1);
	$('#addr2').val(addrDetail);
	$('#addr3').val(roadAddrPart2);
};


$('#people').blur(function() {
    var addr1 = $('#addr1').val();
    var addr2 = $('#addr2').val();
    var location = addr1 +" "+ addr2;
    $('#location').val(location);
 });
</script>