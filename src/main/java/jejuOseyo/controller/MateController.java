package jejuOseyo.controller;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
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

import jejuOseyo.dao.MateDAO;
import jejuOseyo.dao.MateapplyDAO;
import jejuOseyo.dao.MatecmDAO;
import jejuOseyo.dao.MatewishDAO;
import jejuOseyo.vo.MateVO;
import jejuOseyo.vo.MateapplyVO;
import jejuOseyo.vo.MatecmVO;
import jejuOseyo.vo.MatewishVO;

//@WebServlet("*.do")
@WebServlet({"/Mate/*", "/Mateapply/*", "/Matewish/*"})				//mate로 시작하는 것만 여기서 받도록 지정
public class MateController extends HttpServlet {
	private static final double AMOUNT_PER_PAGE = 5.0;		//한 페이지의 게시물 수
	private static final double NUM_PER_PAGE = 5.0;				//한 페이지에 표시할 페이지 번호 수
	
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private MateDAO mdao;
	private MatecmDAO mcmdao;
	private MatewishDAO mwdao;
	private MateapplyDAO madao;
	private String url;

	public void init(ServletConfig config) throws ServletException {
		ServletContext servletCtx = config.getServletContext();
		Connection con = (Connection) servletCtx.getAttribute("con");
		mdao = new MateDAO(con);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
// <Tip!!!> Controller가 여러개일경우, 두번째 처럼 쓴다!!
//		if (cmd.equals("/BoardList.do")) 			 {		list(request);		}			//컨트롤러를 하나만 쓸때 사용할 것
//		if (cmd.equals("/board/list.do")) 			 {		list(request);		}			//컨트롤러를 여러개로 나누어 쓸때 시작하는 폴더명을 지정하여 이렇게 사용!!!
		
		String cmd = request.getRequestURI().substring(request.getContextPath().length());
		session = request.getSession();
		
		if (cmd.equals("/Mate/List.do")) 			 {		mateList(request);		}
		if (cmd.equals("/Mate/WriteForm.do")) {		url = "/Mate/write.jsp";	}
		if (cmd.equals("/Mate/Write.do")) 		 {		mateWrite(request);
																			response.sendRedirect(url);
																			return;	}
		if (cmd.equals("/Mate/View.do"))			{		mateView(request);		 }
		if (cmd.equals("/Mate/Remove.do"))	{		mateRemove(request);
																			response.sendRedirect(url);
																			return;	}
		if (cmd.equals("/Mate/Modify.do"))		{		mateModify(request);
																			response.sendRedirect(url);
																			return;	}
		
		/////////////////////////////////////////////////////////////////
		if (cmd.equals("/Mate/cmList.do")) 			 {		matecmList(request);		}
		if (cmd.equals("/Mate/cmWriteForm.do")) {		url = "/Matecm/write.jsp";	}
		if (cmd.equals("/Mate/cmWrite.do")) 		 {		matecmWrite(request);
																			response.sendRedirect(url);
																			return;	}
	//	if (cmd.equals("/Mate/cmView.do"))			{		view(request);		 }
		if (cmd.equals("/Mate/cmRemove.do"))	{		matecmRemove(request);
																			response.sendRedirect(url);
																			return;	}
		
		////////////////////////////////////////////////////////////////
		if (cmd.equals("/Mateapply/List.do")) 			 {		mateapplyList(request);		}
	//	if (cmd.equals("/Mateapply/WriteForm.do")) {		url = "/board/write.jsp";	}
		if (cmd.equals("/Mateapply/Write.do")) 		 {		mateapplyWrite(request);
																			response.sendRedirect(url);
																			return;	}
	//	if (cmd.equals("/Mateapply/View.do"))			{		view(request);		 }
	//	if (cmd.equals("/Mateapply/Remove.do"))	{		remove(request);
	//																		response.sendRedirect(url);
	//																		return;	}
	//	if (cmd.equals("/Mateapply/Modify.do"))		{		modify(request);
	//																		response.sendRedirect(url);
	//																		return;	}
		
		////////////////////////////////////////////////////////////////////
		
	//	if (cmd.equals("/Matewish/List.do")) 			 {		matewishList(request);		}
	//	if (cmd.equals("/Matewish/WriteForm.do")) {		url = "/board/write.jsp";	}
		if (cmd.equals("/Matewish/Write.do")) 		 {		matewishWrite(request);
																			response.sendRedirect(url);
																			return;	}
	//	if (cmd.equals("/Matewish/View.do"))			{		view(request);		 }
		if (cmd.equals("/Matewish/Remove.do"))	{		matewishRemove(request);
																			response.sendRedirect(url);
																			return;	}
	//	if (cmd.equals("/Matewish/Modify.do"))		{		modify(request);
	//																		response.sendRedirect(url);
	//																		return;	}
		
		RequestDispatcher rdp = request.getRequestDispatcher(url);
		rdp.forward(request, response);
		
	}
	
	


	





	//게시판 목록 조회
	public void mateList(HttpServletRequest request) {

		System.out.println("mateList확인");
		//matedao에서 selectall 반환되는거 리스트 담아서 조회
		//토탈카운트 조회
		request.setAttribute("mateList", mdao.selectAll());

		url = "/mate/mateList.jsp";
	}


	
	//게시글 등록
	private void mateWrite(HttpServletRequest request) {
		MateVO mvo = new MateVO();
		mvo.setMtitle(request.getParameter("mtitle"));
		mvo.setMnick(request.getParameter("mnick"));
		mvo.setDepart(request.getParameter("depart"));
		mvo.setFin(request.getParameter("fin"));
	    mvo.setMplace(request.getParameter("mplace"));
		mvo.setMnum(Integer.parseInt(request.getParameter("mnum")));
		mvo.setMtext(request.getParameter("mtext"));
		
		if(mdao.insert(mvo)) {
			session.setAttribute("msg", "게시물이 등록되었습니다.");
		} else {
			session.setAttribute("msg", "게시물 등록에 실패했습니다.");
		}
		
		url = "/mate/mateview.jsp";
		
	}
	
		
	//게시글 수정
	private void mateModify(HttpServletRequest request) {
		MateVO mvo = new MateVO();
		mvo.setMno(Integer.parseInt(request.getParameter("mno")));
		mvo.setMtitle(request.getParameter("mtitle"));
		mvo.setDepart(request.getParameter("depart"));
		mvo.setFin(request.getParameter("fin"));
		mvo.setMplace(request.getParameter("mplace"));
		mvo.setMnum(Integer.parseInt(request.getParameter("mnum")));
		mvo.setMtext(request.getParameter("mtext"));
		
		if(mdao.update(mvo)) {
			session.setAttribute("msg", "게시물이 수정되었습니다.");
		} else {
			session.setAttribute("msg", "게시물 수정에 실패했습니다.");
		}
		
		url = "./MateList.do?pageNum=" + request.getParameter("pageNum") +
										"&type=" + request.getParameter("type") +
										"&keyword=" + request.getParameter("keyword");
	
	}
	
	
	//게시글 삭제
	private void mateRemove(HttpServletRequest request) {
		int mno = Integer.parseInt(request.getParameter("mno"));
		
		if(mdao.delete(mno)) {
			session.setAttribute("msg", "게시물이 삭제되었습니다.");
		} else {
			session.setAttribute("msg", "게시물 삭제에 실패했습니다.");
		}
		
		url = "./MateList.do?pageNum=1&type=&keyword=";
	}
	
	
	//게시글 하나 조회
	private void mateView(HttpServletRequest request) {
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));						//페이지넘버꺼내기
		int mno = Integer.parseInt(request.getParameter("mno"));
		String mnick = request.getParameter("mnick");
		String sid = (String)session.getAttribute("sid");
		
		//num
//		System.out.println(num);
		//id
//		System.out.println(sid);
		
		if(sid == null || !sid.equals(mnick)) {			//sid가 널이거나, sid와 id가 같지 않으면
			mdao.updateHit(mno);												//조회 수 1증가 처리
		}
		request.setAttribute("pageNum", pageNum);															//페이지넘버를 속성에 저장한다
		request.setAttribute("mvo", mdao.select(mno));			//요청객체 속성에 mvo넣기
		url = "./Mate/List.do?pageNum=1&type=&keyword=";
	}
	
	
	
	//댓글 목록 조회
	public void matecmList(HttpServletRequest request) {
		url = "/matecm/matecmList.jsp";
	}
	
	
	//댓글 등록
	private void matecmWrite(HttpServletRequest request) {
		MatecmVO mcmvo = new MatecmVO();
		mcmvo.setMnick(request.getParameter("mnick"));
		mcmvo.setMcmtext(request.getParameter("mcmtext"));
		
		if(mcmdao.insert(mcmvo)) {
			session.setAttribute("msg", "댓글이 등록되었습니다.");
		} else {
			session.setAttribute("msg", "댓글 등록에 실패했습니다.");
		}
		
		url = "./Matecm/List.do?pageNum=1&type=&keyword=";
		
	}

	
	
	
	//댓글 삭제
	private void matecmRemove(HttpServletRequest request) {
		int mcmno = Integer.parseInt(request.getParameter("mcmno"));
		
		if(mcmdao.delete(mcmno)) {
			session.setAttribute("msg", "댓글이 삭제되었습니다.");
		} else {
			session.setAttribute("msg", "댓글 삭제에 실패했습니다.");
		}
		
		url = "./Matecm/List.do?pageNum=1&type=&keyword=";
	}
	
	
	
	
	//위시리스트(찜) 등록
	private void matewishWrite(HttpServletRequest request) {
		MatewishVO mwvo = new MatewishVO();
		mwvo.setMno(request.getParameter("mno"));
		mwvo.setMid(request.getParameter("mid"));
		
		if(mwdao.insert(mwvo)) {
			session.setAttribute("msg", "위시리스트(찜)이 등록되었습니다.");
		} else {
			session.setAttribute("msg", "위시리스트(찜) 등록에 실패했습니다.");
		}
		
		url = "./Matewish/List.do?pageNum=1&type=&keyword=";
		
	}
	
	
	
	//위시리스트(찜) 삭제
	private void matewishRemove(HttpServletRequest request) {
		int mwno = Integer.parseInt(request.getParameter("mwno"));
		
		if(mwdao.delete(mwno)) {
			session.setAttribute("msg", "위시리스트(찜)이 삭제되었습니다.");
		} else {
			session.setAttribute("msg", "위시리스트(찜) 삭제에 실패했습니다.");
		}
		
		url = "./Matewish/List.do?pageNum=1&type=&keyword=";
	}
	
	
	//신청자 목록
	private void mateapplyList(HttpServletRequest request) {
		url = "/mateapply/mateApplyList.jsp";
	}
	
	
	
	//참여 신청 등록
	private void mateapplyWrite(HttpServletRequest request) {
		MateapplyVO mavo = new MateapplyVO();
		mavo.setAno(Integer.parseInt(request.getParameter("ano")));
		mavo.setMnick(request.getParameter("mnick"));
		mavo.setMphone(request.getParameter("mphone"));
		mavo.setMnow(request.getParameter("mnow"));
		
		if(madao.insert(mavo)) {
			session.setAttribute("msg", "신청되었습니다.");
		} else {
			session.setAttribute("msg", "신청에 실패했습니다.");
		}
		
		url = "./Mateapply/List.do";
		
	}
	
	
}
