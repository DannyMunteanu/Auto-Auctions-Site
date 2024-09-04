package com.github.grngoo.AutoAuctions.Repositories;

import com.github.grngoo.AutoAuctions.Models.Bid;
import com.github.grngoo.AutoAuctions.Models.Listing;
import com.github.grngoo.AutoAuctions.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository Interface for Bid
 *
 * @author danielmunteanu
 */
@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    /**
     * Find entire bid history of a given user.
     *
     * @param userUsername unique name of user linked to bid.
     * @return all bids related to a certain user.
     */
    List<Bid> findByUserUsername(String userUsername);

    /**
     * Finds all bids for a specific listing.
     *
     * @param listingListingid the listing for which bids are to be retrieved
     * @return a list of bids for the given listing
     */
    List<Bid> findByListingListingid(Long listingListingid);

    /**
     * Finds the highest bid for a specific listing by listing ID.
     *
     * @param listingId the ID of the listing for which the highest bid is to be retrieved.
     * @return an optional containing the highest bid for the given listing, or empty if no bids are found.
     */
    @Query("SELECT b FROM Bid b WHERE b.listing.listingid = :listingId ORDER BY b.amount DESC")
    Optional<Bid> findHighestBidForListing(@Param("listingId") Long listingId);
}
