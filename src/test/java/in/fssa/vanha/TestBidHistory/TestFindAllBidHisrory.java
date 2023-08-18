package in.fssa.vanha.TestBidHistory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.service.*;
import in.fssa.vanha.model.*;
import in.fssa.vanha.exception.*;

public class TestFindAllBidHisrory {
	@Test
	public void testFindAllBidsByProductIdWithValidProductId() {
	    BidHistoryService bidService = new BidHistoryService();
	    String ProductId = "P12345"; // Assume this product ID exists in the database
	    
	    assertDoesNotThrow(() -> {
	        Set<BidHistory> bidHistory = bidService.findAllBidssByProductId(ProductId);
	        assertNotNull(bidHistory);
	        assertTrue(bidHistory.size() > 0);
	    });
	}

	@Test
	public void testFindAllBidsByProductIdWithNonExistentProductId() {
	    BidHistoryService bidService = new BidHistoryService();
	    String nonExistentProductId = "P67890"; // Use a product ID that doesn't exist in the database
	    
	    assertThrows(ServiceException.class, () -> {
	        bidService.findAllBidssByProductId(nonExistentProductId);
	    });
	}

	@Test
	public void testFindValidateWithValidProductId() {
	    String validProductId = "P12345"; // Assume this product ID is valid
	    
	    assertDoesNotThrow(() -> {
	        BidHistoryService bidHistoryService = new BidHistoryService();
			bidHistoryService.findAllBidssByProductId(validProductId);
	    });
	}

	@Test
	public void testFindValidateWithInvalidProductId() {
	    String invalidProductId = ""; // Invalid product ID
	    
	    assertThrows(ServiceException.class, () -> {
	    	BidHistoryService bidHistoryService = new BidHistoryService();
			bidHistoryService.findAllBidssByProductId(invalidProductId);
	    });
	}

}
