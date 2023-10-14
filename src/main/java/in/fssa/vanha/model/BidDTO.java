package in.fssa.vanha.model;

public class BidDTO {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerImage() {
		return buyerImage;
	}

	public void setBuyerImage(String buyerImage) {
		this.buyerImage = buyerImage;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public int getListNo() {
		return listNo;
	}

	public void setListNo(int listNo) {
		this.listNo = listNo;
	}

	@Override
	public String toString() {
		return "BidDTO [id=" + id + ", amount=" + amount + ", buyerName=" + buyerName + ", buyerImage=" + buyerImage
				+ ", buyerEmail=" + buyerEmail + ", date=" + date + ", listNo=" + listNo + "]";
	}

	int id;
	int amount;
	String buyerName;
	String buyerImage;
	String buyerEmail;
	String date;
	int listNo;
}
