package in.fssa.vanha.validator;

import in.fssa.vanha.enumPackage.Category;
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
	public static void createValidate(Product newProduct, String userEmail)
			throws ValidationException, ServiceException {

		// null or empty checking
		if (newProduct == null) {
			throw new ValidationException("Invalid product input");
		}

		StringUtil.RegectIfInvalidString(newProduct.getProductId(), "Product Id");

		StringUtil.RegectIfInvalidString(newProduct.getName(), "Name");

		StringUtil.RegectIfInvalidString(newProduct.getDescription(), "Description");

		StringUtil.RegectIfInvalidString(userEmail, "User Email");

		// Length checking

		if (newProduct.getProductId().length() > 50) {
			throw new ValidationException("Invalid product id length");
		}
		if (newProduct.getName().length() > 100) {
			throw new ValidationException("Invalid name length");

		}

		// Category Checking

		if (Category.getCate(newProduct.getCategory()).equals("non")) {
			throw new ValidationException("Invalid input string for Category type");
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

		// Product exists checking
		if (ProductService.ifExistsOrNot(newProduct.getProductId()) != null) {
			throw new ServiceException("product already exists");
		}

		// Seller exists checking
		if (UserService.findUserByEmail(userEmail) == null) {
			throw new ServiceException("User does not exists");
		}
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
		if (updateProduct.getName().length() > 50) {
			throw new ValidationException("Invalid product name length");

		}

		// Duration Checking

		if (!updateProduct.getUsedDuration().equalsIgnoreCase("month")
				&& !updateProduct.getUsedDuration().equalsIgnoreCase("year")) {
			throw new ValidationException("Invalid used duration");
		}

		// Int checking

		if (updateProduct.getUsedPeriod() < 0 || updateProduct.getUsedPeriod() > 100) {
			throw new ValidationException("Used period should between the limit 1 - 100");
		}
		if (updateProduct.getPrice() < 0 || updateProduct.getPrice() > 100000000) {
			throw new ValidationException("Price should between the limit 1 - 100000000");
		}
		if (updateProduct.getMinPrice() < 0 || updateProduct.getMinPrice() > 100000000) {
			throw new ValidationException("Price should between the limit 1 - 100000000");
		}
		if (updateProduct.getMinPrice() >= updateProduct.getPrice()) {
			throw new ValidationException("Minimum amount price should be lesser than price");
		}

		if (ProductService.ifExistsOrNot(updateProduct.getProductId()) == null) {
			throw new ServiceException("Product does not exists");
		}
	}

	/**
	 * 
	 * @param productID
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public static void productIdValidate(String productID) throws ValidationException, ServiceException {

		StringUtil.RegectIfInvalidString(productID, "Product ID");

		// Product exists checking
		if (ProductService.ifExistsOrNot(productID) == null) {
			throw new ServiceException("Product does not exists");
		}
	}

	/**
	 * 
	 * @param category
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public static void findAllProductValidate(String category, String userEmail)
			throws ValidationException, ServiceException {

		StringUtil.RegectIfInvalidString(category, "Category");
		StringUtil.RegectIfInvalidString(userEmail, "User Email");

		String input = category.toLowerCase();

		// Category Checking

		if (Category.getCate(input).equals("non")) {
			throw new ValidationException("Invalid input string for Category type");
		}

		// User checking

		if (UserService.findUserByEmail(userEmail) == null) {
			throw new ServiceException("User does not exists");
		}
	}

	public static void findAllProductValidateWithoutEmail(String category) throws ValidationException {

		StringUtil.RegectIfInvalidString(category, "Category");

		String input = category.toLowerCase();

		if (Category.getCate(input).equals("non")) {
			throw new ValidationException("Invalid input string for Category type");
		}
	}

}
