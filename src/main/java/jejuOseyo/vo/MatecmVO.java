package jejuOseyo.vo;

import java.util.Date;

public class MatecmVO {
	private int mno;  //게시글 번호_MateVO 통일
	private String mnick;  //회원 닉네임_MemberVO 통일
	private Date mcmdate;  //댓글 작성일자
	private String mcmtext;  //댓글 내용
	private int mcmno;  //댓글 번호
	
	
	public MatecmVO() {
		super();
	}

	public MatecmVO(int mno, String mnick, Date mcmdate, String mcmtext, int mcmno) {
		super();
		this.mno = mno;
		this.mnick = mnick;
		this.mcmdate = mcmdate;
		this.mcmtext = mcmtext;
		this.mcmno = mcmno;
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
	public Date getMcmdate() {
		return mcmdate;
	}
	public void setMcmdate(Date mcmdate) {
		this.mcmdate = mcmdate;
	}
	public String getMcmtext() {
		return mcmtext;
	}
	public void setMcmtext(String mcmtext) {
		this.mcmtext = mcmtext;
	}
	public int getMcmno() {
		return mcmno;
	}
	public void setMcmno(int mcmno) {
		this.mcmno = mcmno;
	}
	
	
	
	
}
