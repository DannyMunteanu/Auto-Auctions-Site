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
     * Find manufacturer by name.
     *
     * @return a list of all manufacturers.
     */
    @GetMapping("/specific")
    public ResponseEntity<Manufacturer> findManfacturer(@Valid @RequestBody ManufacturerDto manufacturerDto) {
        try {
            return new ResponseEntity<Manufacturer>(manufacturerService.findManufacturer(manufacturerDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Show all manufacturers.
     * If filter given the show all from certain country.
     *
     * @param manufacturerDto Data Transferrable Object to store request body params.
     * @return a list of manufacturers filtered/unfiltered.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Manufacturer>> searchManufacturers(@Valid @RequestBody ManufacturerDto manufacturerDto) {
        try {
            return new ResponseEntity<List<Manufacturer>>(manufacturerService.filterManufacturers(manufacturerDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
