package in.fssa.vanha.testProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.Test;

import in.fssa.vanha.util.MocValue;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.service.ProductService;

public class TestCreateProduct {

	ProductService productService = new ProductService();

	@Test
	public void testCreateProductWithValidData() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName("Tommy");
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "karukvel@gmail.com";

		assertDoesNotThrow(() -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithNullData() {
		Product newProduct = new Product();

		String email = null;

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithEmptyProductId() {
		Product newProduct = new Product();
		newProduct.setProductId("");
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithMoreMinPrice() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(10000);
		newProduct.setMinPrice(100000);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithEmptyName() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName("");
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithEmptyDescription() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription("");
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithEmptyEmail() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithInvalidProductIdLength() {
		Product newProduct = new Product();
		newProduct.setProductId(
				"hdydhhdcyajvcydcbaduc1387e1e61kyadvcadbcvuksbvubvuabubvuabvulbauvbuladvbudbnuhbvuhsuvhvhnusnvhvuoan");
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithInvalidNameLength() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(
				"aefuahbvubwrgoy8qwgvyqegwqruivbqwivbrwbvybwvhuwervbiwvygqe6vgywerbvywrbgvyugrvygw6rgvwrgiywvybygvybgeyg7wyt7w4yt78oyr68qgy7wyrwayg44w74waygrl8yzsyrf8syr8wytlyw5ya8w5y2ay42qy42q85ywirywyrawyrywyrw7yrwayriawyway5yw5ytwy5892y5");
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithInvalidCategory() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory("day");
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithInvalidPriceNegative() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(-1);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithInvalidPricePositive() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(1000000000);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithInvalidMinPriceNegative() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(-10);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithInvalidMinPricePositive() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(1000000000);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithInvalidPeriod() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(-1);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithInvalidperiod() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(120);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "karkuvel@gmail.com";

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithInvalidEmail() {
		Product newProduct = new Product();
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "notexists@gmail.com";

		assertThrows(ServiceException.class, () -> {
			productService.create(newProduct, email);
		});
	}

	@Test
	public void testCreateProductWithInvalidProductID() {
		Product newProduct = new Product();
		newProduct.setProductId("de8bb");
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);

		String email = "karkuvel@gmail.com";

		assertThrows(ServiceException.class, () -> {
			productService.create(newProduct, email);
		});
	}
}
