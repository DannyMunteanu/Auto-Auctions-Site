package com.github.grngoo.AutoAuctions.DTOs;

/**
 * Data Transfer Object for car details.
 * Represents the car's registration information, model, color, mileage, year, and previous owners.
 *
 * @author danielmunteanu
 */
public class CarDto {

    private String registration;
    private Long modelId;
    private String color;
    private Integer[] mileage;
    private Integer[] year;
    private Integer[] previousOwners;

    /**
     * Gets the car's registration number.
     *
     * @return the registration number of the car
     */
    public String getRegistration() {
        return registration;
    }

    /**
     * Sets the car's registration number.
     *
     * @param registration the registration number to set
     */
    public void setRegistration(String registration) {
        this.registration = registration;
    }

    /**
     * Gets the car's model ID.
     *
     * @return the model ID of the car
     */
    public Long getModelId() {
        return modelId;
    }

    /**
     * Sets the car's model ID.
     *
     * @param modelId the model ID to set
     */
    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    /**
     * Gets the car's color.
     *
     * @return the color of the car
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the car's color.
     *
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the car's min mileage.
     *
     * @return the mileage of the car
     */
    public Integer[] getMileage() {
        return mileage;
    }

    /**
     * Set the mileage range.
     *
     * @param minMileage lower bound.
     * @param maxMileage upper bound.
     */
    public void setMileage(Integer minMileage, Integer maxMileage) {
        mileage = new Integer[2];
        mileage[0] = minMileage;
        mileage[1] = maxMileage;
    }

    /**
     * Gets the car's manufacturing year.
     *
     * @return the year of the car
     */
    public Integer[] getYear() {
        return year;
    }

    /**
     * Sets the car's manufacturing year.
     *
     * @param minYear the minimum year to set.
     * @param maxYear the maximum year to set.
     */
    public void setYear(Integer minYear, Integer maxYear) {
        year = new Integer[2];
        year[0] = minYear;
        year[1] = maxYear;
    }

    /**
     * Gets the range of desired previous owners of the car.
     *
     * @return the specified range of previous owners
     */
    public Integer[] getPreviousOwners() {
        return previousOwners;
    }

    /**
     * Sets the range for the number of previous owners of the car.
     *
     * @param minPrevious lower bound.
     * @param maxPrevious upper bound.
     */
    public void setPreviousOwners(Integer minPrevious, Integer maxPrevious) {
        previousOwners = new Integer[2];
        previousOwners[0] = minPrevious;
        previousOwners[1] = maxPrevious;
    }
}
