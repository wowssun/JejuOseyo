package jejuOseyo.listener;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class DBCPInitListener implements ServletContextListener {
	private Connection con;
	
    public void contextDestroyed(ServletContextEvent sce)  { 
    	//5.서버 종료 시 DB 연결 객체 닫기
		try {
			if(con != null) {
				con.close();
				System.out.println("DB connection closed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("DBCPInitListener stopped!");
    }
         
    

	
    public void contextInitialized(ServletContextEvent sce)  { 

    	try {
			InitialContext initCtx = new InitialContext();	//1.DBCP 이용
			DataSource ds = (DataSource) initCtx.lookup("java:/comp/env/jdbc/oracle");
			
			con = ds.getConnection();		//2.con 객체 생성
			
			if(con != null) {				//3.연결 확인
				System.out.println("DBCPInitListener con ok!");
				
				//4.ServletContext의 con 속성에 con 객체 저장
				//  (↑ JSP의 application 내장 객체)
				ServletContext servletCtx = sce.getServletContext();
				servletCtx.setAttribute("con", con);
			} else {
				System.out.println("DBCPInitListener con null!");
			}
		} catch (Exception e) {
			System.out.println("DBCP 이용 DB 연결 실패......");
			e.printStackTrace();
		}
    }
	
}
