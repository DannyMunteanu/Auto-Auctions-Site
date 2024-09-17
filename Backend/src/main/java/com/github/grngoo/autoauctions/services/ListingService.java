package com.github.grngoo.autoauctions.services;

import com.github.grngoo.autoauctions.dtos.ListingDto;
import com.github.grngoo.autoauctions.models.Car;
import com.github.grngoo.autoauctions.models.Listing;
import com.github.grngoo.autoauctions.models.Users;
import com.github.grngoo.autoauctions.repositories.CarRepository;
import com.github.grngoo.autoauctions.repositories.ListingRepository;
import com.github.grngoo.autoauctions.repositories.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for Listing.
 *
 * @author danielmunteanu
 */
@Service
public class ListingService {

  @Autowired private ListingRepository listingRepository;

  @Autowired private CarRepository carRepository;

  @Autowired private UsersRepository usersRepository;

  /**
   * Find listing via ID.
   *
   * @param listingId unique value for each listing.
   * @return listing entity(all details for listing) contained in Optional.
   */
  public Optional<Listing> findListing(Long listingId) {
    return listingRepository.findById(listingId);
  }

  /**
   * Find a specific listing based on the registration of the car.
   *
   * @param registration unique value ID for each car.
   * @return the listing entity representing listing.
   */
  public Listing findListingByReg(String registration) {
    return listingRepository.findByCarRegistration(registration).get();
  }

  /**
   * Find all listings and filter based on input. If there is no input then return all listing.
   * Otherwise, returns an intersection set of filtered results from repository. Note: requires
   * start/end time, min/max reserve. User filter only applied if specified.
   *
   * @param listingDto Contains all request param for search filters
   * @return A set of listings filtered or unfiltered.
   */
  public List<Listing> filterListing(ListingDto listingDto) {
    List<List<Listing>> allFilterSet = new ArrayList<>();
    if (listingDto.getTime() != null) {
      allFilterSet.add(listingRepository.findByStartAfter(listingDto.getTime()[0]));
      allFilterSet.add(listingRepository.findByEndBefore(listingDto.getTime()[1]));
    }
    if (listingDto.getReserve() != null) {
      allFilterSet.add(listingRepository.findByReserveGreaterThan(listingDto.getReserve()[0]));
      allFilterSet.add(listingRepository.findByReserveLessThan(listingDto.getReserve()[1]));
    }
    if (listingDto.getUsername() != null) {
      allFilterSet.add(listingRepository.findByUserUsername(listingDto.getUsername()));
    }
    List<Listing> listingSet = listingRepository.findAll();
    for (List<Listing> filterSet : allFilterSet) {
      listingSet.retainAll(filterSet);
    }
    return listingSet;
  }

  /**
   * Save a new listing to system and return saved entity.
   *
   * @param listingDto container with parameters for new listing to be added.
   * @return newly added entity.
   */
  public Listing saveListing(ListingDto listingDto) {
    Optional<Listing> existingListing =
        listingRepository.findByCarRegistration(listingDto.getRegistration());
    if (existingListing.isEmpty()) {
      Car car = carRepository.findById(listingDto.getRegistration()).get();
      Users user = usersRepository.findById(listingDto.getUsername()).get();
      Listing newListing =
          new Listing(
              car,
              user,
              listingDto.getReserve()[0],
              listingDto.getTime()[0],
              listingDto.getTime()[1]);
      return listingRepository.save(newListing);
    } else {
      throw new IllegalArgumentException("This car is registered to a listing.");
    }
  }

  /**
   * Delete a specified listing from table.
   *
   * @param listingDto container for unique value for each listing.
   */
  public void deleteListing(ListingDto listingDto) {
    Long id = listingDto.getListingId();
    if (listingRepository.findById(id).isPresent()) {
      listingRepository.deleteById(id);
    } else {
      throw new IllegalArgumentException("The listing ID provided is invalid");
    }
  }
}
