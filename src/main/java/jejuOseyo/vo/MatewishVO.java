package jejuOseyo.vo;

public class MatewishVO {
	private int mwno;  //위시리스트 번호
	private String mno;  //게시글 번호_MateVO 통일
	private String mid;  //회원 아이디_MemberVO 통일
	
	
	public MatewishVO() {
		super();
	}


	public MatewishVO(int mwno, String mno, String mid) {
		super();
		this.mwno = mwno;
		this.mno = mno;
		this.mid = mid;
	}


	public int getMwno() {
		return mwno;
	}


	public void setMwno(int mwno) {
		this.mwno = mwno;
	}


	public String getMno() {
		return mno;
	}


	public void setMno(String mno) {
		this.mno = mno;
	}


	public String getMid() {
		return mid;
	}


	public void setMid(String mid) {
		this.mid = mid;
	}
	
	
	
	
}
