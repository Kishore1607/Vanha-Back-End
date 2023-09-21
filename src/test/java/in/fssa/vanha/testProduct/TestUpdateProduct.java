package in.fssa.vanha.testProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.MocValue;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.service.ProductService;

public class TestUpdateProduct {
	ProductService productService = new ProductService();

	@Test
	public void testValidProductUpdate() {
		Product updateProduct = new Product();
		updateProduct.setProductId("de8bb");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(MocValue.price);
		updateProduct.setMinPrice(MocValue.minPrice);

		assertDoesNotThrow(() -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void testNullProductUpdate() {
		assertThrows(ValidationException.class, () -> {
			productService.update(null);
		});
	}

	@Test
	public void tesProductUpdateWithInvalidProductId() {
		Product updateProduct = new Product();
		updateProduct.setProductId("");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(MocValue.price);
		updateProduct.setMinPrice(MocValue.minPrice);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void tesProductUpdateWithInvalidName() {
		Product updateProduct = new Product();
		updateProduct.setProductId("de8bb");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName("");
		updateProduct.setPrice(MocValue.price);
		updateProduct.setMinPrice(MocValue.minPrice);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void tesProductUpdateWithInvalidDescription() {
		Product updateProduct = new Product();
		updateProduct.setProductId("de8bb");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription("");
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(MocValue.price);
		updateProduct.setMinPrice(MocValue.minPrice);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void tesProductUpdateWithInvalidNameLength() {
		Product updateProduct = new Product();
		updateProduct.setProductId("de8bb");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(
				"iuadhuahvueahfuoaehvuoshilahilvhailvhilahvilahdvlhadivhifhuhsnbkjzxjkb kzxb ukxzbukxzfbvuzsfuvbkusfbvkushbkusbfuvbsbuksbn");
		updateProduct.setPrice(MocValue.price);
		updateProduct.setMinPrice(MocValue.minPrice);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void tesProductUpdateWithInvalidDuration() {
		Product updateProduct = new Product();
		updateProduct.setProductId("de8bb");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration("day");
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(MocValue.price);
		updateProduct.setMinPrice(MocValue.minPrice);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void tesProductUpdateWithInvalidPeriodNegative() {
		Product updateProduct = new Product();
		updateProduct.setProductId("de8bb");
		updateProduct.setUsedPeriod(-1);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(MocValue.price);
		updateProduct.setMinPrice(MocValue.minPrice);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void tesProductUpdateWithInvalidPeriodPositive() {
		Product updateProduct = new Product();
		updateProduct.setProductId("de8bb");
		updateProduct.setUsedPeriod(10000000);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(MocValue.price);
		updateProduct.setMinPrice(MocValue.minPrice);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void tesProductUpdateWithNegativePrice() {
		Product updateProduct = new Product();
		updateProduct.setProductId("de8bb");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(-1);
		updateProduct.setMinPrice(MocValue.minPrice);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void tesProductUpdateWithAbovePrice() {
		Product updateProduct = new Product();
		updateProduct.setProductId("de8bb");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(1000000000);
		updateProduct.setMinPrice(MocValue.minPrice);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void tesProductUpdateWithNegativeMinPrice() {
		Product updateProduct = new Product();
		updateProduct.setProductId("de8bb");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(MocValue.price);
		updateProduct.setMinPrice(-10);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void tesProductUpdateWithAboveMinPrice() {
		Product updateProduct = new Product();
		updateProduct.setProductId("de8bb");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(MocValue.price);
		updateProduct.setMinPrice(100000000);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void tesProductUpdateWithMoreMinPrice() {
		Product updateProduct = new Product();
		updateProduct.setProductId("de8bb");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(10000);
		updateProduct.setMinPrice(100000);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});

	}

	@Test
	public void testInvalidUsedDuration() {
		Product updateProduct = new Product();
		updateProduct.setProductId("b6084");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration("");
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(MocValue.price);
		updateProduct.setMinPrice(MocValue.minPrice);

		assertThrows(ValidationException.class, () -> {
			productService.update(updateProduct);
		});
	}

	@Test
	public void testNonExistentProductUpdate() {
		Product updateProduct = new Product();
		updateProduct.setProductId("P12345");
		updateProduct.setUsedPeriod(MocValue.usedPeriod);
		updateProduct.setUsedDuration(MocValue.usedDuration);
		updateProduct.setDescription(MocValue.description);
		updateProduct.setName(MocValue.productName);
		updateProduct.setPrice(MocValue.price);
		updateProduct.setMinPrice(MocValue.minPrice);

		assertThrows(ServiceException.class, () -> {
			productService.update(updateProduct);
		});
	}
}
