package in.fssa.vanha.TestProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.exception.*;

public class TestDeleteProduct {

	@Test
	public void testDeleteValidateWithValidProductID() {
	    ProductService productService = new ProductService();
	    String existingProductID = "P12345"; // Assume this product ID exists in the database
	    
	    assertDoesNotThrow(() -> {
	        productService.delete(existingProductID);
	    });
	}

	@Test
	public void testDeleteValidateWithNonExistentProductID() {
	    ProductService productService = new ProductService();
	    String nonExistentProductID = "P99999"; // Use a product ID that doesn't exist in the database
	    
	    assertThrows(ServiceException.class, () -> {
	        productService.delete(nonExistentProductID);
	    });
	}

	@Test
	public void testDeleteValidateWithInvalidProductID() {
	    ProductService productService = new ProductService();
	    String invalidProductID = ""; // Empty product ID
	    
	    assertThrows(ValidationException.class, () -> {
	        productService.delete(invalidProductID);
	    });
	}

}
