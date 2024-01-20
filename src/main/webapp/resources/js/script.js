

// /resources/js/script.js

// 장바구니 담기
function cartDamgi(rmNo, checkIn, checkOut, people,pageNum,keyword,rmName){
	if(confirm( '<' + rmName + '>' +'\n체크인 날짜 : ' + checkIn +' ~ 체크아웃 날짜 : '+ checkOut + '\n예약인원수 : ' + people +' \n장바구니에 담으시겠습니까?')){
				 
		location.href='/jejuOseyo/Cart/cartDamgiyo.do?rmNo='+ rmNo +'&checkIn='
		+ checkIn + '&checkOut=' + checkOut + '&people=' + people +'&pageNum=' + pageNum + '&keyword=' + keyword;
		
	} // if end
		
	
} // cartDamgi end


// 장바구니 삭제
function cartRemove(cno){

		if(confirm('장바구니에서 삭제하시겠습니까?')){
			location.href='/jejuOseyo/Cart/cartRemove.do?cno='+ cno;
			// cartRemove.do로 보내야함
			// cno를 가지고 어떻게 할것인가.
			// 이렇게 보내면 되는거징~~
			
		}// if end
					
	} // cartRemove end

// 장바구니 비우기
function cartRemoveAll(){

		if(confirm('장바구니를 비우시겠습니까?')){
			location.href='/jejuOseyo/Cart/cartRemoveAll.do';
			// 그럼 얘도 똑같이?
			// 장바구니 비우기는 세션에 저장되어 있는 아이디로 지우면 되니까
			// 따로 파라미터는 안보내고 알림창 확인을 누르면 cartRemoveAll.do로 이동
		}// if end
					
	} // cartRemove end
	
	
// 예약하러 가기
function yeyak(rmName,cno,pageNum){

		if(confirm('< ' + rmName + ' >' + '\n 예약하시겠습니까?')){
			
			location.href='/jejuOseyo/Cart/cartView.do?cno=' + cno + '&pageNum=' + pageNum;
		}// if end
		
			
			// 그럼 얘도 똑같이?
			// 장바구니 비우기는 세션에 저장되어 있는 아이디로 지우면 되니까
			// 따로 파라미터는 안보내고 알림창 확인을 누르면 cartRemoveAll.do로 이동
		
					
	}  // yeyak end
	


	// 이건 체크박스를 클릭 안하고 결제하기 버튼 눌렀을때 알림창
function payCheck(mid,hid,rmNo,name,checkIn,checkOut,guest,mphone,price,pageNum,cno,email) {
	 // checkbox의 상태를 확인합니다.
    var checkBox = document.getElementById("allCheck");
    if (checkBox.checked === true) {
      // checkbox가 체크되어 있을 때의 처리를 여기에 작성합니다.
      alert("결제가 진행됩니다.");
      // 여기에 결제 로직을 추가하면 됩니다.
      payment(mid,hid,rmNo,name,checkIn,checkOut,guest,mphone,price,pageNum,cno,email);
    } else {
      // checkbox가 체크되어 있지 않을 때의 처리를 여기에 작성합니다.
      alert("예약 정보를 확인해주세요.");
    }
	
}

    // ${cvo.mvo.mid }','${cvo.rvo.hid }',${cvo.rvo.rmNo },'${cvo.mvo.name }',
	//	${cvo.checkIn }','${cvo.checkOut }','${cvo.guest }','${cvo.mvo.mphone }',${cvo.rvo.price },${pageNum })" 
								   
	// 예약번호, 회원아이디,호스트아이디,숙소 번호, 결제번호,체크인날짜, 체크아웃날짜,예약인원수
	// 그럼 여기로 올때 예약자명이랑 전화번호, 가격	
	// 결제하기
function payment(mid,hid,rmNo,name,checkIn,checkOut,guest,mphone,price,pageNum,cno,email) {	
	
	
	
// ------------객체 초기화 하기--------------------

	const IMP = window.IMP; 			// 생략 가능
	IMP.init("imp26665025"); 			// 예: imp00000000a  , 자신의 "가맹점 식별코드"를 사용
	 									// i'mport 관리자 페이지 -> 내정보 -> 가맹점식별코드
	  IMP.request_pay({
			  
		   pg: "html5_inicis",    	 // 하나의 아임포트계정으로 여러 PG를 사용할 때 구분자로 사용할 PG사의 코드값을 입력한다.
		   /* 
           'kakao':카카오페이, 
           html5_inicis':이니시스(웹표준결제)
               'nice':나이스페이
               'jtnet':제이티넷
               'uplus':LG유플러스
               'danal':다날
               'payco':페이코
               'syrup':시럽페이
               'paypal':페이팔
           */
		   pay_method: "card",  // 결제수단 (옵션값 참고), 신용카드로 결제한다는 뜻
		   
		   /* 
           'samsung':삼성페이, 
           'card':신용카드, 
           'trans':실시간계좌이체,
           'vbank':가상계좌,
           'phone':휴대폰소액결제 
       */
		   merchant_uid : 'jeju_'+new Date().getTime(),  	 //가맹점에서 생성/관리하는 고유 주문번호(이건 내가 만들어야 함)
		   name : '혼저옵서예',     // 주문명(예약명 : 숙소명으로 해야 되나?) //결제창에서 보여질 이름
		   amount : 100,   					 			// 결제 금액  // 이거 테스트 하느라고 100원으로 해놓음 나중에 price로 넣기!
		   buyer_name : name,							// 예약자 이름
		   buyer_tel : mphone,						// 예약자 전화번호
		   buyer_email :email,
		   buyer_addr : '서울특별시 강남구 삼성동',			// 예약자 주소? 없어도 됨
		   buyer_postcode : '123-456'						// 이것도 없어도 됨
		 }, function (rsp) { // callback   // 결과값 날라오는거
		     if (rsp.success) {
		       console.log(rsp);
		       // 결과가 모두 rsp에 저장되어 있음 여기서 뽑아낼 것만 뽑아내서 예약 db에 저장?
		       // 아니다 이미 예약 정보에 값들이 모두 저장되어 있으니까 예약 확정 정보에 결제 정보 포함할 것 있으면 rsp. 해서 쓰면 될듯	   
		       // 결제 성공 시 로직
		       // 예약번호, 회원아이디,호스트아이디,숙소 번호, 결제번호,체크인날짜, 체크아웃날짜,예약인원수
		        //[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
               jQuery.ajax({
                    url: "/payments/complete", //cross-domain error가 발생하지 않도록 주의해주세요
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        imp_uid : rsp.imp_uid
                        //기타 필요한 데이터가 있으면 추가 전달
                    }
                }).done(function(data) {
                    //[2] 서버에서 REST API로 결제정보확인 및 서비스루틴이 정상적인 경우
                    if ( everythings_fine ) {
                        msg = '결제가 완료되었습니다.';
                        msg += '\n고유ID : ' + rsp.imp_uid;
                        msg += '\n상점 거래ID : ' + rsp.merchant_uid;
                        msg += '\결제 금액 : ' + rsp.paid_amount;
                        msg += '카드 승인번호 : ' + rsp.apply_num;
                        
                        alert(msg);
                    } else {
                        //[3] 아직 제대로 결제가 되지 않았습니다.
                        //[4] 결제된 금액이 요청한 금액과 달라 결제를 자동취소처리하였습니다.
                    }
                });
                // 성공시 보낼거
		       
		       var queryString = "impUid=" + rsp.imp_uid +
                 				 "&merchantUid=" + rsp.merchant_uid +
                 				 "&cardName=" + rsp.card_name +
                 				 "&cardNumber=" + rsp.card_number +
                				 "&amount=" + rsp.paid_amount +	
                 				 "&mid=" + mid + "&hid=" + hid + "&rmNo=" + rmNo +
                 				 "&name=" + name + "&checkIn=" + checkIn +
                 				 "&checkOut=" + checkOut + "&guest=" + guest +
                 				 "&mphone=" + mphone + "&pageNum=" + pageNum + "&cno=" + cno;                 				 ;
                 		
          	
	     		 location.href = '/jejuOseyo/Yeyak/yeyakHagi.do?' + queryString;
				
		
					
		     } else {
				 alert(`결제에 실패하였습니다. 에러 내용: ${rsp.error_msg}`);
		       
            }
          
		       
		 });									
	 								
}  // payment end


// 예약취소
function yecancel(payno,checkIn,yno,price){
	
	var cancheckIn = new Date(checkIn); // 체크인 날짜
	var now = new Date();				// 현재 날짜
	
	var cancelDate = cancheckIn.setDate(cancheckIn.getDate() - 2);    // 체크인 날짜부터 이틀전
	
	if(cancelDate >= now){
		if(confirm('예약을 취소하시겠습니까?')){
			
			jQuery.ajax({
			// 예: http://www.myservice.com/payments/cancel
		    "url": "/jejuOseyo/Yeyak/payCancel.do?yno=" + yno + "&price=" + price,
		    "type": "POST",
		    "contentType": "application/json",
		    "data": {
			    "merchant_uid": yno, // 예: ORD20180131-0000011
			    "cancel_request_amount": price, // 환불금액
			    "reason": "예약 취소" // 환불사유
		   
		 	}
		}).done(function(result){
			alert('결제 취소 성공! \n 신용카드/실시간계좌이체/휴대폰소액결제의 경우 즉시 취소처리됩니다.');
			location.href='/jejuOseyo/Yeyak/yeyakModify.do?payno=' + payno + "&yno=" + yno + "&price=" + price;
			
		}).fail(function(error){
			
			alert('실패?' + error)
		});
		
		
	}
	}else{
		alert('예약을 취소할 수 없습니다. \n (체크인 날짜 이틀전까지 예약취소가 가능합니다.)')
	}
	
	
	
} // yecancel end


// 리뷰작성
function review(rmNo,checkIn){
	
	var yecheckIn = new Date(checkIn); // 체크인 날짜
	var now = new Date();				// 현재 날짜

	console.log(now>yecheckIn)
	
	
	if(now>yecheckIn){					// 체크인 날짜가 현재 기준으로 지났다면
		 location.href = '/jejuOseyo/review/reviewAdd.jsp?rmNo=' + rmNo; 
	}else{ 
		alert('체크아웃 날짜가 지나면 리뷰 작성이 가능합니다.');
	}// if end*/
	
} // review end



	