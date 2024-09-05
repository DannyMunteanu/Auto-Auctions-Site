package com.github.grngoo.AutoAuctions.Services;

import com.github.grngoo.AutoAuctions.Models.Car;
import com.github.grngoo.AutoAuctions.Repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Find all cars based on type of model.
     *
     * @return all cars.
     */
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    /**
     * Find cars based on type of model.
     *
     * @param modelId unique Id for each model.
     * @return all cars of exact model.
     */
    public List<Car> findByModel(Long modelId) {
        return carRepository.findByModelModelid(modelId);
    }

    /**
     * Find all cars of a certain color.
     *
     * @param color of car.
     * @return All cars of mathcing color.
     */
    public List<Car> findByColor(String color) {
        return carRepository.findByColor(color);
    }

    /**
     * Find all cars within a range of miles.
     *
     * @param minMiles lowerbound
     * @param maxMiles upperbound
     * @return all cars between min and max number of miles.
     */
    public List<Car> findByMileage(Integer minMiles, Integer maxMiles) {
        return carRepository.findByMileageBetween(minMiles, maxMiles);
    }

    /**
     * Specifies desired number of owners
     *
     * @param minYear oldest year.
     * @param maxYear newest year.
     * @return cars within the specified range.
     */
    public List<Car> findByYear(Integer minYear, Integer maxYear) {
        return carRepository.findByYearBetween(minYear, maxYear);
    }

    /**
     * Specifies desired number of owners
     *
     * @param minPrevious Least amount of desired owners.
     * @param maxPrevious Limit for desired owners.
     * @return cars within the specified range.
     */
    public List<Car> findByPreviousOwners(Integer minPrevious, Integer maxPrevious) {
        return carRepository.findByPreviousownersBetween(minPrevious, maxPrevious);
    }

    /**
     * Add new car.
     *
     * @param car entity to be added.
     * @return newly added entity.
     */
    public Car saveCar(Car car){
        return carRepository.save(car);
    }

    /**
     * Deletes a car entity from system.
     * 
     * @param registration unique ID for each car.s
     */
    public void deleteCar(String registration){
        carRepository.deleteById(registration);
    }
}
