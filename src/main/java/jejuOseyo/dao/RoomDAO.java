package jejuOseyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jejuOseyo.vo.RoomVO;

public class RoomDAO {
	private Connection con;
	private String query; // 쿼리문 저장 필드
	private PreparedStatement pstmt;
	private ResultSet rs; // select 쿼리 저장할 필드

	public RoomDAO(Connection con) {
		this.con = con;
	}

	public boolean insert(RoomVO rmvo) {
		try {
			String query = "INSERT INTO room VALUES (SEQ_ROOM_RM_NO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, 0, 0,?,?,?,?)";

			pstmt = con.prepareStatement(query);

			pstmt.setString(1, rmvo.getHid());
			pstmt.setString(2, rmvo.getRmName());
			pstmt.setInt(3, rmvo.getPeople());
			pstmt.setInt(4, rmvo.getPrice());
			pstmt.setString(5, rmvo.getImg());
			pstmt.setString(6, rmvo.getMemo());
			pstmt.setString(7, rmvo.getNotice());
			pstmt.setString(8, rmvo.getAddr1());
			pstmt.setString(9, rmvo.getAddr2());
			pstmt.setString(10, rmvo.getImg2());
			pstmt.setString(11, rmvo.getImg3());

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public List<RoomVO> search(double amount, int pageNum, String keyword, int people, Date checkIn, Date checkOut) {
		List<RoomVO> availableRooms = new ArrayList<>();

		try {
			query = " SELECT * "
					+ "FROM    (SELECT ROWNUM AS rnum, b.* "
					+ "         FROM(";
			query += "SELECT rm.* FROM room rm " + "WHERE (rm.addr1 LIKE ? OR rm.rm_name LIKE ?) "
					+ "AND rm.people >= ? " + "AND NOT EXISTS (" + "    SELECT 1 FROM yeyak y "
					+ "    WHERE y.rm_no = rm.rm_no " + "    AND (" + "        y.ye_checkin BETWEEN ? AND ? "
					+ "        OR " + "        y.ye_checkout BETWEEN ? AND ?" + "  )" + ") ORDER BY rm_name) b   WHERE  ROWNUM <= ? * ? ) "
							+  "WHERE  rnum > ? * ?" ;

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setInt(3, people);
			pstmt.setDate(4, new java.sql.Date(checkIn.getTime()));
			pstmt.setDate(5, new java.sql.Date(checkOut.getTime()));
			pstmt.setDate(6, new java.sql.Date(checkIn.getTime()));
			pstmt.setDate(7, new java.sql.Date(checkOut.getTime()));
			pstmt.setDouble(8, amount);
			pstmt.setInt(9, pageNum);
			pstmt.setDouble(10, amount);
			pstmt.setInt(11, (pageNum - 1));

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				RoomVO rmvo = new RoomVO();
				rmvo.setRmNo(rs.getInt("rm_no"));
				rmvo.setImg(rs.getString("img"));
				rmvo.setRmName(rs.getString("rm_name"));
				rmvo.setHid(rs.getString("hid"));
				rmvo.setPrice(rs.getInt("price"));
				rmvo.setStarTotal(rs.getDouble("star_total"));
				rmvo.setRvCnt(rs.getDouble("rv_cnt"));

				availableRooms.add(rmvo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return availableRooms;
	}
	public List<RoomVO> search2(double amount, int pageNum, String keyword, int people, Date checkIn, Date checkOut) {
		List<RoomVO> availableRooms = new ArrayList<>();

		try {
			query = " SELECT * "
					+ "FROM    (SELECT ROWNUM AS rnum, b.* "
					+ "         FROM(";
			query += "SELECT rm.* FROM room rm " + "WHERE (rm.addr1 LIKE ? OR rm.rm_name LIKE ?) "
					+ "AND rm.people >= ? " + "AND NOT EXISTS (" + "    SELECT 1 FROM yeyak y "
					+ "    WHERE y.rm_no = rm.rm_no " + "    AND (" + "        y.ye_checkin BETWEEN ? AND ? "
					+ "        OR " + "        y.ye_checkout BETWEEN ? AND ?" + "  )" + ") ORDER BY CASE WHEN rv_cnt = 0 THEN 1 ELSE 0 END, star_total / NULLIF(rv_cnt, 0) DESC) b   WHERE  ROWNUM <= ? * ? ) "
							+  "WHERE  rnum > ? * ?" ;

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setInt(3, people);
			pstmt.setDate(4, new java.sql.Date(checkIn.getTime()));
			pstmt.setDate(5, new java.sql.Date(checkOut.getTime()));
			pstmt.setDate(6, new java.sql.Date(checkIn.getTime()));
			pstmt.setDate(7, new java.sql.Date(checkOut.getTime()));
			pstmt.setDouble(8, amount);
			pstmt.setInt(9, pageNum);
			pstmt.setDouble(10, amount);
			pstmt.setInt(11, (pageNum - 1));

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				RoomVO rmvo = new RoomVO();
				rmvo.setRmNo(rs.getInt("rm_no"));
				rmvo.setImg(rs.getString("img"));
				rmvo.setRmName(rs.getString("rm_name"));
				rmvo.setHid(rs.getString("hid"));
				rmvo.setPrice(rs.getInt("price"));
				rmvo.setStarTotal(rs.getDouble("star_total"));
				rmvo.setRvCnt(rs.getDouble("rv_cnt"));

				availableRooms.add(rmvo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return availableRooms;
	}

	public List<RoomVO> myRoom(double amount, int pageNum, String hid) { // 나의 숙소 목록
		List<RoomVO> mylist = new ArrayList<>();
		RoomVO rmvo = null;
		try {
			query = "SELECT * " + "FROM (SELECT ROWNUM AS rnum, b.* "
					+ "      FROM (SELECT * FROM room WHERE hid = ? ORDER BY rm_name) b "
					+ "      WHERE ROWNUM <= ? * ?) " + "WHERE rnum > ? * ? ";

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, hid);
			pstmt.setDouble(2, amount);
			pstmt.setInt(3, pageNum);
			pstmt.setDouble(4, amount);
			pstmt.setInt(5, (pageNum - 1));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rmvo = new RoomVO();
				rmvo.setRmNo(rs.getInt("rm_no"));
				rmvo.setImg(rs.getString("img"));
				rmvo.setRmName(rs.getString("rm_name"));
				rmvo.setHid(rs.getString("hid"));
				rmvo.setPrice(rs.getInt("price"));
				rmvo.setStarTotal(rs.getDouble("star_total"));
				rmvo.setRvCnt(rs.getDouble("rv_cnt"));

				mylist.add(rmvo); // list 객체에 값 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mylist;

	}

	public List<RoomVO> selectAll(double amount, int pageNum, String keyword) { // 전체 숙소 목록
		List<RoomVO> allList = new ArrayList<>();
		RoomVO rmvo = null;
		try {
			query = " SELECT * " + "FROM    (SELECT ROWNUM AS rnum, b.* " + "         FROM   (SELECT * FROM room ";
			if (keyword != null && !keyword.equals("")) {
				query += " WHERE addr1 LIKE '%" + keyword + "%' OR rm_name LIKE '%" + keyword + "%' ";
			}
			query += "                  ORDER BY rm_name) b " + "         WHERE  ROWNUM <= ? * ? ) "
					+ "WHERE  rnum > ? * ? ";

			pstmt = con.prepareStatement(query);
			pstmt.setDouble(1, amount);
			pstmt.setInt(2, pageNum);
			pstmt.setDouble(3, amount);
			pstmt.setInt(4, (pageNum - 1));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rmvo = new RoomVO();
				rmvo.setRmNo(rs.getInt("rm_no"));
				rmvo.setImg(rs.getString("img"));
				rmvo.setRmName(rs.getString("rm_name"));
				rmvo.setPrice(rs.getInt("price"));
				rmvo.setStarTotal(rs.getDouble("star_total"));
				rmvo.setRvCnt(rs.getDouble("rv_cnt"));

				allList.add(rmvo); // list 객체에 값 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allList;

	}

	public RoomVO select(int rmNo) { // 숙소 상세보기
		RoomVO rmvo = null;
		try {
			query = "SELECT * FROM ROOM WHERE rm_no = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, rmNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				rmvo = new RoomVO();
				rmvo.setRmName(rs.getString("rm_name"));
				rmvo.setHid(rs.getString("hid"));
				rmvo.setAddr1(rs.getString("addr1"));
				rmvo.setAddr2(rs.getString("addr2"));
				rmvo.setImg(rs.getString("img"));
				rmvo.setImg2(rs.getString("img2"));
				rmvo.setImg3(rs.getString("img3"));
				rmvo.setPeople(rs.getInt("people"));
				rmvo.setPrice(rs.getInt("price"));
				rmvo.setImg(rs.getString("img"));
				rmvo.setMemo(rs.getString("memo"));
				rmvo.setNotice(rs.getString("notice"));
				rmvo.setStarTotal(rs.getDouble("star_total"));
				rmvo.setRvCnt(rs.getDouble("rv_cnt"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rmvo;
	}

	public boolean update(RoomVO rmvo) {// 숙소 수정
		try {
			String query = "UPDATE room SET rm_name=?, people=?, price=?, img=?, memo=?, notice=?, addr1=?, addr2=?, img2=?, img3=? WHERE rm_no=?";

			pstmt = con.prepareStatement(query);

			pstmt.setString(1, rmvo.getRmName());
			pstmt.setInt(2, rmvo.getPeople());
			pstmt.setInt(3, rmvo.getPrice());
			pstmt.setString(4, rmvo.getImg());
			pstmt.setString(5, rmvo.getMemo());
			pstmt.setString(6, rmvo.getNotice());
			pstmt.setString(7, rmvo.getAddr1());
			pstmt.setString(8, rmvo.getAddr2());
			pstmt.setString(9, rmvo.getImg2());
			pstmt.setString(10, rmvo.getImg3());
			pstmt.setInt(11, rmvo.getRmNo());

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(int rmNo) { // 숙소 삭제
		try {
			query = "DELETE FROM room where rm_no = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, rmNo);

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// 나의 숙소 목록의 전체 게시물 수
	public int myCount(String hid) {
		int cnt = 0;
		try {
			query = " SELECT COUNT(*) FROM room where hid=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, hid);

			rs = pstmt.executeQuery();
			if (rs.next() == true) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	// 전체 숙소 목록의 전체 게시물 수
	public int totalCount(String keyword) {
		int cnt = 0;
		try {
			query = " SELECT COUNT(*) FROM room ";

			if (keyword != null && !keyword.equals("")) {
				query += " WHERE addr1 LIKE '%" + keyword + "%' OR rm_name LIKE '%" + keyword + "%' ";

			}
			pstmt = con.prepareStatement(query);

			rs = pstmt.executeQuery();
			if (rs.next() == true) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	// 검색 숙소 목록의 전체 게시물 수
	public int searchCount(String keyword, int people, Date checkIn, Date checkOut) {
		int cnt = 0;
		try {
			String query = "SELECT count(*) FROM room rm " + "WHERE (rm.addr1 LIKE ? OR rm.rm_name LIKE ?) "
					+ "AND rm.people >= ? " + "AND NOT EXISTS (" + "    SELECT 1 FROM yeyak y "
					+ "    WHERE y.rm_no = rm.rm_no " + "    AND (" + "        y.ye_checkin BETWEEN ? AND ? "
					+ "        OR " + "        y.ye_checkout BETWEEN ? AND ?" + "    )" + ")";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setInt(3, people);
			pstmt.setDate(4, new java.sql.Date(checkIn.getTime()));
			pstmt.setDate(5, new java.sql.Date(checkOut.getTime()));
			pstmt.setDate(6, new java.sql.Date(checkIn.getTime()));
			pstmt.setDate(7, new java.sql.Date(checkOut.getTime()));

			rs = pstmt.executeQuery();
			if (rs.next() == true) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
}