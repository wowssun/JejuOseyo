package jejuOseyo.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter()
public class MemberFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	
	public void destroy() {}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    System.out.println("MemberFilter");
	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;
	    HttpSession session = req.getSession();

	    // 로그인하지 않은 경우
	    String sid = (String) session.getAttribute("sid");
	    if (sid == null) {

	        // 요청 객체의 msg 속성에
	        // "회원전용메뉴입니다. 로그인 후 이용해주세요."를 저장
	        System.out.println("MemberFilter login - x");
	        session.setAttribute("msg", "회원 전용 메뉴입니다. 로그인 후 이용해주세요.");

	        // 로그인 페이지로 포워딩
	        RequestDispatcher rdp = req.getRequestDispatcher("/common/memLogin.jsp");
	        rdp.forward(req, res);
	    } else {
	        // 그렇지 않으면
	        System.out.println("MemberFilter login - o");
	        chain.doFilter(request, response);
	    }
	}



	public void init(FilterConfig fConfig) throws ServletException {
	
	}

}
