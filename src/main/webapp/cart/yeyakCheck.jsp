<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- iamport.payment.js -->
    <script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<meta charset="UTF-8">
<title>yeyakCheck.jsp</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			확인 &amp; 결제 </h1></div></div>

<main role="main">
 <div class="container col-9 border p-3 shadow-sm">  
 <form action="/jejuOseyo/Cart/cartView.do" method="post" id="yeyakForm">
<div class="container-fluid">
	<div class="row">
		<div class="col-md-6">
			<div class="row">
				<div class="col-md-12">
				<div class="container col-12 border p-3 shadow-sm mt-5 ">  
					<p>
						<label class="col-sm-4 font-weight-bold mt-3">예약자</label>
						<span>${cvo.mvo.name }</span>
					</p>
					<p>	
						<label class="col-sm-4 font-weight-bold">예약자 전화번호</label>
						<span>${cvo.mvo.mphone }</span>
					</p>	
				</div>   
				<div class="container col-12 border p-3 shadow-sm mt-5">  
						<label class="col-sm-3 font-weight-bold mt-3">환불 정책</label>
						<p class="col-sm-12">체크아웃 날짜 이틀 전까지 취소하기 버튼을 눌러야 100% 환불 가능.</p>
				</div> 
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="container col-12 border p-3 shadow-sm mt-5">  
				<p>
					<label class="col-sm-3 font-weight-bold mt-3">숙소 공지사항</label>
					<span>${cvo.rvo.notice }</span>
				</p>
				</div> 
				</div>
			</div>
		</div>
		<div class="col-md-6">
				<div class="container col-12 border p-3 shadow-sm mt-5">  
				<p>
					<label class="col-sm-4 font-weight-bold mt-3">숙소명</label>
					<span>${cvo.rvo.rmName }</span>
				</p>
				<p>	
					<label class="col-sm-4 font-weight-bold">숙소 주소</label>
					<span>${cvo.rvo.addr1 }</span>
					<span>${cvo.rvo.addr2 }</span>
				</p>	
				<p>	
					<label class="col-sm-4 font-weight-bold">예약 인원수</label>
					<span>${cvo.guest }</span>
				</p>
				<p>	
					<label class="col-sm-4 font-weight-bold">체크인/체크아웃</label>
					<span>${cvo.checkIn } </span> ~ 
					<span>${cvo.checkOut } </span>
				</p>
					<p>	
					<label class="col-sm-4 font-weight-bold">총 결제 금액</label>
					<span><fmt:formatNumber value="${cvo.rvo.price }" type="currency"/></span>  
				</p>
				</div> 
			</div>
		
				<div class="container-fluid mt-5">
					<div class="row">
    					<div class="col-md-12 d-flex justify-content-center">
       						 <p class="text-center">
           						 <label>예약 정보를 모두 확인했습니다.
								<input type="checkbox" name="allCheck" value="allCheck" id="allCheck"></label>
        					</p>
    					</div>
					</div>
				</div>
				
				
				<div class="container-fluid mt-5 d-flex justify-content-center">
    				<div class="row">
    				
       					 <div class="col-md-6 d-flex justify-content-center mb-3">
				            <a href="/jejuOseyo/Cart/cartList.do?pageNum=${pageNum }&mid=${sid }" class="btn btn-secondary">이전으로</a>
       					 </div>

       					<div class="col-md-6 d-flex justify-content-center mb-3">
							<a href="javascript:payCheck('${cvo.mvo.mid }','${cvo.rvo.hid }', ${cvo.rvo.rmNo },'${cvo.mvo.name }',
							'${cvo.checkIn }','${cvo.checkOut }', ${cvo.guest },'${cvo.mvo.mphone }', ${cvo.rvo.price }, ${pageNum },${cvo.cno },'${cvo.mvo.memail }' )" class="btn btn-info" id="payBtn">
								결제하기</a>       			 	
						</div>
			    </div>
			</div>		
	</div>
</div>
</form>
</div>

</main>
<!--  
 회원아이디,호스트아이디,숙소 번호,체크인날짜, 체크아웃날짜,예약인원수
${cvo.mvo.mid }
${cvo.rvo.hid }
${cvo.rvo.rmNo }

호스트 아이디 : ${cvo.rvo.hid }
장바구니 번호 : ${cvo.cno }
숙소 번호 : ${cvo.rvo.rmNo }

결제하기에서 예약테이블에 등록할거 다시 생각하기
먼저 1. 결제가 완료되면 결제 정보들이 결제 테이블에 등록된다.
그 후에 여기서 보낸 값들이 예약 테이블에 저장된다. 

여기서 보낼거 다시 생각하기 예약 테이블에 뭐 저장해가야하는지	-->
						
<%@ include file="../include/footer.jsp" %>

</body>
</html>