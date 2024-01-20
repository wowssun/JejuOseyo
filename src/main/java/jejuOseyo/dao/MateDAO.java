package jejuOseyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jejuOseyo.util.DBConn;
import jejuOseyo.vo.MateVO;


public class MateDAO {

	
	private Connection con;
	
	private String query;      //쿼리문 저장 필드
	private PreparedStatement pstmt;
	private ResultSet rs;     //SELECT 쿼리를 실행한 결과를 저장할 필드
	
	//매개변수 있는 생성자 통해서 받기 -> 초기화해서 사용
	public MateDAO(Connection con) {
		this.con = con;
	}
	
	
	//전체 게시글 목록
	public List<MateVO> selectAll(){
		
		List<MateVO> mvoList = new ArrayList<>();
		MateVO mvo = null;   //변수선언+초기화
		
		try {
			
			//전체 데이터 조회 쿼리문(select)   +   검색까지 추가됨
			query = "SELECT * FROM mate ";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();    //rs=resultSet //쿼리를 실행하는 것을 의미함. 테이블을 구현
			
			//MateVO mvo; //변수선언 밖으로 빼기
			while(rs.next() == true) {                 //조회된 레코드들이 있다면
				mvo = new MateVO();                    //MateVO 객체를 생성하여,
				mvo.setMno(rs.getInt("mno"));       //해당 레코드 값을 저장한 후
				mvo.setMtitle(rs.getString("mtitle"));
				mvo.setMnick(rs.getString("mnick"));
				mvo.setMdate(rs.getDate("mdate"));
				mvo.setMhit(rs.getInt("mhit"));
				
				mvoList.add(mvo);				//List 객체에 추가
				
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rs);     //pstmt와 rs를 같이 닫아야 함
		}
		return mvoList;
		
	}

	
	
	
	
	
	
	
	
	
	
	
	//전체 게시글 수 - 검색까지 추가
	public int totalCount(String mtype, String mkeyword) {
		int cnt = 0;
		
		try {
			query = " SELECT COUNT(*) FROM mate ";
			
			if(!mtype.equals("") && !mkeyword.equals("")) {
				query += " WHERE " + mtype + " LIKE '%" + mkeyword + "%' ";
			}
			
			
			pstmt = con.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			if (rs.next() == true) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rs);
		}
		
		return cnt;
	}	
	
	
	
	
	
	
	
	
	//게시글 하나 상세 조회
		public MateVO select(int mno) {
			MateVO mvo = null;   //변수선언+초기화
			try {
				//id 조회 쿼리문(select)
				query = " SELECT * FROM mate WHERE mno=? ";
				
				//선언해둔 pstmt 객체 생성
				pstmt = DBConn.getConnection().prepareStatement(query);
				
				//번호에 해당하는 값 바인딩
				pstmt.setInt(1, mno);
				
				rs = pstmt.executeQuery();    //rs=resultSet //쿼리를 실행하는 것을 의미함. 테이블을 구현
				
				//MateVO mvo; //변수선언 밖으로 빼기
				if(rs.next() == true) {                    //조회된 레코드가 있다면
					mvo = new MateVO();                  //MateVO 객체를 생성하여,
					mvo.setMtitle(rs.getString("mtitle"));       //해당 레코드 값을 저장
					mvo.setMnick(rs.getString("mnick"));
					mvo.setDepart(rs.getString("depart"));
					mvo.setFin(rs.getString("fin"));
					mvo.setMplace(rs.getString("mplace"));
					mvo.setMnum(rs.getInt("mnum"));
					mvo.setMtext(rs.getString("mtext"));
					
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBConn.close(pstmt, rs);     //pstmt와 rs를 같이 닫아야 함
			}
			return mvo;  //선언된 블럭 밖에서 사용하게 되어 변수 인식 못함
					
		}
	
	
	
	
		
	//게시글 등록 (insert 메서드)
	public boolean insert(MateVO mvo) {
		try {
			//insert 쿼리문
			query = " INSERT INTO mate VALUES (?, ?, ?, ?, ?, ?, ?, SYSDATE) ";
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, mvo.getMtitle());
			pstmt.setString(2, mvo.getMnick());
			pstmt.setString(3, mvo.getDepart());
			pstmt.setString(4, mvo.getFin());
			pstmt.setString(5, mvo.getMplace());
			pstmt.setInt(6, mvo.getMnum());
			pstmt.setString(7, mvo.getMtext());
			
			int result = pstmt.executeUpdate();
			if(result == 1) {   //정상적으로 메모작성 성공 시 true 반환
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt);    //pstmt를 닫는 메소드를 DBConn에서 호출
		}
		
		 return false;		//그렇지 않으면 false 반환				
	}
	
	
	
	//게시글 조회 수 증가
	public void updateHit(int mno) {
		try {
			//조회 수 증가 쿼리문(update)
			query = " UPDATE mate SET mhit = mhit + 1 WHERE mno=? ";
			
			//선언해둔 pstmt 객체 생성
			pstmt = DBConn.getConnection().prepareStatement(query);
			
			//글번호에 해당하는 값 바인딩
			pstmt.setInt(1, mno);
			
			rs = pstmt.executeQuery();    //rs=resultSet //쿼리를 실행하는 것을 의미함. 테이블을 구현
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rs);     //pstmt와 rs를 같이 닫아야 함
		}
			
	}
	
	
	//게시글 삭제
	public boolean delete(int mno) {
		try {
			//delete 쿼리문
			query = " DELETE mate WHERE mno = ? ";
			
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1, mno);
			
			int result = pstmt.executeUpdate();
			if(result == 1) {   //정상적으로 삭제 성공 시 true 반환
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt);    //pstmt를 닫는 메소드를 DBConn에서 호출
		}
		return false;		//그렇지 않으면 false 반환
	}
	
	
	
	
	//게시글 수정
	public boolean update(MateVO mvo) {
		try {
			query = "UPDATE mate SET mtitle = ?, depart = ?, fin = ?, mplace = ?, mnum = ?, mtext = ? WHERE mno = ? ";
			
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, mvo.getMtitle());
			pstmt.setString(2, mvo.getDepart());
			pstmt.setString(3, mvo.getFin());
			pstmt.setString(4, mvo.getMplace());
			pstmt.setInt(3, mvo.getMnum());
			pstmt.setString(3, mvo.getMtext());
			pstmt.setInt(3, mvo.getMno());
			
			int result = pstmt.executeUpdate();
			if(result == 1) {   //정상적으로 메모작성 성공 시 true 반환
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt);    //pstmt를 닫는 메소드를 DBConn에서 호출
		}
		return false;
	}
	
	
}