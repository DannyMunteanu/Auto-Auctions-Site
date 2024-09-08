package com.github.grngoo.autoauctions.services;

import com.github.grngoo.autoauctions.models.Users;
import com.github.grngoo.autoauctions.repositories.UsersRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of spring security UserDetailsService. In order to use custom User not
 * default.
 *
 * @author danielmunteanu
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired private UsersRepository usersRepository;

  /**
   * Validates that a given user exists. Loads the user entity via username. Sets user details for
   * spring security with default authorisation via entity attributes.
   *
   * @param username unique id for users.
   * @return Details for user and authority.
   * @throws UsernameNotFoundException if user not found with username
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Users> usersOpt = usersRepository.findById(username);
    if (usersOpt.isEmpty()) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
    Users user = usersOpt.get();
    return User.builder().username(user.getUsername()).password(user.getPassword()).build();
  }
}
