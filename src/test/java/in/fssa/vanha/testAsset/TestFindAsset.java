package in.fssa.vanha.testAsset;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.service.AssetsService;

public class TestFindAsset {

	AssetsService assetsService = new AssetsService();

	@Test
	public void testFindAllAssetsByProductIdWithValidProductId() {

		int existingProductId = 1;

		assertDoesNotThrow(() -> {
			List<Assets> assets = assetsService.findAllAssetsByProductId(existingProductId);
			assertNotNull(assets);
			assertTrue(assets.size() > 0);
		});
	}

	@Test
	public void testFindAssetValidateWithValidProductId() {
		int existingProductId = 1;

		assertDoesNotThrow(() -> {
			assetsService.findAllAssetsByProductId(existingProductId);
		});
	}

	@Test
	public void testFindAssetValidateWithInvalidProductId() {
		int invalidProductId = -10;

		assertThrows(ValidationException.class, () -> {
			assetsService.findAllAssetsByProductId(invalidProductId);
		});
	}

}
