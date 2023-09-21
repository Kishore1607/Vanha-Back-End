package in.fssa.vanha.testAsset;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.service.AssetsService;

public class TestUpdateAsset {
	@Test
	public void testUpdateAssetsByAssetId_Success() {
		AssetsService assetsService = new AssetsService();

			Assets asset = new Assets();
			asset.setValue("https://source.unsplash.com/featured/?motorcycle");
			
		int productId = 1;

		assertDoesNotThrow(() -> {
			assetsService.updateAssets(asset, productId);
		});
	}

	@Test
	public void testUpdateAssetsByAssetId_NonExistingProductId() {
		AssetsService assetsService = new AssetsService();

			Assets asset = new Assets();
			asset.setValue("https://source.unsplash.com/featured/?motorcycle");
			
		int productId = 9999;

		assertThrows(ServiceException.class, () -> {
			assetsService.updateAssets(asset, productId);
		});

	}

	@Test
	public void testUpdateAssetsByAssetId_InvalidAssetValue() {
		AssetsService assetsService = new AssetsService();


			Assets asset = new Assets();
			asset.setValue("Invalid.URl");

		int productId = 1;

		assertThrows(ValidationException.class, () -> {
			assetsService.updateAssets(asset, productId);
		});

	}
}
