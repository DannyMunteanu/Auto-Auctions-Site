package com.github.grngoo.AutoAuctions.Services;

import com.github.grngoo.AutoAuctions.Models.Users;
import com.github.grngoo.AutoAuctions.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authenticate user based on username and password.
     *
     * @param username the username of the account.
     * @param password the plain text password of the account.
     * @return Optional user containing the user if authentication is successful, otherwise empty.
     */
    public Optional<Users> loginUser(String username, String password) {
        Optional<Users> userOpt = usersRepository.findById(username);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /**
     * Register a new user, ensuring unique username, email, and telephone.
     *
     * @param user user entity representing user account.
     * @return newly added entity.
     */
    public Users registerUser(Users user) {
        avoidDuplicateDetails(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    /**
     * Requires a valid username and password combination to delete user.
     *
     * @param username name of user account.
     * @param rawPassword secret value of user.
     * @return boolean indicating if the user was deleted.
     */
    public boolean deleteUser(String username, String rawPassword) {
        Optional<Users> userOpt = usersRepository.findById(username);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                usersRepository.deleteById(user.getUsername());
                return true;
            }
        }
        return false;
    }

    /**
     * Acts as a helper to registerUser.
     * Validating all unique attributes aren't already in use.
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
