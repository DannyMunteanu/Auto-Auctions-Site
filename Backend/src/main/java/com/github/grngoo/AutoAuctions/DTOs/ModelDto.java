package com.github.grngoo.AutoAuctions.DTOs;

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
    private BigDecimal minDisplacement;
    private BigDecimal maxDisplacement;
    private Integer cylinders;

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
     * Gets the max engine displacement of the car model.
     *
     * @return the displacement of the car model.
     */
    public BigDecimal getMaxDisplacement() {
        return maxDisplacement;
    }

    /**
     * Sets the max engine displacement of the car model.
     *
     * @param displacement the displacement of the car model.
     */
    public void setMaxDisplacement(BigDecimal displacement) {
        this.maxDisplacement = displacement;
    }

    /**
     * Gets the min engine displacement of the car model.
     *
     * @return the min displacement of the car model.
     */
    public BigDecimal getMinDisplacement() {
        return minDisplacement;
    }

    /**
     * Sets the min engine displacement of the car model.
     *
     * @param displacement the min displacement of the car model.
     */
    public void setMinDisplacement(BigDecimal displacement) {
        this.minDisplacement = displacement;
    }

    /**
     * Gets the number of cylinders in the car model.
     *
     * @return the number of cylinders in the car model.
     */
    public Integer getCylinders() {
        return cylinders;
    }

    /**
     * Sets the number of cylinders in the car model.
     *
     * @param cylinders the number of cylinders in the car model.
     */
    public void setCylinders(Integer cylinders) {
        this.cylinders = cylinders;
    }
}
