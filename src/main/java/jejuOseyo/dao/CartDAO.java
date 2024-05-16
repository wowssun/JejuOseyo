package jejuOseyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jejuOseyo.util.DBConn;
import jejuOseyo.vo.CartVO;
import jejuOseyo.vo.MemberVO;
import jejuOseyo.vo.RoomVO;

public class CartDAO {
	
	private Connection con;
	private String query;	   // 쿼리문 저장 필드
	private PreparedStatement psmt;
	private ResultSet rs;
	
	
	public CartDAO(Connection con) {
		this.con = con;
	}

	// ( 미완성 ) 숙소 상세조회에서 넘어오는 값 확인해서 다시 완료하기!!!!!!
	//장바구니 담기 (등록) 숙소명(숙소), 예약인원수(보류)(파라미터), 체크인 날짜(파라미터), 체크아웃 날짜(파라미터), 가격(숙소)
	
	public boolean cartInsert(CartVO cvo) {
		
		 try {  // 쿼리를 실행하다가 예외가 발생할 수 있으니까 try/ catch문에다가 	// insert 쿼리문
			   query = " INSERT INTO cart VALUES (SEQ_CART_cno.NEXTVAL, ? , ? , ? , ? , ?)";  // dao 쿼리문 수정 다시하기

			   psmt = con.prepareStatement(query);
			   psmt.setInt(1,cvo.getRvo().getRmNo());   				//   1. 숙소번호
			   psmt.setString(2, cvo.getMvo().getMid());		    		//   2. mid
			   psmt.setString(3, cvo.getCheckIn());  			//   3. 체크인 날짜  // String 처리할지 생각( 해결 )
			   psmt.setString(4, cvo.getCheckOut());			//   4. 체크아웃 날짜
			   psmt.setInt(5, cvo.getGuest());					//   5. 예약할 인원수
			   
			   int result = psmt.executeUpdate();
			 
			   if(result ==1) {   // 정상적으로 회원가입 성공 시 true 반환
				  return true; }
		  		} catch (SQLException e) {
		  			e.printStackTrace();
		  		}finally {   
		  		 DBConn.close(psmt);
		  		}
		  		// 그렇지 않으면  false 반환
		  		return false;
	} 
	
	//장바구니 전체 목록(페이징)
	public List<CartVO> cartSelectAll(double amount,int pageNum, String mid){
		List<CartVO> cvoList = new ArrayList<CartVO>();
		
		CartVO cvo = null;
		RoomVO rvo = null;
		MemberVO mvo = null;
		
		// (카트) 숙소명(숙소), 예약인원수(보류)(카트), 체크인 날짜(카트), 체크아웃 날짜(카트), 가격(숙소)
		try {
			query = " SELECT * FROM (SELECT rownum as rnum,b.*"
				  + " FROM (SELECT c.cno, c.mid, c.checkin ,c.checkout ,c.guest, r.img, r.rm_name, r.price"
				  + " FROM cart c"
				  + " JOIN room r ON c.rm_no = r.rm_no"
				  + " WHERE c.mid = ?"
				  + " ORDER BY c.cno DESC) b"
				  + " WHERE rownum <= ? * ?)"
				  + " WHERE rnum > ? * ? ";
			
				 psmt = con.prepareStatement(query);  // 바인딩이 없으니 이것만 사용
			
				  psmt.setString(1, mid);
				  psmt.setDouble(2, amount);
				  psmt.setInt(3, pageNum);
				  psmt.setDouble(4, amount);
				  psmt.setInt(5, (pageNum -1));
				
			
				  rs = psmt.executeQuery();
			
			
			while(rs.next()) {   //. 여러 줄이니까 while 
				
				cvo = new CartVO();   //  CartVO 객체를 생성하여 
				rvo = new RoomVO();   //  RoomVO 객체를 생성하여 
				mvo = new MemberVO();
				
				cvo.setCno(rs.getInt("cno"));
				mvo.setMid(rs.getString("mid"));
				rvo.setRmName(rs.getString("rm_name"));  	// 숙소명			//  해당 레코드 값을 저장
				rvo.setImg(rs.getString("img"));
				cvo.setCheckIn(rs.getString("checkin").substring(0,10));	// 체크인 날짜	
				cvo.setCheckOut(rs.getString("checkout").substring(0,10));	// 체크아웃 날짜
				cvo.setGuest(rs.getInt("guest"));
				rvo.setPrice(rs.getInt("price"));			// 숙소 가격
				cvo.setRvo(rvo);							// rvo에 저장된 값을 cvo에 set
				cvo.setMvo(mvo);
				
				cvoList.add(cvo);	// list 객체에 추가
			}
		}catch (SQLException e) {
  			e.printStackTrace();
  		}
		return cvoList;
	} 
	
	//결제 전 예약 정보조회
	public CartVO cartSelect(int cno){
		CartVO cvo = null;
		RoomVO rvo = null;
		MemberVO mvo = null;
		
		try {
			query = " SELECT m.mid, m.name, m.memail, m.mphone, r.rm_name, r.addr1, r.addr2, r.price, r.notice, r.hid ,r.rm_no,c.checkin, c.checkout, c.guest, c.cno"
				  + " FROM cart c"
				  + " JOIN room r"
				  + " ON c.rm_no = r.rm_no"
				  + " JOIN j_member m"
				  + " ON c.mid = m.mid"
				  + " WHERE cno = ?";    // cno를 가지고 조인해서 내가 가져올 값 가져오기
						
			 psmt = con.prepareStatement(query);// 실행할 prepared
			
			psmt.setInt(1,cno); // 물음표 바인딩   // 여기서 넘어온 mid
			
			rs = psmt.executeQuery();  // 실행한다.  executeQuery 는 resultSet 으로 온다. 그래서 int로 받을 수 없다. 결과가 있기 때문에 
			
			
			if(rs.next()) {		// 조회된 레코드가 있다면  // == true 기본
				
				cvo = new CartVO();   //  CartVO 객체를 생성하여 
				rvo = new RoomVO();   //  RoomVO 객체를 생성하여 
				mvo = new MemberVO();	// MemberVO 객체를 생성하여 
				
				//  m.name, m.mphone, r.rm_name, r.location, r.price, r.notice,c.checkin, c.checkout, c.guest, c.cno
				mvo.setMid(rs.getString("mid"));            // 회원 아이디
				mvo.setName(rs.getString("name"));			// 예약자
				mvo.setMphone(rs.getString("mphone"));		// 예약자 전화번호
				mvo.setMemail(rs.getString("memail"));  	// 예약자 이메일	
				rvo.setHid(rs.getString("hid"));	        // 호스트 아이디
				rvo.setRmNo(rs.getInt("rm_no"));			// 숙소번호
				rvo.setRmName(rs.getString("rm_name"));  	// 숙소명			//  해당 레코드 값을 저장
				rvo.setAddr1(rs.getString("addr1"));	    // 숙소 위치1
				rvo.setAddr2(rs.getString("addr2"));		// 숙소 위치 2
				rvo.setPrice(rs.getInt("price"));			// 숙소 가격
				rvo.setNotice(rs.getString("notice"));		// 숙소 공지사항
				cvo.setCno(rs.getInt("cno"));				// 장바구니 번호
				cvo.setCheckIn(rs.getString("checkin").substring(0,10));	// 체크인 날짜	
				cvo.setCheckOut(rs.getString("checkout").substring(0,10));	// 체크아웃 날짜
				cvo.setGuest(rs.getInt("guest"));			// 예약인원수
				cvo.setMvo(mvo);							// mvo에 저장된 값을 cvo에 set
				cvo.setRvo(rvo);							// rvo에 저장된 값을 cvo에 set
			}
			}catch (SQLException e) {
	  			e.printStackTrace();
	  		}
		return cvo;
		
	} 
	
	//장바구니 삭제
	public boolean cartDelete(int cno){
		try {
			query = "DELETE FROM CART WHERE cno = ? ";

			 psmt = con.prepareStatement(query);
			psmt.setInt(1, cno);

			int result = psmt.executeUpdate();

			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(psmt);
		}
		// 그렇지 않으면 false 반환
		return false;
	}
	
	 //장바구니 비우기
	public boolean cartDeleteAll(String mid){
		try {
			query = "DELETE FROM CART WHERE mid = ? ";

			 psmt = con.prepareStatement(query);
			psmt.setString(1, mid);

			int result = psmt.executeUpdate();

			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(psmt);
		}
		// 그렇지 않으면 false 반환
		return false;
	} 
	
	// 전체 게시물 수
	public int totalCount(String mid) {   
			
			int cnt = 0;
			
			try {
				query = "SELECT COUNT(*) FROM cart WHERE mid = ?";
			       
				 psmt = con.prepareStatement(query);
			    
			    psmt.setString(1, mid);
				 
				 rs = psmt.executeQuery();
			      
			      if (rs.next()) {
			    	cnt = rs.getInt(1);
				}
			}catch (SQLException e) {
	  			e.printStackTrace();
	  		}			
			
			return cnt;
		}

}
