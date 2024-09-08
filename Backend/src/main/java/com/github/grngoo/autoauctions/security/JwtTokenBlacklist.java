package com.github.grngoo.autoauctions.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A component used to store tokens (not yet expired) that are invalidated after logging out. Also
 * checks any token is member of blacklist. As well as functionality to remove expired tokens (since
 * server will reject them).
 *
 * @author danielmunteanu
 */
@Component
public class JwtTokenBlacklist {

  @Autowired JwtUtility jwtUtility;

  private final Map<String, Date> jwtBlacklist = new HashMap<>();

  /**
   * Adds a token to blacklist so a token not yet expired can be invalidated.
   *
   * @param token JWT token (used to allow secure access to endpoints)
   */
  public void invalidateToken(String token) {
    Date expiration = jwtUtility.extractExpiration(token);
    jwtBlacklist.put(token, expiration);
  }

  /**
   * Check if a provided authentication token(if not already expired) is invalid.
   *
   * @param token JWT token (used to allow secure access to endpoints)
   * @return true if invalid else false (for valid tokens);
   */
  public boolean searchBlacklist(String token) {
    return jwtBlacklist.containsKey(token);
  }

  /**
   * Check for expired tokens in blacklist. Remove all expired tokens.
   * Provides better optimisation of blacklist.
   * Since number of tokens that need to be stored is reduced.
   * Needs to be frequently called for best results.
   */
  public void removeExpiredTokens() {
    Date now = new Date();
    jwtBlacklist.entrySet().removeIf(entry -> entry.getValue().before(now));
  }
}
