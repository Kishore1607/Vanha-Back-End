package in.fssa.vanha.order.TestBidHistory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import in.fssa.vanha.service.*;
import in.fssa.vanha.model.*;
import in.fssa.vanha.exception.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestFindAllBid {
	@Order(10)
	@Test
	public void testFindAllBidsByProductIdWithValidProductId() {
	    BidHistoryService bidService = new BidHistoryService();
	    String ProductId = "j0205"; // Assume this product ID exists in the database
	    
	    assertDoesNotThrow(() -> {
	        Set<BidHistory> bidHistory = bidService.findAllBidsByProductId(ProductId);
	        assertNotNull(bidHistory);
	        assertTrue(bidHistory.size() > 0);
	    });
	}

	@Test
	public void testFindAllBidsByProductIdWithNonExistentProductId() {
	    BidHistoryService bidService = new BidHistoryService();
	    String nonExistentProductId = "P67890"; // Use a product ID that doesn't exist in the database
	    
	    assertThrows(ServiceException.class, () -> {
	        bidService.findAllBidsByProductId(nonExistentProductId);
	    });
	}

	@Test
	public void testFindValidateWithValidProductId() {
	    String validProductId = "j0205"; // Assume this product ID is valid
	    
	    assertDoesNotThrow(() -> {
	        BidHistoryService bidHistoryService = new BidHistoryService();
			bidHistoryService.findAllBidsByProductId(validProductId);
	    });
	}

	@Test
	public void testFindValidateWithInvalidProductId() {
	    String invalidProductId = ""; // Invalid product ID
	    
	    assertThrows(ValidationException.class, () -> {
	    	BidHistoryService bidHistoryService = new BidHistoryService();
			bidHistoryService.findAllBidsByProductId(invalidProductId);
	    });
	}

}
