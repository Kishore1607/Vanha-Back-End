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
	 * @throws ValidationException
	 */
	public void create(BidHistory newBid) throws ServiceException, ValidationException {
		try {
			BidHistoryValidator.createValidate(newBid);
			bidHistoryDao.create(newBid);

		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in creating asset in asset DAO");
		}

	}

	/**
	 * 
	 * @param productId
	 * @return Set<BidHistory>
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public Set<BidHistory> findAllBidssByProductId(String productId)
			throws ServiceException, ValidationException {
		try {
			BidHistoryValidator.findValidate(productId);
			return bidHistoryDao.findAllBidsByProductId(productId);

		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in creating asset in asset DAO");
		}

	}

}
