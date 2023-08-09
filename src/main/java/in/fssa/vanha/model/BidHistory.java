package in.fssa.vanha.model;

public class BidHistory extends BidHistoryEntity{

	@Override
	public int compareTo(BidHistoryEntity o) {
		// TODO Auto-generated method stub
		if (o instanceof BidHistory) {
			BidHistory otherBidHistory = (BidHistory) o;
			return Integer.compare(this.getBidId(), otherBidHistory.getBidId());
		}
		return -1;
	}


}
