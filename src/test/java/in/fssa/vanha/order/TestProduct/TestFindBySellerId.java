package in.fssa.vanha.order.TestProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.MocValue;
import in.fssa.vanha.exception.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestFindBySellerId {
	@Order(12)
	@Test
	public void testFindAllProductsBySellerIdWithValidSellerId() {
	    ProductService productService = new ProductService();
	    String existingSellerId = MocValue.email; // Assume this seller ID exists in the database
	    
	    assertDoesNotThrow(() -> {
	        Set<Product> products = productService.findAllProductsBySellerId(existingSellerId);
	        assertNotNull(products);
	        assertTrue(products.size() >= 0);
	    });
	}

	@Test
	public void testFindAllProductsBySellerIdWithNonExistentSellerId() {
	    ProductService productService = new ProductService();
	    String nonExistentSellerId = "nonexistent@example.com"; // Use a seller ID that doesn't exist in the database
	    
	    assertThrows(ServiceException.class, () -> {
	        productService.findAllProductsBySellerId(nonExistentSellerId);
	    });
	}
	
	ProductService productService = new ProductService();

	@Test
	public void testFindUserValidateWithValidSellerId() {
	    String existingSellerId = "john.doe@example.com"; // Assume this seller ID exists in the database
	    
	    assertDoesNotThrow(() -> {
			productService.findAllProductsBySellerId(existingSellerId);
	    });
	}

	@Test
	public void testFindUserValidateWithNonExistentSellerId() {
	    String nonExistentSellerId = "nonexistent@example.com"; // Use a seller ID that doesn't exist in the database
	    
	    assertThrows(ServiceException.class, () -> {
	        productService.findAllProductsBySellerId(nonExistentSellerId);
	    });
	}

	@Test
	public void testFindUserValidateWithInvalidSellerId() {
	    String invalidSellerId = ""; // Empty seller ID
	    
	    assertThrows(ValidationException.class, () -> {
	        productService.findAllProductsBySellerId(invalidSellerId);
	    });
	}

}
