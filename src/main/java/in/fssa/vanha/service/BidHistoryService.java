package in.fssa.vanha.service;

import java.util.List;

import in.fssa.vanha.dao.BidHistoryDAO;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.BidDTO;
import in.fssa.vanha.model.YourListDTO;
import in.fssa.vanha.validator.BidHistoryValidator;
import in.fssa.vanha.validator.ProductValidator;

public class BidHistoryService {
	BidHistoryDAO bidHistoryDao = new BidHistoryDAO();

	/**
	 * Creates a new bid history record with the specified amount, buyer ID, and
	 * product ID.
	 *
	 * @param amount    The amount of the bid.
	 * @param buyerId   The ID of the buyer placing the bid.
	 * @param productId The ID of the product for which the bid is placed.
	 * @throws ServiceException    if an error occurs while creating the bid history
	 *                             record.
	 * @throws ValidationException if the input parameters fail validation.
	 */
	public void create(int amount, String buyerId, String productId) throws ServiceException, ValidationException {
		try {
			BidHistoryValidator.createValidate(amount, buyerId, productId);
			bidHistoryDao.create(amount, buyerId, productId);

		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in creating asset in asset DAO");
		}

	}

	/**
	 * Retrieves a list of bid data transfer objects (DTOs) associated with a
	 * specific product ID.
	 *
	 * This method queries the data source for all bids related to the given product
	 * ID and returns them as a list of BidDTO objects.
	 *
	 * @param productId The unique identifier of the product for which bids are to
	 *                  be retrieved.
	 * @return A List of BidDTO objects representing the bids for the specified
	 *         product.
	 * @throws ServiceException    if there is a service-related error.
	 * @throws ValidationException if the provided productId fails validation.
	 */
	public List<BidDTO> findAllBidsByProductId(int productId) throws ServiceException, ValidationException {
		try {
			BidHistoryValidator.findValidate(productId);
			BidHistoryDAO bidHistoryDAO = new BidHistoryDAO();
			return bidHistoryDAO.findAllBidsByProductId(productId);

		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in creating asset in asset DAO");
		}

	}

	/**
	 * Retrieves a list of product cards associated with the specified user's email.
	 *
	 * This method validates the user's email, retrieves their product cards from
	 * the database, and returns a list of YourListDTO objects representing the
	 * product cards.
	 *
	 * @param userEmail The email of the user for whom you want to retrieve product
	 *                  cards.
	 * @return A List of YourListDTO objects representing the user's product cards.
	 * @throws ServiceException    if an error occurs during the execution of this
	 *                             method.
	 * @throws ValidationException if the user's email is not valid.
	 */
	public List<YourListDTO> listProductCards(String userEmail) throws ServiceException, ValidationException {
		try {
			ProductValidator.findUserValidate(userEmail);
			BidHistoryDAO bidHistoryDAO = new BidHistoryDAO();
			return bidHistoryDAO.myProductList(userEmail);
		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in fetching the list");
		}

	}

	/**
	 * Finds and returns a list of product IDs associated with a specific bid.
	 *
	 * This method retrieves the product IDs for a given bid by calling the
	 * BidHistoryDAO's 'findRow' method. If successful, it returns the product IDs
	 * as a string. If there is an issue with the database or the validation
	 * process, it throws a ServiceException or a ValidationException.
	 *
	 * @param bidId The ID of the bid for which you want to find product IDs.
	 * @return A string containing the product IDs associated with the bid.
	 * @throws ServiceException    if an error occurs during the data retrieval
	 *                             process.
	 * @throws ValidationException if there is an issue with the data validation.
	 */
	public String findBuyerProductIDs(int bidId) throws ServiceException, ValidationException {
		try {
			BidHistoryDAO bh = new BidHistoryDAO();
			return bh.findRow(bidId);
		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in creating asset in asset DAO");
		}
	}

}
