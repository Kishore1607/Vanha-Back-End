package in.fssa.vanha.validator;

import in.fssa.vanha.model.BidHistory;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.service.UserService;
import in.fssa.vanha.util.StringUtil;

public class BidHistoryValidator {


	public static void createValidate(BidHistory newBid) throws Exception {

		if (newBid.getProductId() < 0) {
			throw new RuntimeException("Invalid Product Id");
		}

		if (newBid.getBidAmount() < 0 || newBid.getBidAmount() > 100000000) {
			throw new RuntimeException("Bidded amount should be with in the limit of 1 - 100000000");
		}
		
		Product productValue = ProductService.findByProductId(newBid.getProductUnique());
		
		if (productValue.getMinPrice() > newBid.getBidAmount()) {
			throw new RuntimeException("Bidded amount is lesser than minimum amount");
		}
		
		if (productValue.getSellerId() == newBid.getBuyerId()) {
			throw new RuntimeException("Buyer Id cannot be same as Seller Id");
		}

		if (newBid.getBuyerId() < 0) {
			throw new RuntimeException("Invalid Buyer Id");
		}

		if (UserService.findUserByEmail(newBid.getBuyerUnique()) == null) {
			throw new RuntimeException("Buyer not foung in user table");
		}

	}

	public static void findValidate(String productId) throws Exception {
	
		StringUtil.RegectIfInvalidString(productId, "Product Id");
	}

}
