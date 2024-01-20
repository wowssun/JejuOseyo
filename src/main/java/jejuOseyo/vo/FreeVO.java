package jejuOseyo.vo;

import java.sql.Blob;
import java.util.Date;

public class FreeVO {
	private int freeNo; //게시물 번호
	private String mid; //작성자
	private String freeTitle; //제목
	private String freeContent; //내용
	private Blob freeImage; //이미지
	private Date regDate; //작성일
	private int freeHit;
	private String ip;
	
	public FreeVO() {
			
	}

	public FreeVO(int freeNo, String mid, String freeTitle, String freeContent, Blob freeImage, Date regDate,
			int freeHit, String ip) {
		super();
		this.freeNo = freeNo;
		this.mid = mid;
		this.freeTitle = freeTitle;
		this.freeContent = freeContent;
		this.freeImage = freeImage;
		this.regDate = regDate;
		this.freeHit = freeHit;
		this.ip = ip;
	}

	public int getFreeNo() {
		return freeNo;
	}

	public void setFreeNo(int freeNo) {
		this.freeNo = freeNo;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getFreeTitle() {
		return freeTitle;
	}

	public void setFreeTitle(String freeTitle) {
		this.freeTitle = freeTitle;
	}

	public String getFreeContent() {
		return freeContent;
	}

	public void setFreeContent(String freeContent) {
		this.freeContent = freeContent;
	}

	public Blob getFreeImage() {
		return freeImage;
	}

	public void setFreeImage(Blob freeImage) {
		this.freeImage = freeImage;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getFreeHit() {
		return freeHit;
	}

	public void setFreeHit(int freeHit) {
		this.freeHit = freeHit;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
