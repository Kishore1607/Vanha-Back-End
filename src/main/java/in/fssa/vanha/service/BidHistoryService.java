package in.fssa.vanha.service;

import java.util.Set;

import in.fssa.vanha.dao.BidHistoryDAO;
import in.fssa.vanha.model.BidHistory;
import in.fssa.vanha.validator.BidHistoryValidator;

public class BidHistoryService {
	BidHistoryDAO bidHistoryDao = new BidHistoryDAO();

	public void create(BidHistory newBid) throws Exception {
		BidHistoryValidator.createValidate(newBid);
		bidHistoryDao.create(newBid);
	}
	
	public Set<BidHistory> findAllBidssByProductId(String productId) throws Exception {
		BidHistoryValidator.findValidate(productId);
		return bidHistoryDao.findAllBidsByProductId(productId);
	}

}
