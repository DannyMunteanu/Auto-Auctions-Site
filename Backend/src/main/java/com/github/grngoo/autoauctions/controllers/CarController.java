package com.github.grngoo.autoauctions.controllers;

import com.github.grngoo.autoauctions.dtos.CarDto;
import com.github.grngoo.autoauctions.models.Car;
import com.github.grngoo.autoauctions.services.CarService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing Car-related operations. Provides endpoints to fetch, add, and delete
 * cars.
 */
@RestController
@RequestMapping("/api/car")
public class CarController {

  @Autowired private CarService carService;

  /**
   * Search and filter car based on given parameters. If no filters given every car is returned. If
   * filters given, a filtered response is given. I.e. all cars within a certain reserve range.
   *
   * @param carDto DTO containing mileage, year, previous owners, color, model
   *     params(filters).
   * @return A result all desired cars based on filters.
   */
  @PostMapping("/search")
  public ResponseEntity<List<Car>> searchCars(@Valid @RequestBody CarDto carDto) {
    try {
      return new ResponseEntity<>(carService.filterCars(carDto), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  /**
   * Adds a new car to the system.
   *
   * @param carDto DTO containing car details
   * @return ResponseEntity with the created car and HTTP status OK or CONFLICT if the car exists.
   */
  @PostMapping("/add")
  public ResponseEntity<Car> addNewCar(@Valid @RequestBody CarDto carDto) {
    try {
      return new ResponseEntity<>(carService.saveCar(carDto), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  /**
   * Deletes a car by its registration within request DTO.
   *
   * @param carDto DTO containing car registration
   * @return ResponseEntity with a success message or a conflict message if the car doesn't exist
   */
  @PostMapping("/delete")
  public ResponseEntity<String> deleteCar(@Valid @RequestBody CarDto carDto) {
    try {
      carService.deleteCar(carDto);
      return ResponseEntity.ok(
          "Car with registration: " + carDto.getRegistration() + " deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.status(409).body("Unable to delete, provide a valid registration");
    }
  }
}
