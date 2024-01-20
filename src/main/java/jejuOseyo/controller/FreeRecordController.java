package jejuOseyo.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jejuOseyo.dao.FreeDAO;
import jejuOseyo.vo.FreeVO;

@WebServlet({ "/Free/*", "/Record/*" })
//@WebServlet("*.do")
public class FreeRecordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; // 서블릿은 내장객체가 아니라서 session을 선언하고 사용하여야 한다.
									// 로그인한 사람만 게시판에 글쓰게 하려고
	private FreeDAO fdao;
	private String url;
	private static final double AMOUNT_PER_PAGE = 5.0; // 한 페이지의 게시물 수
	private static final double NUM_PER_PAGE = 5.0; // 한 페이지에 표시할 페이지 번호 수

	public void init(ServletConfig config) throws ServletException {

		ServletContext servletCtx = config.getServletContext();
		Connection con = (Connection) servletCtx.getAttribute("con");

		fdao = new FreeDAO(con);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getRequestURI().substring(request.getContextPath().length()); // jejuOseyo/FreeList.do
		// 원래 이렇게 뜨지만 /jejuOseyo을 자르는 것.

		session = request.getSession(); // 내장객체가 아니기 때문에 선언하고 써야한다.
		url = ""; // url 변수 초기화

		// cmd가 /BordList.do인 경우 게시판 목록 조회 메서드 호출
		if (cmd.equals("/Free/FreeList.do")) {
			list(request);
		}
		if (cmd.equals("/Free/FreeWriteForm.do")) {
			url = "/free/freeWrite.jsp";
		}
		if (cmd.equals("/Free/FreeWrite.do")) {
			write(request);
			response.sendRedirect(url); // redirect하고// write를 하고 나서는 redirect로 Freewrite.do 처리해야한다.( 이건 위에서 처리하기)
			return; // return이 없으면 밑에 forward가 실행되기 때문에 return을 해서 중단시키는것.
		}
		if (cmd.equals("/Free/FreeView.do")) {
			view(request);
		}

		if (cmd.equals("/Free/FreeModifyForm.do")) {
			modifyForm(request);
			response.sendRedirect(url);
			return;
		}

		if (cmd.equals("/Free/FreeModify.do")) {
			modify(request);
			response.sendRedirect(url);
			return;
		}

		if (cmd.equals("/Free/FreeRemove.do")) {
			remove(request);
			response.sendRedirect(url);
			return;

		}

		RequestDispatcher rdp = request.getRequestDispatcher(url);
		rdp.forward(request, response);
	}

	// 게시판 글 하나 조회 / FreeView.do
	public void view(HttpServletRequest request) {
		
		int pageNum = Integer.parseInt(request.getParameter("pageNum")); // 페이지 넘버 꺼낸다

		int freeNo = Integer.parseInt(request.getParameter("freeNo")); // 여기에 아이디값
		String mid = request.getParameter("mid"); // 번호가 넘어오는지 확인
		String sid = (String) session.getAttribute("sid");

		if (sid == null || !sid.equals(mid)) { // 로그인을 하지 않았을 경우 , 수정 ,삭제도 똑같이
			fdao.updateHit(freeNo); // update hit 호출하기
		}
		// 담는다.
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("fvo", fdao.select(freeNo)); // FreeVO fvo = fdao.select(freeNo);
		// fvo 객체에 담아서? select 불러오기 , forward나 redirct 하기 view 페이지로
		url = "/free/freeView.jsp";
	}

	// 게시판 글쓰기 / FreeWrite.do
	public void write(HttpServletRequest request) { // 수정과 똑같이 한다.

		FreeVO fvo = new FreeVO(); // FreeVO 객체 생성
		fvo.setMid(request.getParameter("mid")); // vo.setIp(request.getremoteAddr) // 넘어오는 값(작성자,제목,내용,사용자ip)을 vo에 담고
													// 담아서 dao로 호출하고
		fvo.setFreeTitle(request.getParameter("freeTitle"));
		fvo.setFreeContent(request.getParameter("freeContent"));
		// 이미지
		fvo.setIp(request.getParameter("ip"));

		if (fdao.insert(fvo)) {
			session.setAttribute("msg", "게시물이 등록되었습니다.");
		} else {
			session.setAttribute("msg", "게시물 등록에 실패했습니다.");
		}
		url = "./FreeList.do?pageNum=1&type=&keyword=";
	}

	// 게시판 목록 조회 / FreeList.do
	public void list(HttpServletRequest request) {
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");

		String ip = request.getRemoteAddr(); // 클라이언트의 IP 주소 얻기

		int totalCnt = fdao.totalCount(type, keyword); // 여기 값 넣기
		int pages = (int) (Math.ceil(totalCnt / AMOUNT_PER_PAGE));

		// 각 페이지의 시작 페이지 번호 | ... | 끝번호
		int end = (int) (Math.ceil(pageNum / NUM_PER_PAGE) * NUM_PER_PAGE);
		int start = end - (int) (NUM_PER_PAGE - 1);
		end = end > pages ? pages : end;

		// 이전 | 이후 버튼 활성화 여부
		boolean prev = start > 1;
		boolean next = end < pages;
		// 여기에서도 매개변수 두개 추가하기
		request.setAttribute("freeList", fdao.selectAll(AMOUNT_PER_PAGE, pageNum, type, keyword)); // 조회 list dao에서 호출하고
																									// 반환되는 값을 요청객체에
																									// 담는다. // 한 페이지에
																									// 5개씩 1페이지
		request.setAttribute("totalCnt", totalCnt); // 숫자 totalConunt request속성에 담는다?

		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("end", end);
		request.setAttribute("start", start);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);

		request.setAttribute("type", type);
		System.out.println("type: " + type);
		request.setAttribute("keyword", keyword);
		System.out.println("keyword: " + keyword);

		url = "/free/freeList.jsp";

	}

	// 게시판 수정 폼 조회 / FreeViewForm.do
	public void modifyForm(HttpServletRequest request) {
		
		//int pageNum = Integer.parseInt(request.getParameter("pageNum")); // 페이지 넘버 꺼낸다
		String pageNum = request.getParameter("pageNum");
		int freeNo = Integer.parseInt(request.getParameter("freeNo")); // 여기에 아이디값
		String mid = request.getParameter("mid"); // 번호가 넘어오는지 확인
		String sid = (String) session.getAttribute("sid");

		System.out.println("sid 5: " + sid);
		System.out.println("mid 5: " + mid);
		System.out.println("mpw 5: " + session.getAttribute("mpw"));
		
		if (sid == null || !sid.equals(mid)) { // 로그인을 하지 않았을 경우 , 수정 ,삭제도 똑같이
			fdao.updateHit(freeNo); // update hit 호출하기
		}
		// 담는다.
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("fvo", fdao.select(freeNo)); // FreeVO fvo = fdao.select(freeNo);
		// fvo 객체에 담아서? select 불러오기 , forward나 redirct 하기 view 페이지로
		url = "/jejuOseyo/free/freeModify.jsp?freeNo="+freeNo;
	}

	// 게시판 수정 / FreeModify.do
	public void modify(HttpServletRequest request) {

		int pageNum = Integer.parseInt(request.getParameter("pageNum")); // 페이지 넘버 꺼낸다

		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");

		FreeVO fvo = new FreeVO(); // FreeVO 객체 생성
		// System.out.println(fvo.toString());
		fvo.setFreeTitle(request.getParameter("freeTitle"));
		fvo.setFreeContent(request.getParameter("freeContent"));
		fvo.setMid(request.getParameter("mid"));
		fvo.setFreeNo(Integer.parseInt(request.getParameter("freeNo"))); // vo.setIp(request.getremoteAddr) // 넘어오는
																			// 값(작성자,제목,내용,사용자ip)을 vo에 담고 담아서 dao로 호출하고

		if (fdao.update(fvo)) {
			session.setAttribute("msg", "게시물이 수정되었습니다.");
		} else {
			session.setAttribute("msg", "게시물 수정에 실패했습니다.");
		}

		url = "./FreeList.do?pageNum=" + pageNum + "&type=" + type + "&keyword=" + keyword; // 수정이 된다면 수정했던 게시글이 있는 페이지로
																							// 간다.
	}

	// 게시판 삭제 / FreeRemove.do
	public void remove(HttpServletRequest request) {
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));
		System.out.println(freeNo);

		if (fdao.delete(freeNo)) {
			session.setAttribute("msg", "게시물이 삭제되었습니다.");
		} else {
			session.setAttribute("msg", "게시물 삭제에 실패했습니다.");
		}

		url = "./FreeList.do?pageNum=1&type=&keyword="; // 삭제하면 다시 1페이지로 돌아간다.
	}

}
