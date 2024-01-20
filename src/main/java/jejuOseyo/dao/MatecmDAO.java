package jejuOseyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jejuOseyo.util.DBConn;
import jejuOseyo.vo.MatecmVO;


public class MatecmDAO {
	
	private Connection con;
	
	private String query;      //쿼리문 저장 필드
	private PreparedStatement pstmt;
	private ResultSet rs;     //SELECT 쿼리를 실행한 결과를 저장할 필드
	
	//매개변수 있는 생성자 통해서 받기 -> 초기화해서 사용
	public MatecmDAO(Connection con) {
		this.con = con;
	}
	
	
	//전체 댓글 목록
	public List<MatecmVO> selectAll(){
		List<MatecmVO> mcmvoList = new ArrayList<>();    //리스트 객체 생성 //new를 했으니 null체크 불가!
		MatecmVO mcmvo = null;   //변수선언+초기화
		
		try {
			//전체 데이터 조회 쿼리문(select)
			query = " SELECT * FROM matecm ";
			
			//선언해둔 pstmt 객체 생성
			pstmt = DBConn.getConnection().prepareStatement(query);
			
			//?데이터 없으니 바인딩할 데이터 없음 삭제~
			
			rs = pstmt.executeQuery();    //rs=resultSet //쿼리를 실행하는 것을 의미함. 테이블을 구현
			
			//matecmVO mcmvo; //변수선언 밖으로 빼기
			while(rs.next() == true) {                 //조회된 레코드들이 있다면
				mcmvo = new MatecmVO();                  //MatecmVO 객체를 생성하여,
				mcmvo.setMnick(rs.getString("mnick"));       //해당 레코드 값을 저장한 후
				mcmvo.setMcmdate(rs.getDate("mcmdate"));
				mcmvo.setMcmtext(rs.getString("mcmtext"));
				
				mcmvoList.add(mcmvo);				//List 객체에 추가
				
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rs);     //pstmt와 rs를 같이 닫아야 함
		}
		return mcmvoList;
		
	}
	
		
	//댓글 등록 (insert 메서드)
	public boolean insert(MatecmVO mcmvo) {
		try {
			//insert 쿼리문
			query = " INSERT INTO matecm VALUES (SYSDATE, ?) ";
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, mcmvo.getMcmtext());
			
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
	
	
	
	//댓글 삭제
	public boolean delete(int mcmno) {
		try {
			//delete 쿼리문
			query = " DELETE matecm WHERE mcmno = ? ";
			
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1, mcmno);
			
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
	

	
	//내가 쓴 댓글 전체 목록
	public List<MatecmVO> selectAll(String mnick) {
		
		List<MatecmVO> mcmvoList = new ArrayList<>();    //리스트 객체 생성 //new를 했으니 null체크 불가!
		MatecmVO mcmvo = null;   //변수선언+초기화
		
		try {
			//전체 데이터 조회 쿼리문(select)
			query = " SELECT * FROM matecm WHERE mnick = ? ";
			
			//선언해둔 pstmt 객체 생성
			pstmt = DBConn.getConnection().prepareStatement(query);
			
			pstmt.setString(1, mnick);
			
			rs = pstmt.executeQuery();    //rs=resultSet //쿼리를 실행하는 것을 의미함. 테이블을 구현
			
			//matecmVO mcmvo; //변수선언 밖으로 빼기
			while(rs.next() == true) {                 //조회된 레코드들이 있다면
				mcmvo = new MatecmVO();                  //MatecmVO 객체를 생성하여,
				mcmvo.setMcmno(rs.getInt("mcmno"));
				mcmvo.setMnick(rs.getString("mnick"));       //해당 레코드 값을 저장한 후
				mcmvo.setMcmdate(rs.getDate("mcmdate"));
				mcmvo.setMcmtext(rs.getString("mcmtext"));
				
				mcmvoList.add(mcmvo);				//List 객체에 추가
				
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rs);     //pstmt와 rs를 같이 닫아야 함
		}
		return mcmvoList;
	}
	
	
}