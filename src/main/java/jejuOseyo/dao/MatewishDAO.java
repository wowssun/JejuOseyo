package jejuOseyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jejuOseyo.vo.MatewishVO;


public class MatewishDAO {

	private Connection con;
	
	private String query;      //쿼리문 저장 필드
	private PreparedStatement pstmt;
	private ResultSet rs;     //SELECT 쿼리를 실행한 결과를 저장할 필드
	
	//매개변수 있는 생성자 통해서 받기 -> 초기화해서 사용
	public MatewishDAO(Connection con) {
		this.con = con;
	}
	
	//위시리스트(찜) 전체목록
	public List<MatewishVO> selectAll(){
	    List<MatewishVO> wishList = new ArrayList<>();
	    String query = "SELECT * FROM matewish";
	    try {
	        pstmt = con.prepareStatement(query);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int mwno = rs.getInt("mwno");
	            String mno = rs.getString("mno");
	            String mid = rs.getString("mid");

	            MatewishVO matewishVO = new MatewishVO(mwno, mno, mid);
	            wishList.add(matewishVO);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (pstmt != null) {
	                pstmt.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return wishList;
	}
	
	
	//위시리스트(찜) 등록
	public boolean insert(MatewishVO mwvo) {
		String query = "INSERT INTO matewish (mwno, mno, mid) VALUES (?, ?, ?)";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, mwvo.getMwno());
            pstmt.setString(2, mwvo.getMno());
            pstmt.setString(3, mwvo.getMid());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	
	//위시리스트(찜) 삭제
	public boolean delete(int mwno) {
		String query = "DELETE FROM matewish WHERE mwno = ?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, mwno);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	

}
