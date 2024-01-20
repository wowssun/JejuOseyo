package jejuOseyo.vo;

public class RggVO {
	private int rggNo, rmNo;
	private String mid;
	private RoomVO rmvo;
	private String checkIn;       		// 체크인 날짜
	private String checkOut;      		// 체크아웃 날짜
	private int people;	
	
	public RggVO(int rggNo, int rmNo, String mid, RoomVO rmvo, String checkIn, String checkOut, int people) {
		super();
		this.rggNo = rggNo;
		this.rmNo = rmNo;
		this.mid = mid;
		this.rmvo = rmvo;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.people = people;
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
	public int getPeople() {
		return people;
	}
	public void setPeople(int people) {
		this.people = people;
	}
	public RoomVO getRmvo() {
		return rmvo;
	}
	public void setRmvo(RoomVO rmvo) {
		this.rmvo = rmvo;
	}
	public int getRggNo() {
		return rggNo;
	}
	public void setRggNo(int rggNo) {
		this.rggNo = rggNo;
	}
	public int getRmNo() {
		return rmNo;
	}
	public void setRmNo(int rmNo) {
		this.rmNo = rmNo;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	
	public RggVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
