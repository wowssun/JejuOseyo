package jejuOseyo.vo;

import java.util.Date;

public class HostVO {
	
	private String rep;			//대표자명
	private String hid;			//아이디
	private String hpw;			//비밀번호
	private String hnick;		//닉네임
	private String hphone;		//전화번호
	private String dnumber;		//대표번호
	private String hemail; 		//이메일
	private String crnum;		//사업자번호
	private String photo;		//사업자등록증사본
	private Date regDate;		//호스트회원가입신청일자
	private String procState; 	//호스트승인/거절상태
	private Date procDate;		//호스트승인/거절일자
	
	public HostVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HostVO(String rep, String hid, String hpw, String hnick, String hphone, String dnumber,
			String hemail, String crnum, String photo, Date regDate, String procState, Date procDate) {
		super();
		this.rep = rep;
		this.hid = hid;
		this.hpw = hpw;
		this.hnick = hnick;
		this.hphone = hphone;
		this.dnumber = dnumber;
		this.hemail = hemail;
		this.crnum = crnum;
		this.photo = photo;
		this.regDate = regDate;
		this.procState = procState;
		this.procDate = procDate;
	}

	public String getRep() {
		return rep;
	}

	public void setRep(String rep) {
		this.rep = rep;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getHpw() {
		return hpw;
	}

	public void setHpw(String hpw) {
		this.hpw = hpw;
	}

	public String getHnick() {
		return hnick;
	}

	public void setHnick(String hnick) {
		this.hnick = hnick;
	}

	public String getHphone() {
		return hphone;
	}

	public void setHphone(String hphone) {
		this.hphone = hphone;
	}

	public String getDnumber() {
		return dnumber;
	}

	public void setDnumber(String dnumber) {
		this.dnumber = dnumber;
	}


	public String getHemail() {
		return hemail;
	}

	public void setHemail(String hemail) {
		this.hemail = hemail;
	}

	public String getCrnum() {
		return crnum;
	}

	public void setCrnum(String crnum) {
		this.crnum = crnum;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getProcState() {
		return procState;
	}

	public void setProcState(String procState) {
		this.procState = procState;
	}

	public Date getProcDate() {
		return procDate;
	}

	public void setProcDate(Date procDate) {
		this.procDate = procDate;
	}

	
	
	
}
