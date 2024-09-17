package com.github.grngoo.autoauctions.controllers;

import com.github.grngoo.autoauctions.dtos.ListingDto;
import com.github.grngoo.autoauctions.models.Listing;
import com.github.grngoo.autoauctions.security.JwtUtility;
import com.github.grngoo.autoauctions.services.ListingService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for endpoints related to listing functionality.
 *
 * @author danielmunteanu
 */
@RestController
@RequestMapping("api/listing")
public class ListingController {

  @Autowired private ListingService listingService;

  @Autowired private JwtUtility jwtUtility;

  /**
   * Find a specific listing based on the registration of the car.
   *
   * @param registration unique id for car contained within a listing.
   * @return A response with listing entity and status code.
   */
  @GetMapping("/public/specified")
  public ResponseEntity<Listing> specificListing(@Valid @RequestParam String registration) {
    try {
      Listing listing = listingService.findListingByReg(registration);
      return new ResponseEntity<>(listing, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  /**
   * Search and filter listings based on given parameters. If no filters given every listing
   * returned. If filters given, a filtered response is given. I.e. all listing within a certain
   * reserve range.
   *
   * @param listingDto DTO containing time, reserve and optional username params(filters).
   * @return A result all desired listing based on filters.
   */
  @PostMapping("/public/search")
  public ResponseEntity<List<Listing>> searchListings(@Valid @RequestBody ListingDto listingDto) {
    try {
      return new ResponseEntity<>(
          listingService.filterListing(listingDto), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  /**
   * Create a new listing and add to database. Only allows an authenticated user to create a listing
   * for their account.
   *
   * @param authorizationHeader contains the string for JWT token.
   * @param listingDto All parameters required to construct a listing entity.
   * @return String status either confirmation or error code.
   */
  @PostMapping("/add")
  public ResponseEntity<Listing> addListing(
      @RequestHeader("Authorization") String authorizationHeader,
      @Valid @RequestBody ListingDto listingDto) {
    try {
      String token = authorizationHeader.substring(7);
      if (jwtUtility.validateToken(token, listingDto.getUsername())) {
        return new ResponseEntity<>(listingService.saveListing(listingDto), HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  /**
   * Delete a listing from database Only allows authenticated users to delete their own listings.
   *
   * @param authorizationHeader contains the string for JWT token.
   * @param listingDto requires the listingId and username.
   * @return Status of operation indicating successful/unsuccessful.
   */
  @PostMapping("/delete")
  public ResponseEntity<String> deleteListing(
      @RequestHeader("Authorization") String authorizationHeader,
      @Valid @RequestBody ListingDto listingDto) {
    try {
      String token = authorizationHeader.substring(7);
      if (jwtUtility.validateToken(token, listingDto.getUsername())) {
        listingService.deleteListing(listingDto);
        return ResponseEntity.ok("Listing #" + listingDto.getListingId() + " successfully deleted");
      }
      return ResponseEntity.status(403).body("Unauthorised request provide valid credentials");
    } catch (Exception e) {
      return ResponseEntity.status(409).body("Unable to delete listing due to invalid ID input");
    }
  }
}
