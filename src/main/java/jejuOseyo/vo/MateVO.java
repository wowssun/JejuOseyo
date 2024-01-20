package jejuOseyo.vo;

import java.sql.Date;

public class MateVO {
	private int mno;  //게시글 번호
	private String mtitle;  //게시글 제목
	private String mnick;  //게시글(회원) 닉네임_MemberVO 통일
	private int mhit;  //게시글 조회수
	private String depart;  //여행 출발일
	private String fin;  //여행 마지막일
	private String mplace;  //여행 장소
	private int mnum;  //참가인원
	private String mtext;  //게시글 내용
	private Date mdate;  //게시글 작성일자
	
	
	public MateVO() {
		super();
	}


	public MateVO(int mno, String mtitle, String mnick, int mhit, String depart, String fin, String mplace, int mnum,
			String mtext, Date mdate) {
		super();
		this.mno = mno;
		this.mtitle = mtitle;
		this.mnick = mnick;
		this.mhit = mhit;
		this.depart = depart;
		this.fin = fin;
		this.mplace = mplace;
		this.mnum = mnum;
		this.mtext = mtext;
		this.mdate = mdate;
	}


	public int getMno() {
		return mno;
	}


	public void setMno(int mno) {
		this.mno = mno;
	}


	public String getMtitle() {
		return mtitle;
	}


	public void setMtitle(String mtitle) {
		this.mtitle = mtitle;
	}


	public String getMnick() {
		return mnick;
	}


	public void setMnick(String mnick) {
		this.mnick = mnick;
	}


	public int getMhit() {
		return mhit;
	}


	public void setMhit(int mhit) {
		this.mhit = mhit;
	}


	public String getDepart() {
		return depart;
	}


	public void setDepart(String depart) {
		this.depart = depart;
	}


	public String getFin() {
		return fin;
	}


	public void setFin(String fin) {
		this.fin = fin;
	}


	public String getMplace() {
		return mplace;
	}


	public void setMplace(String mplace) {
		this.mplace = mplace;
	}


	public int getMnum() {
		return mnum;
	}


	public void setMnum(int mnum) {
		this.mnum = mnum;
	}


	public String getMtext() {
		return mtext;
	}


	public void setMtext(String mtext) {
		this.mtext = mtext;
	}


	public Date getMdate() {
		return mdate;
	}


	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}


	

}
