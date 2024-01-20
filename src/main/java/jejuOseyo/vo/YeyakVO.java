package jejuOseyo.vo;


public class YeyakVO {

	private String yno;    			 //예약번호
	private MemberVO mvo;			 // 회원
	private RoomVO rvo;			     // 숙소
	private HostVO hvo;				 // 호스트
	private CartVO cvo;				 // 장바구니
	private PaymentVO pvo;           // 결제번호
	private String yeCheckIn;
	private String yeCheckOut;
	private int yeGuest;

	
	
	public YeyakVO() {
	
	}


	public String getYno() {
		return yno;
	}


	public void setYno(String yno) {
		this.yno = yno;
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


	public HostVO getHvo() {
		return hvo;
	}


	public void setHvo(HostVO hvo) {
		this.hvo = hvo;
	}


	public CartVO getCvo() {
		return cvo;
	}


	public void setCvo(CartVO cvo) {
		this.cvo = cvo;
	}


	public String getYeCheckIn() {
		return yeCheckIn;
	}


	public void setYeCheckIn(String yeCheckIn) {
		this.yeCheckIn = yeCheckIn;
	}


	public String getYeCheckOut() {
		return yeCheckOut;
	}


	public void setYeCheckOut(String yeCheckOut) {
		this.yeCheckOut = yeCheckOut;
	}


	public int getYeGuest() {
		return yeGuest;
	}


	public void setYeGuest(int yeGuest) {
		this.yeGuest = yeGuest;
	}


	public PaymentVO getPvo() {
		return pvo;
	}


	public void setPvo(PaymentVO pvo) {
		this.pvo = pvo;
	}



	

	
}
