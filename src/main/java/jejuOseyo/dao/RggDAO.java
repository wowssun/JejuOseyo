package jejuOseyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jejuOseyo.vo.RggVO;
import jejuOseyo.vo.RoomVO;

public class RggDAO {
	
	private Connection con;
	private String query; // 쿼리문 저장 필드
	private PreparedStatement pstmt;
	private ResultSet rs; // select 쿼리 저장할 필드
	
	public RggDAO(Connection con) {
		this.con = con;
	}
	
	public boolean insert(RggVO rgvo) {//찜 담기
		
		try {
			query = "INSERT INTO rgg VALUES (SEQ_RGG_RGG_NO.NEXTVAL, ?, ?,?,?,?)";

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, rgvo.getMid());
			pstmt.setInt(2, rgvo.getRmNo());
			pstmt.setString(3, rgvo.getCheckIn());  			//   3. 체크인 날짜  // String 처리할지 생각( 해결 )
			pstmt.setString(4, rgvo.getCheckOut());			//   4. 체크아웃 날짜
			pstmt.setInt(5, rgvo.getPeople());					//   5. 예약할 인원수

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public List<RggVO> selectAll(double amount, int pageNum, String mid) { //숙소 찜 목록
		List<RggVO> rgList = new ArrayList<>();
		RggVO rgvo = null;	
		RoomVO rmvo = null;
		try {
			query = "SELECT * FROM (SELECT ROWNUM AS rnum, b.* FROM (SELECT rgg.rgg_no, rgg.mid, rgg.rcheckin, rgg.rcheckout, rgg.rpeople, room.* FROM rgg JOIN room ON rgg.rm_no = room.rm_no WHERE rgg.mid = ? ORDER BY rgg.rgg_no DESC) b WHERE ROWNUM <= ? * ?) WHERE rnum > ? * ?";

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mid);
			pstmt.setDouble(2, amount);
			pstmt.setInt(3, pageNum);
			pstmt.setDouble(4, amount);
			pstmt.setInt(5, (pageNum - 1));
			
			rs = pstmt.executeQuery();
			while (rs.next() == true) {				 
				rgvo = new RggVO();
				rmvo = new RoomVO();
				rmvo.setPrice(rs.getInt("price"));
				rmvo.setStarTotal(rs.getDouble("star_total"));
				rmvo.setRvCnt(rs.getDouble("rv_cnt"));
				rmvo.setRmName(rs.getString("rm_name"));
				rmvo.setImg(rs.getString("img"));
				rmvo.setRmNo(rs.getInt("rm_no"));	 
				
				rgvo.setMid(rs.getString("mid"));
				rgvo.setRggNo(rs.getInt("rgg_no"));
				rgvo.setCheckIn(rs.getString("rcheckin").substring(0,10));	// 체크인 날짜	
				rgvo.setCheckOut(rs.getString("rcheckout").substring(0,10));	// 체크아웃 날짜
				rgvo.setPeople(rs.getInt("rpeople"));
				rgvo.setRmvo(rmvo);
				
				rgList.add(rgvo);  //List 객체에 추가
			   }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return rgList;
	}
	
	public boolean delete(int rggNo) { //찜 삭제
		try {
			query = "DELETE FROM rgg WHERE rgg_no = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, rggNo);

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public boolean clear(String mid) { //찜 전체 삭제
		try {
			query = "DELETE FROM rgg WHERE mid = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, mid);

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
		public int totalCount(String mid) {
			int cnt = 0;
			try {
				query = " SELECT COUNT(*) FROM rgg WHERE mid=?";
	
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, mid);
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
