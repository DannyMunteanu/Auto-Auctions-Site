package com.github.grngoo.AutoAuctions.Services;

import com.github.grngoo.AutoAuctions.Models.Users;
import com.github.grngoo.AutoAuctions.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Custom implementation of spring security UserDetailsService.
 * In order to use custom User not default.
 *
 * @author danielmunteanu
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    /**
     * Validates that a given user exists.
     * Loads the user entity via username.
     * Sets user details for spring security with default authorisation via entity attributes.
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
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
