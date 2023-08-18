package in.fssa.vanha.validator;

import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.BidHistory;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.service.UserService;
import in.fssa.vanha.util.StringUtil;

public class BidHistoryValidator {

	/**
	 * 
	 * @param newBid
	 * @throws ValidationException
	 * @throws ServiceException
	 * @throws PersistenceException
	 */
	public static void createValidate(BidHistory newBid) throws ValidationException, ServiceException, PersistenceException  {
		
		if (newBid == null) {
			throw new ValidationException("Bid input cannot be null");
		}
		
		StringUtil.RegectIfInvalidString(newBid.getProductUnique(), "Product ID");


		if (newBid.getBidAmount() < 0 || newBid.getBidAmount() > 100000000) {
			throw new ValidationException("Bidded amount should be with in the limit of 1 - 100000000");
		}

		Product productValue = ProductService.findByProductId(newBid.getProductUnique());

		if (productValue.getMinPrice() > newBid.getBidAmount()) {
			throw new ValidationException("Bidded amount is lesser than minimum amount");
		}

		if (productValue.getSellerId() == newBid.getBuyerId()) {
			throw new ValidationException("Buyer Id cannot be same as Seller Id");
		}

		if (newBid.getBuyerId() < 0) {
			throw new ValidationException("Invalid Buyer Id");
		}

		if (UserService.findUserByEmail(newBid.getBuyerUnique()) == null) {
			throw new ValidationException("Buyer not found in user table");
		}

	}

	/**
	 * 
	 * @param productId
	 * @throws ValidationException
	 */
	public static void findValidate(String productId) throws ValidationException {

		StringUtil.RegectIfInvalidString(productId, "Product Id");
	}

}
