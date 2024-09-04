package com.github.grngoo.AutoAuctions.Services;

import com.github.grngoo.AutoAuctions.Models.Manufacturer;
import com.github.grngoo.AutoAuctions.Repositories.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for Manufacturer
 *
 * @author danielmunteanu
 */
@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

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
     * Retrieve all manufacturers
     *
     * @return All manufacturers.
     */
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll(Sort.by(Sort.Order.asc("make")));
    }

    /**
     * Search for all manufacturers of a certain nation.
     *
     * @param country origin country of manufacturer.
     * @return A collection of manufacturers.
     */
    public List<Manufacturer> findByOriginCountry(String country) {
        return manufacturerRepository.findByOrigincountry(country);
    }

    /**
     * Adds a new manufacturer.
     *
     * @param manufacturer to be added to database.
     * @return Manufacturer entity
     */
    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }
}
