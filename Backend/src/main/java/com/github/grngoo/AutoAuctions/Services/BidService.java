package com.github.grngoo.AutoAuctions.Services;

import com.github.grngoo.AutoAuctions.DTOs.BidDto;
import com.github.grngoo.AutoAuctions.Models.Bid;
import com.github.grngoo.AutoAuctions.Models.Listing;
import com.github.grngoo.AutoAuctions.Models.Users;
import com.github.grngoo.AutoAuctions.Repositories.BidRepository;
import com.github.grngoo.AutoAuctions.Repositories.ListingRepository;
import com.github.grngoo.AutoAuctions.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for Bid.
 *
 * @author danielmunteanu
 */
@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UsersRepository usersRepository;

    /**
     * Find a specific Bid.
     *
     * @param id unique identifier for bid.
     * @return Specified bid or null object if not found.
     */
    public Optional<Bid> findBid(Long id) {
        return bidRepository.findById(id);
    }

    /**
     * Search through all bids. Show all bids if no filters in bidDto.
     * Else provide a filtered search of bids. Based on DTO.
     *
     * @param bidDto specifies filters regarding searching bids by user or associated listing.
     * @return A set of all bids after the intersection operation with filters if specified.
     */
    public List<Bid> filterBids(BidDto bidDto) {
        List<Bid> bidSet = bidRepository.findAll();
        List<List<Bid>> allFilterSet = new ArrayList<>();
        if (bidDto.getListingId() != null) {
            allFilterSet.add(bidRepository.findByUserUsername(bidDto.getUsername()));
        }
        if (bidDto.getListingId() != null) {
            allFilterSet.add(bidRepository.findByListingListingid(bidDto.getListingId()));
        }
        for (List<Bid> filterSet: allFilterSet) {
            bidSet.retainAll(filterSet);
        }
        return bidSet;
    }

    /**
     * Finds the highest bid for a specific listing.
     *
     * @param listingId unique identifier of listing entity,
     * @return Highest bid or null if no bids.
     */
    public Bid findHighestBid(Long listingId) {
        Optional<Listing> listing = listingRepository.findById(listingId);
        if (listing.isEmpty()) {
            throw new IllegalArgumentException("Invalid listing provided");
        }
        return bidRepository.findHighestBidForListing(listingId);
    }

    /**
     * Delete a bid.
     *
     * @param id identifier for bid.
     */
    public void deleteBid(Long id) {
        if (bidRepository.findById(id).isPresent()) {
            bidRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invalid ID provided");
        }
    }

    /**
     * Saves a new bid entity with given a DTO.
     * The bid must be placed within the listing period and must be higher than the current highest bid.
     *
     * @param bidDto container with params for the new bid entity to be saved.
     * @return the newly created bid entity once saved.
     * @throws IllegalArgumentException if the bid is placed outside the listing period or is lower than the current highest bid.
     */
    public Bid saveBid(BidDto bidDto) {
        Listing listing = listingRepository.findById(bidDto.getListingId()).get();
        Users user = usersRepository.findById(bidDto.getUsername()).get();
        Bid bid = new Bid(
                listing,
                bidDto.getTime(),
                bidDto.getAmount(),
                user
        );
        BigDecimal highestBid = findHighestBid(listing.getListingid()).getAmount();
        validateBidTime(bid, listing);
        validateBidAmount(bid, highestBid);
        return bidRepository.save(bid);
    }

    /**
     * Validates if the bid is within the listing's active time period.
     *
     * @param bid the bid to validate.
     * @param listing the listing associated with the bid.
     * @throws IllegalArgumentException if the bid is placed outside the listing's active time.
     */
    private void validateBidTime(Bid bid, Listing listing) {
        if (bid.getTime().isAfter(listing.getEnd())) {
            throw new IllegalArgumentException("Bid was placed after the listing ended.");
        }
        if (bid.getTime().isBefore(listing.getStart())) {
            throw new IllegalArgumentException("Bid was placed before the listing started.");
        }
    }

    /**
     * Validates if the bid amount is higher than the current highest bid.
     *
     * @param bid the bid to validate.
     * @param highestBid the current highest bid amount.
     * @throws IllegalArgumentException if the bid amount is lower than or equal to the highest bid.
     */
    private void validateBidAmount(Bid bid, BigDecimal highestBid) {
        if (highestBid != null && highestBid.compareTo(bid.getAmount()) >= 0) {
            throw new IllegalArgumentException("Current bid amount is less than or equal to the highest bid.");
        }
    }
}
