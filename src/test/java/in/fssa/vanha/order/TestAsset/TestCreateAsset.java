package in.fssa.vanha.order.TestAsset;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import in.fssa.vanha.model.Assets;
import in.fssa.vanha.service.AssetsService;
import in.fssa.vanha.exception.ValidationException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCreateAsset {
	@Order(8)
	@Test
	public void testCreateValidateWithNullInput() {
		Assets newAsset = new Assets();
		newAsset.setValue(null);

		assertThrows(ValidationException.class, () -> {
			AssetsService assetsService = new AssetsService();
			assetsService.create(newAsset);
		});
	}

	@Test
	public void testCreateValidateWithEmptyInput() {
		Assets newAsset = new Assets();
		newAsset.setValue("");

		assertThrows(ValidationException.class, () -> {
			AssetsService assetsService = new AssetsService();
			assetsService.create(newAsset);
		});
	}

	@Test
	public void testCreateValidateWithValidInput() {
		Assets newAsset = new Assets();
		newAsset.setValue("http://www.example.com");

		assertDoesNotThrow(() -> {
			AssetsService assetsService = new AssetsService();
			assetsService.create(newAsset);
		});
	}
	
	@Test
	public void testCreateValidateWithInvalidInput() {
		Assets newAsset = new Assets();
		newAsset.setValue("invalid-url");

		assertThrows(ValidationException.class, () -> {
			AssetsService assetsService = new AssetsService();
			assetsService.create(newAsset);
		});
	}
}
