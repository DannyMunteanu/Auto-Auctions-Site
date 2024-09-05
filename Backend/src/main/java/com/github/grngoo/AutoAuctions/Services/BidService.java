package com.github.grngoo.AutoAuctions.Services;

import com.github.grngoo.AutoAuctions.DTOs.BidDto;
import com.github.grngoo.AutoAuctions.Models.Bid;
import com.github.grngoo.AutoAuctions.Models.Listing;
import com.github.grngoo.AutoAuctions.Repositories.BidRepository;
import com.github.grngoo.AutoAuctions.Repositories.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Save newly made bid.
     * Cannot place bid before start or after end of listing.
     *
     * @param bid new entity for bid.
     * @return nwley created bid entity once saved.
     */
    public Bid saveBid(Bid bid) {
        Listing listing = bid.getListing();
        if (bid.getTime().isAfter(listing.getEnd())) {
            throw new IllegalArgumentException("Bid was placed after the listing ended.");
        } else if (bid.getTime().isBefore(listing.getStart())) {
            throw new IllegalArgumentException("Bid was placed before the listing started.");
        } else {
            return bidRepository.save(bid);
        }
    }
}
