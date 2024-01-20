package jejuOseyo.vo;

public class PaymentVO {

	private String payNo;
	private String cardName;
	private String cardNum;
	private int amount;
	private String payDate;
	private String payCanDate;
	
	public PaymentVO() {
	
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getPayCanDate() {
		return payCanDate;
	}

	public void setPayCanDate(String payCanDate) {
		this.payCanDate = payCanDate;
	}
	
	
	
	
}
