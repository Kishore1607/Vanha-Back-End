package in.fssa.vanha.testProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.dao.ProductDAO;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.service.ProductService;

class TestDeleteProduct {

	ProductService productService = new ProductService();

//	@Test
//	public void testDeleteProductWithValidData() {
//
//		assertDoesNotThrow(() -> {
//			productService.delete("k1833");
//		});
//	}

	@Test
	public void testDeleteProductWithNullProductId() {
		assertThrows(ValidationException.class, () -> {
			productService.delete(null);
		});
	}

	@Test
	public void testDeleteProductWithEmptyProductId() {
		assertThrows(ValidationException.class, () -> {
			productService.delete("");
		});
	}
}
