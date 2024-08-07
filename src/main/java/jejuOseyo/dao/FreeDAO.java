package jejuOseyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jejuOseyo.vo.FreeVO;

public class FreeDAO {
	
	private Connection con;
	private String query;	   // 쿼리문 저장 필드
	private PreparedStatement psmt;
	private ResultSet rs;
	

	public FreeDAO(Connection con) {   // memberDAO에서는 setter,getter로 이제는 생성자로
		this.con = con;
	}
	
	// 전체 게시물 목록 - 페이징
	
	public List<FreeVO> selectAll(double amount,int pageNum, String type, String keyword){
		
		List<FreeVO> fvoList = new ArrayList<FreeVO>();
		
		FreeVO fvo = null;
		
		try {
			query = " select * from (select rownum as rnum,b.* "
				  + " from (select * from Free ";
			if(!type.equals("") && !keyword.equals("")) {
				query += " where " + type + " like '%" + keyword + "%' ";
			}
				query +=" order by free_No desc) b "
					  + " where rownum <= ? * ?) "
					  + " where rnum > ? * ? ";
			
			
				psmt = con.prepareStatement(query);  // 바인딩이 없으니 이것만 사용
				//psmt = con.prepareStatement(query);
			
			  psmt.setDouble(1, amount);
			  psmt.setInt(2, pageNum);
			  psmt.setDouble(3, amount);
			  psmt.setInt(4, (pageNum -1));
			
			
			  rs = psmt.executeQuery();
			
			
			while(rs.next()) {   //. 여러 줄이니까 while 
				
				fvo = new FreeVO();   //  MemberVO 객체를 생성하여 
				fvo.setFreeNo(rs.getInt("free_No"));						//  해당 레코드 값을 저장
				fvo.setMid(rs.getString("mid"));
				fvo.setFreeTitle(rs.getString("free_Title"));
				fvo.setRegDate(rs.getDate("reg_date"));	
				fvo.setFreeHit(rs.getInt("free_Hit"));
				fvo.setIp(rs.getString("ip"));
		
			fvoList.add(fvo);	// list 객체에 추가
			}
		}catch (SQLException e) {
  			e.printStackTrace();
  		}
		return fvoList;
		
	}
	
	// 게시물 하나 조회
	public FreeVO select(int freeNo) {
		
		FreeVO fvo = null;
		
		try {
		query = "SELECT * FROM Free WHERE free_No=?";    //쿼리문 아이디 조회하는
		psmt = con.prepareStatement(query);// 실행할 prepared
		
		psmt.setInt(1,freeNo); // 물음표 바인딩   // 여기서 넘어온 freeNo
		
		rs = psmt.executeQuery();  // 실행한다.  executeQuery 는 resultSet 으로 온다. 그래서 int로 받을 수 없다. 결과가 있기 때문에 
		
		if(rs.next()) {		// 조회된 레코드가 있다면  // == true 기본
			fvo = new FreeVO();   //  FreeVO 객체를 생성하여 
			fvo.setFreeNo(rs.getInt("free_No"));						//  해당 레코드 값을 저장
			fvo.setMid(rs.getString("mid"));
			fvo.setFreeTitle(rs.getString("free_Title"));
			fvo.setFreeContent(rs.getString("free_Content"));
			fvo.setRegDate(rs.getDate("reg_date"));	
			fvo.setFreeHit(rs.getInt("free_Hit"));
			fvo.setIp(rs.getString("ip"));	
		}
		}catch (SQLException e) {
  			e.printStackTrace();
  		}
		return fvo;
	
	}
	
	// 게시물 조회 수 증가    // 일단 이렇게 작성해놓기
	public void updateHit(int freeNo) {
	
		try {  
			query = "UPDATE Free SET FREE_HIT = FREE_HIT + 1 WHERE free_No = ?";  // 쿼리문 확인하기
			   
			   
			   psmt = con.prepareStatement(query);
			
			   psmt.setInt(1, freeNo);
			  
			   psmt.executeUpdate();	  
	
	  		} catch (SQLException e) {
	  			e.printStackTrace();
	  		}
		  	
	}
	
	
	
	// 전체 게시물 수
	
	public int totalCount(String type, String keyword) {   
		
		int cnt = 0;
		
		try {
			query = "SELECT COUNT(*) FROM Free" ;
			
			if(!type.equals("") && !keyword.equals("")) {
				query += " WHERE " + type + " LIKE '%" + keyword + "%' ";
			}
		       
		    psmt = con.prepareStatement(query);
			// psmt = con.prepareStatement(query);
			 
			 rs = psmt.executeQuery();
		      
		      if (rs.next()) {
		    	cnt = rs.getInt(1);
			}
		}catch (SQLException e) {
  			e.printStackTrace();
  		}
		
		return cnt;
	}
	
	// 게시물 등록하기
	public boolean insert(FreeVO fvo) {
		 try {  // 쿼리를 실행하다가 예외가 발생할 수 있으니까 try/ catch문에다가 	// insert 쿼리문
			   query = " INSERT INTO Free VALUES ( seq_free_free_no.nextval, ? ,? , ?, SYSDATE , 0, ? )";  // dao 쿼리문 수정 다시하기
			   // INSERT INTO Free VALUES ( seq_free_free_no.nextval, 'AAAAA','제목','내용',sysdate,0,'aaa');  // 여기서 4개의 값만 입력받으면 됨
			   // 여기서 hit는 그냥 0으로 처리해놓는다.
			   
			   // 매개변수로 넘겨받은 데이터를 t_member 테이블에 저장
			   psmt = con.prepareStatement(query);
			//(작성자,제목,내용,사용자ip)
			   
			   psmt.setString(1, fvo.getMid());
			   psmt.setString(2, fvo.getFreeTitle());
			   psmt.setString(3, fvo.getFreeContent());
			   //이미지
			   psmt.setString(4, fvo.getIp());
			  
			   
			   int result = psmt.executeUpdate();
			 
			   if(result ==1) {   // 정상적으로 회원가입 성공 시 true 반환
				  return true; }
		  		} catch (SQLException e) {
		  			e.printStackTrace();
		  		}
		  		return false;
	}
	
	// 게시글 수정하기
	public boolean update(FreeVO fvo) {
		try {  
			   query = " UPDATE Free SET MID=?, FREE_TITLE=?, FREE_CONTENT=? WHERE free_No=?";
			   
			  // 매개변수로 넘겨받은 데이터를 t_member 테이블에 저장
			   
			   psmt = con.prepareStatement(query);
			
			   psmt.setString(1, fvo.getMid());
			   psmt.setString(2, fvo.getFreeTitle());
			   psmt.setString(3, fvo.getFreeContent());
			   psmt.setInt(4, fvo.getFreeNo());
			  
			  
			   int result = psmt.executeUpdate();
			 
			   if(result ==1) {   
				  return true; }
		  		} catch (SQLException e) {
		  			e.printStackTrace();
		  		}
		  		// 그렇지 않으면  false 반환
		  		return false;	  
		
	}
	
	
	// 게시글 삭제하기
	 public boolean delete(int freeNo) {   // 가져올 값이 memo에서는 mno를 가져온다.
		 try {
			  query = " DELETE FROM Free WHERE free_No = ? ";
			  
			  psmt = con.prepareStatement(query);
			  
			  psmt.setInt(1,freeNo);
			  
			  
			  	int result = psmt.executeUpdate();
			  	
			  	 if(result ==1) { 
			  		return true ;	
					   }
			  		} catch (SQLException e) {
			  			e.printStackTrace();
			  		}
			  		// 그렇지 않으면  false 반환
			  		return false ;	   
	 }
	 
	
	
}