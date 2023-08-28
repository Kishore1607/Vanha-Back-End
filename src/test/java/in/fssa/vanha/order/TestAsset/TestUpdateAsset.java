package in.fssa.vanha.order.TestAsset;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import in.fssa.vanha.model.Assets;
import in.fssa.vanha.service.AssetsService;
import in.fssa.vanha.MocValue;
import in.fssa.vanha.ProductRandomGenerator;
import in.fssa.vanha.exception.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUpdateAsset {

    @Test
    @Order(5)
    public void testUpdateAssetWithValidData() {
        AssetsService assetsService = new AssetsService();
        Assets updateAsset = new Assets();

        ProductRandomGenerator prod = new ProductRandomGenerator();
        String url = prod.assetGenerator();
        Assets asset = new Assets();
        asset.setValue(url);
        updateAsset.setOldValue(MocValue.asset);
        updateAsset.setValue("https://www.updatedUrl.com");
        updateAsset.setProductId(MocValue.id);

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

        assertThrows(ValidationException.class, () -> {
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

        assertThrows(ValidationException.class, () -> {
            assetsService.updateAssets(updateAsset);
        });
    }

    @Test
    public void testUpdateAssetWithNonExistingProductId() {
        AssetsService assetsService = new AssetsService();
        Assets updateAsset = new Assets();

        updateAsset.setOldValue("https://www.example.com");
        updateAsset.setValue("https://www.example3.com");
        updateAsset.setProductId("NonExistingProductId");

        assertThrows(ServiceException.class, () -> {
            assetsService.updateAssets(updateAsset);
        });
    }

    @Test
    public void testUpdateAssetWithSameOldAndNewValues() {
        AssetsService assetsService = new AssetsService();
        Assets updateAsset = new Assets();

        updateAsset.setOldValue("https://www.n3c42.com");
        updateAsset.setValue("https://www.n3c42.com"); // Same as old value
        updateAsset.setProductId("P12345");

        assertThrows(ValidationException.class, () -> {
            assetsService.updateAssets(updateAsset);
        });
    }

}
