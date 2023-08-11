package in.fssa.vanha.TestAsset;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.model.Assets;
import in.fssa.vanha.service.AssetsService;

public class TestCreateAsset {
	@Test
	public void testCreateValidateWithNullInput() {
		Assets newAsset = new Assets();
		newAsset.setValue(null);

		assertThrows(Exception.class, () -> {
			AssetsService assetsService = new AssetsService();
			assetsService.create(newAsset);
		});
	}

	@Test
	public void testCreateValidateWithEmptyInput() {
		Assets newAsset = new Assets();
		newAsset.setValue("");

		assertThrows(Exception.class, () -> {
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

		assertThrows(Exception.class, () -> {
			AssetsService assetsService = new AssetsService();
			assetsService.create(newAsset);
		});
	}
}
