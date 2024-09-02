package com.github.grngoo.AutoAuctions.Models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity Class mapped to the Listing table in database.
 * Consists of the represented Model entity.
 *
 * @author danielmunteanu
 */
@Entity
@Table(name = "listing")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listingid")
    private Long listingid;

    @ManyToOne
    @JoinColumn(name = "registration", referencedColumnName = "registration", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Users user;

    @Column(name = "reserve", nullable = false, precision = 10, scale = 2)
    private BigDecimal reserve;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime start;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime end;

    /**
     * Default constructor for Class.
     */
    public Listing() {}

    /**
     * Constructs a new Listing object with the specified details.
     *
     * @param car        the car associated with the listing
     * @param user       the user who created the listing
     * @param reserve    the reserve price for the listing
     * @param start  the start time of the listing
     * @param end    the end time of the listing
     */
    public Listing(Car car, Users user, BigDecimal reserve, LocalDateTime start, LocalDateTime end) {
        this.car = car;
        this.user = user;
        this.reserve = reserve;
        this.start = start;
        this.end = end;
    }

    /**
     * Gets the unique identifier for this listing.
     *
     * @return the listing ID
     */
    public Long getListingid() {
        return listingid;
    }

    /**
     * Gets the car associated with this listing.
     *
     * @return the car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Gets the user who created this listing.
     *
     * @return the user
     */
    public Users getUser() {
        return user;
    }

    /**
     * Gets the reserve price for this listing.
     *
     * @return the reserve price
     */
    public BigDecimal getReserve() {
        return reserve;
    }

    /**
     * Gets the start time of this listing.
     *
     * @return the start time
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Gets the end time of this listing.
     *
     * @return the end time
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets the reserve price for this listing.
     *
     * @param reserve the reserve price to set
     */
    public void setReserve(BigDecimal reserve) {
        this.reserve = reserve;
    }

    /**
     * Sets the start time of this listing.
     *
     * @param start the start time to set
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Sets the end time of this listing.
     *
     * @param end the end time to set
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Returns a string representation of the Listing instance.
     *
     * @return a string representation of the listing
     */
    @Override
    public String toString() {
        return ("Listing: \n" +
                "ListingId: " + listingid + "\n" +
                "Car: " + car + "\n" +
                "User: " + user + "\n" +
                "Reserve: " + reserve + "\n" +
                "Start Time:" + start + "\n" +
                "End Time: " + end);
    }
}