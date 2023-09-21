package in.fssa.vanha.testBid;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.service.*;
import in.fssa.vanha.model.*;
import in.fssa.vanha.exception.*;

public class TestFindAllBid {
	@Test
	public void testFindAllBidsByProductIdWithValidProductId() {
		BidHistoryService bidService = new BidHistoryService();
		int ProductId = 1; // Assume this product ID exists in the database

		assertDoesNotThrow(() -> {
			List<BidDTO> bidHistory = bidService.findAllBidsByProductId(ProductId);
			assertNotNull(bidHistory);
			assertTrue(bidHistory.size() > 0);
		});
	}

	@Test
	public void testFindAllBidsByProductIdWithNonExistentProductId() {
		BidHistoryService bidService = new BidHistoryService();
		int nonExistentProductId = 100; // Use a product ID that doesn't exist in the database

		assertThrows(ServiceException.class, () -> {
			bidService.findAllBidsByProductId(nonExistentProductId);
		});
	}

	@Test
	public void testFindValidateWithValidProductId() {
		int validProductId = 2; // Assume this product ID is valid

		assertDoesNotThrow(() -> {
			BidHistoryService bidHistoryService = new BidHistoryService();
			bidHistoryService.findAllBidsByProductId(validProductId);
		});
	}

	@Test
	public void testFindValidateWithInvalidProductId() {
		int invalidProductId = -100; // Invalid product ID

		assertThrows(ValidationException.class, () -> {
			BidHistoryService bidHistoryService = new BidHistoryService();
			bidHistoryService.findAllBidsByProductId(invalidProductId);
		});
	}

}