package jejuOseyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import jejuOseyo.vo.MemberVO;
import jejuOseyo.util.DBConn;

public class MemberDAO {

	private Connection con;
    private String query;
    private PreparedStatement pstmt;
    private ResultSet rset;

	public MemberDAO(Connection con) {
        this.con = con;
    }
	public void setCon(Connection con) {
        this.con = con;
    }

	// 회원가입
	public boolean insert(MemberVO mvo) {
		try {
			
			query = "INSERT INTO J_MEMBER VALUES (?, ?, ?, ?, ?, ?, SYSDATE)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mvo.getMid()); // 아이디
			pstmt.setString(2, mvo.getName()); // 이름
			pstmt.setString(3, mvo.getMpw()); // 비밀번호
			pstmt.setString(4, mvo.getMnick()); // 닉네임
			pstmt.setString(5, mvo.getMemail()); // 이메일
			pstmt.setString(6, mvo.getMphone()); // 전화번호
			

			int result = pstmt.executeUpdate();

			if (result != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBConn.close(pstmt);
		}

	}
	
//	//아이디 중복 확인
//	public boolean selectIdChk(String mid) {
//		
//		try {
//			query = "SELECT mid FROM j_member WHERE mid=?";
//
//			
//			pstmt = con.prepareStatement(query);
//			pstmt.setString(1, mid);
//			
//			rset = pstmt.executeQuery();
//			
//			if (rset.next()) {
//
//				return true;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBConn.close(pstmt, rset);
//		}
//
//		return false;
//	}
			
	

	// 회원로그인
	public boolean isMember(String mid, String mpw) {
		String sql = "SELECT * FROM j_member WHERE mid=? AND mpw=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mid);
			pstmt.setString(2, mpw);
			rset = pstmt.executeQuery();

			if (rset.next()) {

				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rset);
		}

		return false;
	}

	// 회원아이디찾기
	public String midSearch(String name, String mphone) {// 회원 아이디찾기
		
		String mid = null;
		
		String sql = "SELECT mid FROM j_member WHERE name = ? AND mphone = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, mphone);

			rset = pstmt.executeQuery();
			if (rset.next()) {
				
			 mid = rset.getString("mid");
		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rset);
		}
		return mid;
	}

	// 회원비밀번호찾기
	public String mpwSearch(String mid, String name, String mphone) {// 회원 비밀번호 찾기
		String mpw = null;
		
		String sql = "SELECT mpw FROM j_member WHERE mid =? AND name = ? AND mphone = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mid);
			pstmt.setString(2, name);
			pstmt.setString(3, mphone);

			rset = pstmt.executeQuery();
			if (rset.next()) {
				mpw = rset.getString("mpw");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rset);
		}
		return mpw;
	}

	// 회원비밀번호재설정, 관리자비밀번호재설정
	public boolean mpwUpdate(MemberVO mvo) {// 회원 비밀번호 재설정
		try {
			query = "UPDATE j_member SET mpw=? WHERE mid=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mvo.getMpw());
			pstmt.setString(2, mvo.getMid());

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt);
		}
		return false;
	}
	
	

	// 회원상세조회 --> 내 정보 보기, 회원상세조회(관리자)
	public MemberVO select(String mid) {
		MemberVO mvo = null;
		try {
			query = "SELECT mid, name, mpw, mnick, memail, " 
					+ "mphone, reg_date FROM j_member WHERE mid = ?";

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mid);

			rset = pstmt.executeQuery();

			if (rset.next() == true) {
				mvo = new MemberVO();
				mvo.setMid(rset.getString("mid"));// 아이디
				mvo.setName(rset.getString("name")); // 이름
				mvo.setMpw(rset.getString("mpw")); // 이름
				mvo.setMnick(rset.getString("mnick")); // 회원닉네임
				mvo.setMemail(rset.getString("memail")); // 이메일
				mvo.setMphone(rset.getString("mphone")); // 전화번호
				mvo.setRegDate(rset.getDate("reg_date")); // 회원가입일

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rset);

		}
		return mvo;
	}
	
	

	// 정보수정
	public boolean update(MemberVO mvo) {
		try {
			// 전화번호, 이메일만 수정 가능
			query = "UPDATE j_member SET memail=?, mphone=? WHERE mid=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mvo.getMemail());
			pstmt.setString(2, mvo.getMphone());
			pstmt.setString(3, mvo.getMid());

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt);
		}
		return false;
	}
	
	

	// 회원탈퇴or삭제
	public boolean delete(String mid) {
		try {
			query = "DELETE FROM j_member where mid = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, mid);

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt);

		}

		return false;
	}

	
	
	// 회원전체목록
	public List<MemberVO> selectAll(double amount, int pageNum, String type, String keyword) {
		MemberVO mvo = null;
		List<MemberVO> memList = new ArrayList<>();
		try {
			query = "SELECT mid, name, mphone, memail, reg_date \n" 
			        + "FROM (SELECT ROWNUM AS rnum, b.*\n"
			        + "FROM (SELECT * FROM j_member\n";

			if (!type.equals("") && !keyword.equals("")) {
			    query += "WHERE " + type + " LIKE '%" + keyword + "%'\n";
			}

			query += "ORDER BY reg_date DESC) b)\n" 
			        + "WHERE rnum <= ? * ?\n"
			        + "AND rnum > ? * ?";




			pstmt = con.prepareStatement(query);
			pstmt.setDouble(1, amount);
			pstmt.setInt(2, pageNum);
			pstmt.setDouble(3, amount);
			pstmt.setInt(4, pageNum - 1);

			rset = pstmt.executeQuery();

			while (rset.next() == true) {
				mvo = new MemberVO();
				mvo.setMid(rset.getString("mid")); // 아이디
				mvo.setName(rset.getString("name")); // 이름
				mvo.setMemail(rset.getString("memail")); // 이메일
				mvo.setMphone(rset.getString("mphone")); // 전화번호
				mvo.setRegDate(rset.getDate("reg_date")); // 가입일자

				memList.add(mvo);// 값 저장 후 list 객체에 추가
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rset);
		}

		return memList;
	}
	
	
	//전체회원수
    public int totalCount(String type, String keyword) {
        int cnt = 0;

        try {
           query = "SELECT COUNT(*) FROM j_member ";
           if(!type.equals("")&& !keyword.equals("")) {
    			query += " WHERE " + type + " LIKE '%" + keyword + "%' ";
    		}
    
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            if (rset.next()) {
                cnt = rset.getInt(1); // 조회된 게시물 수 가져오기
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConn.close(pstmt, rset);
        }

        return cnt;
    }

	 //관리자비밀번호변경
	public boolean changepw(MemberVO mvo) {
		try {
	        query = "UPDATE member SET mpw=? WHERE mid=?";
	        pstmt = con.prepareStatement(query);
	        pstmt.setString(1, mvo.getMpw());
	        pstmt.setString(2, mvo.getMid());
	  
	        int result = pstmt.executeUpdate();
	        if (result == 1) {
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBConn.close(pstmt);
	    }
	    return false;
	}
	
	

}// DAO END
