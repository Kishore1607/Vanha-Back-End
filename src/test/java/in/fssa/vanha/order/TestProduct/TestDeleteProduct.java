package in.fssa.vanha.order.TestProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.MocValue;
import in.fssa.vanha.ProductRandomGenerator;
import in.fssa.vanha.exception.*;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.model.Product;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDeleteProduct {

	@Test
	@Order(7)
	public void testDeleteValidateWithValidProductID() {

		ProductService productService = new ProductService();
		assertDoesNotThrow(() -> {
			productService.delete(MocValue.id);
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
