package com.github.grngoo.AutoAuctions.Services;

import com.github.grngoo.AutoAuctions.DTOs.CarDto;
import com.github.grngoo.AutoAuctions.Models.Car;
import com.github.grngoo.AutoAuctions.Models.Model;
import com.github.grngoo.AutoAuctions.Repositories.CarRepository;
import com.github.grngoo.AutoAuctions.Repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for Car(s).
 *
 * @author danielmunteanu
 */
@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ModelRepository modelRepository;

    /**
     * Find all cars and filter based on input.
     * If there is no input then return all cars.
     * Otherwise, returns an intersection set of filtered results from repository.
     * Note: requires mileage, year, previousowners ranges.
     * User filter only applied if specified.
     *
     * @param carDto Contains all request param for search filters
     * @return A set of cars filtered or unfiltered.
     */
    public List<Car> filterCars(CarDto carDto) {
        List<Car> carSet = carRepository.findAll();
        List<List<Car>> allFilterSet = new ArrayList<>();
        allFilterSet.add(carRepository.findByMileageBetween(carDto.getMileage()[0], carDto.getMileage()[1]));
        allFilterSet.add(carRepository.findByYearBetween(carDto.getYear()[0], carDto.getYear()[1]));
        allFilterSet.add(carRepository.findByPreviousownersBetween(carDto.getPreviousOwners()[0], carDto.getPreviousOwners()[1]));
        if(carDto.getColor() != null) {
            allFilterSet.add(carRepository.findByColor(carDto.getColor()));
        }
        if (carDto.getModelId() != null) {
            allFilterSet.add(carRepository.findByModelModelid(carDto.getModelId()));
        }
        for(List<Car> filterSet : allFilterSet) {
            carSet.retainAll(filterSet);
        }
        return carSet;
    }

    /**
     * Add new car.
     *
     * @param carDto container with params to construct entity to be added.
     * @return newly added entity.
     */
    public Car saveCar(CarDto carDto){
        Model model = modelRepository.findById(carDto.getModelId()).get();
        Car newCar = new Car(
                carDto.getRegistration(),
                model,
                carDto.getColor(),
                carDto.getMileage()[0],
                carDto.getYear()[0],
                carDto.getPreviousOwners()[0]
        );
        if (carRepository.findById(newCar.getRegistration()).isEmpty()) {
            return carRepository.save(newCar);
        } else {
            throw new IllegalArgumentException("Invalid car details provided");
        }
    }

    /**
     * Deletes a car entity from system.
     * 
     * @param carDto container with registration specified.
     */
    public void deleteCar(CarDto carDto){
        if (carRepository.findById(carDto.getRegistration()).isPresent()) {
            carRepository.deleteById(carDto.getRegistration());
        } else {
            throw new IllegalArgumentException("Invalid ID (registration) provided");
        }
    }
}
