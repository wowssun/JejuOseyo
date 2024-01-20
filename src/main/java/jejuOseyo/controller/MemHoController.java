package jejuOseyo.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

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

import jejuOseyo.dao.HostDAO;
import jejuOseyo.dao.MemberDAO;
import jejuOseyo.vo.HostVO;
import jejuOseyo.vo.MemberVO;

@WebServlet({"/Member/*","/Host/*","/Admin/*","/Common/*"})
public class MemHoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final double AMOUNT_PER_PAGE = 5.0; // 한 페이지의 게시물 수
	private static final double NUM_PER_PAGE = 5.0; // 한 페이지에 표시할 페이지 번호 수
	private HttpSession session;
	private MemberDAO mdao;
	private HostDAO hdao;
	private String url;

	public void init(ServletConfig config) throws ServletException {
		ServletContext servletCtx = config.getServletContext();
		Connection con = (Connection) servletCtx.getAttribute("con");
		mdao = new MemberDAO(con);
		hdao = new HostDAO(con);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getRequestURI().substring(request.getContextPath().length());
		session = request.getSession();
		url = ""; // url 변수 초기화

		// 회원가입
		if (cmd.endsWith("/Member/MemJoin.do")) {
			memJoin(request);
			response.sendRedirect(url);
			return;
		}

		// 회원로그인
		if (cmd.equals("/Common/MemLogin.do")) {
			memLogin(request, response);
			response.sendRedirect(url);
			return;
		}

		// 회원아이디찾기
		if (cmd.equals("/Common/MidFind.do")) {
			midFind(request);
		}

		// 회원비밀번호찾기
		if (cmd.equals("/Common/MpwFind.do")) {
			mpwFind(request);
			response.sendRedirect(url);
			return;
		}

		// 회원비밀번호변경
		if (cmd.equals("/Common/MpwModify.do")) {
			mpwModify(request);
			response.sendRedirect(url);
			return;
		}

		// 개인정보조회
		if (cmd.equals("/Member/MemView.do")) {
			memView(request);
		}

		// 회원정보수정
		if (cmd.equals("/Member/MemModify.do")) {
			memModify(request);
			response.sendRedirect(url);
			return;
		}

		// 마이페이지비밀번호변경
		if (cmd.equals("/Member/MypagePWModify.do")) {
			mypagePWModify(request);
			response.sendRedirect(url);
			return;
		}

		// 회원탈퇴
		if (cmd.equals("/Member/MemRemove.do")) {
			memRemove(request);
			response.sendRedirect(url);
			return;
		}

		///////////////////////

		// 호스트회원가입
		if (cmd.endsWith("/Host/HostJoin.do")) {
			hostJoin(request);
			response.sendRedirect(url);
			return;
		}

		// 호스트로그인
		if (cmd.equals("/Common/HostLogin.do")) {
			hostLogin(request, response);
			response.sendRedirect(url);
			return;
		}

		// 호스트아이디찾기
		if (cmd.equals("/Common/HidFind.do")) {
			hidFind(request);
			response.sendRedirect(url);
			return;
		}

		// 호스트비밀번호찾기
		if (cmd.equals("/Common/HpwFind.do")) {
			hpwFind(request);
			response.sendRedirect(url);
			return;
		}

		// 호스트비밀번호변경
		if (cmd.equals("/Common/HpwModify.do")) {
			hpwModify(request);
			response.sendRedirect(url);
			return;
		}

		// 개인정보조회
		if (cmd.equals("/Host/HostView.do")) {
			hostView(request);

		}

		// 정보수정
		if (cmd.equals("/Host/HostModify.do")) {
			hostModify(request);
			response.sendRedirect(url);
			return;
		}

		// 마이페이지비밀번호변경
		if (cmd.equals("/Host/HostmyPWModify.do")) {
			hostmyPWModify(request);
			response.sendRedirect(url);
			return;
		}

		// 호스트탈퇴
		if (cmd.equals("/Host/HostRemove.do")) {
			hostRemove(request);
			response.sendRedirect(url);
			return;
		}
		

		//////////////////////////////

		// 관리자정보조회
		if (cmd.equals("/Admin/AdminView.do")) {
			adminView(request);
		}

		// 관리자정보수정
		if (cmd.equals("/Admin/AdminModify.do")) {
			adminModify(request);
			response.sendRedirect(url);
			return;
		}

		// 관리자비밀번호변경
		if (cmd.equals("/Admin/AdmPwChange.do")) {
			admPwChange(request);
			response.sendRedirect(url);
			return;
		}

		// 회원목록
		if (cmd.equals("/Admin/MemList.do")) {
			memList(request);
		}

		// 회원상세정보
		if (cmd.equals("/Admin/AdMemView.do")) {
			adMemView(request);
		}

		// 회원삭제
		if (cmd.equals("/Admin/AdMemRemove.do")) {
			adMemRemove(request);
			response.sendRedirect(url);
			return;
		}

		// 호스트목록(호스트승인내역)
		if (cmd.equals("/Admin/HostList.do")) {
			hostList(request);
		}

		// 호스트상세정보
		if (cmd.equals("/Admin/AdHostView.do")) {
			adHostView(request);
		}

		// 호스트삭제
		if (cmd.equals("/Admin/AdHostRemove.do")) {
			adHostRemove(request);
			response.sendRedirect(url);
			return;
		}

		// 호스트회원가입신청내역
		if (cmd.equals("/Admin/HostRQList.do")) {
			hostRQList(request);
		}

		// 호스트회원가입신청상세정보
		if (cmd.equals("/Admin/AdHostRQView.do")) {
			adHostRQView(request);
		}

		// 호스트거절
		if (cmd.equals("/Admin/AdHostReject.do")) {
			adHostReject(request);
			response.sendRedirect(url);
			return;
		}

		// 호스트승인
		if (cmd.equals("/Admin/AdHostAccept.do")) {
			adHostAccept(request);
			response.sendRedirect(url);
			return;
		}

		// 호스트거절내역
		if (cmd.equals("/Admin/AdHostRJList.do")) {
			adHostRJList(request);
		}

		RequestDispatcher rdp = request.getRequestDispatcher(url);
		rdp.forward(request, response);
	}

	// 회원가입
	private void memJoin(HttpServletRequest request) {
		MemberVO mvo = new MemberVO();

		// Set the properties of the mvo object
		mvo.setMid(request.getParameter("mid"));
		mvo.setName(request.getParameter("name"));
		mvo.setMpw(request.getParameter("mpw"));
		mvo.setMnick(request.getParameter("mnick"));
		mvo.setMemail(request.getParameter("email"));
		mvo.setMphone(request.getParameter("mphone"));

		if (mdao.insert(mvo)) {
			// 세션에 메시지 저장
			session.setAttribute("msg", "회원가입이 완료되었습니다. 로그인 후 이용해 주시길 바랍니다.");
			url = "../common/memLogin.jsp";
		} else {
			session.setAttribute("msg", "회원가입에 실패했습니다. 다시 시도해 주세요.");
			url = "../member/memJoin.jsp";
		}

	}

	// 로그인
	private boolean memLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mid = request.getParameter("mid"); // 아이디 파라미터 가져오기
		String mpw = request.getParameter("mpw"); // 비밀번호 파라미터 가져오기

		MemberVO mvo = new MemberVO();
		mvo.setMid(mid);
		mvo.setMpw(mpw);

		if (mdao.isMember(mid, mpw)) {
			session.setAttribute("sid", mid); // 로그인시 mid 저장
			session.setAttribute("mid", mid);
			session.setAttribute("msg", "로그인이 완료되었습니다.");
			url = "../index.jsp";
			return true; // 로그인 성공 시 true 리턴
		} else {
			session.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다. 다시 시도해 주세요.");
			url = "../common/memLogin.jsp";
			return false; // 로그인 실패 시 false 리턴
		}
	}

	// 아이디찾기
	private void midFind(HttpServletRequest request) {

		String name = request.getParameter("name");
		String mphone = request.getParameter("mphone");
		String mid = mdao.midSearch(name, mphone);

		if (mid != null) {

			request.setAttribute("sid", mid);
			session.setAttribute("msg", "아이디는 " + mid + " 입니다.");

			url = "../common/memLogin.jsp";

		} else {

			HttpSession session = request.getSession();
			session.setAttribute("msg", "해당 정보와 일치하는 아이디가 없습니다.");
			url = "../common/midSearch.jsp";

		}
	}

	// 비밀번호찾기
	private void mpwFind(HttpServletRequest request) {

		String mid = request.getParameter("mid");
		String name = request.getParameter("name");
		String mphone = request.getParameter("mphone");

		String mpw = mdao.mpwSearch(mid, name, mphone);

		if (mpw != null) {

			session.setAttribute("mpw_mid", mid);
			url = "../common/mpwModify.jsp";

		} else {

			session.setAttribute("msg", "일치하는 회원 정보가 없습니다.");
			url = "../common/mpwSearch.jsp";

		}
	}

	// 비밀번호변경
	private void mpwModify(HttpServletRequest request) {

		String newpw = request.getParameter("newpw");
		String mpw_mid = (String) session.getAttribute("mpw_mid");

		MemberVO mvo = new MemberVO();
		mvo.setMpw(newpw);
		mvo.setMid(mpw_mid);

		System.out.println(newpw);
		System.out.println(mpw_mid);

		if (mdao.mpwUpdate(mvo)) {
			session.setAttribute("msg", "비밀번호가 변경되었습니다.");
			url = "../common/memLogin.jsp";
		} else {
			session.setAttribute("msg", "비밀번호 변경에 실패했습니다. 다시 시도해 주세요.");
			url = "../common/mpwModify.jsp";
		}

	}

	// 회원정보조회
	private void memView(HttpServletRequest request) {

		String mid = (String) session.getAttribute("sid");

		System.out.println(mid);

		request.setAttribute("view", mdao.select(mid));
		request.setAttribute("mid", mid);

		url = "../member/memMyInfo.jsp";

	}

	// 회원정보수정
	private void memModify(HttpServletRequest request) {

		String mid = (String) session.getAttribute("mid");
		String memail = request.getParameter("email");
		String mphone = request.getParameter("mphone");

		System.out.println(mid);
		System.out.println(memail);
		System.out.println(mphone);

		MemberVO mvo = new MemberVO();

		mvo.setMemail(memail);
		mvo.setMphone(mphone);
		mvo.setMid(mid);

		if (mdao.update(mvo)) {
			session.setAttribute("msg", "수정이 완료되었습니다.");
			url = "/jejuOseyo/Member/MemView.do?mid=" + mid;

		} else {
			session.setAttribute("msg", "수정에 실패했습니다. 다시 시도해 주세요.");
			url = "/jejuOseyo/Member/MemView.do?mid=" + mid;

		}
	}

	// 회원마이페이지에서비밀번호변경
	private void mypagePWModify(HttpServletRequest request) {

		String newpw = request.getParameter("newpw");
		String mid = (String) session.getAttribute("mid");

		MemberVO mvo = new MemberVO();
		mvo.setMpw(newpw);
		mvo.setMid(mid);

		System.out.println(newpw);
		System.out.println(mid);

		if (mdao.mpwUpdate(mvo)) {
			session.setAttribute("msg", "비밀번호가 변경되었습니다.");
			url = "/jejuOseyo/Member/MemView.do?mid=" + mid;
		} else {
			session.setAttribute("msg", "비밀번호 변경에 실패했습니다. 다시 시도해 주세요.");
			url = "../member/mypagePWModify.jsp";
		}

	}

	// 회원탈퇴
	private void memRemove(HttpServletRequest request) {

		String mid = (String) session.getAttribute("sid");
		System.out.println(mid);

		if (mdao.delete(mid)) {
			session.setAttribute("msg", "회원 탈퇴가 완료되었습니다. 이용해 주셔서 감사합니다.");
			url = "../common/logout.jsp";
		} else {
			session.setAttribute("msg", "회원 탈퇴에 실패했습니다. 다시 시도해 주세요.");
			url = "/jejuOseyo/Member/MemView.do?mid=" + mid;
		}
		session.invalidate();
	}

	/////////////////////////////////////////////

	// 호스트회원가입
	private void hostJoin(HttpServletRequest request) throws IOException {

		HostVO hvo = new HostVO();

		String savePath = "/resources/images";
		ServletContext context = request.getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		int maxSize = 1024 * 1024 * 10; // 최대 업로드 크기 10
		String encType = "UTF-8";

		MultipartRequest multi = new MultipartRequest(request, uploadFilePath, maxSize, encType,
				new DefaultFileRenamePolicy());

		hvo.setHid(multi.getParameter("hid"));
		hvo.setRep(multi.getParameter("rep"));
		hvo.setHpw(multi.getParameter("hpw"));
		hvo.setHnick(multi.getParameter("hnick"));
		hvo.setHphone(multi.getParameter("hphone"));
		hvo.setDnumber(multi.getParameter("dnumber"));
		hvo.setHemail(multi.getParameter("email"));
		hvo.setCrnum(multi.getParameter("crnum"));
		hvo.setPhoto(multi.getFilesystemName("photo"));

		if (hdao.insert(hvo)) {
			// 세션에 메시지 저장
			session.setAttribute("msg", "호스트 회원가입 신청이 완료되었습니다. 신청결과는 3~5일이 소요됩니다.");
			url = "../index.jsp";
		} else {
			session.setAttribute("msg", "호스트 회원가입 신청에 실패했습니다. 다시 시도해 주세요.");
			url = "../host/hostJoin.jsp";
		}

	}

	// 호스트로그인
	private boolean hostLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String hid = request.getParameter("hid"); // 아이디 파라미터 가져오기
		String hpw = request.getParameter("hpw"); // 비밀번호 파라미터 가져오기

		HostVO hvo = new HostVO();
		hvo.setHid(hid);
		hvo.setHpw(hpw);

		if (hdao.isHost(hid, hpw)) {

			session.setAttribute("sid", hid);
			session.setAttribute("hid", hid);
			session.setAttribute("msg", "로그인이 완료되었습니다.");
			url = "../index.jsp";
			return true; // 로그인 성공 시 true 리턴
		} else {
			session.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다. 다시 시도해 주세요.");
			url = "../common/hostLogin.jsp";
			return false; // 로그인 실패 시 false 리턴
		}
	}

	// 호스트아이디찾기
	private void hidFind(HttpServletRequest request) {

		String rep = request.getParameter("rep");
		String hphone = request.getParameter("hphone");
		String hid = hdao.hidSearch(rep, hphone);

		System.out.println("대표자명: " + rep);
		System.out.println("전화번호: " + hphone);
		System.out.println("찾은 아이디: " + hid);

		if (hid != null) {

			request.setAttribute("sid", hid);
			session.setAttribute("msg", "아이디는 " + hid + " 입니다.");

			url = "../common/hostLogin.jsp";

		} else {

			session.setAttribute("msg", "해당 정보와 일치하는 아이디가 없습니다.");
			url = "../common/hidSearch.jsp";

		}
	}

	// 호스트비밀번호찾기
	private void hpwFind(HttpServletRequest request) {

		String hid = request.getParameter("hid");
		String rep = request.getParameter("rep");
		String hphone = request.getParameter("hphone");

		String hpw = hdao.hpwSearch(hid, rep, hphone);

//		System.out.println("아이디: " + hid);
//		System.out.println("이름: " + rep);
//		System.out.println("전화번호: " + hphone);
//		System.out.println("비밀번호: " + hpw);

		if (hpw != null) {

			session.setAttribute("hpw_hid", hid);
			url = "../common/hpwModify.jsp";

		} else {

			session.setAttribute("msg", "일치하는 회원 정보가 없습니다.");
			url = "../common/hpwSearch.jsp";

		}
	}

	// 호스트비밀번호변경
	private void hpwModify(HttpServletRequest request) {

		String newpw = request.getParameter("newpw");
		String hpw_hid = (String) session.getAttribute("hpw_hid");

		HostVO hvo = new HostVO();
		hvo.setHpw(newpw);
		hvo.setHid(hpw_hid);

		System.out.println(newpw);
		System.out.println(hpw_hid);

		if (hdao.hpwUpdate(hvo)) {

			session.setAttribute("msg", "비밀번호가 변경되었습니다.");
			url = "../common/hostLogin.jsp";
		} else {
			session.setAttribute("msg", "비밀번호 변경에 실패했습니다. 다시 시도해 주세요.");
			url = "../common/hpwModify.jsp";
		}

	}

	// 개인정보조회
	private void hostView(HttpServletRequest request) {

		String hid = (String) session.getAttribute("sid");

		System.out.println(hid);

		request.setAttribute("view", hdao.select(hid));
		request.setAttribute("hid", hid);

		url = "../host/hostMyInfo.jsp";

	}

	// 호스트정보수정
	private void hostModify(HttpServletRequest request) {

		String hid = (String) session.getAttribute("hid");
		String hemail = request.getParameter("email");
		String hphone = request.getParameter("hphone");
		String dnumber = request.getParameter("dnumber");


		HostVO hvo = new HostVO();

		hvo.setHphone(hphone);
		hvo.setDnumber(dnumber);
		hvo.setHemail(hemail);
		hvo.setHid(hid);

		if (hdao.update(hvo)) {
			session.setAttribute("msg", "수정이 완료되었습니다.");
			url = "/jejuOseyo/Host/HostView.do?hid=" + hid;
		} else {
			session.setAttribute("msg", "수정에 실패했습니다. 다시 시도해 주세요.");
			url = "/jejuOseyo/Host/HostView.do?hid=" + hid;
		}
	}

	// 호스트마이페이지에서비밀번호변경
	private void hostmyPWModify(HttpServletRequest request) {

		String newpw = request.getParameter("newpw");
		String hid = (String) session.getAttribute("hid");

		HostVO hvo = new HostVO();
		hvo.setHpw(newpw);
		hvo.setHid(hid);

		System.out.println(newpw);
		System.out.println(hid);

		if (hdao.hpwUpdate(hvo)) {
			session.setAttribute("msg", "비밀번호가 변경되었습니다.");
			url = "/jejuOseyo/Host/HostView.do?hid=" + hid;
		} else {
			session.setAttribute("msg", "비밀번호 변경에 실패했습니다. 다시 시도해 주세요.");
			url = "../host/hostmyPWModify.jsp";
		}

	}

	// 호스트탈퇴
	private void hostRemove(HttpServletRequest request) {
		String hid = (String) session.getAttribute("sid");
		System.out.println(hid);

		if (hdao.delete(hid)) {
			session.setAttribute("msg", "호스트 탈퇴가 완료되었습니다. 이용해 주셔서 감사합니다.");
	        url = "../common/logout.jsp";
		} else {
			session.setAttribute("msg", "호스트 탈퇴에 실패했습니다. 다시 시도해 주세요.");
			url = "/jejuOseyo/Host/HostView.do?hid=" + hid;
		}
	}

	/////////////////////////////////

	// 관리자정보조회
	private void adminView(HttpServletRequest request) {

		String mid = (String) session.getAttribute("sid");

		System.out.println(mid);

		request.setAttribute("view", mdao.select(mid));
		request.setAttribute("mid", mid);

		url = "../admin/adMyInfo.jsp";

	}

	// 관리자정보수정
	private void adminModify(HttpServletRequest request) {
		String mid = (String) session.getAttribute("mid");
		String memail = request.getParameter("email");
		String mphone = request.getParameter("mphone");

		System.out.println(mid);
		System.out.println(memail);
		System.out.println(mphone);

		MemberVO mvo = new MemberVO();

		mvo.setMemail(memail);
		mvo.setMphone(mphone);
		mvo.setMid(mid);

		if (mdao.update(mvo)) {
			session.setAttribute("msg", "수정이 완료되었습니다.");
			url = "/jejuOseyo/Admin/AdminView.do?mid=" + mid;

		} else {
			session.setAttribute("msg", "수정에 실패했습니다. 다시 시도해 주세요.");
			url = "/jejuOseyo/Admin/AdminView.do?mid=" + mid;

		}
	}

	// 관리자비밀번호변경
	private void admPwChange(HttpServletRequest request) {

		String newpw = request.getParameter("newpw");
		String mid = (String) session.getAttribute("mid");

		MemberVO mvo = new MemberVO();
		mvo.setMpw(newpw);
		mvo.setMid(mid);

		System.out.println(newpw);
		System.out.println(mid);

		if (mdao.mpwUpdate(mvo)) {
			session.setAttribute("msg", "비밀번호가 변경되었습니다.");
			url = "/jejuOseyo/Admin/AdminView.do?mid=" + mid;
		} else {
			session.setAttribute("msg", "비밀번호 변경에 실패했습니다. 다시 시도해 주세요.");
			url = "../admin/adPWChange.jsp";
		}

	}

	// 회원전체목록
	private void memList(HttpServletRequest request) {
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		int totalCnt = mdao.totalCount(type, keyword);
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pages = (int) (Math.ceil(totalCnt / AMOUNT_PER_PAGE));

		int end = (int) (Math.ceil(pageNum / NUM_PER_PAGE) * NUM_PER_PAGE);
		int start = end - (int) (NUM_PER_PAGE - 1);
		end = end >= pages ? pages : end;

		// 이전 | 이후 버튼 활성화 여부
		boolean prev = start > 1;
		boolean next = end < pages;

		List<MemberVO> memList = mdao.selectAll(AMOUNT_PER_PAGE, pageNum, type, keyword);

		request.setAttribute("memList", memList);
		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("end", end);
		request.setAttribute("start", start);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);
		request.setAttribute("type", type);
		request.setAttribute("keyword", keyword);

		url = "../admin/memList.jsp";

	}

	// 회원상세조회
	private void adMemView(HttpServletRequest request) {

		String mid = request.getParameter("mid"); // 작성자의 id
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));

		request.setAttribute("view", mdao.select(mid));
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("mid", mid); // mid 값을 추가로 설정

		url = "../admin/memInfo.jsp";

	}

	// 회원삭제
	private void adMemRemove(HttpServletRequest request) {

		String mid = request.getParameter("mid");

		if (mdao.delete(mid)) {
			session.setAttribute("msg", "회원 삭제가 완료되었습니다.");
		} else {
			session.setAttribute("msg", "회원 삭제에 실패했습니다. 다시 시도해 주세요.");
		}
		url = "/jejuOseyo/Admin/MemList.do?pageNum=1&type=&keyword=";
	}

	private void hostList(HttpServletRequest request) {
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		int totalCnt = hdao.totalCount(type, keyword);// total카운트 조회 count에 담고
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pages = (int) (Math.ceil(totalCnt / AMOUNT_PER_PAGE));

		// 각 페이지의 시작 페이지 번호 |...| 끝 번호가 나와야 함
		int end = (int) (Math.ceil(pageNum / NUM_PER_PAGE) * NUM_PER_PAGE);
		int start = end - (int) (NUM_PER_PAGE - 1);
		end = end >= pages ? pages : end;

		// 이전 | 이후 버튼 활성화 여부
		boolean prev = start > 1;
		boolean next = end < pages;

		List<HostVO> hostList = hdao.selectAll(AMOUNT_PER_PAGE, pageNum, type, keyword);

		request.setAttribute("hostList", hostList);
		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("end", end);
		request.setAttribute("start", start);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);
		request.setAttribute("type", type);
		request.setAttribute("keyword", keyword);

		url = "../admin/hostList.jsp";

	}

	// 호스트상세조회
	private void adHostView(HttpServletRequest request) {

		String hid = request.getParameter("hid"); // 작성자의 id
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));

		request.setAttribute("view", hdao.select(hid));
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("hid", hid); // mid 값을 추가로 설정

		url = "../admin/hostInfo.jsp";

	}
	

	// 호스트삭제
	private void adHostRemove(HttpServletRequest request) {

		String hid = request.getParameter("hid");

		if (hdao.delete(hid)) {
			session.setAttribute("msg", "호스트 삭제가 완료되었습니다.");
		} else {
			session.setAttribute("msg", "호스트 삭제에 실패했습니다. 다시 시도해 주세요.");
		}
		url = "/jejuOseyo/Admin/HostList.do?pageNum=1&type=&keyword=";
	}

	// 호스트신청내역
	private void hostRQList(HttpServletRequest request) {
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		int totalCnt = hdao.hRQtotalCount(type, keyword);
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pages = (int) (Math.ceil(totalCnt / AMOUNT_PER_PAGE));

		int end = (int) (Math.ceil(pageNum / NUM_PER_PAGE) * NUM_PER_PAGE);
		int start = end - (int) (NUM_PER_PAGE - 1);
		end = end >= pages ? pages : end;

		// 이전 | 이후 버튼 활성화 여부
		boolean prev = start > 1;
		boolean next = end < pages;

		List<HostVO> hostRQList = hdao.hostRQselectAll(AMOUNT_PER_PAGE, pageNum, type, keyword);

		request.setAttribute("hostRQList", hostRQList);
		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("end", end);
		request.setAttribute("start", start);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);
		request.setAttribute("type", type);
		request.setAttribute("keyword", keyword);

		url = "../admin/hostRQList.jsp";

	}

	// 호스트회원가입신청상세조회
	private void adHostRQView(HttpServletRequest request) {

		String hid = request.getParameter("hid");
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		request.setAttribute("view", hdao.hostRQselect(hid));
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("hid", hid);

		url = "../admin/hostRQInfo.jsp";

	}

	// 호스트거절
	private void adHostReject(HttpServletRequest request) {

		String hid = request.getParameter("hid");
		System.out.println(hid);
		HostVO hvo = new HostVO();

		hvo.setProcState("거절");
		hvo.setHid(hid);

		if (hdao.hostRQupdate(hvo)) {
			session.setAttribute("msg", "거절이 완료되었습니다.");
			url = "/jejuOseyo/Admin/AdHostRJList.do?pageNum=1&type=&keyword=";
		} else {
			session.setAttribute("msg", "거절에 실패했습니다. 다시 시도해 주세요.");
			url = "../admin/hostRQInfo.jsp";
		}
	}

	// 호스트승인
	private void adHostAccept(HttpServletRequest request) {

		String hid = request.getParameter("hid");

		HostVO hvo = new HostVO();

		hvo.setProcState("승인");
		hvo.setHid(hid);

		if (hdao.hostRQupdate(hvo)) {
			session.setAttribute("msg", "승인이 완료되었습니다.");
		        
			url = "/jejuOseyo/Admin/HostList.do?pageNum=1&type=&keyword=";
			
		} else {
			session.setAttribute("msg", "승인에 실패했습니다. 다시 시도해 주세요.");
			url = "/jejuOseyo/Admin/HostRQList.do?pageNum=1&type=&keyword=";
		}
	}
	
	
	
	

	// 호스트거절내역
	private void adHostRJList(HttpServletRequest request) {
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		int totalCnt = hdao.hJtotalCount(type, keyword);// total카운트 조회 count에 담고
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pages = (int) (Math.ceil(totalCnt / AMOUNT_PER_PAGE));

		// 각 페이지의 시작 페이지 번호 |...| 끝 번호가 나와야 함
		int end = (int) (Math.ceil(pageNum / NUM_PER_PAGE) * NUM_PER_PAGE);
		int start = end - (int) (NUM_PER_PAGE - 1);
		end = end >= pages ? pages : end;

		// 이전 | 이후 버튼 활성화 여부
		boolean prev = start > 1;
		boolean next = end < pages;

		List<HostVO> hostRJList = hdao.hostRJselect(AMOUNT_PER_PAGE, pageNum, type, keyword);

		request.setAttribute("hostRJList", hostRJList);
		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("end", end);
		request.setAttribute("start", start);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);
		request.setAttribute("type", type);
		request.setAttribute("keyword", keyword);

		url = "../admin/hostRJList.jsp";

	}

}// END
