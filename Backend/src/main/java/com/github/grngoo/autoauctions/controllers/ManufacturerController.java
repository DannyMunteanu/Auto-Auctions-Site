package com.github.grngoo.autoauctions.controllers;

import com.github.grngoo.autoauctions.dtos.ManufacturerDto;
import com.github.grngoo.autoauctions.models.Manufacturer;
import com.github.grngoo.autoauctions.services.ManufacturerService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoint for manufacturer functionality.
 *
 * @author danielmunteanu.
 */
@RestController
@RequestMapping("api/manufacturer")
public class ManufacturerController {

  @Autowired private ManufacturerService manufacturerService;

  /**
   * Find manufacturer by name.
   *
   * @param manufacturerDto contains params to filter by.
   * @return a list of all manufacturers.
   */
  @GetMapping("/specific")
  public ResponseEntity<Manufacturer> findManufacturer(
      @Valid @RequestBody ManufacturerDto manufacturerDto) {
    try {
      return new ResponseEntity<Manufacturer>(
          manufacturerService.findManufacturer(manufacturerDto), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  /**
   * Show all manufacturers. If filter given the show all from certain country.
   *
   * @param manufacturerDto Data Transferable Object to store request body params.
   * @return a list of manufacturers filtered/unfiltered.
   */
  @PostMapping("/search")
  public ResponseEntity<List<Manufacturer>> searchManufacturers(
      @Valid @RequestBody ManufacturerDto manufacturerDto) {
    try {
      return new ResponseEntity<List<Manufacturer>>(
          manufacturerService.filterManufacturers(manufacturerDto), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }
}
