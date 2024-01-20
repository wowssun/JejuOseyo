package jejuOseyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jejuOseyo.vo.HostVO;
import jejuOseyo.util.DBConn;

public class HostDAO {

	private Connection con;
	private String query;
	private PreparedStatement pstmt;
	private ResultSet rset;

	public HostDAO(Connection con) {
		this.con = con;
	}

	// 호스트회원가입신청
	public boolean insert(HostVO hvo) {
		try {
			// proc_state --> 기본값 지정해놔서 insert 필요없음, proc_date는 나중에 승인/거절때 해야됨
			query = "INSERT INTO host(hid, rep, hpw, hnick, hphone, dnumber, hemail, crnum, photo, reg_date) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, hvo.getHid()); //아이디
			pstmt.setString(2, hvo.getRep()); //대표자명
			pstmt.setString(3, hvo.getHpw()); //비밀번호
			pstmt.setString(4, hvo.getHnick()); //닉네임
			pstmt.setString(5, hvo.getHphone()); //전화번호
			pstmt.setString(6, hvo.getDnumber()); //대표번호->약간 숙소전화번호
			pstmt.setString(7, hvo.getHemail()); //이메일
			pstmt.setString(8, hvo.getCrnum()); //사업자번호
			pstmt.setString(9, hvo.getPhoto()); //사진

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

	// 호스트로그인
	public boolean isHost(String hid, String hpw) {
		String sql = "SELECT * FROM host WHERE hid=? AND hpw=? AND proc_State ='승인'";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, hid);
			pstmt.setString(2, hpw);
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

	// 호스트아이디찾기
	public String hidSearch(String rep, String hphone) {
		String hid = null;
		
		String sql = "SELECT hid FROM host WHERE rep = ? AND hphone = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, rep);
			pstmt.setString(2, hphone);

			rset = pstmt.executeQuery();
			if (rset.next()) {
				
				hid = rset.getString("hid");
		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rset);
		}
		return hid;
	}
	

	// 호스트비밀번호찾기
	public String hpwSearch(String hid, String rep, String hphone) {
		
		String hpw = null;
		
		String sql = "SELECT hpw FROM host WHERE hid =? AND rep = ? AND hphone = ?";
	    try {
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, hid);
	        pstmt.setString(2, rep);
	        pstmt.setString(3, hphone);

	        rset = pstmt.executeQuery();
	        if (rset.next()) {
	        	
	            hpw = rset.getString("hpw");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBConn.close(pstmt, rset);
	    }
	    return hpw;
	}
	

	// 호스트비밀번호재설정
	public boolean hpwUpdate(HostVO hvo) {
		try {
	        query = "UPDATE host SET hpw=? WHERE hid=?";
	        pstmt = con.prepareStatement(query);
	        pstmt.setString(1, hvo.getHpw());
	        pstmt.setString(2, hvo.getHid());
	  
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

	// 마이페이지 --> 내 정보 보기, 호스트 목록 상세 조회
	public HostVO select(String hid) {
		HostVO hvo = null;
        try {
           query = "SELECT hid, rep, hnick, hphone, dnumber, "
           		+ "hemail, crnum, photo, proc_date FROM host WHERE hid = ?";
           
           	pstmt = con.prepareStatement(query); 
             pstmt.setString(1,hid); 
              
              rset= pstmt.executeQuery();  
               
               if (rset.next()==true) { 
            	   hvo= new HostVO();
            	   hvo.setHid(rset.getString("hid")); //아이디
            	   hvo.setRep(rset.getString("rep"));//대표자명
            	   hvo.setHnick(rset.getString("hnick")); //호스트닉네임
            	   hvo.setHphone(rset.getString("hphone")); //전화번호
            	   hvo.setDnumber(rset.getString("dnumber")); //대표번호
            	   hvo.setHemail(rset.getString("hemail")); //이메일
            	   hvo.setCrnum(rset.getString("crnum")); //사업자번호
            	   hvo.setPhoto(rset.getString("photo")); //사진
            	   hvo.setProcDate(rset.getDate("proc_date")); //호스트승인일자

               }
           
        } catch (SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
        }finally {
           DBConn.close(pstmt, rset);
           
        }
        return hvo; 
     }
	

	// 호스트정보수정
	public boolean update(HostVO hvo) {
		try {
			//비밀번호, 전화번호, 대표번호, 이메일만 수정 가능
	        query = "UPDATE host SET  hphone=?, dnumber=?, hemail=? WHERE hid=?";
	        pstmt = con.prepareStatement(query);
	     
	        pstmt.setString(1, hvo.getHphone());
	        pstmt.setString(2, hvo.getDnumber());
	        pstmt.setString(3, hvo.getHemail());
	        pstmt.setString(4, hvo.getHid());
	  
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
	public boolean delete(String hid) {
		try {
			query = "DELETE FROM host where hid = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, hid);

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
	

	// 호스트전체목록
	public List<HostVO> selectAll(double amount, int pageNum, String type, String keyword) {
		HostVO hvo = null;
	       List<HostVO> hostList = new ArrayList<>(); 
	       try {
	    	   query = "SELECT hid, rep, hphone, hemail, crnum, proc_date\n"
	    		        + "FROM (\n"
	    		        + "    SELECT ROWNUM AS rnum, b.*\n"
	    		        + "    FROM (\n"
	    		        + "        SELECT *\n"
	    		        + "        FROM host\n"
	    		        + "        WHERE PROC_STATE = '승인'\n";

	    		if (!type.equals("") && !keyword.equals("")) {
	    		    query += "        AND " + type + " LIKE '%" + keyword + "%'\n";
	    		}

	    		query += "        ORDER BY proc_date DESC\n"
	    		        + "    ) b\n"
	    		        + "    WHERE ROWNUM <= ? * ?\n"
	    		        + ")\n"
	    		        + "WHERE rnum > ? * ?";

		
	          
	          pstmt = con.prepareStatement(query);
	          pstmt.setDouble(1, amount);
	          pstmt.setInt(2, pageNum);
	          pstmt.setDouble(3, amount);
	          pstmt.setInt(4, pageNum-1);
	          
	          
	          rset= pstmt.executeQuery();  
	          

	           while (rset.next()==true) { 
	        	   hvo= new HostVO(); 
	        	   hvo.setHid(rset.getString("hid")); //아이디
	        	   hvo.setRep(rset.getString("rep")); //대표자명
	        	   hvo.setHphone(rset.getString("hphone")); //전화번호
	        	   hvo.setHemail(rset.getString("hemail")); //이메일
	        	   hvo.setCrnum(rset.getString("crnum")); //사업자등록번호
	        	   hvo.setProcDate(rset.getDate("proc_date")); //승인일자
	         	 
	        	  hostList.add(hvo);// 값 저장 후 list 객체에 추가
	              }
	          
	       } catch (SQLException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	       }finally {
	          DBConn.close(pstmt, rset);
	       }
	    	
	    	return hostList;
	    }
	
	
	//전체회원수
    public int totalCount(String type, String keyword) {
        int cnt = 0;

        try {
           query = "SELECT COUNT(*) FROM host WHERE proc_State = '승인'";
           if(!type.equals("")&& !keyword.equals("")) {
     			query += " AND " + type + " LIKE '%" + keyword + "%' ";
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
	    
	    

	// 호스트회원가입신청목록
	public List<HostVO> hostRQselectAll(double amount, int pageNum, String type, String keyword) {
		HostVO hvo = null;
	       List<HostVO> hostRQList = new ArrayList<>(); 
	       try {
	    	   query = "SELECT hid, rep, hphone, hemail, crnum, proc_state, reg_date\n"
	    	   		+ "FROM (\n"
	    	   		+ "    SELECT ROWNUM AS rnum, b.*\n"
	    	   		+ "    FROM (\n"
	    	   		+ "        SELECT *\n"
	    	   		+ "        FROM host\n"
	    	   		+ "        WHERE PROC_STATE = '대기'\n";
	    	   if (!type.equals("") && !keyword.equals("")) {
	    		    query += "        AND " + type + " LIKE '%" + keyword + "%'\n";
	    		}
	    				query += "        ORDER BY reg_date DESC\n"
	    	   		+ "    ) b\n"
	    	   		+ "    WHERE ROWNUM <= ? * ?\n"
	    	   		+ ")\n"
	    	   		+ "WHERE rnum > ? * ?";
	          
	          pstmt = con.prepareStatement(query);
	          pstmt.setDouble(1, amount);
	          pstmt.setInt(2, pageNum);
	          pstmt.setDouble(3, amount);
	          pstmt.setInt(4, pageNum-1);
	          
	          
	          rset= pstmt.executeQuery();  
	          

	           while (rset.next()==true) { 
	        	   hvo= new HostVO(); 
	        	   hvo.setHid(rset.getString("hid")); //아이디
	        	   hvo.setRep(rset.getString("rep")); //대표자명
	        	   hvo.setHphone(rset.getString("hphone")); //전화번호
	        	   hvo.setHemail(rset.getString("hemail")); //이메일
	        	   hvo.setCrnum(rset.getString("crnum")); //사업자등록번호
	        	   hvo.setRegDate(rset.getDate("reg_date")); //가입신청일자
	        	   hvo.setProcState(rset.getString("proc_state")); //처리상태
	         	 
	        	   hostRQList.add(hvo);// 값 저장 후 list 객체에 추가
	              }
	          
	       } catch (SQLException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	       }finally {
	          DBConn.close(pstmt, rset);
	       }
	    	
	    	return hostRQList;
	    }
	
	//호스트신청수
    public int hRQtotalCount(String type, String keyword) {
        int cnt = 0;

        try {
           query = "SELECT COUNT(*) FROM host WHERE proc_State = '대기' ";
           if(!type.equals("")&& !keyword.equals("")) {
     			query += " AND " + type + " LIKE '%" + keyword + "%' ";
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



	// 호스트회원가입신청상세조회
	public HostVO hostRQselect(String hid) {
		HostVO hvo = null;
        try {
           query = "SELECT hid, rep, hpw, hnick, hphone, dnumber, "
           		+ "hemail, crnum, photo, reg_date FROM host WHERE hid = ?";
           
           	pstmt = con.prepareStatement(query); 
             pstmt.setString(1,hid); 
              
              rset= pstmt.executeQuery();  
               
               if (rset.next()==true) { 
            	   hvo= new HostVO();
            	   hvo.setHid(rset.getString("hid")); //아이디
            	   hvo.setRep(rset.getString("rep"));		//대표자명
            	   hvo.setHpw(rset.getString("hpw")); //비밀번호
            	   hvo.setHnick(rset.getString("hnick")); //호스트닉네임
            	   hvo.setHphone(rset.getString("hphone")); //전화번호
            	   hvo.setDnumber(rset.getString("dnumber")); //대표번호
            	   hvo.setHemail(rset.getString("hemail")); //이메일
            	   hvo.setCrnum(rset.getString("crnum")); //사업자번호
            	   hvo.setPhoto(rset.getString("photo")); //사진
            	   hvo.setRegDate(rset.getDate("reg_date")); //호스트신청일자

               }
           
        } catch (SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
        }finally {
           DBConn.close(pstmt, rset); //pstmt, rset close 호출
           
        }
        return hvo; 
     }

	// 호스트회원가입신청 승인/거절
	public boolean hostRQupdate(HostVO hvo) {
		
		try {

	        query  = "UPDATE host "
                    + "SET PROC_STATE = ?, PROC_DATE = SYSDATE "
                    + "WHERE HID = ? AND PROC_STATE = '대기'";
	        pstmt = con.prepareStatement(query);
	        
	        pstmt.setString(1, hvo.getProcState());
	        pstmt.setString(2, hvo.getHid());
	        
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
	


	// 호스트거절내역
	public List<HostVO> hostRJselect(double amount, int pageNum, String type, String keyword) {
		HostVO hvo = null;
	       List<HostVO> hostRJList = new ArrayList<>(); 
	       try {
	    	   query = "SELECT hid, rep, hphone, hemail, crnum, proc_date\n"
	    		        + "FROM (\n"
	    		        + "    SELECT ROWNUM AS rnum, b.*\n"
	    		        + "    FROM (\n"
	    		        + "        SELECT *\n"
	    		        + "        FROM host\n"
	    		        + "        WHERE PROC_STATE = '거절'\n";

	    		if (!type.equals("") && !keyword.equals("")) {
	    		    query += "        AND " + type + " LIKE '%" + keyword + "%'\n";
	    		}

	    		query += "        ORDER BY proc_date DESC\n"
	    		        + "    ) b\n"
	    		        + "    WHERE ROWNUM <= ? * ?\n"
	    		        + ")\n"
	    		        + "WHERE rnum > ? * ?";
			
	          
	          pstmt = con.prepareStatement(query);
	          pstmt.setDouble(1, amount);
	          pstmt.setInt(2, pageNum);
	          pstmt.setDouble(3, amount);
	          pstmt.setInt(4, pageNum-1);
	          
	          
	          rset= pstmt.executeQuery();  
	          

	           while (rset.next()==true) { 
	        	   hvo= new HostVO(); 
	        	   hvo.setHid(rset.getString("hid")); //아이디
	        	   hvo.setRep(rset.getString("rep")); //대표자명
	        	   hvo.setHphone(rset.getString("hphone")); //전화번호
	        	   hvo.setHemail(rset.getString("hemail")); //이메일
	        	   hvo.setCrnum(rset.getString("crnum")); //사업자등록번호
	        	   hvo.setProcDate(rset.getDate("proc_date")); //거절일자
	         	 
	        	   hostRJList.add(hvo);// 값 저장 후 list 객체에 추가
	              }
	          
	       } catch (SQLException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	       }finally {
	          DBConn.close(pstmt, rset);
	       }
	    	
	    	return hostRJList;
	    }
	
	
	
	//호스트거절수
    public int hJtotalCount(String type, String keyword) {
        int cnt = 0;

        try {
           query = "SELECT COUNT(*) FROM host WHERE proc_State = '거절' ";
           if(!type.equals("")&& !keyword.equals("")) {
     			query += " AND " + type + " LIKE '%" + keyword + "%' ";
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

}// DAO END
