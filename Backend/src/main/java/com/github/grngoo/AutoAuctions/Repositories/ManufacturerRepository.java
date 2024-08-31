package com.github.grngoo.AutoAuctions.Repositories;

import com.github.grngoo.AutoAuctions.Models.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, String> {
    /**
     *Custom JPQL Query finds all Manufacturers by their country of origin.
     *
     * @param origincountry the country of origin
     * @return a list of Manufacturer entities
     */
    List<Manufacturer> findByOrigincountry(String origincountry);
}
