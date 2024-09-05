package com.github.grngoo.AutoAuctions.Services;

import com.github.grngoo.AutoAuctions.DTOs.ListingDto;
import com.github.grngoo.AutoAuctions.Models.Car;
import com.github.grngoo.AutoAuctions.Models.Listing;
import com.github.grngoo.AutoAuctions.Repositories.CarRepository;
import com.github.grngoo.AutoAuctions.Repositories.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for Listing.
 *
 * @author danielmunteanu
 */
@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    /**
     * Find a specific listing based on the registration of the car.
     * @param registration unique value ID for each car.
     * @return the listing entity representing listing.
     */
    public Listing findListingByReg(String registration) {
        return listingRepository.findByCarRegistration(registration).get();
    }

    /**
     * Find all listings and filter based on input.
     * If there is no input then return all listing.
     * Otherwise, returns an intersection set of filtered results from repository.
     * Note: requires start/end time, min/max reserve.
     * User filter only applied if specified.
     *
     * @param listingDto Contains all request param for search filters
     * @return A set of listings filtered or unfiltered.
     */
    public List<Listing> filterListing(ListingDto listingDto) {
        List<Listing> listingSet = listingRepository.findAll();
        List<List<Listing>> allFilterSet = new ArrayList<>();
        allFilterSet.add(listingRepository.findByStartAfter(listingDto.getTime()[0]));
        allFilterSet.add(listingRepository.findByEndBefore(listingDto.getTime()[1]));
        allFilterSet.add(listingRepository.findByReserveGreaterThan(listingDto.getReserve()[0]));
        allFilterSet.add(listingRepository.findByReserveLessThan(listingDto.getReserve()[1]));
        if (listingDto.getUsername() != null) {
            allFilterSet.add(listingRepository.findByUserUsername(listingDto.getUsername()));
        }
        for (List<Listing> filterSet : allFilterSet) {
            listingSet.retainAll(filterSet);
        }
        return listingSet;
    }

    /**
     * Save a new listing to system and return saved entity.
     *
     * @param listing new listing to be added.
     * @return newly added entity.
     */
    public Listing saveListing(Listing listing) {
        Optional<Listing> existingListing = listingRepository.findByCarRegistration(listing.getCar().getRegistration());
        if (existingListing.isEmpty()) {
            return listingRepository.save(listing);
        } else {
            throw new IllegalArgumentException("This car is registered to a listing.");
        }
    }

    /**
     * Delete a specified listing from table.
     *
     * @param id unique value for each listing.
     */
    public void deleteListing(Long id) {
        if (listingRepository.findById(id).isPresent()) {
            listingRepository.deleteById(id);
        } else throw new IllegalArgumentException("The listing ID provided is invalid");
    }
}
