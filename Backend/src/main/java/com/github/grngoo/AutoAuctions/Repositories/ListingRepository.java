package com.github.grngoo.AutoAuctions.Repositories;

import com.github.grngoo.AutoAuctions.Models.Listing;
import com.github.grngoo.AutoAuctions.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository Interface for Listing
 *
 * @author danielmunteanu
 */
@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    /**
     * Find all Listings created by a certain user.
     *
     * @param user associated with the listing.
     * @return all listings made by a certain user.
     */
    List<Listing> findByUserUsername(Users user);

    /**
     * Find listings that start after the specified time.
     *
     * @param start the start time
     * @return a list of listings that start after the specified time
     */
    List<Listing> findByStartAfter(LocalDateTime start);

    /**
     * Find listings that end before the specified time.
     *
     * @param end the end time
     * @return a list of listings that end before the specified time
     */
    List<Listing> findByEndBefore(LocalDateTime end);
}
