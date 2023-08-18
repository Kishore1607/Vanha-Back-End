package in.fssa.vanha.TestProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.exception.*;

public class TestFindAllProductByCategory {

	private ProductService productService;

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
