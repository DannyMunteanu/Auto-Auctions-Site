package com.github.grngoo.AutoAuctions.Repositories;

import com.github.grngoo.AutoAuctions.Models.Car;
import com.github.grngoo.AutoAuctions.Models.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository Interface for Car
 *
 * @author danielmunteanu
 */
@Repository
public interface CarRepository extends JpaRepository<Car, String> {

    /**
     * Finds all cars of certain model
     *
     * @param model type of car (make, series etc)
     * @return All cars of same model.
     */
    List<Car> findByModelModelid (Model model);

    /**
     * Finds all cars of a certain color
     *
     * @param color Color of bodywork
     * @return All cars with same color
     */
    List<Car> findByColor (String color);

    /**
     * Find all cars within certain mileage range
     *
     * @param minMileage lower bound
     * @param maxMileage upper bound
     * @return All cars between range
     */
    List<Car> findByMileageBetween (Integer minMileage, Integer maxMileage);

    /**
     * Find all cars between year range
     * @param minYear lower bound
     * @param maxYear uppper bound
     * @return All cars between specified years
     */
    List<Car> findByYearBetween(Integer minYear, Integer maxYear);

    /**
     * Finds all cars with range specified for number of previous owners
     * @param minPreviousowners lower bound
     * @param maxPreviousowners upper bounc
     * @return All cars within specified number of preivous owners.
     */
    List<Car> findByPreviousownersBetween(Integer minPreviousowners, Integer maxPreviousowners);
}
