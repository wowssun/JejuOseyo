package jejuOseyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jejuOseyo.vo.ReviewVO;
import jejuOseyo.vo.RoomVO;


public class ReviewDAO {
	private Connection con;
	private String query; // 쿼리문 저장 필드
	private PreparedStatement pstmt;
	private ResultSet rs; // select 쿼리 저장할 필드

	public ReviewDAO(Connection con) {
		this.con = con;
	}
	public boolean insert(ReviewVO revo){
		
		try {
			String query = "INSERT INTO review VALUES (SEQ_REVIEW_REV_NO.NEXTVAL, ?, ?, SYSDATE, ?, ?, ?)";

			pstmt = con.prepareStatement(query);

			pstmt.setString(1, revo.getMid());
			pstmt.setInt(2, revo.getRmNo());
			pstmt.setDouble(3, revo.getStar());
			pstmt.setString(4, revo.getTitle());
			pstmt.setString(5, revo.getContent());

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public List<ReviewVO> selectAll(double amount, int pageNum, int rmNo) { //리뷰 목록 
		List<ReviewVO> rvList = new ArrayList<>();
		ReviewVO rvvo = null;	
		try {
			query = " SELECT * "
					+ "FROM    (SELECT ROWNUM AS rnum, b.* "
					+ "         FROM   (SELECT * FROM review WHERE rm_no = ? ORDER BY rev_no DESC) b WHERE  ROWNUM <= ? * ? )"
					+ "WHERE  rnum > ? * ? ";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, rmNo);
			pstmt.setDouble(2, amount);
			pstmt.setInt(3, pageNum);
			pstmt.setDouble(4, amount);
			pstmt.setInt(5, (pageNum - 1));
			
			rs = pstmt.executeQuery();
			while (rs.next() == true) {				 
				rvvo = new ReviewVO();
				rvvo.setMid(rs.getString("mid"));
				rvvo.setRmNo(rs.getInt("rm_no"));	 
				rvvo.setRvNo(rs.getInt("rev_no"));
				rvvo.setStar(rs.getDouble("star"));
				rvvo.setTitle(rs.getString("title"));	
				rvvo.setContent(rs.getString("content"));	 
				rvvo.setRegDate(rs.getDate("reg_date"));
			
				
				rvList.add(rvvo);  //List 객체에 추가
			   }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return rvList;
	}
	
	public ReviewVO select(int rvNo) { //리뷰 상세조회
		ReviewVO rvvo = null;
		try {
			query = "SELECT * FROM review WHERE rev_no = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, rvNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				rvvo = new ReviewVO();
				rvvo.setMid(rs.getString("mid"));
				rvvo.setRegDate(rs.getDate("reg_date"));
				rvvo.setStar(rs.getDouble("star"));
				rvvo.setTitle(rs.getString("title"));
				rvvo.setContent(rs.getString("content"));
				rvvo.setRmNo(rs.getInt("rm_no"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rvvo;
	}

	public boolean update(ReviewVO revo) {
		try {
			String query = "UPDATE review SET  star=?, title=?, content=? 	WHERE rev_no =?";

			pstmt = con.prepareStatement(query);

			pstmt.setDouble(1, revo.getStar());
			pstmt.setString(2, revo.getTitle());
			pstmt.setString(3, revo.getContent());
			pstmt.setInt(4, revo.getRvNo());
			

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(int rvNo) {
		try {
			query = "DELETE FROM review where rev_no = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, rvNo);

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	
	
	//전체 게시물 수
	public int totalCount(int rmNo) {
		int cnt = 0;
		try {
			query = " SELECT COUNT(*) FROM review WHERE rm_no=?";

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, rmNo);
			rs = pstmt.executeQuery();
			if (rs.next() == true) {				 
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConn.close(pstmt, rs);
		}
		return cnt;
	}
	
}
