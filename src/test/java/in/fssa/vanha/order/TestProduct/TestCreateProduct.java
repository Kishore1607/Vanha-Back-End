package in.fssa.vanha.order.TestProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import in.fssa.vanha.service.*;
import in.fssa.vanha.model.*;
import in.fssa.vanha.exception.*;
import in.fssa.vanha.MocValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCreateProduct {

	@Test
	@Order(3)
	public void testCreateProductWithValidData() {
		ProductService productService = new ProductService();
		Product newProduct = new Product();
		
		newProduct.setProductId(MocValue.id);
		newProduct.setCategory(MocValue.category);
		newProduct.setUsedPeriod(MocValue.usedPeriod);
		newProduct.setUsedDuration(MocValue.usedDuration);
		newProduct.setDescription(MocValue.description);
		newProduct.setName(MocValue.productName);
		newProduct.setPrice(MocValue.price);
		newProduct.setMinPrice(MocValue.minPrice);
		newProduct.setSellerUnique(MocValue.email);

		Assets newAssets = new Assets();

		newAssets.setValue(MocValue.asset);

		assertDoesNotThrow(() -> {
			productService.create(newProduct, newAssets);
		});

	}

	@Test
	public void testCreateProductWithInvalidPrice() {
		ProductService productService = new ProductService();
		Product newProduct = new Product();

		newProduct.setProductId("P99999");
		newProduct.setCategory("mobile");
		newProduct.setUsedPeriod(3);
		newProduct.setUsedDuration("years");
		newProduct.setDescription("Budget smartphone");
		newProduct.setName("Affordable Phone");
		newProduct.setPrice(150000000); // Invalid price
		newProduct.setMinPrice(10000);
		newProduct.setSellerUnique("john.doe@example.com");

		Assets newAssets = new Assets();

		newAssets.setValue("https://www.example.com");

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, newAssets);
		});
	}

//
	@Test
	public void testCreateProductWithInvalidMinPrice() {
		ProductService productService = new ProductService();
		Product newProduct = new Product();

		newProduct.setProductId("P456");
		newProduct.setCategory("bike");
		newProduct.setUsedPeriod(12);
		newProduct.setUsedDuration("months");
		newProduct.setDescription("Fitness tracker");
		newProduct.setName("Activity Tracker");
		newProduct.setPrice(8000);
		newProduct.setMinPrice(8000); // Invalid minimum price
		newProduct.setSellerUnique("john.doe@example.com");

		Assets newAssets = new Assets();

		newAssets.setValue("https://www.example.com");

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, newAssets);
		});
	}

//
	@Test
	public void testCreateProductWithNonExistentSeller() {
		ProductService productService = new ProductService();
		Product newProduct = new Product();

		newProduct.setProductId("P55555");
		newProduct.setCategory("cmputer");
		newProduct.setUsedPeriod(24);
		newProduct.setUsedDuration("months");
		newProduct.setDescription("Gaming laptop");
		newProduct.setName("High-Performance Laptop");
		newProduct.setPrice(250000);
		newProduct.setMinPrice(200000);
		newProduct.setSellerUnique("nonexistent@example.com"); // Non-existent seller email

		Assets newAssets = new Assets();

		newAssets.setValue("https://www.example.com");

		assertThrows(ServiceException.class, () -> {
			productService.create(newProduct, newAssets);
		});
	}

//
	@Test
	public void testCreateProductWithExistingProductId() {
		ProductService productService = new ProductService();
		Product newProduct = new Product();

		newProduct.setProductId("P456"); // Assume this product ID already exists
		newProduct.setCategory("mobile");
		newProduct.setUsedPeriod(6);
		newProduct.setUsedDuration("months");
		newProduct.setDescription("New product with existing ID");
		newProduct.setName("Duplicate Product");
		newProduct.setPrice(500);
		newProduct.setMinPrice(400);
		newProduct.setSellerUnique("john.doe@example.com");

		Assets newAssets = new Assets();

		newAssets.setValue("https://www.example.com");

		assertThrows(ServiceException.class, () -> {
			productService.create(newProduct, newAssets);
		});
	}

//
	@Test
	public void testCreateProductWithNegativeUsedPeriod() {
		ProductService productService = new ProductService();
		Product newProduct = new Product();

		newProduct.setProductId("P12346");
		newProduct.setCategory("bike");
		newProduct.setUsedPeriod(-3); // Negative used period
		newProduct.setUsedDuration("months");
		newProduct.setDescription("Expired product");
		newProduct.setName("Expired Item");
		newProduct.setPrice(50);
		newProduct.setMinPrice(25);
		newProduct.setSellerUnique("john.doe@example.com");

		Assets newAssets = new Assets();

		newAssets.setValue("https://www.example.com");

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, newAssets);
		});
	}

//
	@Test
	public void testCreateProductWithNullInput() {
		ProductService productService = new ProductService();
		Product newProduct = null;

		Assets newAssets = new Assets();

		newAssets.setValue(null);

		assertThrows(ValidationException.class, () -> {
			productService.create(newProduct, newAssets);
		});
	}

}