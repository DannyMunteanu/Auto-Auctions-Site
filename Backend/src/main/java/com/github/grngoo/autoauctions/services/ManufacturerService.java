package com.github.grngoo.autoauctions.services;

import com.github.grngoo.autoauctions.dtos.ManufacturerDto;
import com.github.grngoo.autoauctions.models.Manufacturer;
import com.github.grngoo.autoauctions.repositories.ManufacturerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service for Manufacturer.
 *
 * @author danielmunteanu
 */
@Service
public class ManufacturerService {

  @Autowired private ManufacturerRepository manufacturerRepository;

  /**
   * Search for a particular manufacturer.
   *
   * @param name of Manufacturer.
   * @return Manufacturer entity.
   */
  public Optional<Manufacturer> findManufacturer(String name) {
    return manufacturerRepository.findById(name);
  }

  /**
   * Retrieve all manufacturers.
   *
   * @return All manufacturers.
   */
  public Manufacturer findManufacturer(ManufacturerDto manufacturerDto) {
    return manufacturerRepository.findById(manufacturerDto.getName()).get();
  }

  /**
   * Search for all manufacturers. If country filter applied, search all of a certain nation.
   *
   * @param manufacturerDto contains param (origin country).
   * @return A collection of manufacturers.
   */
  public List<Manufacturer> filterManufacturers(ManufacturerDto manufacturerDto) {
    List<Manufacturer> manufacturersSet =
        manufacturerRepository.findAll(Sort.by(Sort.Order.asc("make")));
    if (manufacturerDto.getCountry() != null) {
      manufacturersSet.retainAll(
          manufacturerRepository.findByOrigincountry(manufacturerDto.getCountry()));
    }
    return manufacturersSet;
  }
}
