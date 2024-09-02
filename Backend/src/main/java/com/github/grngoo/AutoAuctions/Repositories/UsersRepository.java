package com.github.grngoo.AutoAuctions.Repositories;

import com.github.grngoo.AutoAuctions.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository Interface for Users
 *
 * @author danielmunteanu
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    /**
     * Find a user by their email address.
     * Will help with user account recovery.
     *
     * @param email the email address of the user
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<Users> findByEmail(String email);

    /**
     * Find a user by their telephone number.
     * Will help with user account recovery.
     *
     * @param telephone the telephone number of the user
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<Users> findByTelephone(String telephone);
}
