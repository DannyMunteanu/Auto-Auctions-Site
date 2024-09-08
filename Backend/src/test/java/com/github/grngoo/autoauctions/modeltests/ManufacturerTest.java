package com.github.grngoo.autoauctions.modeltests;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.grngoo.autoauctions.models.Manufacturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Manufacturer class. Verifies the behavior of constructors, getters, setters,
 * and the toString method.
 *
 * @author danielmunteanu
 */
public class ManufacturerTest {

  private Manufacturer manufacturer;

  @BeforeEach
  public void setUp() {
    manufacturer = new Manufacturer();
  }

  @Test
  @DisplayName("Test default constructor initializes fields to null")
  public void testDefaultConstructor() {
    assertThat(manufacturer.getMake()).isNull();
    assertThat(manufacturer.getOrigincountry()).isNull();
  }

  @Test
  @DisplayName("Test parameterized constructor initializes fields correctly")
  public void testParameterizedConstructor() {
    String make = "Toyota";
    String originCountry = "Japan";
    Manufacturer manufacturer = new Manufacturer(make, originCountry);
    assertThat(manufacturer.getMake()).isEqualTo(make);
    assertThat(manufacturer.getOrigincountry()).isEqualTo(originCountry);
  }

  @Test
  @DisplayName("Test setters and getters functionality")
  public void testSettersAndGetters() {
    manufacturer.setMake("Ford");
    manufacturer.setOrigincountry("USA");
    assertThat(manufacturer.getMake()).isEqualTo("Ford");
    assertThat(manufacturer.getOrigincountry()).isEqualTo("USA");
  }

  @Test
  @DisplayName("Test toString method format")
  public void testToString() {
    manufacturer = new Manufacturer("BMW", "Germany");
    String result = manufacturer.toString();
    assertThat(result)
        .isEqualTo("Manufacturer Details: \nMake: BMW\nCountry of Origin: Germany");
  }
}
