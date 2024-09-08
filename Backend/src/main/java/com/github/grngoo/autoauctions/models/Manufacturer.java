package com.github.grngoo.autoauctions.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity Class mapped to the manufacturer table in database. Consists of the Make and Origin
 * Country for the represented entity.
 *
 * @author danielmunteanu
 */
@Entity
@Table(name = "manufacturer")
public class Manufacturer {

  @Id
  @Column(name = "make")
  private String make;

  @Column(name = "origincountry")
  private String origincountry;

  /** Default constructor. Initializes a new instance of the Manufacturer class. */
  public Manufacturer() {}

  /**
   * Initialises a new instance of manufacturer.
   *
   * @param make Make of manufacturer.
   * @param origincountry Country of Origin of manufacturer.
   */
  public Manufacturer(String make, String origincountry) {
    this.make = make;
    this.origincountry = origincountry;
  }

  /**
   * Returns the value of private Make attribute.
   *
   * @return Make
   */
  public String getMake() {
    return make;
  }

  /**
   * Return value of private Origin Country attribute.
   *
   * @return Origin Country
   */
  public String getOrigincountry() {
    return origincountry;
  }

  /**
   * Updates private Make attribute.
   *
   * @param make Make of manufacturer.
   */
  public void setMake(String make) {
    this.make = make;
  }

  /**
   * Updates private OriginCountry attribute.
   *
   * @param origincountry OriginCountry of Manufacturer
   */
  public void setOrigincountry(String origincountry) {
    this.origincountry = origincountry;
  }

  /**
   * Concatenates attributes to string representation of Manufacturer.
   *
   * @return String representation of Make and Country of Origin
   */
  @Override
  public String toString() {
    return ("Manufacturer Details: \n"
        + "Make: " + make + "\n"
        + "Country of Origin: " + origincountry);
  }
}
