package com.github.grngoo.AutoAuctions.Controllers;

import com.github.grngoo.AutoAuctions.DTOs.UsersDto;
import com.github.grngoo.AutoAuctions.Models.Users;
import com.github.grngoo.AutoAuctions.Services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/login")
    public ResponseEntity<Users> loginUser(@Valid @RequestBody UsersDto usersDto) {
        try {
            return new ResponseEntity<Users>(usersService.loginUser(usersDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Public endpoint for creating a new account.
     * @param usersDto Data Transmissible Object to contain request body.
     * @return Response indicating status of request.
     */
    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@Valid @RequestBody UsersDto usersDto) {
        try {
            return new ResponseEntity<Users>(usersService.registerUser(usersDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Endpoint for deleting an account.
     * @param usersDto Data Transmissible Object to contain request body.
     * @return Response indicating status of request.
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody UsersDto usersDto) {
        try {
            usersService.deleteUser(usersDto);
            return ResponseEntity.ok(usersDto.getUsername() + "'s account successfully deleted");
        } catch (Exception e) {
            return ResponseEntity.status(409).body("Unable to delete account");
        }
    }
}
