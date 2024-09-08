package com.github.grngoo.autoauctions.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for listing details.
 * Used to transfer data related to a listing, including
 * its ID, username, and start/end time.
 *
 * @author danielmunteanu
 */
public class ListingDto {

  private Long listingId;
  private String registration;
  private String username;
  private BigDecimal[] reserve;
  private LocalDateTime[] time;

  /**
   * Gets the ID of the listing.
   *
   * @return the listing ID
   */
  public Long getListingId() {
    return listingId;
  }

  /**
   * Gets the username associated with the listing.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets the time range for the listing, with start and end times.
   *
   * @return an array of LocalDateTime with the start time at index 0 and end time at index 1
   */
  public LocalDateTime[] getTime() {
    return time;
  }

  /**
   * Sets the ID of the listing.
   *
   * @param listingId the listing ID
   */
  public void setListingId(Long listingId) {
    this.listingId = listingId;
  }

  /**
   * Sets the username associated with the listing.
   *
   * @param username the username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Sets the start and end times for the listing.
   *
   * @param start the start time of the listing
   * @param end the end time of the listing
   */
  public void setTime(LocalDateTime start, LocalDateTime end) {
    time = new LocalDateTime[2];
    time[0] = start;
    time[1] = end;
  }

  /**
   * Gets the reserve amount on listing.
   *
   * @return reserve in decimal representation of currency.
   */
  public BigDecimal[] getReserve() {
    return reserve;
  }

  /**
   * Set the reserve amount (range) for listing.
   *
   * @param min minimum reserve amount.
   * @param max maximum reserve amount.
   */
  public void setReserve(BigDecimal min, BigDecimal max) {
    reserve = new BigDecimal[2];
    reserve[0] = min;
    reserve[1] = max;
  }

  /**
   * Get registration from DTO.
   *
   * @return car registration (ID for Car).
   */
  public String getRegistration() {
    return registration;
  }

  /**
   * Set registration of DTO.
   *
   * @param registration car reg associated with listing.
   */
  public void setRegistration(String registration) {
    this.registration = registration;
  }
}
