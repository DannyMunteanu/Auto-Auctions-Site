package com.github.grngoo.AutoAuctions.Controllers;

import com.github.grngoo.AutoAuctions.DTOs.ManufacturerDto;
import com.github.grngoo.AutoAuctions.Models.Manufacturer;
import com.github.grngoo.AutoAuctions.Services.ManufacturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Endpoint for manufacturer functionality.
 *
 * @author danielmunteanu.
 */
@RestController
@RequestMapping("api/manufacturer")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    /**
     * Show all the stored manufacturers.
     *
     * @return a list of all manufacturers.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Manufacturer>> allManfacturers() {
        List<Manufacturer> manufacturers = manufacturerService.findAll();
        return new ResponseEntity<>(manufacturers, HttpStatus.OK);
    }

    /**
     * Show all manufacturers from certain country.
     *
     * @param manufacturerDto Data Transferrable Object to store request body params.
     * @return a list of manufacturers from one country.
     */
    @GetMapping("/country")
    public ResponseEntity<List<Manufacturer>> countryOfManufacturers(@Valid @RequestBody ManufacturerDto manufacturerDto) {
        List<Manufacturer> manufacturers = manufacturerService.findByOriginCountry(manufacturerDto.getCountry());
        return new ResponseEntity<>(manufacturers, HttpStatus.OK);
    }

    /**
     * Add a new manufacturer.
     *
     * @param manufacturerDto Data Transferrable Object to store request body params.
     * @return status if added or if manufacturer already exists
     */
    @PostMapping("/add")
    public ResponseEntity<String> addManufacturer(@Valid @RequestBody ManufacturerDto manufacturerDto) {
        Optional<Manufacturer> existingManufactuer = manufacturerService.findManufacturer(manufacturerDto.getName());
        if (existingManufactuer.isEmpty()) {
            Manufacturer manufacturer = new Manufacturer(manufacturerDto.getName(), manufacturerDto.getCountry());
            manufacturerService.saveManufacturer(manufacturer);
            return ResponseEntity.ok("New manufacturer added");
        } else {
            return ResponseEntity.status(401).body("Manufacturer already exists");
        }
    }
}
