package com.github.grngoo.autoauctions.repositories;

import com.github.grngoo.autoauctions.models.Listing;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Interface for Listing.
 *
 * @author danielmunteanu
 */
@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

  /**
   * Find all Listings created by a certain user.
   *
   * @param userUsername account name associated with the listing.
   * @return all listings made by a certain user.
   */
  List<Listing> findByUserUsername(String userUsername);

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

  /**
   * Finds all listings where the reserve price is greater than the specified amount.
   *
   * @param reserve the reserve price to compare
   * @return a list of listings with reserve prices higher than the specified amount
   */
  List<Listing> findByReserveGreaterThan(BigDecimal reserve);

  /**
   * Finds all listings where the reserve price is less than the specified amount.
   *
   * @param reserve the reserve price to compare
   * @return a list of listings with reserve prices lower than the specified amount
   */
  List<Listing> findByReserveLessThan(BigDecimal reserve);

  /**
   * Finds if listing is present via a given car.
   *
   * @param registration specific registration of car.
   * @return Listing for car(reg) as contained object or null if not found.
   */
  Optional<Listing> findByCarRegistration(String registration);
}
