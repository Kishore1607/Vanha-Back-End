package in.fssa.vanha.testBid;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.service.BidHistoryService;

public class TestCreateBid {

	@Test
	public void testCreateBidHistoryWithValidData() {
		BidHistoryService bidHistoryService = new BidHistoryService();

		int amount = 1000000;
		String email = "tamil@gmail.com";
		String productId = "de8bb";
		assertDoesNotThrow(() -> {
			bidHistoryService.create(amount, email, productId);
		});
	}

	@Test
	public void testCreateBidHistoryWithInvalidBidAmount() {
		BidHistoryService bidHistoryService = new BidHistoryService();

		int amount = -50;
		String email = "tamil@gmail.com";
		String productId = "de8bb";

		assertThrows(ValidationException.class, () -> {
			bidHistoryService.create(amount, email, productId);
		});
	}

	@Test
	public void testCreateBidHistoryWithBidAmountLessThanMinPrice() {
		BidHistoryService bidHistoryService = new BidHistoryService();

		int amount = 500;
		String email = "tamil@gmail.com";
		String productId = "de8bb"; // Assume this product ID exists in the database

		assertThrows(ValidationException.class, () -> {
			bidHistoryService.create(amount, email, productId);
		});
	}

	@Test
	public void testCreateBidHistoryWithNonExistentBuyer() {
		BidHistoryService bidHistoryService = new BidHistoryService();

		int amount = 50000000;
		String email = "notexist@gmail.com";
		String productId = "de8bb";

		assertThrows(ServiceException.class, () -> {
			bidHistoryService.create(amount, email, productId);
		});
	}
}
