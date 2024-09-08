package com.github.grngoo.autoauctions.repositories;

import com.github.grngoo.autoauctions.models.Bid;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository Interface for Bid.
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
   * Finds the highest bid for a specific listing by listing ID using native SQL.
   *
   * @param listingId the ID of the listing for which the highest bid is to be retrieved.
   * @return the highest bid for the given listing, or null if no bids are found.
   */
  @Query(
      value = "SELECT * FROM bid WHERE listingid = :listingId ORDER BY amount DESC LIMIT 1",
      nativeQuery = true)
  Bid findHighestBidForListing(@Param("listingId") Long listingId);
}
