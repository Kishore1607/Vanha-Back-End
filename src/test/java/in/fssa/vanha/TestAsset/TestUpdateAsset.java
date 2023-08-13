package in.fssa.vanha.TestAsset;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.model.Assets;
import in.fssa.vanha.service.AssetsService;

public class TestUpdateAsset {

    @Test
    public void testUpdateAssetWithValidData() {
        AssetsService assetsService = new AssetsService();
        Assets updateAsset = new Assets();

        updateAsset.setOldValue("https://www.example.com");
        updateAsset.setValue("https://www.new-example.com");
        updateAsset.setProductId("P12345");

        assertDoesNotThrow(() -> {
            assetsService.updateAssets(updateAsset);
        });
    }

    @Test
    public void testUpdateAssetWithInvalidOldValue() {
        AssetsService assetsService = new AssetsService();
        Assets updateAsset = new Assets();

        updateAsset.setOldValue("invalid_old_value.jpg");
        updateAsset.setValue("https://example.com/new_asset.jpg");
        updateAsset.setProductId("P123");

        assertThrows(RuntimeException.class, () -> {
            assetsService.updateAssets(updateAsset);
        });
    }

    @Test
    public void testUpdateAssetWithInvalidNewValue() {
        AssetsService assetsService = new AssetsService();
        Assets updateAsset = new Assets();

        updateAsset.setOldValue("old_asset.jpg");
        updateAsset.setValue("invalid_new_value"); // Invalid URL pattern
        updateAsset.setProductId("P123");

        assertThrows(RuntimeException.class, () -> {
            assetsService.updateAssets(updateAsset);
        });
    }

    @Test
    public void testUpdateAssetWithNonExistingProductId() {
        AssetsService assetsService = new AssetsService();
        Assets updateAsset = new Assets();

        updateAsset.setOldValue("old_asset.jpg");
        updateAsset.setValue("https://example.com/new_asset.jpg");
        updateAsset.setProductId("NonExistingProductId");

        assertThrows(RuntimeException.class, () -> {
            assetsService.updateAssets(updateAsset);
        });
    }

    @Test
    public void testUpdateAssetWithSameOldAndNewValues() {
        AssetsService assetsService = new AssetsService();
        Assets updateAsset = new Assets();

        updateAsset.setOldValue("https://www.new-example.com");
        updateAsset.setValue("https://www.new-example.com"); // Same as old value
        updateAsset.setProductId("P123");

        assertThrows(RuntimeException.class, () -> {
            assetsService.updateAssets(updateAsset);
        });
    }

}
