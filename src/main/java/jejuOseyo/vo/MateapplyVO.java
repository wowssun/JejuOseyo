package jejuOseyo.vo;

public class MateapplyVO {
	private int ano;  //목록 번호
	private int mno;  //게시글 번호_MateVO 통일
	private String mnick;  //신청자 닉네임_MemberVO 통일
	private String mphone;	//신청자 전화번호__MemberVO 통일
	private String mnow;  //신청내역 상태값
	
	
	public MateapplyVO() {
		super();
	}

	public MateapplyVO(int ano, int mno, String mnick, String mphone, String mnow) {
		super();
		this.ano = ano;
		this.mno = mno;
		this.mnick = mnick;
		this.mphone = mphone;
		this.mnow = mnow;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMno() {
		return mno;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	public String getMnick() {
		return mnick;
	}

	public void setMnick(String mnick) {
		this.mnick = mnick;
	}

	public String getMphone() {
		return mphone;
	}

	public void setMphone(String mphone) {
		this.mphone = mphone;
	}

	public String getMnow() {
		return mnow;
	}

	public void setMnow(String mnow) {
		this.mnow = mnow;
	}
	
	
	
}
