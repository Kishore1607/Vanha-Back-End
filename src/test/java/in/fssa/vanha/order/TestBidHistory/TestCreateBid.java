package in.fssa.vanha.order.TestBidHistory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import in.fssa.vanha.service.*;
import in.fssa.vanha.model.*;
import in.fssa.vanha.exception.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCreateBid {
	@Order(9)
	@Test
	public void testCreateBidHistoryWithValidData() {
	    BidHistoryService bidHistoryService = new BidHistoryService();
	    BidHistory newBid = new BidHistory();

	    // Assume valid data here
	    newBid.setBidAmount(70000);
	    newBid.setBuyerUnique("woau.dhmfts@gmail.com");
	    newBid.setProductUnique("j0205");
	    assertDoesNotThrow(() -> {
	        bidHistoryService.create(newBid);
	    });
	}

	@Test
	public void testCreateBidHistoryWithInvalidBidAmount() {
	    BidHistoryService bidHistoryService = new BidHistoryService();
	    BidHistory newBid = new BidHistory();

	    // Assume invalid bid amount
	    newBid.setBidAmount(-50);

	    assertThrows(ValidationException.class, () -> {
	        bidHistoryService.create(newBid);
	    });
	}

	@Test
	public void testCreateBidHistoryWithBidAmountLessThanMinPrice() {
	    BidHistoryService bidHistoryService = new BidHistoryService();
	    BidHistory newBid = new BidHistory();

	    // Assume bid amount is less than minimum price
	    newBid.setBidAmount(90);
	    newBid.setProductUnique("P12345"); // Assume this product ID exists in the database

	    assertThrows(ValidationException.class, () -> {
	        bidHistoryService.create(newBid);
	    });
	}


	@Test
	public void testCreateBidHistoryWithNonExistentBuyer() {
	    BidHistoryService bidHistoryService = new BidHistoryService();
	    BidHistory newBid = new BidHistory();

	    // Assume nonexistent buyer email
	    newBid.setBuyerUnique("nonexistent@example.com");
	    newBid.setBidAmount(28000);
	    newBid.setProductUnique("P123");

	    assertThrows(ServiceException.class, () -> {
	        bidHistoryService.create(newBid);
	    });
	}

}
