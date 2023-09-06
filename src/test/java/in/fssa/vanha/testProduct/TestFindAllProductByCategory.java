package in.fssa.vanha.testProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.service.ProductService;

public class TestFindAllProductByCategory {

	ProductService ps = new ProductService();

	@Test
	public void testFindProductByValidId() {

		assertDoesNotThrow(() -> {
			ps.findAllProductsByCategory("mobile", "karkuvel@gmail.com");
		});

	}

	@Test
	public void testFindProductByInValidId() {

		assertThrows(ValidationException.class, () -> {
			ps.findAllProductsByCategory("invalid.category", "karkuvel@gmail.com");
		});

	}

	@Test
	public void testFindProductByEmptyId() {

		assertThrows(ValidationException.class, () -> {
			ps.findAllProductsByCategory("", "karkuvel@gmail.com");
		});

	}

	@Test
	public void testFindProductByInvalidEmail() {

		assertThrows(ServiceException.class, () -> {
			ps.findAllProductsByCategory("car", "invalid@gmail.com");
		});

	}

	@Test
	public void testFindProductByEmptyEmail() {

		assertThrows(ValidationException.class, () -> {
			ps.findAllProductsByCategory("car", "");
		});

	}
}
