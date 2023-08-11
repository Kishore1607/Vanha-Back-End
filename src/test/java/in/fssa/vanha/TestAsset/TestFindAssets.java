package in.fssa.vanha.TestAsset;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.model.Assets;
import in.fssa.vanha.service.AssetsService;

public class TestFindAssets {

	@Test
	public void testFindAllAssetsByProductIdWithValidProductId() {
	    // Assume this product ID exists in the database
		
		String existingProductId = "P12345";
	    
	    assertDoesNotThrow(() -> {
	        Set<Assets> assets = AssetsService.findAllAssetsByProductId(existingProductId);
	        assertNotNull(assets);
	        assertTrue(assets.size() >= 0);
	    });
	}

	@Test
	public void testFindAllAssetsByProductIdWithNonExistentProductId() {
	    
	    String nonExistentProductId = "P99999"; // Use a product ID that doesn't exist in the database
	    
	    assertThrows(RuntimeException.class, () -> {
	    	AssetsService.findAllAssetsByProductId(nonExistentProductId);
	    });
	}

	@Test
	public void testFindAssetValidateWithValidProductId() {
	    String existingProductId = "P12345"; // Assume this product ID exists in the database
	    
	    assertDoesNotThrow(() -> {
	    	AssetsService.findAllAssetsByProductId(existingProductId);
	    });
	}

	@Test
	public void testFindAssetValidateWithNonExistentProductId() {
	    String nonExistentProductId = "P99999"; // Use a product ID that doesn't exist in the database
	    
	    assertThrows(RuntimeException.class, () -> {
	    	AssetsService.findAllAssetsByProductId(nonExistentProductId);
	    });
	}

	@Test
	public void testFindAssetValidateWithInvalidProductId() {
	    String invalidProductId = ""; // Empty product ID
	    
	    assertThrows(RuntimeException.class, () -> {
	    	AssetsService.findAllAssetsByProductId(invalidProductId);
	    });
	}

	
}
