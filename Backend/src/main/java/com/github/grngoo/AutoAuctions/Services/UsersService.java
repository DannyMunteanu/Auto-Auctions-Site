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
     * Add user and encoding password for db security.
     *
     * @param user entity representing user account.
     * @return newly add entity.
     */
    public Users saveUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    /**
     * Find an account via its username.
     *
     * @param username unique name of account.
     * @return Account/user entity
     */
    public Optional<Users> findByUsername(String username) {
        return usersRepository.findById(username);
    }

    /**
     * Find an account via associated email
     *
     * @param email user email for account.
     * @return User entity or null if not found.
     */
    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    /**
     * Find an account via associated number
     *
     * @param telephone phone number for account.
     * @return User entity or null if not found.
     */
    public Optional<Users> findByTelephone(String telephone) {
        return usersRepository.findByTelephone(telephone);
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
}
