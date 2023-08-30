package in.fssa.vanha.order.TestAsset;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import in.fssa.vanha.model.Assets;
import in.fssa.vanha.service.AssetsService;
import in.fssa.vanha.exception.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestFindAssets {

	AssetsService assetsService = new AssetsService();
	@Order(6)
	@Test
	public void testFindAllAssetsByProductIdWithValidProductId() {
	    // Assume this product ID exists in the database
		
		int existingProductId = 11;
	    
	    assertDoesNotThrow(() -> {
	        Set<Assets> assets = assetsService.findAllAssetsByProductId(existingProductId);
	        assertNotNull(assets);
	        assertTrue(assets.size() >= 0);
	    });
	}

	@Test
	public void testFindAllAssetsByProductIdWithNonExistentProductId() {
	    
	    int nonExistentProductId = 34; // Use a product ID that doesn't exist in the database
	    
	    assertThrows(ServiceException.class, () -> {
	    	assetsService.findAllAssetsByProductId(nonExistentProductId);
	    });
	}

	@Test
	public void testFindAssetValidateWithValidProductId() {
	    int existingProductId = 1; // Assume this product ID exists in the database
	    
	    assertDoesNotThrow(() -> {
	    	assetsService.findAllAssetsByProductId(existingProductId);
	    });
	}

	@Test
	public void testFindAssetValidateWithNonExistentProductId() {
	    int nonExistentProductId = 35; // Use a product ID that doesn't exist in the database
	    
	    assertThrows(ServiceException.class, () -> {
	    	assetsService.findAllAssetsByProductId(nonExistentProductId);
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
