package in.fssa.vanha.service;

import java.util.Set;

import in.fssa.vanha.dao.BidHistoryDAO;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.BidDTO;
import in.fssa.vanha.validator.BidHistoryValidator;

public class BidHistoryService {
	BidHistoryDAO bidHistoryDao = new BidHistoryDAO();

	/**
	 * 
	 * @param newBid
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void create(int amount, String buyerId, String productId)
			throws ServiceException, ValidationException {
		try {
			BidHistoryValidator.createValidate(amount, buyerId, productId);
			bidHistoryDao.create(amount, buyerId, productId);

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
	public Set<BidDTO> findAllBidsByProductId(int productId) throws ServiceException, ValidationException {
		try {
			BidHistoryValidator.findValidate(productId);
			return BidHistoryDAO.findAllBidsByProductId(productId);

		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in creating asset in asset DAO");
		}

	}

}
