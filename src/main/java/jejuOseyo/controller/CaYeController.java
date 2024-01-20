package jejuOseyo.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import jejuOseyo.dao.CartDAO;
import jejuOseyo.dao.YeyakDAO;
import jejuOseyo.vo.CartVO;
import jejuOseyo.vo.HostVO;
import jejuOseyo.vo.MemberVO;
import jejuOseyo.vo.PaymentVO;
import jejuOseyo.vo.RoomVO;
import jejuOseyo.vo.YeyakVO;


@WebServlet({"/Cart/*","/Yeyak/*"})   // 그럼 /cart/ 뒤에 오는게 모두 여기 controller로  {'/Cart/*','/Yeyak/*'}   이거 왜 안먹혀
public class CaYeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;   // 서블릿은 내장객체가 아니라서 session을 선언하고 사용하여야 한다.
									//  로그인한 사람만 게시판에 글쓰게 하려고
	
	private CartDAO cdao;
	private YeyakDAO ydao;
	private String url;
	
	public static final String IMPORT_TOKEN_URL = "https://api.iamport.kr/users/getToken"; 
	public static final String IMPORT_CANCEL_URL = "https://api.iamport.kr/payments/cancel"; 
	    
    public static final String KEY = "2488640118353225";
	public static final String SECRET = "u9O60lMRXcJE5DlvVt4Jkmm0U32VXgaMMbvSd6FRZ8a7f2Akt77nUS5prdlzuhyL4yzKo11U8VR3bguq"; 

	
	private static final double AMOUNT_PER_PAGE = 5.0; // 한 페이지의 게시물 수
	private static final double NUM_PER_PAGE = 5.0; // 한 페이지에 표시할 페이지 번호 수
	
    
    	public void init(ServletConfig config) throws ServletException {
		
			ServletContext servletCtx = config.getServletContext();
			Connection con = (Connection) servletCtx.getAttribute("con");
		
			cdao = new CartDAO(con);
			ydao = new YeyakDAO(con);
	
	}
    	// 아임포트 토큰가져오기!!!!!!
    	public String getToken() throws IOException{
    		// requestURL 아임퐅크 고유키, 시크릿 키 정보를 포함하는 url 정보 
    		
    		String Itoken = "";
    		try {
    		String requestString = IMPORT_TOKEN_URL;
    		
    		
    		HttpURLConnection conn = null;
    		
    		URL url = new URL(requestString);
    		
    		conn = (HttpURLConnection) url.openConnection();

    		conn.setRequestMethod("POST");

    		conn.setRequestProperty("Content-Type", "application/json");
    		
    		conn.setRequestProperty("Accept", "application/json");
    		
    		conn.setDoOutput(true);

    		JSONObject json = new JSONObject();
    		
    		json.put("imp_key", KEY);
    		json.put("imp_secret", SECRET);


    		OutputStream os= conn.getOutputStream();

    		os.write(json.toString().getBytes());

    		conn.connect();

    		StringBuilder sb = new StringBuilder();

    		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

    		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

    		String line = null;

    		while ((line = br.readLine()) != null) {

    		sb.append(line + "\n");

    		}

    		br.close();

    		requestString = sb.toString();

    		}

    		os.flush();

    		conn.disconnect();

    		JSONParser jsonParser = new JSONParser();

    		JSONObject jsonObj = (JSONObject) jsonParser.parse(requestString);

    		if((Long)jsonObj.get("code")== 0){

    		JSONObject getToken = (JSONObject) jsonObj.get("response");

    		System.out.println("getToken==>>"+getToken.get("access_token") );

    		Itoken = (String)getToken.get("access_token");

    		}

    		}catch(Exception e){

    		e.printStackTrace();

    		Itoken = "";

    		}
    		
    		
    		return Itoken;
    	}
    	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String cmd =request.getRequestURI().substring(request.getContextPath().length());   // /cart/cartList.jsp
	//	System.out.println(cmd);
		session = request.getSession(); // 내장객체가 아니기 때문에 선언하고 써야한다.
		
		// 장바구니 등록
		if(cmd.equals("/Cart/cartDamgiyo.do")) {
			cartDamgiyo(request);
			response.sendRedirect(url);	// redirect하고// write를 하고 나서는 redirect로 boardwrite.do 처리해야한다.( 이건 위에서 처리하기)
			return; //return이 없으면 밑에 forward가 실행되기 때문에 return을 해서 중단시키는것.
		}
		
		// 장바구니 목록
		if(cmd.equals("/Cart/cartList.do")) {
			cartList(request);
		}
		
		// 장바구니 삭제
		if(cmd.equals("/Cart/cartRemove.do")) {
			cartRemove(request);
			response.sendRedirect(url);
			return;
		}
		
		// 장바구니 비우기
		if(cmd.equals("/Cart/cartRemoveAll.do")) {
			cartRemoveAll(request);
			response.sendRedirect(url);
			return;
		}
		
		// 결제 전 정보조회
		if(cmd.equals("/Cart/cartView.do")) {
			cartView(request);
			
		}
		
		// 회원 예약 목록  / yeyakList
		if(cmd.equals("/Yeyak/yeyakList.do")) {
			yeyakList(request);
					
		}
		
		// 호스트 예약 목록  /  hoyeyakList
		if(cmd.equals("/Yeyak/hoyeyakList.do")) {
			hoyeyakList(request);
							
		}
		
		// 회원 예약 확인/취소 상세 조회 /yeyakView.do
		if(cmd.equals("/Yeyak/yeyakView.do")) {
			yeyakView(request);
					
		}
		
		// 호스트 예약 확인/취소 상세 조회 /hoyeyakView.do
		if(cmd.equals("/Yeyak/hoyeyakView.do")) {
			hoyeyakView(request);
							
		}
		
		// 예약등록  /yeyakHagi.do
		if(cmd.equals("/Yeyak/yeyakHagi.do")) {
			yeyakHagi(request);
			response.sendRedirect(url);
			return;
									
		}
		
		// 예약취소  /yeyakModify.do
		if(cmd.equals("/Yeyak/yeyakModify.do")) {
			yeyakModify(request);
			response.sendRedirect(url);
			return;
											
				}
		
		
			// 결제취소  /payCancel.do
			if(cmd.equals("/Yeyak/payCancel.do")) {
				payCancel(request);									
			}
	
		
		
		
		RequestDispatcher rdp = request.getRequestDispatcher(url);
		rdp.forward(request, response);
		
		
	}

		// 장바구니 담기 (등록)
		public void cartDamgiyo(HttpServletRequest request) throws UnsupportedEncodingException  {
		
			
			//// SEQ_CART_cno.NEXTVAL,21,'UserFour','2023-08-08','2023-08-29', 3
			 CartVO cvo = new CartVO();       // CartVO 객체 생성
			 RoomVO rvo = new RoomVO();
			 MemberVO mvo = new MemberVO();
			  
			 
			 String mid = (String) session.getAttribute("mid");
			 
			 int rmNo =	 Integer.parseInt(request.getParameter("rmNo"));
			 int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			 String checkIn = request.getParameter("checkIn");
			 String checkOut = request.getParameter("checkOut");
			 int people = Integer.parseInt(request.getParameter("people"));
			 String keyword = request.getParameter("keyword");
			 
			 
			 rvo.setRmNo(rmNo); // 숙소번호
			 mvo.setMid(mid);     // 회원이름은 세션에서 넘어온다.
			 cvo.setCheckIn(checkIn);   // 여기에서 값이 넘어올때 String으로 넘어온다.  yyyy-mm-dd? '2023-08-07'
			 cvo.setCheckOut(checkOut); 
			 cvo.setGuest(people);
			 
			 cvo.setRvo(rvo);
			 cvo.setMvo(mvo);
			 
			 System.out.println(Integer.parseInt(request.getParameter("rmNo")));
			 System.out.println(request.getParameter("checkIn"));
			 System.out.println(request.getParameter("checkOut"));
			 System.out.println(Integer.parseInt(request.getParameter("people")));
			 System.out.println(keyword);
			 
			 if(cdao.cartInsert(cvo)){
				session.setAttribute("msg", "장바구니 담기 완료!"); 
			 }
		     else{
		    	 session.setAttribute("msg", "장바구니 담기 실패!"); 
		     }
			 //&people=1&keyword=호텔스타

			 String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
			 
			 url="/jejuOseyo/Room/RoomInfo.do?rmNo="+ rmNo + "&pageNum=" + pageNum
					 + "&checkIn=" + checkIn + "&checkOut=" + checkOut  + "&people=" + people + "&keyword=" + encodedKeyword ; // 숙소 상세조회로 간다. 일단 index로 가도록 처리
			
		}
		
		// 장바구니 전체 목록 /cartlist.do
		public void cartList(HttpServletRequest request) {
			// 여기서도 회원이 로그인한 아이디값 보내야함 !!!!!
		
			 
			 String mid = (String) session.getAttribute("mid");
			
			int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			int totalCnt = cdao.totalCount(mid);
			int pages = (int)(Math.ceil(totalCnt / AMOUNT_PER_PAGE)); 
			
			// 각 페이지의 시작 페이지 번호 | ... | 끝번호
			int end =(int)(Math.ceil(pageNum / NUM_PER_PAGE)*NUM_PER_PAGE); 
			int start =end - (int)(NUM_PER_PAGE-1);
			end = end > pages ? pages : end;
			
			// 이전 | 이후 버튼 활성화 여부
			boolean prev = start > 1;
			boolean next = end < pages;
			
			// CartVO 객체에서 RoomVO 객체 얻기
			//	RoomVO rvo = cvo.getRvo();
			
			// RoomVO 객체에서 rm_name 가져오기
			//	String rmName = rvo.getRmName();
			
			// 로그인을 하면 세션에 아이디가 저장되어 있으니까
			// 로그인 처리가 끝났으면 나는 getAttribute만 하면 된다.
			// 세션에서 저장된 아이디 값 일단 여기서 처리 ( 위에서 같이써서 위에다 처리하자!! )
			 
			 request.setAttribute("cartList", cdao.cartSelectAll(AMOUNT_PER_PAGE,pageNum,mid));   // 조회 list dao에서 호출하고 반환되는 값을 요청객체에 담는다.  // 한 페이지에 5개씩 1페이지
			 request.setAttribute("totalCnt", totalCnt);	// 숫자 totalConunt request속성에 담는다?
			
			 // 여기 뷰 단 꾸밀때 페이징 처리하자.
			 request.setAttribute("pageNum", pageNum);
			 request.setAttribute("pages", pages);
			 request.setAttribute("end", end);
			 request.setAttribute("start", start);
			 request.setAttribute("prev", prev);
			 request.setAttribute("next", next);
			 
			 url ="/cart/cartList.jsp";
			
		}
		
		// 결제 전 예약 정보조회 /CartView.do
		public void cartView(HttpServletRequest request) {
			int pageNum = Integer.parseInt(request.getParameter("pageNum"));  // 페이지 넘버 꺼낸다
			  
			
			int cno = Integer.parseInt(request.getParameter("cno"));         // 장바구니 번호
			 System.out.println(cno);
			 
				// 담는다.
				request.setAttribute("pageNum", pageNum);
				request.setAttribute("cvo", cdao.cartSelect(cno));   //  cartVO cvo = cdao.select(cno);   
										// cvo 객체에 담아서? select 불러오기  , forward나 redirct 하기 view 페이지로
				url ="/cart/yeyakCheck.jsp";
			
		}
		
		// 장바구니 삭제  /cart/remove.do
		public void cartRemove(HttpServletRequest request) {
			
			 String mid =(String)session.getAttribute("mid");
			
			  int cno = Integer.parseInt(request.getParameter("cno"));
			  System.out.println(cno);
			  
			  if(cdao.cartDelete(cno)){ 
				  session.setAttribute("msg", "장바구니에서 삭제 완료!");
			  }
			  else{ 
				  session.setAttribute("msg", "장바구니에서 삭제 실패"); }
			  
			  url = "/jejuOseyo/Cart/cartList.do?pageNum=1&mid=" + mid;
			   // 삭제하면 다시 1페이지로 돌아간다.
			
		}
		
		// 장바구니 비우기 /cart/removeAll.do
		public void cartRemoveAll(HttpServletRequest request) {
				// 여긴 로그인한 아이디가 필요하다.
				// 그래서 임시로 session을 저장했다.
				
			 
			 String mid =(String)session.getAttribute("mid");
			// String mid = request.getParameter("mid");
			  System.out.println(mid);
			  
			  if(cdao.cartDeleteAll(mid)){ 
				  session.setAttribute("msg", "장바구니 비우기 완료!");
			  }
			  else{ 
				  session.setAttribute("msg", "장바구니 비우기 실패!"); }
			  
			  url = "/jejuOseyo/Cart/cartList.do?pageNum=1&mid=" + mid;
			
			
		}
		
		// 예약등록  /yeyakHagi.do
		public void yeyakHagi(HttpServletRequest request) {
			
			Random ran = new Random();
			
			PaymentVO pvo = new PaymentVO();       // PaymentVO 객체 생성
			YeyakVO yvo = new YeyakVO();
			MemberVO mvo = new MemberVO();
			HostVO hvo = new HostVO();
			RoomVO rvo = new RoomVO();
			CartVO cvo = new CartVO();
			
			 String impUid = request.getParameter("impUid");
		     String merchantUid = request.getParameter("merchantUid");
		     String cardName = request.getParameter("cardName");
		     String cardNumber = request.getParameter("cardNumber");
		     int amount = Integer.parseInt(request.getParameter("amount"));
		     String mid = request.getParameter("mid");
		     String hid = request.getParameter("hid");
		     int rmNo = Integer.parseInt(request.getParameter("rmNo"));
		     String name = request.getParameter("name");
		     String checkIn = request.getParameter("checkIn");
		     String checkOut = request.getParameter("checkOut");
		     int guest = Integer.parseInt(request.getParameter("guest"));
		     String mphone = request.getParameter("mphone");
		     int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		     int cno = Integer.parseInt(request.getParameter("cno"));
		    
		     if(cardName.equals("null")) {
		    	 cardName = "kakaopay";
		     }
		     
	    	int randomNum = ran.nextInt(10000)+1;
	    	String applyNum = randomNum + "";
		    
					 
			// 결제 정보 등록 | 결제번호, 카드이름,카드번호,결제금액, 결제 일시
		   // 예약 등록 | 예약번호, 회원이름, 호스트이름, 숙소번호, 결제번호, 체크인, 체크아웃, 게스트 
		    
			 pvo.setPayNo(applyNum);
			 pvo.setCardName(cardName);
			 pvo.setCardNum(cardNumber);
			 pvo.setAmount(amount);
			 pvo.setPayDate("2023-08-10");  // 일단 이렇게 저장
			 
			 yvo.setYno(merchantUid);
			 mvo.setMid(mid);
			 hvo.setHid(hid);
			 rvo.setRmNo(rmNo);
			 yvo.setYeCheckIn(checkIn);
			 yvo.setYeCheckOut(checkOut);
			 yvo.setYeGuest(guest);
			 cvo.setCno(cno);
			 
			 yvo.setMvo(mvo);
			 yvo.setHvo(hvo);
			 yvo.setRvo(rvo);
			 yvo.setPvo(pvo);
			 yvo.setCvo(cvo);
			 	
			 
			 if(ydao.payInsert(pvo)){
				  System.out.println("결제테이블 성공");
				 if(ydao.yeInsert(yvo)) {
					 System.out.println("예약테이블 성공");
					 if(cdao.cartDelete(cno)) {
						 System.out.println("장바구니 삭제 성공");
						 session.setAttribute("msg", "예약 완료! 예약목록으로 이동합니다. ");
						 
					 }else {
						System.out.println("장바구니 삭제 실패"); 
					 }
				 }else {
					 System.out.println("예약테이블 실패");
				 }
				
			 }
		     else{
		    	System.out.println("결제 테이블 실패");
		    	session.setAttribute("msg", "예약 실패!");
		     }
			 
			url="/jejuOseyo/Yeyak/yeyakList.do?pageNum=1&mid=" + mid; 
			
		     
		 
		}
		
		// 회원 예약전체목록 /YeyakList.do
		public void yeyakList(HttpServletRequest request) {

			 String mid = request.getParameter("mid");
			int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			int totalCnt = ydao.totalCount(mid);
			int pages = (int)(Math.ceil(totalCnt / AMOUNT_PER_PAGE)); 
			
			// 각 페이지의 시작 페이지 번호 | ... | 끝번호
			int end =(int)(Math.ceil(pageNum / NUM_PER_PAGE)*NUM_PER_PAGE); 
			int start =end - (int)(NUM_PER_PAGE-1);
			end = end > pages ? pages : end;
			
			// 이전 | 이후 버튼 활성화 여부
			boolean prev = start > 1;
			boolean next = end < pages;
			
			// CartVO 객체에서 RoomVO 객체 얻기
			//	RoomVO rvo = cvo.getRvo();
			
			// RoomVO 객체에서 rm_name 가져오기
			//	String rmName = rvo.getRmName();
			
			// 로그인을 하면 세션에 아이디가 저장되어 있으니까
			// 로그인 처리가 끝났으면 나는 getAttribute만 하면 된다.
			// 세션에서 저장된 아이디 값 일단 여기서 처리 (이것도 위에서 처리하자!!!!)
					 
			 request.setAttribute("yeyakList", ydao.yeSelectAll(AMOUNT_PER_PAGE,pageNum,mid));   // 조회 list dao에서 호출하고 반환되는 값을 요청객체에 담는다.  // 한 페이지에 5개씩 1페이지
			 request.setAttribute("totalCnt", totalCnt);	// 숫자 totalConunt request속성에 담는다?
			
			 // 여기 뷰 단 꾸밀때 페이징 처리하자.
			 request.setAttribute("pageNum", pageNum);
			 request.setAttribute("pages", pages);
			 request.setAttribute("end", end);
			 request.setAttribute("start", start);
			 request.setAttribute("prev", prev);
			 request.setAttribute("next", next);
			 
			 url ="/yeyak/yeyakList.jsp";
		}
		
		// 호스트 예약전체목록 /hoyeyakList.do
		public void hoyeyakList(HttpServletRequest request) {
			
			
			 
			 String hid = (String) session.getAttribute("hid");
			
			
			int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			int hototalCnt = ydao.hototalCount(hid);
			int pages = (int)(Math.ceil(hototalCnt / AMOUNT_PER_PAGE)); 
			
			// 각 페이지의 시작 페이지 번호 | ... | 끝번호
			int end =(int)(Math.ceil(pageNum / NUM_PER_PAGE)*NUM_PER_PAGE); 
			int start =end - (int)(NUM_PER_PAGE-1);
			end = end > pages ? pages : end;
			
			// 이전 | 이후 버튼 활성화 여부
			boolean prev = start > 1;
			boolean next = end < pages;
			
			// CartVO 객체에서 RoomVO 객체 얻기
			//	RoomVO rvo = cvo.getRvo();
			
			// RoomVO 객체에서 rm_name 가져오기
			//	String rmName = rvo.getRmName();
			
			// 로그인을 하면 세션에 아이디가 저장되어 있으니까
			// 로그인 처리가 끝났으면 나는 getAttribute만 하면 된다.
			// 세션에서 저장된 아이디 값 일단 여기서 처리 ( 이것도 위에서 처리 )
		
			 
			 request.setAttribute("hoyeyakList", ydao.hoyeSelectAll(AMOUNT_PER_PAGE,pageNum,hid));   // 조회 list dao에서 호출하고 반환되는 값을 요청객체에 담는다.  // 한 페이지에 5개씩 1페이지
			 request.setAttribute("hototalCnt", hototalCnt);	// 숫자 totalConunt request속성에 담는다?
			
			 // 여기 뷰 단 꾸밀때 페이징 처리하자.
			 request.setAttribute("pageNum", pageNum);
			 request.setAttribute("pages", pages);
			 request.setAttribute("end", end);
			 request.setAttribute("start", start);
			 request.setAttribute("prev", prev);
			 request.setAttribute("next", next);
			 
			 url ="/yeyak/hoyeyakList.jsp";
		}
		
		// 회원 예약 확인/취소 상세 조회 /yeyakView.do
		public void yeyakView(HttpServletRequest request) {
			int pageNum = Integer.parseInt(request.getParameter("pageNum"));  // 페이지 넘버 꺼낸다
			  
			String yno = request.getParameter("yno");         // 예약 번호
			System.out.println(yno);
			 
				// 담는다.
				request.setAttribute("pageNum", pageNum);
				request.setAttribute("yvo", ydao.yeSelect(yno));   //  cartVO cvo = cdao.select(cno);   
										// cvo 객체에 담아서? select 불러오기  , forward나 redirct 하기 view 페이지로
				url ="/yeyak/yeyakDetail.jsp";
			
		}
		
		// 호스트 예약 확인/취소 상세 조회 /hoyeyakView.do
		public void hoyeyakView(HttpServletRequest request) {
			
			int pageNum = Integer.parseInt(request.getParameter("pageNum"));  // 페이지 넘버 꺼낸다
			  
			String yno = request.getParameter("yno");         // 예약 번호
			System.out.println(yno);
			 
				// 담는다.
				request.setAttribute("pageNum", pageNum);
				request.setAttribute("yvo", ydao.yeSelect(yno));   //  cartVO cvo = cdao.select(cno);   
										// cvo 객체에 담아서? select 불러오기  , forward나 redirct 하기 view 페이지로
				url ="/yeyak/hoyeyakDetail.jsp";
		}
		
		// 예약 가능상태 수정 /yeyakModify.do
		public void yeyakModify(HttpServletRequest request)  {
			
			String payno = request.getParameter("payno");     // 결제번호
			
			String mid =(String)session.getAttribute("mid");    // 로그인 아이디
			
			if(ydao.yeUpdate(payno)){
				System.out.println("예약취소 완료");
				session.setAttribute("msg", "예약이 취소되었습니다! 장바구니 목록에 다시 추가되었습니다.");
			 }
		     else{
		    	 System.out.println("예약취소 실패!");
		     }
			 
			url = "/jejuOseyo/Yeyak/yeyakList.do?pageNum=1&mid=" + mid;
			
			
		}
		
		// 여긴 아임포트 결제 취소하는 곳
		public void payCancel(HttpServletRequest request) throws IOException {
			
			// 아임포트 토큰 가져오기
			String access_token  = getToken();
			System.out.println(access_token);
			
			String reason = "예약취소";								// 이건 그냥 통일한 결제취소 이유
			
			int amount = Integer.parseInt(request.getParameter("price"));  // 여기에 취소가격 가져오기
			
			//int amount = 100;
			
			String yno = request.getParameter("yno");						// 이거는 결제취소할때 보낼거merchan
			
			System.out.println(yno);
			try {
				
			String requestString = IMPORT_CANCEL_URL;
			// HttpURLConnection 인스턴스 객체 생성
			HttpURLConnection conn = null;

			// URL 검증 및 객체 생성
			URL urls = new URL(IMPORT_CANCEL_URL);
	 
			// URL 연결(웹페이지 URL 연결)
			conn = (HttpsURLConnection) urls.openConnection();
	 
			// 요청방식 선택
			conn.setRequestMethod("POST");
	 
			// 타입설정(application/json) 형식으로 전송
			// (Request Body 전달 시 applicion/json 로 서버에 전달
			conn.setRequestProperty("Content-type", "application/json");

			// 서버 Response Data를 JSON 형식의 타입으로 요청
			conn.setRequestProperty("Accept", "application/json");

			// 권한을 access_token으로 부여
			conn.setRequestProperty("Authorization", access_token);
		 // OutputStram으로 POST 데이터를 넘겨주겠다는 옵션.
			conn.setDoOutput(true);
			
			JSONObject json = new JSONObject();
		 
			// 여기 안되면 put으로 해야할듯 
			json.put("reason", reason);
			json.put("merchant_uid", yno);   // 정답 알았다....  난 mechant_uid를 보내야함.. 바보냐
			json.put("amount", amount);
			json.put("checksum", amount);
	 
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	 
			bw.write(json.toString());

			// flush는 쉽게 말해서 stream에 남아 있는 데이터를 강제로 내보내는 역할
			bw.flush();
			bw.close();

			int result = 0;
			int responseCode = conn.getResponseCode();  // 응답코드 받기
			System.out.println("응답코드는??" + responseCode);

			if(responseCode==200){ // 성공
				System.out.println("결제 취소 성공!");
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				
				StringBuilder sb = new StringBuilder();
				
				String line = null;

	    		while ((line = br.readLine()) != null) {

	    		sb.append(line + "\n");

	    		}


	    		requestString = sb.toString();

				
				JSONParser jsonParser = new JSONParser();

	    		JSONObject jsonObj = (JSONObject) jsonParser.parse(requestString);

	    		System.out.println(jsonObj.get("code"));    // 뭐가 잘못된거여!! 코드 알아보기
	    		System.out.println(jsonObj.get("message"));		// 오류 메시지 
	    		    		
	    	
				br.close();
				conn.disconnect();	
				
			
			}else {
				System.out.println("결제 취소 실패"); }
			
			
			}catch(Exception e){

	    		e.printStackTrace();
		}
		}
}

