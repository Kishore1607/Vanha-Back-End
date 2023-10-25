package in.fssa.vanha.validator;

import in.fssa.vanha.dao.ProductDAO;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.service.UserService;
import in.fssa.vanha.util.StringUtil;

public class BidHistoryValidator {

	public static void createValidate(int amount, String buyerId, String productId)
			throws ValidationException, ServiceException {

		StringUtil.RegectIfInvalidString(productId, "Product ID");

		StringUtil.RegectIfInvalidString(buyerId, "Buyer email");

		if (UserService.findUserByEmail(buyerId) == null) {
			throw new ServiceException("Invalid Buyer");
		}

		if (amount < 0 || amount > 100000000) {
			throw new ValidationException("Bidded amount should be with in the limit of 1 - 100000000");
		}

		Product productValue = ProductService.ifExistsOrNot(productId);

		int user = UserService.findUserByEmail(buyerId).getId();
		if (productValue.getSellerId() == user) {
			throw new ValidationException("Buyer Id cannot be same as Seller Id");
		}
		if (productValue.getMinPrice() > amount) {
			throw new ValidationException("Bidded amount is lesser than minimum amount");
		}

	}

	public static void findValidate(int productId) throws ValidationException, ServiceException, PersistenceException {

		if (productId < 0) {
			throw new ValidationException("Invalid Product Id");
		}
		ProductDAO productDAO = new ProductDAO();
		if (productDAO.isActive(productId) == false) {
			throw new ServiceException("product not found in product table");
		}

	}

}
