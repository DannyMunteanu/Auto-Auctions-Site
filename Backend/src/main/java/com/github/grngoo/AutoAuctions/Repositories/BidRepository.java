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
     * @param user entity of user linked to bid.
     * @return all bids related to a certain user.
     */
    List<Bid> findByUserUsername(Users user);

    /**
     * Finds all bids for a specific listing.
     *
     * @param listing the listing for which bids are to be retrieved
     * @return a list of bids for the given listing
     */
    List<Bid> findByListing(Listing listing);

    /**
     * Finds the highest bid for a specific listing.
     *
     * @param listing the listing for which the highest bid is to be retrieved.
     * @return an optional containing the highest bid for the given listing, or empty if no bids are found.
     */
    @Query("SELECT b FROM Bid b WHERE b.listing = :listing ORDER BY b.amount DESC LIMIT 1")
    Optional<Bid> findHighestBidForListing(@Param("listing") Listing listing);
}
