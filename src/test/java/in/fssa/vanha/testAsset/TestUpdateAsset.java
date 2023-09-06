package in.fssa.vanha.testAsset;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.service.AssetsService;

public class TestUpdateAsset {
	@Test
	public void testUpdateAssetsByAssetId_Success() {
		AssetsService assetsService = new AssetsService();

		Set<Assets> assetsArray = new HashSet<>();

		for (int i = 0; i < 4; i++) {
			Assets asset = new Assets();
			asset.setValue("https://source.unsplash.com/featured/?motorcycle");
			assetsArray.add(asset);
		}
		int productId = 1;

		assertDoesNotThrow(() -> {
			assetsService.updateAssets(assetsArray, productId);
		});
	}

	@Test
	public void testUpdateAssetsByAssetId_NonExistingProductId() {
		AssetsService assetsService = new AssetsService();
		Set<Assets> assetsArray = new HashSet<>();

		for (int i = 0; i < 4; i++) {
			Assets asset = new Assets();
			asset.setValue("https://source.unsplash.com/featured/?motorcycle");
			assetsArray.add(asset);
		}
		int productId = 9999;

		assertThrows(ServiceException.class, () -> {
			assetsService.updateAssets(assetsArray, productId);
		});

	}

	@Test
	public void testUpdateAssetsByAssetId_InvalidAssetValue() {
		AssetsService assetsService = new AssetsService();

		Set<Assets> assetsArray = new HashSet<>();

		for (int i = 0; i < 4; i++) {
			Assets asset = new Assets();
			asset.setValue("Invalid.URl");
			assetsArray.add(asset);
		}
		int productId = 1;

		assertThrows(ValidationException.class, () -> {
			assetsService.updateAssets(assetsArray, productId);
		});

	}
}
