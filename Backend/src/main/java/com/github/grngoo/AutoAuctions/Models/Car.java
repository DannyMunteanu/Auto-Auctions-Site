package com.github.grngoo.AutoAuctions.Models;

import jakarta.persistence.*;

/**
 *
 * @author danielmunteanu
 */
@Entity
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "registration", nullable = false)
    private String registration;

    @ManyToOne
    @JoinColumn(name = "modelid", referencedColumnName = "modelid")
    private Model model;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "previousowners", nullable = false)
    private Integer previousowners;

    /**
     * Default constructor.
     * Initializes a new instance of the Car class.
     */
    public Car() {}

    /**
     * Initializes a new instance of the Car class with the specified details.
     *
     * @param registration The unique registration number of the car.
     * @param model The model of the car.
     * @param color The color of the car.
     * @param mileage The mileage of the car.
     * @param year The year the car was manufactured.
     * @param previousowners The number of previous owners of the car.
     */
    public Car(String registration, Model model, String color,
               Integer mileage, Integer year, Integer previousowners) {
        this.registration = registration;
        this.model = model;
        this.color = color;
        this.mileage = mileage;
        this.year = year;
        this.previousowners = previousowners;
    }

    /**
     * Gets the registration number of the car.
     *
     * @return The registration number.
     */
    public String getRegistration() {
        return registration;
    }

    /**
     * Gets the model of the car.
     *
     * @return The model.
     */
    public Model getModel() {
        return model;
    }

    /**
     * Gets the color of the car.
     *
     * @return The color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the mileage of the car.
     *
     * @return The mileage.
     */
    public Integer getMileage() {
        return mileage;
    }

    /**
     * Gets the year the car was manufactured.
     *
     * @return The year.
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Gets the number of previous owners of the car.
     *
     * @return The number of previous owners.
     */
    public Integer getPreviousowners() {
        return previousowners;
    }

    /**
     * Sets the registration number of the car.
     *
     * @param registration The registration number.
     */
    public void setRegistration(String registration) {
        this.registration = registration;
    }

    /**
     * Sets the model of the car.
     *
     * @param model The model.
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Sets the color of the car.
     *
     * @param color The color.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Sets the mileage of the car.
     *
     * @param mileage The mileage.
     */
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    /**
     * Sets the year the car was manufactured.
     *
     * @param year The year.
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Sets the number of previous owners of the car.
     *
     * @param previousowners The number of previous owners.
     */
    public void setPreviousowners(Integer previousowners) {
        this.previousowners = previousowners;
    }

    /**
     * Returns a string representation of car entity.
     *
     * @return A string that represents the all the attributes of the car.
     */
    @Override
    public String toString() {
        return ("Car Details: \n" +
                "Registration: " + registration + "\n" +
                "Model: " + model + "\n" +
                "Color: " + color + "\n" +
                "Mileage: " + mileage + "\n" +
                "Year: " + year + "\n" +
                "Previous Owners: " + previousowners + "\n");
    }
}
