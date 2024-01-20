package jejuOseyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jejuOseyo.util.DBConn;
import jejuOseyo.vo.HostVO;
import jejuOseyo.vo.MemberVO;
import jejuOseyo.vo.PaymentVO;
import jejuOseyo.vo.RoomVO;
import jejuOseyo.vo.YeyakVO;

public class YeyakDAO {
	
	private Connection con;
	private String query;	   // 쿼리문 저장 필드
	private PreparedStatement psmt;
	private ResultSet rs;
	
	public YeyakDAO(Connection con) {
		this.con = con;
	}

	// 결제 정보 등록 | 결제번호, 카드이름,카드번호,결제금액, 결제 일시
		public boolean payInsert(PaymentVO pvo) {
			 try {  // 쿼리를 실행하다가 예외가 발생할 수 있으니까 try/ catch문에다가 	// insert 쿼리문
				   query = " INSERT INTO payment VALUES ( ? , ? , ? , ? , SYSDATE ,'')";  // dao 쿼리문 수정 다시하기
				   // SEQ_CART_cno.NEXTVAL,21,'UserFour','2023-08-08','2023-08-29', 3
				   	   		  
				   psmt = con.prepareStatement(query);
				   
				   psmt.setString(1,pvo.getPayNo());   				
				   psmt.setString(2, pvo.getCardName());		    
				   psmt.setString(3, pvo.getCardNum());  			
				   psmt.setInt(4, pvo.getAmount());						
				   
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

	
	// 예약 등록 | 예약번호, 회원이름, 호스트이름, 숙소번호, 결제번호, 체크인, 체크아웃, 게스트
	public boolean yeInsert(YeyakVO yvo) {
		 try {  // 쿼리를 실행하다가 예외가 발생할 수 있으니까 try/ catch문에다가 	// insert 쿼리문
			   query = " INSERT INTO yeyak VALUES ( ?, ? , ? , ? , ? , ?, ?, ?)";  // dao 쿼리문 수정 다시하기
			   // SEQ_CART_cno.NEXTVAL,21,'UserFour','2023-08-08','2023-08-29', 3
			   	   		  
			   psmt = con.prepareStatement(query);
			   
			   psmt.setString(1,yvo.getYno());   				
			   psmt.setString(2, yvo.getMvo().getMid());		    		
			   psmt.setString(3, yvo.getHvo().getHid());  			
			   psmt.setInt(4, yvo.getRvo().getRmNo());			
			   psmt.setString(5, yvo.getPvo().getPayNo());	
			   psmt.setString(6, yvo.getYeCheckIn());
			   psmt.setString(7, yvo.getYeCheckOut());
			   psmt.setInt(8, yvo.getYeGuest());
			   
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
	
	// 회원예약전체목록(페이징)
	public List<YeyakVO> yeSelectAll(double amount,int pageNum, String mid) {
		List<YeyakVO> yvoList = new ArrayList<YeyakVO>();
		

		YeyakVO yvo = null;
		RoomVO rvo = null;
		PaymentVO pvo = null;
		
		try {
			query = " SELECT * FROM (SELECT rownum as rnum,b.*"
				  + " FROM (SELECT r.img, r.rm_name, r.price, y.yno,y.ye_checkin, y.ye_checkout, y.ye_guest, p.pay_date"
				  + " FROM yeyak y"
				  + " JOIN room r"
				  + " ON y.rm_no = r.rm_no"
				  + " JOIN payment p"
				  + " ON y.payno = p.payno"
				  + " WHERE mid = ?"
				  + " ORDER BY p.pay_date DESC) b"
				  + " WHERE rownum <= ? * ?)"
				  + " WHERE rnum > ? * ? ";
			
			psmt = con.prepareStatement(query); // 바인딩이 없으니 이것만 사용
				//psmt = con.prepareStatement(query);
			
				  psmt.setString(1, mid);
				  psmt.setDouble(2, amount);
				  psmt.setInt(3, pageNum);
				  psmt.setDouble(4, amount);
				  psmt.setInt(5, (pageNum -1));
				
			
				  rs = psmt.executeQuery();
			
			// r.img, r.rm_name, r.price,y.ye_checkin, y.ye_checkout, y.ye_guest"
			while(rs.next()) {   //. 여러 줄이니까 while 
				
				yvo = new YeyakVO(); //  YeyakVO 객체를 생성하여 
				rvo = new RoomVO();   //  RoomVO 객체를 생성하여 
				pvo = new PaymentVO();
				
				rvo.setImg(rs.getString("img"));// 숙소 이미지
				rvo.setRmName(rs.getString("rm_name"));// 숙소명
				rvo.setPrice(rs.getInt("price"));// 숙소 가격
				yvo.setYno(rs.getString("yno"));	// 예약번호
				yvo.setYeCheckIn(rs.getString("ye_checkin").substring(0,10));// 예약 체크인
				yvo.setYeCheckOut(rs.getString("ye_checkout").substring(0,10));// 예약 체크아웃
				yvo.setYeGuest(rs.getInt("ye_guest"));// 예약 인원수
				pvo.setPayDate(rs.getString("pay_date"));
				
				yvo.setPvo(pvo);
				yvo.setRvo(rvo);							// rvo에 저장된 값을 cvo에 set
				
				yvoList.add(yvo);	// list 객체에 추가
			}
		}catch (SQLException e) {
  			e.printStackTrace();
  		}
		return yvoList;
	} 
	
	
	// 호스트예약전체목록(페이징)
		public List<YeyakVO> hoyeSelectAll(double amount,int pageNum, String hid) {
			List<YeyakVO> yvoList = new ArrayList<YeyakVO>();
			

			YeyakVO yvo = null;
			RoomVO rvo = null;
			PaymentVO pvo = null;
				
			try {
				query = " SELECT * FROM (SELECT rownum as rnum,b.*"
						  + " FROM (SELECT r.img, r.rm_name, r.price, y.yno,y.ye_checkin, y.ye_checkout, y.ye_guest,p.pay_date"
						  + " FROM yeyak y"
						  + " JOIN room r"
						  + " ON y.rm_no = r.rm_no"
						  + " JOIN payment p"
						  + " ON y.payno = p.payno"
						  + " WHERE y.hid = ?"
						  + " ORDER BY y.ye_checkin DESC) b"
						  + " WHERE rownum <= ? * ?)"
						  + " WHERE rnum > ? * ? ";
				
					psmt = con.prepareStatement(query);  // 바인딩이 없으니 이것만 사용
					//psmt = con.prepareStatement(query);
				
					  psmt.setString(1, hid);
					  psmt.setDouble(2, amount);
					  psmt.setInt(3, pageNum);
					  psmt.setDouble(4, amount);
					  psmt.setInt(5, (pageNum -1));
					
				
					  rs = psmt.executeQuery();
				
				
				while(rs.next()) {   //. 여러 줄이니까 while 
					
					yvo = new YeyakVO(); //  YeyakVO 객체를 생성하여 
					rvo = new RoomVO();   //  RoomVO 객체를 생성하여 
					pvo = new PaymentVO();
					
					rvo.setImg(rs.getString("img"));// 숙소 이미지
					rvo.setRmName(rs.getString("rm_name"));// 숙소명
					rvo.setPrice(rs.getInt("price"));// 숙소 가격
					yvo.setYno(rs.getString("yno"));	// 예약번호
					yvo.setYeCheckIn(rs.getString("ye_checkin").substring(0,10));// 예약 체크인
					yvo.setYeCheckOut(rs.getString("ye_checkout").substring(0,10));// 예약 체크아웃
					yvo.setYeGuest(rs.getInt("ye_guest"));// 예약 인원수
					pvo.setPayDate(rs.getString("pay_date"));
					
					yvo.setPvo(pvo);
					yvo.setRvo(rvo);							// rvo에 저장된 값을 yvo에 set
					
					yvoList.add(yvo);	// list 객체에 추가
				}
			}catch (SQLException e) {
	  			e.printStackTrace();
	  		}	
			return yvoList;
		} 
	
	// 회원 예약 확인/취소 상세 조회
	public YeyakVO yeSelect(String yno) {
		
		YeyakVO yvo = null;
		MemberVO mvo = null;
		HostVO hvo = null;
		RoomVO rvo = null;
		PaymentVO pvo = null;
		
		try {
			query = " SELECT y.yno,m.name,m.mphone,h.hnick,h.hphone,r.rm_no,r.notice,r.rm_name,r.addr1, r.addr2, y.ye_guest,y.ye_checkin,y.ye_checkout,r.price,p.payno,p.card_name,p.card_num,p.pay_date,p.pay_candate"
				  + " FROM yeyak y"
				  + " JOIN j_member m"
				  + " ON y.mid = m.mid"
				  + " JOIN host h"
				  + " ON y.hid = h.hid"
				  + " JOIN room r"
				  + " ON y.rm_no = r.rm_no"
				  + " JOIN payment p"
				  + " ON y.payno = p.payno"
				  + " WHERE y.yno = ?";   
						
			psmt = con.prepareStatement(query);// 실행할 prepared
			
			psmt.setString(1,yno); // 물음표 바인딩   // 여기서 넘어온 mid
			
			rs = psmt.executeQuery();  // 실행한다.  executeQuery 는 resultSet 으로 온다. 그래서 int로 받을 수 없다. 결과가 있기 때문에 
			
			
			if(rs.next()) {		// 조회된 레코드가 있다면  // == true 기본
				
				yvo = new YeyakVO();   //  YeyakVO 객체를 생성하여 
				rvo = new RoomVO();   //  RoomVO 객체를 생성하여 
				mvo = new MemberVO();	// MemberVO 객체를 생성하여 
				hvo = new HostVO();
			    pvo = new PaymentVO();
				
				//  y.yno,m.name,m.mphone,h.hnick,h.hphone,r.notice,r.rm_name,r.addr1, r.addr2, y.ye_guest,y.ye_checkin,y.ye_checkout,r.price,
			    // p.payno,p.card_name,p.card_num,p.pay_date,p.pay_candate
				yvo.setYno(rs.getString("yno"));
				mvo.setName(rs.getString("name"));			// 예약자
				mvo.setMphone(rs.getString("mphone"));		// 예약자 전화번호
				hvo.setHnick(rs.getString("hnick"));	    // 호스트 닉네임
				hvo.setHphone(rs.getString("hphone"));      // 호스트 전화번호
				rvo.setRmNo(rs.getInt("rm_no"));
				rvo.setNotice(rs.getString("notice"));		// 숙소 공지사항
				rvo.setRmName(rs.getString("rm_name"));  	// 숙소명
				rvo.setAddr1(rs.getString("addr1"));	    // 숙소 위치1
				rvo.setAddr2(rs.getString("addr2"));		// 숙소 위치 2
				rvo.setPrice(rs.getInt("price"));			// 숙소 가격
				yvo.setYeCheckIn(rs.getString("ye_checkin").substring(0,10));// 예약 체크인
				yvo.setYeCheckOut(rs.getString("ye_checkout").substring(0,10));// 예약 체크아웃
				yvo.setYeGuest(rs.getInt("ye_guest"));// 예약 인원수
				pvo.setPayNo(rs.getString("payno"));		// 결제번호
				pvo.setCardName(rs.getString("card_name"));  // 카드이름
				pvo.setCardNum(rs.getString("card_num").replaceAll("(\\d{4})(\\d{4})(\\d{4})(\\d{3})(\\d{1})", "$1********$4*"));    // 카드번호
				pvo.setPayDate(rs.getString("pay_date"));   // 결제일시
				pvo.setPayCanDate(rs.getString("pay_candate"));    // 결제취소 일시
				
				yvo.setMvo(mvo);
				yvo.setRvo(rvo);
				yvo.setHvo(hvo);
				yvo.setPvo(pvo);
	
			}
			}catch (SQLException e) {
	  			e.printStackTrace();
	  		}
		return yvo;
		
	} 
	
	// 호스트 예약 확인/취소 상세 조회
	public YeyakVO hoyeSelect(String yno) {
		YeyakVO yvo = null;
		
		return yvo;
		
	}
	
	// 예약 취소 (예약여부, 취소일시가 추가된다. ///////체크아웃지나고 다시 예약 가능한 상태로)
	public boolean yeUpdate(String payno) {
		try {  
			   query = " UPDATE payment SET pay_candate= SYSDATE WHERE payno=?";
			   
			  // 매개변수로 넘겨받은 데이터를 t_member 테이블에 저장
			   
			   psmt = con.prepareStatement(query);
			
			   psmt.setString(1, payno);
			   
			  
			   int result = psmt.executeUpdate();
			 
			   if(result ==1) {   // 정상적으로 회원가입 성공 시 true 반환
				  return true; }
		  		} catch (SQLException e) {
		  			e.printStackTrace();
		  		}finally {   
		  		 DBConn.close(psmt);
		  		}
		//예약 여부 바꿔주는거랑 취소일시 update됨
		return false;
	} 
	
	
	// 전체 게시물 수
		public int totalCount(String mid) {   
				
				int cnt = 0;
				
				try {
					query = "SELECT COUNT(*) FROM yeyak WHERE mid = ?";
				       
					psmt = con.prepareStatement(query);
					// psmt = con.prepareStatement(query);
				    
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
		
		// 전체 게시물 수
				public int hototalCount(String hid) {   
						
						int cnt = 0;
						
						try {
							query = "SELECT COUNT(*) FROM yeyak WHERE hid = ?";
						       
							psmt = con.prepareStatement(query);
							// psmt = con.prepareStatement(query);
						    
						    psmt.setString(1, hid);
							 
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
