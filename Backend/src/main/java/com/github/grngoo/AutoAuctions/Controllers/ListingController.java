package com.github.grngoo.AutoAuctions.Controllers;

import com.github.grngoo.AutoAuctions.DTOs.ListingDto;
import com.github.grngoo.AutoAuctions.Models.Listing;
import com.github.grngoo.AutoAuctions.Services.ListingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/listing")
public class ListingController {

    @Autowired
    private ListingService listingService;

    /**
     * Find a specific listing based on the registration of the car.
     *
     * @param listingDto Data Transferrable Object (required registration in DTO).
     * @return A response with listing entity and status code.
     */
    @GetMapping("/specified")
    public ResponseEntity<Listing> specificListing(@Valid @RequestBody ListingDto listingDto) {
        try {
            Listing listing = listingService.findListingByReg(listingDto.getRegistration());
            return new ResponseEntity<Listing>(listing, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Search and filter listings based on given parameters.
     * If no filters given every listing returned.
     * If filters given, a filtered response is given.
     * I.e. all listing within a certain reserve range.
     *
     * @param listingDto DTO containing time, reserve and optional username params(filters).
     * @return A result all desired listing based on filters.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Listing>> searchListings(@Valid @RequestBody ListingDto listingDto) {
        try {
            return new ResponseEntity<List<Listing>>(listingService.filterListing(listingDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Create a new listing and add to database.
     *
     * @param listingDto All parameters required to construct a listing entity.
     * @return String status either confirmation or error code.
     */
    @PostMapping("/add")
    public ResponseEntity<Listing> addListing(@Valid @RequestBody ListingDto listingDto) {
        try {
            return  new ResponseEntity<Listing>(listingService.saveListing(listingDto), HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Delete a listing from database
     *
     * @param listingDto only requires the listingId
     * @return Status of operation indicating successful/unsuccessful.
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deleteListing(@Valid @RequestBody ListingDto listingDto) {
        try {
            listingService.deleteListing(listingDto);
            return ResponseEntity.ok("Listing #" + listingDto.getListingId() + " successfully deleted");
        } catch (Exception e) {
            return ResponseEntity.status(409).body("Unable to delete listing due to invalid ID input");
        }
    }
}
