package jejuOseyo.vo;

import java.util.Date;

public class MemberVO {

	private String name; // 이름
	private String mid; // 아이디
	private String mpw; // 비밀번호
	private String mnick; // 닉네임
	private String mphone; // 전화번호
	private String memail; //이메일
	private Date regDate; // 가입일자

	public MemberVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberVO(String name, String mid, String mpw, String mnick, String mphone, String memail,
			Date regDate) {
		super();
		this.name = name;
		this.mid = mid;
		this.mpw = mpw;
		this.mnick = mnick;
		this.mphone = mphone;
		this.memail = memail;
		this.regDate = regDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMpw() {
		return mpw;
	}

	public void setMpw(String mpw) {
		this.mpw = mpw;
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


	public String getMemail() {
		return memail;
	}

	public void setMemail(String memail) {
		this.memail = memail;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}



	
	


}
