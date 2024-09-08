package com.github.grngoo.autoauctions.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for handling bid-related data. Represents a bid in an auction listing.
 */
public class BidDto {

  private Long bidId;
  private Long listingId;
  private String username;
  private LocalDateTime time;
  private BigDecimal amount;

  /**
   * Gets the unique identifier of the bid.
   *
   * @return the bidId
   */
  public Long getBidId() {
    return bidId;
  }

  /**
   * Sets the unique identifier of the bid.
   *
   * @param bidId the bidId to set
   */
  public void setBidId(Long bidId) {
    this.bidId = bidId;
  }

  /**
   * Gets the identifier of the listing associated with the bid.
   *
   * @return the listingId
   */
  public Long getListingId() {
    return listingId;
  }

  /**
   * Sets the identifier of the listing associated with the bid.
   *
   * @param listingId the listingId to set
   */
  public void setListingId(Long listingId) {
    this.listingId = listingId;
  }

  /**
   * Gets the username of the bidder.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the bidder.
   *
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the time the bid was placed.
   *
   * @return the time
   */
  public LocalDateTime getTime() {
    return time;
  }

  /**
   * Sets the time the bid was placed.
   *
   * @param time the time to set
   */
  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  /**
   * Gets the amount of the bid.
   *
   * @return the amount
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Sets the amount of the bid.
   *
   * @param amount the amount to set
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
