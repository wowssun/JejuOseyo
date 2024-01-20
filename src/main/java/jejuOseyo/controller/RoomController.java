package jejuOseyo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import jejuOseyo.dao.ReviewDAO;
import jejuOseyo.dao.RggDAO;
import jejuOseyo.dao.RoomDAO;
import jejuOseyo.vo.ReviewVO;
import jejuOseyo.vo.RggVO;
import jejuOseyo.vo.RoomVO;

//@WebServlet("*.do") 
@WebServlet({"/Room/*", "/Review/*", "/Rgg/*"}) 
public class RoomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;

	private RoomDAO rmdao;
	private ReviewDAO rvdao;
	private RggDAO rgdao;

	private String url;

	private static final double AMOUNT_PER_PAGE = 10.0; // 한 페이지의 게시물 수
	private static final double NUM_PER_PAGE = 5.0; // 한 페이지에 표시할 페이지 번호 수

	public void init(ServletConfig config) throws ServletException {
		
		ServletContext servletCtx = config.getServletContext();
		Connection con = (Connection) servletCtx.getAttribute("con");

		rmdao = new RoomDAO(con);
		rvdao = new ReviewDAO(con);
		rgdao = new RggDAO(con);
		

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		request.setCharacterEncoding("UTF-8");
		String cmd = request.getRequestURI().substring(request.getContextPath().length()); // /cart/cartList.jsp
		System.out.println(cmd);
		session = request.getSession();
	
//////////////////////////////////
		if (cmd.equals("/Room/RoomAddForm.do")) { // 숙소목록에서 등록을 누른 경우
			url = "/room/roomAdd.jsp";
		}

		if (cmd.equals("/Room/RoomAdd.do")) {
			rmWrite(request);
			response.sendRedirect(url);
			return;
		}
		if (cmd.equals("/Room/SearchRoomList.do")) {
		    searchList(request);
		}
		if (cmd.equals("/Room/SortByStar.do")) {
		    searchList2(request);
		}

		
		if (cmd.equals("/Room/AllRoomList.do")) {
			allList(request);
		}
		if (cmd.equals("/Room/MyRoomList.do")) {
			myList(request);
		}
		if (cmd.equals("/Room/RoomInfo.do")) {
			rmView(request);
		}
		if (cmd.equals("/Room/RoomModifyForm.do")) { // 상세조회 -> 수정폼 연결
			rmView(request);
			url = "/room/roomModify.jsp";
		}
		if (cmd.equals("/Room/RoomModify.do")) {
			rmModify(request);
			response.sendRedirect(url);
			return;
		}
		if (cmd.equals("/Room/RoomRemove.do")) {
			rmRemove(request);
			response.sendRedirect(url);
			return;
		}
		if (cmd.equals("/Room/RoomRemoveAd.do")) {
			rmRemoveAd(request);
			response.sendRedirect(url);
			return;
		}
		///////////////////////////////////////////리뷰
		if (cmd.equals("/Review/ReviewAdd.do")) {
			rvWrite(request);
			response.sendRedirect(url);
			return;
		}
		if (cmd.equals("/Review/ReviewInfo.do")) {
			rvView(request);
		}
		if (cmd.equals("/Review/ReviewModify.do")) {
			rvModify(request);
			response.sendRedirect(url);
			return;
		}
		if (cmd.equals("/Review/ReviewRemove.do")) {
			rvRemove(request);
			response.sendRedirect(url);
			return;
		}
		///////////////////////////////////////////숙소찜
		if (cmd.equals("/Rgg/RggList.do")) {
		    rggList(request);
		}
		if (cmd.equals("/Rgg/RggRemove.do")) {
			rggRemove(request);
			response.sendRedirect(url);
			return;
		}
		if (cmd.equals("/Rgg/RggClear.do")) {
			rggClear(request);
			response.sendRedirect(url);
			return;
		}
		if (cmd.equals("/Rgg/RggAdd.do")) {
			rggAdd(request);
			response.sendRedirect(url);
			return;
		}
		RequestDispatcher rdp = request.getRequestDispatcher(url);
		rdp.forward(request, response);
	}

//////////////////////////////////////////
	private void rmWrite(HttpServletRequest request) throws IOException {// 숙소등록

		String savePath = "/resources/images";
		ServletContext context = request.getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		int maxSize = 1024 * 1024 * 10; // 최대 업로드 크기 10
		String encType = "UTF-8";

		MultipartRequest multi = new MultipartRequest(request, uploadFilePath, maxSize, encType,
				new DefaultFileRenamePolicy());
		String hid = multi.getParameter("hid");
		RoomVO rmvo = new RoomVO();
		rmvo.setHid(multi.getParameter("hid"));
		rmvo.setRmName(multi.getParameter("rmName"));
		rmvo.setAddr1(multi.getParameter("addr1"));
		rmvo.setAddr2(multi.getParameter("addr2"));
		rmvo.setPeople(Integer.parseInt(multi.getParameter("people")));
		rmvo.setPrice(Integer.parseInt(multi.getParameter("price")));
		rmvo.setImg(multi.getFilesystemName("img"));
		rmvo.setImg2(multi.getFilesystemName("img2"));
		rmvo.setImg3(multi.getFilesystemName("img3"));
		rmvo.setMemo(multi.getParameter("memo"));
		rmvo.setNotice(multi.getParameter("notice"));

		if (rmdao.insert(rmvo)) {
			session.setAttribute("msg", "숙소등록을 성공했습니다.");
		} else {
			session.setAttribute("msg", "숙소등록을 실패했습니다.");
		}
		url = "/jejuOseyo/Room/MyRoomList.do?hid="+hid+"&pageNum=1";
	}

	public void searchList(HttpServletRequest request)  {// 숙소검색목록
		// 별점 평점 계산 setAttribute
		
		String keyword = request.getParameter("keyword");
		int people = Integer.parseInt(request.getParameter("people"));

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String checkInStr = request.getParameter("checkIn");
			Date checkIn = sdf.parse(checkInStr);
			String chekOutStr = request.getParameter("checkOut");
			Date checkOut = sdf.parse(chekOutStr);
			System.out.println(checkIn);
			System.out.println(checkOut);
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int totalCnt = rmdao.searchCount(keyword, people, checkIn, checkOut);
		System.out.println(totalCnt);
		int pages = (int) (Math.ceil(totalCnt / AMOUNT_PER_PAGE));

		// 각 페이지의 시작 번호 | ... | 끝 번호
		int end = (int) (Math.ceil(pageNum / NUM_PER_PAGE) * NUM_PER_PAGE);
		int start = end - (int) (NUM_PER_PAGE - 1);
		end = end >= pages ? pages : end;

		// 이전 | 이후 버튼 활성화 여부
		boolean prev = start > 1;
		boolean next = end < pages;
		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("searchRoomList", rmdao.search(AMOUNT_PER_PAGE,pageNum,keyword,people, checkIn, checkOut));
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("end", end);
		request.setAttribute("start", start);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);
		request.setAttribute("keyword", keyword);
		request.setAttribute("checkIn", checkIn);
		request.setAttribute("checkOut", checkOut);
		request.setAttribute("people", people);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url = "/room/searchRoomList.jsp";
	}
	public void searchList2(HttpServletRequest request)  {// 숙소검색목록 별점순정렬
		// 별점 평점 계산 setAttribute
		
		String keyword = request.getParameter("keyword");
		int people = Integer.parseInt(request.getParameter("people"));

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String checkInStr = request.getParameter("checkIn");
			Date checkIn = sdf.parse(checkInStr);
			String chekOutStr = request.getParameter("checkOut");
			Date checkOut = sdf.parse(chekOutStr);
			System.out.println(checkIn);
			System.out.println(checkOut);
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int totalCnt = rmdao.searchCount(keyword, people, checkIn, checkOut);
		System.out.println(totalCnt);
		int pages = (int) (Math.ceil(totalCnt / AMOUNT_PER_PAGE));

		// 각 페이지의 시작 번호 | ... | 끝 번호
		int end = (int) (Math.ceil(pageNum / NUM_PER_PAGE) * NUM_PER_PAGE);
		int start = end - (int) (NUM_PER_PAGE - 1);
		end = end >= pages ? pages : end;

		// 이전 | 이후 버튼 활성화 여부
		boolean prev = start > 1;
		boolean next = end < pages;
		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("searchRoomList", rmdao.search2(AMOUNT_PER_PAGE,pageNum,keyword,people, checkIn, checkOut));
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("end", end);
		request.setAttribute("start", start);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);
		request.setAttribute("keyword", keyword);
		request.setAttribute("checkIn", checkIn);
		request.setAttribute("checkOut", checkOut);
		request.setAttribute("people", people);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url = "/room/sortByStar.jsp";
	}
	private void myList(HttpServletRequest request) {// 나의 숙소목록
		
		String hid = request.getParameter("hid");
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int totalCnt = rmdao.myCount(hid);
		int pages = (int) (Math.ceil(totalCnt / AMOUNT_PER_PAGE));

		// 각 페이지의 시작 번호 | ... | 끝 번호
		int end = (int) (Math.ceil(pageNum / NUM_PER_PAGE) * NUM_PER_PAGE);
		int start = end - (int) (NUM_PER_PAGE - 1);
		end = end >= pages ? pages : end;

		// 이전 | 이후 버튼 활성화 여부
		boolean prev = start > 1;
		boolean next = end < pages;

		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("myRoomList", rmdao.myRoom(AMOUNT_PER_PAGE, pageNum, hid));
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("end", end);
		request.setAttribute("start", start);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);

		url = "/room/myRoomList.jsp";
	}

	private void allList(HttpServletRequest request) {// 전체 숙소목록
		// 별점 평점 계산 setAttribute
		String keyword = request.getParameter("keyword");
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int totalCnt = rmdao.totalCount(keyword);
		int pages = (int) (Math.ceil(totalCnt / AMOUNT_PER_PAGE));

		// 각 페이지의 시작 번호 | ... | 끝 번호
		int end = (int) (Math.ceil(pageNum / NUM_PER_PAGE) * NUM_PER_PAGE);
		int start = end - (int) (NUM_PER_PAGE - 1);
		end = end >= pages ? pages : end;

		// 이전 | 이후 버튼 활성화 여부
		boolean prev = start > 1;
		boolean next = end < pages;
		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("allRoomList", rmdao.selectAll(AMOUNT_PER_PAGE, pageNum, keyword));
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("end", end);
		request.setAttribute("start", start);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);
		request.setAttribute("keyword", keyword);

		url = "/room/allRoomList.jsp";
	}

	public void rmView(HttpServletRequest request) {// 숙소 상세보기+리뷰목록
		int rmNo = Integer.parseInt(request.getParameter("rmNo"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int totalCnt = rvdao.totalCount(rmNo);
		int pages = (int) (Math.ceil(totalCnt / AMOUNT_PER_PAGE));

		// 각 페이지의 시작 번호 | ... | 끝 번호
		int end = (int) (Math.ceil(pageNum / NUM_PER_PAGE) * NUM_PER_PAGE);
		int start = end - (int) (NUM_PER_PAGE - 1);
		end = end >= pages ? pages : end;

		// 이전 | 이후 버튼 활성화 여부
		boolean prev = start > 1;
		boolean next = end < pages;

		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("end", end);
		request.setAttribute("start", start);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);
		request.setAttribute("view", rmdao.select(rmNo));
		request.setAttribute("rvList",  rvdao.selectAll(AMOUNT_PER_PAGE,pageNum, rmNo));  //////
		
		url = "/room/roomInfo.jsp";
	}

	private void rmModify(HttpServletRequest request) throws IOException { // 숙소 수정

		String savePath = "/resources/images";
		ServletContext context = request.getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		int maxSize = 1024 * 1024 * 10; // 최대 업로드 크기 10
		String encType = "UTF-8";

		MultipartRequest multi = new MultipartRequest(request, uploadFilePath, maxSize, encType,
				new DefaultFileRenamePolicy());

		RoomVO rmvo = new RoomVO();
		String hid = multi.getParameter("hid");
		rmvo.setRmName(multi.getParameter("rmName"));
		rmvo.setAddr1(multi.getParameter("addr1"));
		rmvo.setAddr2(multi.getParameter("addr2"));
		rmvo.setPeople(Integer.parseInt(multi.getParameter("people")));
		rmvo.setPrice(Integer.parseInt(multi.getParameter("price")));
		rmvo.setImg(multi.getFilesystemName("img"));
		rmvo.setImg2(multi.getFilesystemName("img2"));
		rmvo.setImg3(multi.getFilesystemName("img3"));
		rmvo.setMemo(multi.getParameter("memo"));
		rmvo.setNotice(multi.getParameter("notice"));
		rmvo.setRmNo(Integer.parseInt(multi.getParameter("rmNo")));

		if (rmdao.update(rmvo)) {
			session.setAttribute("msg", "숙소가 수정되었습니다.");
		} else {
			session.setAttribute("msg", "숙소 수정에 실패했습니다.");
		}
		// int pageNum = Integer.parseInt(request.getParameter("pageNum"));

		url = "/jejuOseyo/Room/MyRoomList.do?hid="+hid+"&pageNum=1";
	}

	private void rmRemove(HttpServletRequest request) { // 숙소 삭제
		int rmNo = Integer.parseInt(request.getParameter("rmNo"));
		String hid = request.getParameter("hid");
		if (rmdao.delete(rmNo)) {
			session.setAttribute("msg", "숙소가 삭제되었습니다.");
		} else {
			session.setAttribute("msg", "숙소 삭제에 실패했습니다.");
		}
		url = "/jejuOseyo/Room/MyRoomList.do?hid="+hid+"&pageNum=1";
	}
	private void rmRemoveAd(HttpServletRequest request) { // 숙소 삭제
		int rmNo = Integer.parseInt(request.getParameter("rmNo"));
		if (rmdao.delete(rmNo)) {
			session.setAttribute("msg", "숙소가 삭제되었습니다.");
		} else {
			session.setAttribute("msg", "숙소 삭제에 실패했습니다.");
		}
		url = "/jejuOseyo/Room/AllRoomList.do?pageNum=1&keyword=";
	}

	private void rvWrite(HttpServletRequest request) { // 리뷰 등록
		ReviewVO revo = new ReviewVO();
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int rmNo = Integer.parseInt(request.getParameter("rmNo"));
		revo.setMid(request.getParameter("mid"));
		revo.setTitle(request.getParameter("title"));
		revo.setContent(request.getParameter("content"));
		revo.setStar(Integer.parseInt(request.getParameter("star")));
		revo.setRmNo(rmNo);
		
		
		if (rvdao.insert(revo)) {
			session.setAttribute("msg", "리뷰가 등록되었습니다.");
		} else {
			session.setAttribute("msg", "리뷰등록에 실패했습니다.");
		}
		session.setAttribute("rmNo", rmNo);
		 url= "/jejuOseyo/Room/RoomInfo.do?rmNo="+rmNo+"&pageNum="+pageNum;
	}


	private void rvView(HttpServletRequest request) { // 리뷰 상세조회
		int rvNo = Integer.parseInt(request.getParameter("rvNo"));
		request.setAttribute("reviewInfo", rvdao.select(rvNo));

		url = "../review/reviewInfo.jsp";
	}

	private void rvModify(HttpServletRequest request){ // 리뷰 수정
		ReviewVO revo = new ReviewVO();
		int rvNo = Integer.parseInt(request.getParameter("rvNo"));
		int rmNo =Integer.parseInt(request.getParameter("rmNo"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		revo.setRvNo(rvNo);
		revo.setTitle(request.getParameter("title"));
		revo.setContent(request.getParameter("content"));
		revo.setStar(Integer.parseInt(request.getParameter("star")));

		if (rvdao.update(revo)) {
			session.setAttribute("msg", "리뷰가 수정되었습니다.");
		} else {
			session.setAttribute("msg", "리뷰 수정에 실패했습니다.");
		}
		
		url="/jejuOseyo/Room/RoomInfo.do?rmNo="+ rmNo + "&pageNum=" + pageNum; 
	}

	private void rvRemove(HttpServletRequest request){ // 리뷰 삭제
		int rvNo = Integer.parseInt(request.getParameter("rvNo"));
		int rmNo =Integer.parseInt(request.getParameter("rmNo"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		if (rvdao.delete(rvNo)) {
			session.setAttribute("msg", "리뷰가 삭제되었습니다.");
		} else {
			session.setAttribute("msg", "리뷰 삭제에 실패했습니다.");
		}

		url="/jejuOseyo/Room/RoomInfo.do?rmNo="+ rmNo + "&pageNum=" + pageNum; 
	}
	private void rggList(HttpServletRequest request) { // 숙소찜 목록
		String mid = request.getParameter("mid");
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int totalCnt = rgdao.totalCount(mid);
		int pages = (int) (Math.ceil(totalCnt / AMOUNT_PER_PAGE));

		// 각 페이지의 시작 번호 | ... | 끝 번호
		int end = (int) (Math.ceil(pageNum / NUM_PER_PAGE) * NUM_PER_PAGE);
		int start = end - (int) (NUM_PER_PAGE - 1);
		end = end >= pages ? pages : end;

		// 이전 | 이후 버튼 활성화 여부
		boolean prev = start > 1;
		boolean next = end < pages;

		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("rggList", rgdao.selectAll(AMOUNT_PER_PAGE, pageNum, mid));
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("end", end);
		request.setAttribute("start", start);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);

		

		url = "../rgg/rggList.jsp";
	}

	private void rggAdd(HttpServletRequest request) throws UnsupportedEncodingException { // 숙소찜 담기
		System.out.println("rggAdd");
		RggVO rgvo = new RggVO();
		int rmNo = Integer.parseInt(request.getParameter("rmNo"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		String mid = request.getParameter("mid");
		 String checkIn = request.getParameter("checkIn");
		 String checkOut = request.getParameter("checkOut");
		 int people = Integer.parseInt(request.getParameter("people"));
		 String keyword = request.getParameter("keyword");
		 rgvo.setCheckIn(checkIn);   // 여기에서 값이 넘어올때 String으로 넘어온다.  yyyy-mm-dd? '2023-08-07'
		 rgvo.setCheckOut(checkOut); 
		 rgvo.setPeople(people);
		rgvo.setMid(mid);
		rgvo.setRmNo(rmNo);
		
		rgdao.insert(rgvo);
		 String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
		 
		 url="/jejuOseyo/Room/RoomInfo.do?rmNo="+ rmNo + "&pageNum=" + pageNum
				 + "&checkIn=" + checkIn + "&checkOut=" + checkOut  + "&people=" + people + "&keyword=" + encodedKeyword ; 
	}

	private void rggRemove(HttpServletRequest request) { // 숙소찜 삭제
		int rggNo = Integer.parseInt(request.getParameter("rggNo"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		String mid = request.getParameter("mid");
		rgdao.delete(rggNo);
		url= "/jejuOseyo/Rgg/RggList.do?mid="+mid+"&pageNum="+pageNum;
	}
	private void rggClear(HttpServletRequest request) { // 숙소찜 전체삭제
		String mid = request.getParameter("mid");
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));

		rgdao.clear(mid);
		url= "/jejuOseyo/Rgg/RggList.do?mid="+mid+"&pageNum="+pageNum;
	}

}