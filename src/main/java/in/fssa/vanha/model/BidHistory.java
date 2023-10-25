package in.fssa.vanha.model;

public class BidHistory extends BidHistoryEntity {

	/**
	 * Compares this BidHistoryEntity to another BidHistoryEntity based on their bid
	 * IDs.
	 * 
	 * @param o The BidHistoryEntity to compare to.
	 * @return A negative integer, zero, or a positive integer if this
	 *         BidHistoryEntity is less than, equal to, or greater than the
	 *         specified BidHistoryEntity based on their bid IDs.
	 * @throws ClassCastException if the argument is not an instance of BidHistory.
	 */
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
