package com.github.grngoo.AutoAuctions.Services;

import com.github.grngoo.AutoAuctions.Models.Listing;
import com.github.grngoo.AutoAuctions.Repositories.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    ListingRepository listingRepository;

    /**
     * Finds a specified listing
     *
     * @param id unique attribute for Listing entities.
     * @return listing entity or null if not found.
     */
    public Optional<Listing> findListing(Long id) {
        return listingRepository.findById(id);
    }

    /**
     * Finds all listings created by an account.
     *
     * @param username unique name of account.
     * @return All listings with same associated user.
     */
    public List<Listing> findByUsername(String username) {
        return listingRepository.findByUserUsername(username);
    }

    /**
     * Finds all listings that begin for bidding after a specific time.
     *
     * @param startTime exact moment of time of start.
     * @return listings beginning after given time.
     */
    public List<Listing> findByStartAfter(LocalDateTime startTime) {
        return listingRepository.findByStartAfter(startTime);
    }

    /**
     * Finds all listing that end before a given date.
     *
     * @param endTime specified time limit for all desired listing.
     * @return all listings that are within the desired time limit.
     */
    public List<Listing> findByEndBefore(LocalDateTime endTime) {
        return listingRepository.findByEndBefore(endTime);
    }

    /**
     * Delete a specified listing from table.
     *
     * @param id unique value for each listing.
     */
    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }

    /**
     * Save a new listing to system and return saved entity.
     *
     * @param listing new listing to be added.
     * @return newly added entity.
     */
    public Listing saveListing(Listing listing) {
        return listingRepository.save(listing);
    }
}
