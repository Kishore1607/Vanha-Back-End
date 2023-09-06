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

	public String getBuyerImage() {
		return buyerImage;
	}

	public void setBuyerImage(String buyerImage) {
		this.buyerImage = buyerImage;
	}
	
	public Long getBuyerNumber() {
		return buyerNumber;
	}
	public void setBuyerNumber(Long buyerNumber) {
		this.buyerNumber = buyerNumber;
	}

	@Override
	public String toString() {
		return "BidDTO [id=" + id + ", amount=" + amount + ", buyerName=" + buyerName + ", buyerImage=" + buyerImage
				+ "]";
	}

	int id;
	int amount;
	String buyerName;
	String buyerImage;
	Long buyerNumber;


}
