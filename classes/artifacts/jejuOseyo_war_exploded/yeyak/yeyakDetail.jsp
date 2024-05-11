<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>yeyakDetail.jsp</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

	<div class="jumbotron" style="background-color:#FFFBC9" >
	<div class="container">
		<h1 class="display-4" style="text-align: center; font-weight: bold; color: #649E77" >
			예약 상세정보 </h1></div></div>			
			
<main role="main">
<div class="container col-9 border p-3 shadow-sm">  
 	<c:set var = "yeyakDate" value ='${yvo.pvo.payDate }' />
 	<c:set var = "yeyakDt" value = "${fn:substring(yeyakDate, 0, 10)}" />
<div class="text-right">${yeyakDt }</div>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-6">
			<div class="row">
				<div class="col-md-12">
					<div class="container col-12 border p-3 shadow-sm mt-5">  
					<p>
						<label class="col-sm-3 font-weight-bold mt-3">예약번호</label>
						<span>${yvo.yno }</span>
					</p>
					</div>
				<div class="container col-12 border p-3 shadow-sm mt-5">  
					<p>
						<label class="col-sm-4 font-weight-bold mt-3">예약자</label>
						<span>${yvo.mvo.name }</span>
					</p>
					<p>	
						<label class="col-sm-4 font-weight-bold">예약자 전화번호</label>
						<span>${yvo.mvo.mphone }</span>
					</p>	
				</div> 
				<div class="container col-12 border p-3 shadow-sm mt-5">  
					<p>
						<label class="col-sm-4 font-weight-bold mt-3">호스트</label>
						<span>${yvo.hvo.hnick }</span>
					</p>
					<p>
						<label class="col-sm-4 font-weight-bold mt-3">호스트 전화번호</label>
						<span>${yvo.hvo.hphone }</span>
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
					<span>${yvo.rvo.notice }</span>
				</p>
				</div> 
				</div>
			</div>
		</div>
		<div class="col-md-6">
				<div class="container col-12 border p-3 shadow-sm mt-5">  
				<p>
					<label class="col-sm-4 font-weight-bold mt-3">숙소명</label>
					<span>${yvo.rvo.rmName }</span>
				</p>
				<p>	
					<label class="col-sm-4 font-weight-bold">숙소 주소</label>
					<span>${yvo.rvo.addr1 }</span>
					<span>${yvo.rvo.addr2 }</span>
				</p>	
				<p>	
					<label class="col-sm-4 font-weight-bold">예약 인원수</label>
					<span>${yvo.yeGuest }</span>
				</p>
				<p>	
					<label class="col-sm-4 font-weight-bold">체크인/체크아웃</label>
					<span>${yvo.yeCheckIn } </span> ~ 
					<span>${yvo.yeCheckOut } </span>
				</p>
					<p>	
					<label class="col-sm-4 font-weight-bold">총 결제 금액</label>
					<span><fmt:formatNumber value="${yvo.rvo.price }" type="currency"/></span>  
				</p>
				</div>
				<div class="container col-12 border p-3 shadow-sm mt-5">  
					<p>	
						<label class="col-sm-4 font-weight-bold">결제 정보</label>
					</p>
					<p>	
						<span class="col-sm-4"> 결제번호 : ${yvo.pvo.payNo }</span>
					</p>
					<p>	
						<span class="col-sm-4"> 카드사 : ${yvo.pvo.cardName }</span>
					</p>
					<p>	
						<span class="col-sm-4">카드번호 :  ${yvo.pvo.cardNum }</span>
					</p>
					<p>	
						<span class="col-sm-4">결제 금액 : <fmt:formatNumber value="${yvo.rvo.price }" type="currency"/> </span>
					</p>
					<p>	
						<span class="col-sm-4">결제일시 : ${yvo.pvo.payDate }</span>
					</p>	
				
					</div>
				</div>	
				
					<div class="container-fluid mt-5 d-flex justify-content-center">
    					<div class="row">
    				
	       					 <div class="col-md-4 d-flex justify-content-center mb-3">
					            <a href="/jejuOseyo/Yeyak/yeyakList.do?pageNum=${pageNum }&mid=${sid }" class="btn btn-secondary">이전으로</a>
	       					 </div>

        					<div class="col-md-4 d-flex justify-content-center mb-3">
								<a href="javascript:review(${yvo.rvo.rmNo },'${yvo.yeCheckIn }')" class="btn btn-info" id="revBtn">
									리뷰등록</a>       			 	
							</div>
							<div class="col-md-4 d-flex justify-content-center mb-3">
								<a href="javascript:yecancel('${yvo.pvo.payNo }','${yvo.yeCheckIn }','${yvo.yno }',${yvo.rvo.price })" class="btn btn-danger" id="yeCanBtn">
									예약취소</a>       			 	
							</div>
			    </div>
			</div>		
		    </div>
		</div>
	</div>						
</main>

<!-- 

	 여기에 이전으로 버튼 넣기
	
	숙소 번호 : ${yvo.rvo.rmNo }   이건 리뷰 등록할때 넘길 값
	
	결제 취소 일시 : ${yvo.pvo.payCanDate } 
	
 -->


<%@ include file="../include/footer.jsp" %>

</body>
</html>