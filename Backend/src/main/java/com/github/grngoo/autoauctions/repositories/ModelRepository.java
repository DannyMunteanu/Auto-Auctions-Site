package com.github.grngoo.autoauctions.repositories;

import com.github.grngoo.autoauctions.models.Model;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Interface for Model.
 *
 * @author danielmunteanu
 */
@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

  /**
   * Query for all models related to specific make.
   *
   * @param manufacturerMake name of the make for manufactuerer entity.
   * @return list of all models associated with given manufacturer.
   */
  List<Model> findByManufacturerMake(String manufacturerMake);

  /**
   * Query for all models with given model name (Note can be different makes). I.e. Fiat 124 Spider
   * and Abarth 124 Spider
   *
   * @param modelname name of model in model entity.
   * @return list of all models with same model name.
   */
  List<Model> findByModelname(String modelname);

  /**
   * Query for all models of a particular series. I.e. all BMW 3 series models of e92 series.
   *
   * @param series subcategory of general model entity.
   * @return list of all models related to series.
   */
  List<Model> findBySeries(String series);

  /**
   * Query for all models of particular displacement range.
   *
   * @param minDisplacement The minimum displacement value.
   * @param maxDisplacement The maximum displacement value.
   * @return A list of Models with displacement between the specified values.
   */
  List<Model> findByDisplacementBetween(BigDecimal minDisplacement, BigDecimal maxDisplacement);

  /**
   * Query for all models of particular range of number of cylinders.
   *
   * @param minCylinders minimum number of engine cylinders/pistons.
   * @param maxCylinders maximum number of engine cylinders/pistons.
   * @return A list of Models with specified cylinder count.
   */
  List<Model> findByCylindersBetween(Integer minCylinders, Integer maxCylinders);
}
