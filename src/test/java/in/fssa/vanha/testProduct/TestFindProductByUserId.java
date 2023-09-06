package in.fssa.vanha.testProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.service.ProductService;

public class TestFindProductByUserId {

	ProductService ps = new ProductService();

	@Test
	public void testFindProductByValidId() {

		assertDoesNotThrow(() -> {
			ps.findAllProductsBySellerId("karkuvel@gmail.com");
		});

	}

	@Test
	public void testFindProductByInValidId() {

		assertThrows(ServiceException.class, () -> {
			ps.findAllProductsBySellerId("invalid.email@gmail.com");
		});

	}

	@Test
	public void testFindProductByEmptyId() {

		assertThrows(ValidationException.class, () -> {
			ps.findAllProductsBySellerId("");
		});

	}
}
