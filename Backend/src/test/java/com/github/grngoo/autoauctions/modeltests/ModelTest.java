package com.github.grngoo.autoauctions.modeltests;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.grngoo.autoauctions.models.Manufacturer;
import com.github.grngoo.autoauctions.models.Model;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Model class. Verifies the behavior of constructors, getters, and the toString
 * method.
 *
 * @author danielmunteanu
 */
public class ModelTest {

  private Manufacturer manufacturer;
  private Model model;

  @BeforeEach
  public void setUp() {
    manufacturer = new Manufacturer("Toyota", "Japan");
    model = new Model(manufacturer, "Series X", "Model Y", new BigDecimal("2.5"), 4);
  }

  @Test
  @DisplayName("Test Default Constructor of Model")
  public void testDefaultConstructor() {
    Model defaultModel = new Model();
    assertThat(defaultModel.getModelid()).isNull();
    assertThat(defaultModel.getManufacturer()).isNull();
    assertThat(defaultModel.getSeries()).isNull();
    assertThat(defaultModel.getModelname()).isNull();
    assertThat(defaultModel.getDisplacement()).isNull();
    assertThat(defaultModel.getCylinders()).isNull();
  }

  @Test
  @DisplayName("Test Parameterized Constructor of Model")
  public void testParameterizedConstructor() {
    assertThat(model.getManufacturer()).isEqualTo(manufacturer);
    assertThat(model.getSeries()).isEqualTo("Series X");
    assertThat(model.getModelname()).isEqualTo("Model Y");
    assertThat(model.getDisplacement()).isEqualTo(new BigDecimal("2.5"));
    assertThat(model.getCylinders()).isEqualTo(4);
  }

  @Test
  @DisplayName("Test Getters of Model")
  public void testGetters() {
    assertThat(model.getManufacturer()).isEqualTo(manufacturer);
    assertThat(model.getSeries()).isEqualTo("Series X");
    assertThat(model.getModelname()).isEqualTo("Model Y");
    assertThat(model.getDisplacement()).isEqualTo(new BigDecimal("2.5"));
    assertThat(model.getCylinders()).isEqualTo(4);
  }

  @Test
  @DisplayName("Test toString Method of Model")
  public void testToString() {
    String expectedString =
        "Model Details: \n"
            + "Model Id: null\n"
            + "Manufacturer: "
            + manufacturer
            + "\n"
            + "Series=Series X\n"
            + "Model NameModel Y\n"
            + "Displacement=2.5\n"
            + "Cylinders=4";
    assertThat(model.toString()).isEqualTo(expectedString);
  }
}
