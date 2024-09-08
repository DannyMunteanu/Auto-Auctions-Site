package com.github.grngoo.autoauctions.repositories;

import com.github.grngoo.autoauctions.models.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Interface for Users.
 *
 * @author danielmunteanu
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

  /**
   * Find a user by their email address. Will help with user account recovery.
   *
   * @param email the email address of the user
   * @return an Optional containing the user if found, or empty if not found
   */
  Optional<Users> findByEmail(String email);

  /**
   * Find a user by their telephone number. Will help with user account recovery.
   *
   * @param telephone the telephone number of the user
   * @return an Optional containing the user if found, or empty if not found
   */
  Optional<Users> findByTelephone(String telephone);
}
