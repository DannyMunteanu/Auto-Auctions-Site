package com.github.grngoo.AutoAuctions.ModelTests;

import com.github.grngoo.AutoAuctions.Models.Listing;
import com.github.grngoo.AutoAuctions.Models.Users;
import com.github.grngoo.AutoAuctions.Models.Bid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for Listing Class.
 */
class BidTest {

    private Bid bid;
    private Listing listing;
    private Users user;
    private LocalDateTime bidTime;
    private BigDecimal amount;

    @BeforeEach
    void setUp() {
        listing = new Listing();
        user = new Users("johndoe", "password123", "johndoe@example.com",
                "1234567890", "12345", "USA");
        bidTime = LocalDateTime.now();
        amount = new BigDecimal("100.50");
        bid = new Bid(listing, bidTime, amount, user);
        bid.setBidid(1L);
    }

    @Test
    @DisplayName("Test Bid constructor and getters")
    void testBidConstructorAndGetters() {
        assertEquals(listing, bid.getListing());
        assertEquals(bidTime, bid.getTime());
        assertEquals(amount, bid.getAmount());
        assertEquals(user, bid.getUser());
    }

    @Test
    @DisplayName("Test setListing")
    void testSetListing() {
        Listing newListing = new Listing();
        bid.setListing(newListing);
        assertEquals(newListing, bid.getListing());
    }

    @Test
    @DisplayName("Test setBid_time")
    void testSetBidTime() {
        LocalDateTime newBidTime = LocalDateTime.now().plusDays(1);
        bid.setTime(newBidTime);
        assertEquals(newBidTime, bid.getTime());
    }

    @Test
    @DisplayName("Test setAmount")
    void testSetAmount() {
        BigDecimal newAmount = new BigDecimal("150.75");
        bid.setAmount(newAmount);
        assertEquals(newAmount, bid.getAmount());
    }

    @Test
    @DisplayName("Test setUser")
    void testSetUser() {
        Users newUser = new Users();
        bid.setUser(newUser);
        assertEquals(newUser, bid.getUser());
    }

    @Test
    @DisplayName("Test toString method")
    void testToString() {
        String expected = "Bid Details: \n" +
                "Bid Id: 1\n" +
                "Listing Details: " + listing + "\n" +
                "Bid Time: " + bidTime + "\n" +
                "Amount: " + amount + "\n" +
                "User: " + user;
        assertEquals(expected, bid.toString());
    }
}
