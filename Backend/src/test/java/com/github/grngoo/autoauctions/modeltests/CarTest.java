package com.github.grngoo.autoauctions.modeltests;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.grngoo.autoauctions.models.Car;
import com.github.grngoo.autoauctions.models.Manufacturer;
import com.github.grngoo.autoauctions.models.Model;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Unit tests for the Car class. */
public class CarTest {

  private Model model;
  private Car car;

  /** Set up for each test. */
  @BeforeEach
  @DisplayName("Setup common test data")
  public void setUp() {
    Manufacturer manufacturer = new Manufacturer("Toyota", "Japan");
    model = new Model(manufacturer, "Camry", "LE", new BigDecimal("2.5"), 4);
    car = new Car("ABC123", model, "Red", 50000, 2018, 1);
  }

  @Test
  @DisplayName("Test Default Constructor")
  public void testDefaultConstructor() {
    Car car = new Car();
    assertThat(car.getRegistration()).isNull();
    assertThat(car.getModel()).isNull();
    assertThat(car.getColor()).isNull();
    assertThat(car.getMileage()).isNull();
    assertThat(car.getYear()).isNull();
    assertThat(car.getPreviousowners()).isNull();
  }

  @Test
  @DisplayName("Test Parameterized Constructor")
  public void testParameterizedConstructor() {
    assertThat(car.getRegistration()).isEqualTo("ABC123");
    assertThat(car.getModel()).isEqualTo(model);
    assertThat(car.getColor()).isEqualTo("Red");
    assertThat(car.getMileage()).isEqualTo(50000);
    assertThat(car.getYear()).isEqualTo(2018);
    assertThat(car.getPreviousowners()).isEqualTo(1);
  }

  @Test
  @DisplayName("Test Getters and Setters")
  public void testGettersAndSetters() {
    Car car = new Car();
    car.setRegistration("XYZ789");
    car.setModel(model);
    car.setColor("Blue");
    car.setMileage(75000);
    car.setYear(2015);
    car.setPreviousowners(2);

    assertThat(car.getRegistration()).isEqualTo("XYZ789");
    assertThat(car.getModel()).isEqualTo(model);
    assertThat(car.getColor()).isEqualTo("Blue");
    assertThat(car.getMileage()).isEqualTo(75000);
    assertThat(car.getYear()).isEqualTo(2015);
    assertThat(car.getPreviousowners()).isEqualTo(2);
  }

  @Test
  @DisplayName("Test toString Method")
  public void testToString() {
    String expectedString =
        "Car Details: \n"
            + "Registration: ABC123\n"
            + "Model: "
            + model
            + "\n"
            + "Color: Red\n"
            + "Mileage: 50000\n"
            + "Year: 2018\n"
            + "Previous Owners: 1\n";

    assertThat(car.toString()).isEqualTo(expectedString);
  }
}
