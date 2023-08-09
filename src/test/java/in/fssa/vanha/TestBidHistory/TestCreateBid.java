package in.fssa.vanha.TestBidHistory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.service.*;
import in.fssa.vanha.model.*;

public class TestCreateBid {
	@Test
	public void testCreateBidHistoryWithValidData() {
	    BidHistoryService bidHistoryService = new BidHistoryService();
	    BidHistory newBid = new BidHistory();

	    // Assume valid data here
	    newBid.setBidAmount(160);
	    newBid.setBuyerUnique("nat.dyr@example.com");
	    newBid.setProductUnique("P12345");
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

	    assertThrows(RuntimeException.class, () -> {
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

	    assertThrows(RuntimeException.class, () -> {
	        bidHistoryService.create(newBid);
	    });
	}

	@Test
	public void testCreateBidHistoryWithInvalidBuyerId() {
	    BidHistoryService bidHistoryService = new BidHistoryService();
	    BidHistory newBid = new BidHistory();

	    // Assume invalid buyer ID
	    newBid.setBuyerId(-1);

	    assertThrows(RuntimeException.class, () -> {
	        bidHistoryService.create(newBid);
	    });
	}

	@Test
	public void testCreateBidHistoryWithNonExistentBuyer() {
	    BidHistoryService bidHistoryService = new BidHistoryService();
	    BidHistory newBid = new BidHistory();

	    // Assume nonexistent buyer email
	    newBid.setBuyerUnique("nonexistent@example.com");

	    assertThrows(RuntimeException.class, () -> {
	        bidHistoryService.create(newBid);
	    });
	}

}
