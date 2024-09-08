package com.github.grngoo.autoauctions.dtos;

/**
 * Data Transfer Object for Manufacturer details.
 *
 * @author danielmunteanu
 */
public class ManufacturerDto {

  private String name;
  private String country;

  /**
   * Retrieve name variable from DTO.
   *
   * @return name input.
   */
  public String getName() {
    return name;
  }

  /**
   * Find country variable from DTO.
   *
   * @return country input.
   */
  public String getCountry() {
    return country;
  }

  /**
   * Set value to name variable in DTO.
   *
   * @param name value of name of make to be used in request.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set value to country variable in DTO.
   *
   * @param country value for country of origin to be used in request.
   */
  public void setCountry(String country) {
    this.country = country;
  }
}
