package com.github.grngoo.autoauctions.repositories;

import com.github.grngoo.autoauctions.models.Manufacturer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Interface for Manufacturer.
 *
 * @author danielmunteanu
 */
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, String> {

  /**
   * Finds all Manufacturers by their country of origin.
   *
   * @param origincountry the country of origin
   * @return a list of Manufacturer entities
   */
  List<Manufacturer> findByOrigincountry(String origincountry);
}
