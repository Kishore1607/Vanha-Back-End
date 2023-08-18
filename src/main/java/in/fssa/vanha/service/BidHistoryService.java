package in.fssa.vanha.service;

import java.util.Set;

import in.fssa.vanha.dao.BidHistoryDAO;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.BidHistory;
import in.fssa.vanha.validator.BidHistoryValidator;

public class BidHistoryService {
	BidHistoryDAO bidHistoryDao = new BidHistoryDAO();

	/**
	 * 
	 * @param newBid
	 * @throws ServiceException
	 * @throws PersistenceException
	 * @throws ValidationException
	 */
	public void create(BidHistory newBid) throws ServiceException, PersistenceException, ValidationException {
		BidHistoryValidator.createValidate(newBid);
		bidHistoryDao.create(newBid);
	}
	
	/**
	 * 
	 * @param productId
	 * @return
	 * @throws ServiceException
	 * @throws PersistenceException
	 * @throws ValidationException
	 */
	public Set<BidHistory> findAllBidssByProductId(String productId) throws ServiceException, PersistenceException, ValidationException {
		BidHistoryValidator.findValidate(productId);
		return bidHistoryDao.findAllBidsByProductId(productId);
	}

}
