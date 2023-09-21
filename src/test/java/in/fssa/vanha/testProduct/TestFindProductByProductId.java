package in.fssa.vanha.testProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.service.ProductService;

public class TestFindProductByProductId {

	ProductService ps = new ProductService();

	@Test
	public void testFindProductByValidId() {

		assertDoesNotThrow(() -> {
			ProductService.productdetail("458d1");
		});

	}

	@Test
	public void testFindProductByInValidId() {

		assertThrows(ServiceException.class, () -> {
			ProductService.productdetail("invalid.ID");
		});

	}

	@Test
	public void testFindProductByEmptyId() {

		assertThrows(ValidationException.class, () -> {
			ProductService.productdetail("");
		});

	}
}
