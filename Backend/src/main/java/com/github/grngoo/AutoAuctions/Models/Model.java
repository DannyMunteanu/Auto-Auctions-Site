package com.github.grngoo.AutoAuctions.Models;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entity Class mapped to the Model table in database.
 * Consists of the ModelID, Make, Series, Model Name, Displacement and Cylinder count for the represented entity.
 *
 * @author danielmunteanu
 */
@Entity
@Table(name = "model")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modelid")
    private Long modelid;

    @ManyToOne
    @JoinColumn(name = "make", referencedColumnName = "make", nullable = false)
    private Manufacturer manufacturer;

    @Column(name = "series", nullable = false, length = 50)
    private String series;

    @Column(name = "modelname", nullable = false, length = 50)
    private String modelname;

    @Column(name = "displacement", nullable = false, precision = 3, scale = 1)
    private BigDecimal displacement;

    @Column(name = "cylinders", nullable = false)
    private Integer cylinders;

    /**
     * Default constructor.
     * Initializes a new instance of the {@link Model} class.
     */
    public Model() {}

    /**
     * Initializes a new instance of the Model class with the specified details.
     *
     * @param manufacturer The manufacturer of this car model.
     * @param series The series of this car model.
     * @param modelname The name of this car model.
     * @param displacement The engine displacement of this car model.
     * @param cylinders The number of cylinders in the engine of this car model.
     */
    public Model(Manufacturer manufacturer, String series, String modelname, BigDecimal displacement, Integer cylinders) {
        this.manufacturer = manufacturer;
        this.series = series;
        this.modelname = modelname;
        this.displacement = displacement;
        this.cylinders = cylinders;
    }

    /**
     * Gets the unique identifier for this car model.
     *
     * @return The model ID.
     */
    public Long getModelid() {
        return modelid;
    }

    /**
     * Gets the manufacturer of this car model.
     *
     * @return The manufacturer.
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * Gets the series of this car model.
     *
     * @return The series.
     */
    public String getSeries() {
        return series;
    }

    /**
     * Gets the name of this car model.
     *
     * @return The model name.
     */
    public String getModelname() {
        return modelname;
    }

    /**
     * Gets the engine displacement of this car model.
     *
     * @return The engine displacement.
     */
    public BigDecimal getDisplacement() {
        return displacement;
    }

    /**
     * Gets the number of cylinders in the engine of this car model.
     *
     * @return The number of cylinders.
     */
    public Integer getCylinders() {
        return cylinders;
    }

    /**
     * Returns a string representation of this car model.
     *
     * @return A string that represents the car model, including its details.
     */
    @Override
    public String toString() {
        return ("Model Details: \n" +
                "Model Id: " + modelid + "\n" +
                "Manufacturer: " + manufacturer + "\n" +
                "Series=" + series + "\n" +
                "Model Name" + modelname + "\n" +
                "Displacement=" + displacement + "\n" +
                "Cylinders=" + cylinders);
    }
}
