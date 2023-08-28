package in.fssa.vanha.order.TestProduct;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.MocValue;
import in.fssa.vanha.exception.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUpdateProduct {
	
	@Test
	@Order(4)
	public void testUpdateProductWithValidData() {
	    ProductService productService = new ProductService();
	    Product existingProduct = new Product();

	    // Assume this product ID exists in the database
	    existingProduct.setProductId(MocValue.id);
	    existingProduct.setUsedPeriod(12);
	    existingProduct.setUsedDuration("month");
	    existingProduct.setDescription("Updated description");
	    existingProduct.setName("Updated Product");
	    existingProduct.setPrice(6800);
	    existingProduct.setMinPrice(5800);

	    assertDoesNotThrow(() -> {
	        productService.update(existingProduct);
	    });
	}

	@Test
	public void testUpdateProductWithInvalidPrice() {
	    ProductService productService = new ProductService();
	    Product existingProduct = new Product();

	    // Assume this product ID exists in the database
	    existingProduct.setProductId("P66678");
	    existingProduct.setUsedPeriod(6);
	    existingProduct.setUsedDuration("month");
	    existingProduct.setDescription("Updated description");
	    existingProduct.setName("Updated Product");
	    existingProduct.setPrice(1500000000); // Invalid price
	    existingProduct.setMinPrice(100);

	    assertThrows(ValidationException.class, () -> {
	        productService.update(existingProduct);
	    });
	}

	@Test
	public void testUpdateProductWithNegativeUsedPeriod() {
	    ProductService productService = new ProductService();
	    Product existingProduct = new Product();

	    // Assume this product ID exists in the database
	    existingProduct.setProductId("l8947");
	    existingProduct.setUsedPeriod(-3); // Negative used period
	    existingProduct.setUsedDuration("months");
	    existingProduct.setDescription("Updated description");
	    existingProduct.setName("Updated Product");
	    existingProduct.setPrice(100);
	    existingProduct.setMinPrice(50);

	    assertThrows(ValidationException.class, () -> {
	        productService.update(existingProduct);
	    });
	}

	@Test
	public void testUpdateNonExistentProduct() {
	    ProductService productService = new ProductService();
	    Product nonExistentProduct = new Product();

	    // Use a product ID that doesn't exist in the database
	    nonExistentProduct.setProductId("P99999");
	    nonExistentProduct.setUsedPeriod(12);
	    nonExistentProduct.setUsedDuration("months");
	    nonExistentProduct.setDescription("Non-existent product");
	    nonExistentProduct.setName("Non-existent Product");
	    nonExistentProduct.setPrice(50);
	    nonExistentProduct.setMinPrice(30);

	    assertThrows(ServiceException.class, () -> {
	        productService.update(nonExistentProduct);
	    });
	}
}
