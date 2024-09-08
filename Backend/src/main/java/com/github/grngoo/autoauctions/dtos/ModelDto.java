package com.github.grngoo.autoauctions.dtos;

import java.math.BigDecimal;

/**
 * Data Transfer Object for model details.
 *
 * @author danielmunteanu
 */
public class ModelDto {

  private String make;
  private String series;
  private String name;
  private BigDecimal[] displacement;
  private Integer[] cylinders;

  /**
   * Gets the make of the car model.
   *
   * @return the make of the car model.
   */
  public String getMake() {
    return make;
  }

  /**
   * Sets the make of the car model.
   *
   * @param make the make of the car model.
   */
  public void setMake(String make) {
    this.make = make;
  }

  /**
   * Gets the series of the car model.
   *
   * @return the series of the car model.
   */
  public String getSeries() {
    return series;
  }

  /**
   * Sets the series of the car model.
   *
   * @param series the series of the car model.
   */
  public void setSeries(String series) {
    this.series = series;
  }

  /**
   * Gets the name of the car model.
   *
   * @return the name of the car model.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the car model.
   *
   * @param name the name of the car model.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the engine displacement range of the car model request DTO.
   *
   * @return the range for displacement of the car model.
   */
  public BigDecimal[] getDisplacement() {
    return displacement;
  }

  /**
   * Sets the min engine displacement of the car model.
   *
   * @param min desired minimum engine displacement input.
   * @param max desired maximum engine displacement input.
   */
  public void setDisplacement(BigDecimal min, BigDecimal max) {
    displacement = new BigDecimal[2];
    displacement[0] = min;
    displacement[1] = max;
  }

  /**
   * Gets the number of cylinders in the car model (can be a range).
   *
   * @return the desired min/max range of cylinders of car model.
   */
  public Integer[] getCylinders() {
    return cylinders;
  }

  /**
   * Sets the desired input range of cylinders of model.
   *
   * @param min minimum number of cylinders for input.
   * @param max maximum number of cylinders for input.
   */
  public void setCylinders(Integer min, Integer max) {
    cylinders = new Integer[2];
    cylinders[0] = min;
    cylinders[1] = max;
  }
}
