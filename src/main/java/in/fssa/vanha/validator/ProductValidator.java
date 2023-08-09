package in.fssa.vanha.validator;

import in.fssa.vanha.model.Product;
import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.service.UserService;
import in.fssa.vanha.util.StringUtil;

public class ProductValidator {

		public static void createValidate(Product newProduct) throws Exception {

		// null or empty checking

		if (newProduct == null) {
			throw new RuntimeException("Invalid product input");
		}

		StringUtil.RegectIfInvalidString(newProduct.getProductId(), "Product Id");

		StringUtil.RegectIfInvalidString(newProduct.getName(), "Name");

		StringUtil.RegectIfInvalidString(newProduct.getDescription(), "Description");


		// Int checking

		if (newProduct.getPrice() < 0 || newProduct.getPrice() > 100000000) {
			throw new RuntimeException("Price should between the limit 1 - 100000000");
		}
		if (newProduct.getMinPrice() < 0 || newProduct.getMinPrice() > 100000000) {
			throw new RuntimeException("Price should between the limit 1 - 100000000");
		}
		if(newProduct.getMinPrice() >= newProduct.getPrice()) {
			throw new RuntimeException("Minimum amount price should be lesser than price");
		}
		if (newProduct.getUsedPeriod() < 0 || newProduct.getUsedPeriod() > 100) {
			throw new RuntimeException("Used period should between the limit 1 - 100");
		}
		if (newProduct.getSellerId() < 0) {
			throw new RuntimeException("Invalid Seller Id");
		}

		// Product exists checking
		if (ProductService.findByProductId(newProduct.getProductId()) != null) {
			throw new RuntimeException("Product already exists");
		}
		
		// Seller exists checking
		if (UserService.findUserByEmail(newProduct.getSellerUnique()) == null) {
			throw new RuntimeException("User already exists");
		}
	}
		
    public static void findProductValidate (String productId) throws Exception{
    	
    	StringUtil.RegectIfInvalidString(productId, "Product ID");
    	
    }
	
	
	public static void findUserValidate(String sellerId) throws Exception{

		StringUtil.RegectIfInvalidString(sellerId, "User ID");
		
		// Seller exists checking
		if (UserService.findUserByEmail(sellerId) == null) {
			throw new RuntimeException("user doesn't exists");
		}
	}

	public static void updateValidate(Product updateProduct) throws Exception {

		// null or empty checking

		if (updateProduct == null) {
			throw new RuntimeException("Invalid product input");
		}

		StringUtil.RegectIfInvalidString(updateProduct.getProductId(), "Product Id");

		StringUtil.RegectIfInvalidString(updateProduct.getName(), "Name");

		StringUtil.RegectIfInvalidString(updateProduct.getDescription(), "Description");

		// Int checking

		if (updateProduct.getPrice() < 0 || updateProduct.getUsedPeriod() > 100000000) {
			throw new RuntimeException("Price should between the limit 1 - 100000000");
		}
		if (updateProduct.getUsedPeriod() < 0 || updateProduct.getUsedPeriod() > 100) {
			throw new RuntimeException("Used period should between the limit 1 - 100");
		}

		// Product exists checking

		if (ProductService.findByProductId(updateProduct.getProductId()) == null) {
			throw new RuntimeException("product doesn't exists");
		}
	}

	public static void deleteValidate(String productID) throws Exception {

		StringUtil.RegectIfInvalidString(productID, "Product ID");
		
		// Product exists checking
		if (ProductService.findByProductId(productID) == null) {
			throw new RuntimeException("Product already exists");
		}
	}


}
