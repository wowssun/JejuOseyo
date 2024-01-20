package jejuOseyo.vo;

public class RoomVO {
	
	private String hid, rmName, img, img2, img3, memo, notice, addr1, addr2;
	private int rmNo, people, price;
	private boolean available;
	private double rvCnt; //리뷰 개수 
	private double starTotal; //별점의 총 합계


	public RoomVO(String hid, String rmName, String img, String img2, String img3, String memo, String notice,
			String addr1, String addr2, int rmNo, int people, int price, boolean available, double rvCnt,
			double starTotal) {
		super();
		this.hid = hid;
		this.rmName = rmName;
		this.img = img;
		this.img2 = img2;
		this.img3 = img3;
		this.memo = memo;
		this.notice = notice;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.rmNo = rmNo;
		this.people = people;
		this.price = price;
		this.available = available;
		this.rvCnt = rvCnt;
		this.starTotal = starTotal;
	}


	public String getHid() {
		return hid;
	}


	public void setHid(String hid) {
		this.hid = hid;
	}


	public String getRmName() {
		return rmName;
	}


	public void setRmName(String rmName) {
		this.rmName = rmName;
	}


	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}


	public String getImg2() {
		return img2;
	}


	public void setImg2(String img2) {
		this.img2 = img2;
	}


	public String getImg3() {
		return img3;
	}


	public void setImg3(String img3) {
		this.img3 = img3;
	}


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	public String getNotice() {
		return notice;
	}


	public void setNotice(String notice) {
		this.notice = notice;
	}


	public String getAddr1() {
		return addr1;
	}


	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}


	public String getAddr2() {
		return addr2;
	}


	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}


	public int getRmNo() {
		return rmNo;
	}


	public void setRmNo(int rmNo) {
		this.rmNo = rmNo;
	}


	public int getPeople() {
		return people;
	}


	public void setPeople(int people) {
		this.people = people;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public boolean isAvailable() {
		return available;
	}


	public void setAvailable(boolean available) {
		this.available = available;
	}


	public double getRvCnt() {
		return rvCnt;
	}


	public void setRvCnt(double rvCnt) {
		this.rvCnt = rvCnt;
	}


	public double getStarTotal() {
		return starTotal;
	}


	public void setStarTotal(double starTotal) {
		this.starTotal = starTotal;
	}


	public RoomVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	
}
