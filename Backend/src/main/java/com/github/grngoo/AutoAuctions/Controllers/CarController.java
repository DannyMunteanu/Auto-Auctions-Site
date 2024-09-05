package com.github.grngoo.AutoAuctions.Controllers;

import com.github.grngoo.AutoAuctions.DTOs.CarDto;
import com.github.grngoo.AutoAuctions.Models.Car;
import com.github.grngoo.AutoAuctions.Models.Model;
import com.github.grngoo.AutoAuctions.Repositories.CarRepository;
import com.github.grngoo.AutoAuctions.Services.CarService;
import com.github.grngoo.AutoAuctions.Services.ModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing Car-related operations.
 * Provides endpoints to fetch, add, and delete cars.
 */
@RestController
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private CarRepository carRepository;

    /**
     * Retrieves a list of all cars.
     *
     * @return ResponseEntity containing the list of cars and HTTP status OK
     */
    @GetMapping("/all")
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(carService.findAll(), HttpStatus.OK);
    }

    /**
     * Retrieves cars based on model ID.
     *
     * @param carDto DTO containing the model ID
     * @return ResponseEntity containing the list of cars and HTTP status OK
     */
    @GetMapping("/model")
    public ResponseEntity<List<Car>> getCarsByModel(@Valid @RequestBody CarDto carDto) {
        return new ResponseEntity<>(carService.findByModel(carDto.getModelId()), HttpStatus.OK);
    }

    /**
     * Retrieves cars based on color.
     *
     * @param carDto DTO containing the color
     * @return ResponseEntity containing the list of cars and HTTP status OK
     */
    @GetMapping("/color")
    public ResponseEntity<List<Car>> getCarsByColor(@Valid @RequestBody CarDto carDto) {
        return new ResponseEntity<>(carService.findByColor(carDto.getColor()), HttpStatus.OK);
    }

    /**
     * Retrieves cars based on mileage range.
     *
     * @param carDto DTO containing mileage range
     * @return ResponseEntity containing the list of cars and HTTP status OK
     */
    @GetMapping("/mileage")
    public ResponseEntity<List<Car>> getCarByMileage(@Valid @RequestBody CarDto carDto) {
        Integer min = carDto.getMileage()[0];
        Integer max = carDto.getMileage()[1];
        return new ResponseEntity<>(carService.findByMileage(min, max), HttpStatus.OK);
    }

    /**
     * Retrieves cars based on year range.
     *
     * @param carDto DTO containing year range
     * @return ResponseEntity containing the list of cars and HTTP status OK
     */
    @GetMapping("/year")
    public ResponseEntity<List<Car>> getCarByYear(@Valid @RequestBody CarDto carDto) {
        Integer min = carDto.getYear()[0];
        Integer max = carDto.getYear()[1];
        return new ResponseEntity<>(carService.findByYear(min, max), HttpStatus.OK);
    }

    /**
     * Retrieves cars based on previous owners.
     *
     * @param carDto DTO containing previous owners range
     * @return ResponseEntity containing the list of cars and HTTP status OK
     */
    @GetMapping("/previous")
    public ResponseEntity<List<Car>> getCarByPreviousOwners(@Valid @RequestBody CarDto carDto) {
        Integer min = carDto.getPreviousOwners()[0];
        Integer max = carDto.getPreviousOwners()[1];
        return new ResponseEntity<>(carService.findByPreviousOwners(min, max), HttpStatus.OK);
    }

    /**
     * Adds a new car to the system.
     *
     * @param carDto DTO containing car details
     * @return ResponseEntity with the created car and HTTP status OK, or CONFLICT if the car already exists
     */
    @PostMapping("/add")
    public ResponseEntity<Car> addNewCar(@Valid @RequestBody CarDto carDto) {
        Model model = modelService.findModel(carDto.getModelId()).get();
        Car newCar = new Car(
            carDto.getRegistration(),
            model,
            carDto.getColor(),
            carDto.getMileage()[0],
            carDto.getYear()[0],
            carDto.getPreviousOwners()[0]
        );
        if (carRepository.findById(newCar.getRegistration()).isEmpty()) {
            return new ResponseEntity<>(carService.saveCar(newCar), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Deletes a car by its registration.
     *
     * @param carDto DTO containing car registration
     * @return ResponseEntity with a success message or a conflict message if the car doesn't exist
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deleteCar(@Valid @RequestBody CarDto carDto) {
        if (carRepository.findById(carDto.getRegistration()).isPresent()) {
            carService.deleteCar(carDto.getRegistration());
            return ResponseEntity.ok("Car with registration: " + carDto.getRegistration() + " deleted successfully");
        } else {
            return ResponseEntity.status(409).body("Unable to delete, provide a valid registration");
        }
    }
}