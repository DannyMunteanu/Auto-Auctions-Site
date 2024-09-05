package com.github.grngoo.AutoAuctions.Controllers;

import com.github.grngoo.AutoAuctions.DTOs.ListingDto;
import com.github.grngoo.AutoAuctions.Models.Car;
import com.github.grngoo.AutoAuctions.Models.Listing;
import com.github.grngoo.AutoAuctions.Models.Users;
import com.github.grngoo.AutoAuctions.Repositories.CarRepository;
import com.github.grngoo.AutoAuctions.Repositories.UsersRepository;
import com.github.grngoo.AutoAuctions.Services.ListingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/listing")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UsersRepository usersRepository;

    /**
     * Find a specific listing based on the registration of the car.
     *
     * @param listingDto Data Transferrable Object (required registration in DTO).
     * @return A response with listing entity and status code.
     */
    @GetMapping("/specified")
    public ResponseEntity<Listing> specificListing(@Valid @RequestBody ListingDto listingDto) {
        Listing listing = listingService.findListingByReg(listingDto.getRegistration());
        return new ResponseEntity<Listing>(listing, HttpStatus.OK);
    }

    /**
     * Search and filter listings based on given parameters.
     * If no filters given every listing returned.
     * If filters given, a filtered response is given.
     * I.e all listing within a certain reserve range.
     *
     * @param listingDto DTO containing time, reserve and optional username params(filters).
     * @return A result all desired listing based on filters.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Listing>> searchListings(@Valid @RequestBody ListingDto listingDto) {
        return new ResponseEntity<List<Listing>>(listingService.filterListing(listingDto), HttpStatus.OK);
    }

    /**
     * Create a new listing and add to database.
     *
     * @param listingDto All paramaters required to construct a listing entity.
     * @return String status either confirmation or error code.
     */
    @PostMapping("/add")
    public ResponseEntity<String> addListing(@Valid @RequestBody ListingDto listingDto) {
        try {
            Car car = carRepository.findById(listingDto.getRegistration()).get();
            Users user = usersRepository.findById(listingDto.getUsername()).get();
            Listing newListing = new Listing(
                    car,
                    user,
                    listingDto.getReserve()[0],
                    listingDto.getTime()[0],
                    listingDto.getTime()[1]
            );
            Listing savedListing = listingService.saveListing(newListing);
            return ResponseEntity.ok("New listing with id: " + savedListing.getListingid() + " addded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(409).body("Unable to add listing due to input");
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
            listingService.deleteListing(listingDto.getListingId());
            return ResponseEntity.ok("Listing #" + listingDto.getListingId() + " successfully deleted");
        } catch (Exception e) {
            return ResponseEntity.status(409).body("Unable to delete listing due to invalid ID input");
        }
    }
}
