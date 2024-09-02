package com.github.grngoo.AutoAuctions.ModelTests;

import com.github.grngoo.AutoAuctions.Models.Listing;
import com.github.grngoo.AutoAuctions.Models.Model;
import com.github.grngoo.AutoAuctions.Models.Users;
import com.github.grngoo.AutoAuctions.Models.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test cases for Listing Class.
 */
public class ListingTest {

    private Car car;
    private Users user;
    private BigDecimal reserve;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Listing listing;

    @BeforeEach
    public void setUp() {
        user = new Users("johndoe", "password123", "johndoe@example.com",
                "1234567890", "12345", "USA");
        car = new Car("ABC123", new Model(), "Red", 50000, 2018, 1);
        reserve = new BigDecimal("10000.00");
        startTime = LocalDateTime.of(2024, 8, 27, 10, 0);
        endTime = LocalDateTime.of(2024, 8, 30, 18, 0);
        listing = new Listing(car, user, reserve, startTime, endTime);
    }

    @Test
    @DisplayName("Test Listing Constructor and Getters")
    public void testListingConstructorAndGetters() {
        assertNotNull(listing.getCar());
        assertNotNull(listing.getUser());
        assertEquals(reserve, listing.getReserve());
        assertEquals(startTime, listing.getStart());
        assertEquals(endTime, listing.getEnd());
    }

    @Test
    @DisplayName("Test Setters")
    public void testSetters() {
        BigDecimal newReserve = new BigDecimal("12000.00");
        LocalDateTime newStartTime = LocalDateTime.of(2024, 8, 28, 9, 0);
        LocalDateTime newEndTime = LocalDateTime.of(2024, 8, 31, 17, 0);

        listing.setReserve(newReserve);
        listing.setStart(newStartTime);
        listing.setEnd(newEndTime);

        assertEquals(newReserve, listing.getReserve());
        assertEquals(newStartTime, listing.getStart());
        assertEquals(newEndTime, listing.getEnd());
    }

    @Test
    @DisplayName("Test toString Method")
    public void testToString() {
        String expectedToString = "Listing: \n" +
                "ListingId: null\n" +
                "Car: " + car + "\n" +
                "User: " + user + "\n" +
                "Reserve: " + reserve + "\n" +
                "Start Time:" + startTime + "\n" +
                "End Time: " + endTime;

        assertEquals(expectedToString, listing.toString());
    }
}