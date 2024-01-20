package jejuOseyo.vo;


public class CartVO {
	private int cno;   					// 장바구니 아이디
	private String checkIn;       		// 체크인 날짜
	private String checkOut;      		// 체크아웃 날짜
	private int guest;					// 예약 인원수
	private MemberVO mvo;				// 회원
	private RoomVO rvo;					// 숙소
	
	
	public CartVO() {
		
	}


	public int getCno() {
		return cno;
	}


	public void setCno(int cno) {
		this.cno = cno;
	}


	public String getCheckIn() {
		return checkIn;
	}


	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}


	public String getCheckOut() {
		return checkOut;
	}


	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}


	public MemberVO getMvo() {
		return mvo;
	}


	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}


	public RoomVO getRvo() {
		return rvo;
	}


	public void setRvo(RoomVO rvo) {
		this.rvo = rvo;
	}


	public int getGuest() {
		return guest;
	}


	public void setGuest(int guest) {
		this.guest = guest;
	}
	
	

}
