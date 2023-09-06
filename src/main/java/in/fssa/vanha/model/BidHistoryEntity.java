package in.fssa.vanha.model;

public abstract class BidHistoryEntity implements Comparable<BidHistoryEntity> {
	public int getBidId() {
		return bidId;
	}

	public void setBidId(int bidId) {
		this.bidId = bidId;
	}

	public int getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(int bidAmount) {
		this.bidAmount = bidAmount;
	}

	public String getBidDate() {
		return bidDate;
	}

	public void setBidDate(String bidDate) {
		this.bidDate = bidDate;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productd) {
		this.productId = productd;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	int bidId;
	int bidAmount;
	String bidDate;
	int buyerId;
	int productId;
	boolean status;

}
