package in.fssa.vanha.order.TestProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.exception.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestFindAllProductByCategory {

	private ProductService productService;
	
	@Order(11)

	@Test
	public void testFindAllProductsByValidCategory() {

		assertDoesNotThrow(() -> {
			productService = new ProductService();
			productService.findAllProductsByCategory("car");
		});
	}

	@Test
	public void testFindAllProductsByInvalidCategory() {
		assertThrows(ValidationException.class, () -> {
			productService = new ProductService();
			productService.findAllProductsByCategory("invalid_category");
		});
	}

	@Test
	public void testCategoryValidationWithEmptyInput() {
		assertThrows(ValidationException.class, () -> {
			productService = new ProductService();
			productService.findAllProductsByCategory("");
		});
	}

	@Test
	public void testCategoryValidationWithNullInput() {
		assertThrows(ValidationException.class, () -> {
			productService = new ProductService();
			productService.findAllProductsByCategory(null);
		});
	}
}
