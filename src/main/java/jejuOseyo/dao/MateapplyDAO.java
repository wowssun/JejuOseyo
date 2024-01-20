package jejuOseyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jejuOseyo.util.DBConn;
import jejuOseyo.vo.MateapplyVO;


public class MateapplyDAO {

	private Connection con;
	
	private String query;      //쿼리문 저장 필드
	private PreparedStatement pstmt;
	private ResultSet rs;     //SELECT 쿼리를 실행한 결과를 저장할 필드
	
	//매개변수 있는 생성자 통해서 받기 -> 초기화해서 사용
	public MateapplyDAO(Connection con) {
		this.con = con;
	}
	
	
	//신청 내역 수락또는거절 입력
	public boolean insert(MateapplyVO mavo) {
	    try {
	        query = "INSERT INTO mateapply (mno, mnick, mgender, mphone, mnow) VALUES (?, ?, ?, ?)";
	        pstmt = con.prepareStatement(query);
	        
	        pstmt.setInt(1, mavo.getMno());
	        pstmt.setString(2, mavo.getMnick());
	        pstmt.setString(3, mavo.getMphone());
	        pstmt.setString(4, mavo.getMnow());
	        
	        int result = pstmt.executeUpdate();
	        if (result == 1) {
	            return true; // 정상적으로 신청 내역이 입력되었을 때 true 반환
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBConn.close(pstmt);
	    }
	    
	    return false; // 입력 실패 시 false 반환
	}
	
	

}
