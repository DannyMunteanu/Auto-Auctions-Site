package com.github.grngoo.autoauctions.controllers;

import com.github.grngoo.autoauctions.dtos.UsersDto;
import com.github.grngoo.autoauctions.models.Users;
import com.github.grngoo.autoauctions.security.JwtTokenBlacklist;
import com.github.grngoo.autoauctions.security.JwtUtility;
import com.github.grngoo.autoauctions.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoints for user functionality.
 *
 * @author danielmunteanu
 */
@RestController
@RequestMapping("/api/user")
public class UsersController {

  @Autowired private UsersService usersService;

  @Autowired private JwtUtility jwtUtility;

  @Autowired private JwtTokenBlacklist jwtTokenBlackList;

  /**
   * Public endpoint for signing in. Check user exists via service and get username. Then generate a
   * JWT Token
   *
   * @param usersDto Data Transmissible Object to contain request body.
   * @return Response indicating status of request.
   */
  @PostMapping("/login")
  public ResponseEntity<String> loginUser(@Valid @RequestBody UsersDto usersDto) {
    try {
      String username = usersService.loginUser(usersDto).getUsername();
      return ResponseEntity.ok(jwtUtility.generateToken(username));
    } catch (Exception e) {
      return ResponseEntity.status(409).body("Invalid username or password");
    }
  }

  /**
   * Endpoint for signing out a user. This invalidates a user's token via blacklist. Requiring users
   * to sign back in to access protected routes.
   *
   * @param authorizationHeader contains the string for JWT token.
   * @return Status of request if logout was successful or not.
   */
  @PostMapping("/logout")
  public ResponseEntity<String> logoutUser(
      @RequestHeader("Authorization") String authorizationHeader) {
    try {
      String token = authorizationHeader.substring(7);
      jwtTokenBlackList.invalidateToken(token);
      return ResponseEntity.ok("Successfully signed out");
    } catch (Exception e) {
      return ResponseEntity.status(409).body("Unable to logout, invalid user token provided");
    }
  }

  /**
   * Public endpoint for creating a new account.
   *
   * @param usersDto Data Transmissible Object to contain request body.
   * @return Response indicating status of request.
   */
  @PostMapping("/register")
  public ResponseEntity<Users> registerUser(@Valid @RequestBody UsersDto usersDto) {
    try {
      return new ResponseEntity<>(usersService.registerUser(usersDto), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  /**
   * Endpoint for deleting an account. Requires a JWT token to authenticate user. Ensures authorised
   * users can only delete their own accounts. Also invalidate token so deleted user cannot access
   * protected routes afterward.
   *
   * @param usersDto Data Transmissible Object to contain request body.
   * @param authorizationHeader contains the string for JWT token.
   * @return Response indicating status of request.
   */
  @PostMapping("/delete")
  public ResponseEntity<String> deleteUser(
      @Valid @RequestBody UsersDto usersDto,
      @RequestHeader("Authorization") String authorizationHeader) {
    try {
      String token = authorizationHeader.substring(7);
      if (jwtUtility.validateToken(token, usersDto.getUsername())) {
        usersService.deleteUser(usersDto);
        jwtTokenBlackList.invalidateToken(token);
        return ResponseEntity.ok("Account successfully deleted");
      } else {
        return ResponseEntity.status(401).body("Invalid credentials provided");
      }
    } catch (Exception e) {
      return ResponseEntity.status(409).body("Unable to delete account");
    }
  }
}
