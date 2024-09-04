package com.github.grngoo.AutoAuctions.Services;

import com.github.grngoo.AutoAuctions.Models.Bid;
import com.github.grngoo.AutoAuctions.Repositories.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Finds all bids made by a certain account.
     *
     * @param username name of account.
     * @return Entire bid history.
     */
    public List<Bid> findByUsername(String username) {
        return bidRepository.findByUserUsername(username);
    }

    /**
     * Finds all bids for a certain listing.
     *
     * @param listingid unique identifier of listing entity.
     * @return Bids related to specific listing.
     */
    public List<Bid> findByListing(Long listingid) {
        return bidRepository.findByListingListingid(listingid);
    }

    /**
     * Finds the highest bid for a specific listing.
     *
     * @param listingid unique identifier of listing entity,
     * @return Highest bid or null if no bids.
     */
    public Optional<Bid> findHighestBid(Long listingid) {
        return bidRepository.findHighestBidForListing(listingid);
    }

    /**
     * Delete a bid.
     *
     * @param id identifier for bid.
     */
    public void deleteBid(Long id) {
        bidRepository.deleteById(id);
    }

    /**
     * Save newly made bid.
     *
     * @param bid new entity for bid.
     * @return nwley created bid entity once saved.
     */
    public Bid saveBid(Bid bid) {
        return bidRepository.save(bid);
    }
}
