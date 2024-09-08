package com.github.grngoo.autoauctions.services;

import com.github.grngoo.autoauctions.dtos.UsersDto;
import com.github.grngoo.autoauctions.models.Users;
import com.github.grngoo.autoauctions.repositories.UsersRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for User logic.
 *
 * @author danielmunteanu
 */
@Service
public class UsersService {

  @Autowired private UsersRepository usersRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  /**
   * Authenticate user based on username and password.
   *
   * @param usersDto contains username and password params of user.
   * @return Optional user containing the user if authentication is successful, otherwise empty.
   */
  public Users loginUser(UsersDto usersDto) {
    Optional<Users> userOpt = usersRepository.findById(usersDto.getUsername());
    if (userOpt.isPresent()) {
      Users user = userOpt.get();
      if (passwordEncoder.matches(usersDto.getPassword(), user.getPassword())) {
        return user;
      }
    }
    throw new IllegalArgumentException("Invalid username/password provided");
  }

  /**
   * Register a new user, ensuring unique username, email, and telephone.
   *
   * @param usersDto contains params to construct user entity representing user account.
   * @return newly added entity.
   */
  public Users registerUser(UsersDto usersDto) {
    Users user =
        new Users(
            usersDto.getUsername(),
            usersDto.getPassword(),
            usersDto.getEmail(),
            usersDto.getTelephone(),
            usersDto.getPostalCode(),
            usersDto.getCountry());
    avoidDuplicateDetails(user);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return usersRepository.save(user);
  }

  /**
   * Requires a valid username and password combination to delete user.
   *
   * @param usersDto Contains params (username and password) of user.
   */
  public void deleteUser(UsersDto usersDto) {
    Optional<Users> userOpt = usersRepository.findById(usersDto.getUsername());
    if (userOpt.isPresent()) {
      Users user = userOpt.get();
      if (passwordEncoder.matches(usersDto.getPassword(), user.getPassword())) {
        usersRepository.deleteById(user.getUsername());
        return;
      }
    }
    throw new IllegalArgumentException("Invalid username/password provided");
  }

  /**
   * Acts as a helper to registerUser. Validating all unique attributes aren't already in use.
   *
   * @param user entity for the new user account.
   * @throws IllegalArgumentException if the username, email, or telephone is already in use.
   */
  private void avoidDuplicateDetails(Users user) throws IllegalArgumentException {
    if (usersRepository.findById(user.getUsername()).isPresent()) {
      throw new IllegalArgumentException("Username is already taken.");
    }
    if (usersRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new IllegalArgumentException("Email is already in use.");
    }
    if (usersRepository.findByTelephone(user.getTelephone()).isPresent()) {
      throw new IllegalArgumentException("Telephone number is already in use.");
    }
  }
}
