package com.github.grngoo.autoauctions.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity Class mapped to the Bid table in database. Consists of the represented Bid entity.
 *
 * @author danielmunteanu
 */
@Entity
@Table(name = "bid")
public class Bid {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bidid")
  private Long bidid;

  @ManyToOne
  @JoinColumn(name = "listingid", referencedColumnName = "listingid", nullable = false)
  private Listing listing;

  @Column(name = "bid_time", nullable = false)
  private LocalDateTime time;

  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @ManyToOne
  @JoinColumn(name = "username", nullable = false)
  private Users user;

  /** Default constructor for the Bid class. */
  public Bid() {}

  /**
   * Parameterised constructor for the Bid class.
   *
   * @param listing the listing on which the bid was placed
   * @param bid_time the time when the bid was placed
   * @param amount the amount of money for the bid
   * @param user the user who placed the bid
   */
  @SuppressWarnings("checkstyle:ParameterName")
  public Bid(Listing listing, LocalDateTime bid_time, BigDecimal amount, Users user) {
    this.listing = listing;
    this.time = bid_time;
    this.amount = amount;
    this.user = user;
  }

  /**
   * Gets the unique identifier for this bid.
   *
   * @return the unique identifier for this bid
   */
  public Long getBidid() {
    return bidid;
  }

  /**
   * Gets the listing on which this bid was placed.
   *
   * @return the listing on which this bid was placed
   */
  public Listing getListing() {
    return listing;
  }

  /**
   * Gets the time when this bid was placed.
   *
   * @return the time when this bid was placed
   */
  public LocalDateTime getTime() {
    return time;
  }

  /**
   * Gets the amount of money for this bid.
   *
   * @return the amount of money for this bid
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Gets the user who placed this bid.
   *
   * @return the user who placed this bid
   */
  public Users getUser() {
    return user;
  }

  /**
   * Set bidId.
   *
   * @param bidid Test id.
   */
  public void setBidid(Long bidid) {
    this.bidid = bidid;
  }

  /**
   * Sets the listing on which this bid was placed.
   *
   * @param listing the listing to set
   */
  public void setListing(Listing listing) {
    this.listing = listing;
  }

  /**
   * Sets the time when this bid was placed.
   *
   * @param time the time of the bid to set
   */
  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  /**
   * Sets the amount of money for this bid.
   *
   * @param amount the amount of the bid to set
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  /**
   * Sets the user who placed this bid.
   *
   * @param user the user to set
   */
  public void setUser(Users user) {
    this.user = user;
  }

  /**
   * Returns a string representation of the Bid object. This representation includes the bid ID,
   * listing details, bid time, amount, and user information.
   *
   * @return a string representation of the Bid object.
   */
  @Override
  public String toString() {
    return ("Bid Details: \n"
        + "Bid Id: "
        + bidid
        + "\n"
        + "Listing Details: "
        + listing
        + "\n"
        + "Bid Time: "
        + time
        + "\n"
        + "Amount: "
        + amount
        + "\n"
        + "User: "
        + user);
  }
}
