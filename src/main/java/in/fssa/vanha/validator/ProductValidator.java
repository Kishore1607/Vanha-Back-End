package in.fssa.vanha.validator;

import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.service.UserService;
import in.fssa.vanha.util.StringUtil;

public class ProductValidator {

	/**
	 * 
	 * @param newProduct
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public static void createValidate(Product newProduct) throws ValidationException, ServiceException {

		// null or empty checking

		if (newProduct == null) {
			throw new ValidationException("Invalid product input");
		}

		StringUtil.RegectIfInvalidString(newProduct.getProductId(), "Product Id");

		StringUtil.RegectIfInvalidString(newProduct.getName(), "Name");

		StringUtil.RegectIfInvalidString(newProduct.getDescription(), "Description");

		// Length checking

		if (newProduct.getProductId().length() > 50) {
			throw new ValidationException("Invalid product id length");
		}
		if (newProduct.getName().length() > 255) {
			throw new ValidationException("Invalid name length");

		}

		// Int checking

		if (newProduct.getPrice() < 0 || newProduct.getPrice() > 100000000) {
			throw new ValidationException("Price should between the limit 1 - 100000000");
		}
		if (newProduct.getMinPrice() < 0 || newProduct.getMinPrice() > 100000000) {
			throw new ValidationException("Price should between the limit 1 - 100000000");
		}
		if (newProduct.getMinPrice() >= newProduct.getPrice()) {
			throw new ValidationException("Minimum amount price should be lesser than price");
		}
		if (newProduct.getUsedPeriod() < 0 || newProduct.getUsedPeriod() > 100) {
			throw new ValidationException("Used period should between the limit 1 - 100");
		}
		if (newProduct.getSellerId() < 0) {
			throw new ValidationException("Invalid Seller Id");
		}

		// Product exists checking
		if (ProductService.findByProductId(newProduct.getProductId()) != null) {
			throw new ServiceException("Product already exists");
		}

		// Seller exists checking
		if (UserService.findUserByEmail(newProduct.getSellerUnique()) == null) {
			throw new ServiceException("User does not exists");
		}
	}

	/**
	 * 
	 * @param productId
	 * @throws ValidationException
	 */
	public static void findProductValidate(String productId) throws ValidationException {

		StringUtil.RegectIfInvalidString(productId, "Product ID");

	}

	/**
	 * 
	 * @param sellerId
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public static void findUserValidate(String sellerId) throws ValidationException, ServiceException {

		StringUtil.RegectIfInvalidString(sellerId, "User ID");

		// Seller exists checking
		if (UserService.findUserByEmail(sellerId) == null) {
			throw new ServiceException("user doesn't exists");
		}
	}

	/**
	 * 
	 * @param updateProduct
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public static void updateValidate(Product updateProduct) throws ValidationException, ServiceException {

		// null or empty checking

		if (updateProduct == null) {
			throw new ValidationException("Invalid product input");
		}

		StringUtil.RegectIfInvalidString(updateProduct.getProductId(), "Product Id");

		StringUtil.RegectIfInvalidString(updateProduct.getName(), "Name");

		StringUtil.RegectIfInvalidString(updateProduct.getDescription(), "Description");

		// Length checking
		if (updateProduct.getName().length() > 255) {
			throw new ValidationException("Invalid used duration length");

		}

		// Int checking

		if (updateProduct.getPrice() < 0 || updateProduct.getPrice() > 100000000) {
			throw new ValidationException("Price should between the limit 1 - 100000000");
		}
		if (updateProduct.getMinPrice() < 0 || updateProduct.getMinPrice() > 100000000) {
			throw new ValidationException("Price should between the limit 1 - 100000000");
		}
		if (updateProduct.getMinPrice() >= updateProduct.getPrice()) {
			throw new ValidationException("Minimum amount price should be lesser than price");
		}

		// Product exists checking

		if (ProductService.findByProductId(updateProduct.getProductId()) == null) {
			throw new ServiceException("product doesn't exists");
		}
	}

	/**
	 * 
	 * @param productID
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public static void deleteValidate(String productID) throws ValidationException, ServiceException {

		StringUtil.RegectIfInvalidString(productID, "Product ID");

		// Product exists checking
		if (ProductService.findByProductId(productID) == null) {
			throw new ServiceException("Product doesn't exists");
		}
	}

	/**
	 * 
	 * @param category
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public static void findAllProductValidate(String category) throws ValidationException, ServiceException {

		StringUtil.RegectIfInvalidString(category, "category");

		String input = category.toLowerCase();

		switch (input) {
		case "car":
		case "bike":
		case "computer":
		case "mobile":
			break;
		default:
			throw new ValidationException("Input category does not match any of the four options.");
		}

	}

}
