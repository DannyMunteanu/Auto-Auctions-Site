package com.github.grngoo.autoauctions.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Utility class for generating and validating JSON Web Tokens.
 *
 * @author danielmunteanu
 */
@Component
public class JwtUtility {

  @Value("${env.JWT_SECRET_KEY}")
  private String secretKey;

  /**
   * Generates a JWT token with the given username.
   *
   * @param username the username to include in the token
   * @return the generated JWT token
   */
  public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, username);
  }

  /**
   * Extracts the username from the JWT token.
   *
   * @param token the JWT token
   * @return the extracted username
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts the expiration date from the JWT token.
   *
   * @param token the JWT token
   * @return the expiration date
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Validates the JWT token against the given username.
   *
   * @param token the JWT token
   * @param username the username to validate against
   * @return true if the token is valid and the username matches, false otherwise
   */
  public Boolean validateToken(String token, String username) {
    final String extractedUsername = extractUsername(token);
    return (extractedUsername.equals(username) && !isTokenExpired(token));
  }

  /**
   * Extracts a claim from the JWT token.
   *
   * @param token the JWT token
   * @param claimsResolver a function to extract the claim from the JWT claims
   * @param <T> the type of the claim to extract
   * @return the extracted claim
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extracts all claims from the JWT token.
   *
   * @param token the JWT token
   * @return the claims contained in the token
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

  /**
   * Checks if the JWT token is expired.
   *
   * @param token the JWT token
   * @return true if the token is expired, false otherwise
   */
  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Creates a JWT token with the specified claims and subject.
   *
   * @param claims the claims to include in the token
   * @param subject the subject of the token
   * @return the created JWT token
   */
  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(
            new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }
}
