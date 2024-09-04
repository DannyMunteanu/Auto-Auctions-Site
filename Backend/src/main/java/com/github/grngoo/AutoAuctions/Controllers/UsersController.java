package com.github.grngoo.AutoAuctions.Controllers;

import com.github.grngoo.AutoAuctions.DTOs.UsersDto;
import com.github.grngoo.AutoAuctions.Models.Users;
import com.github.grngoo.AutoAuctions.Services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Endpoints for user functionality.
 *
 * @author danielmunteanu
 */
@RestController
@RequestMapping("/api/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    /**
     * Public endpoint for signing in.
     *
     * @param usersDto Data Transmissible Object to contain request body.
     * @return Response indicating status of request.
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UsersDto usersDto) {
        Optional<Users> userOpt = usersService.loginUser(usersDto.getUsername(), usersDto.getPassword());
        if (userOpt.isPresent()) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(401).body("Invalid Username");
        }
    }

    /**
     * Public endpoint for creating a new account.
     * @param usersDto Data Transmissible Object to contain request body.
     * @return Response indicating status of request.
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UsersDto usersDto) {
        Users user = new Users(
            usersDto.getUsername(),
            usersDto.getPassword(),
            usersDto.getEmail(),
            usersDto.getTelephone(),
            usersDto.getPostal_code(),
            usersDto.getCountry()
        );
        if (usersService.registerUser(user) != null){
            return ResponseEntity.ok("Account created successfully");
        } else {
            return ResponseEntity.status(401).body("Unable to make Account");
        }
    }

    /**
     * Endpoint for deleting an account.
     * @param usersDto Data Transmissible Object to contain request body.
     * @return Response indicating status of request.
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody UsersDto usersDto) {
        boolean accountDeleted = usersService.deleteUser(usersDto.getUsername(), usersDto.getPassword());
        if(accountDeleted) {
            return ResponseEntity.ok("Account deletion successful");
        } else {
            return ResponseEntity.status(401).body("Unable to delete account");
        }
    }
}
