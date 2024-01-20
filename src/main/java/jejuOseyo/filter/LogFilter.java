package jejuOseyo.filter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

/**
 * Servlet Filter implementation class LogFilter
 */
@WebFilter("/*")
public class LogFilter extends HttpFilter implements Filter {
    private static final long serialVersionUID = 1L;
    private PrintWriter pw;
    private SimpleDateFormat dateTimeFmt;

    public void init(FilterConfig fConfig) throws ServletException {
        System.out.println("--JejuOseyo ACCESS LOGGING START--");
        dateTimeFmt = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

        String path = fConfig.getServletContext().getRealPath("/log/");
        String file = getDateTime().substring(0, 10) + ".log";

        File logDir = new File(path);
        if (!logDir.exists()) {
            logDir.mkdirs(); // 로그 디렉토리가 존재하지 않으면 생성합니다.
        }

        File logFile = new File(logDir, file);
        try {
            FileWriter fw = new FileWriter(logFile, true);
            pw = new PrintWriter(fw, true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            pw.println("접속 시간: " + getDateTime());
            // 클라이언트 IP: ~
            // 요청 URI: ~(요청 URI + 쿼리 스트링(이 있는 경우))

            chain.doFilter(request, response);

            // 처리 완료: ~(시간)
            pw.println("처리 완료: " + getDateTime());
            // 소요 시간: ~
        } catch (NullPointerException e) {
            e.printStackTrace();
            // 또는 로그 파일에 예외 정보를 기록하고 예외 처리를 진행할 수 있습니다.
        }
    }

    public void destroy() {
        System.out.println("--JejuOseyo ACCESS LOGGING END--");
        if (pw != null) {
            pw.close();
        }
    }

    public String getDateTime() {
        return dateTimeFmt.format(new Date());
    }

}
