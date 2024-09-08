package com.github.grngoo.autoauctions.security;

import com.github.grngoo.autoauctions.models.Users;
import com.github.grngoo.autoauctions.repositories.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter to validate JWT tokens and set authentication in the security context.
 *
 * @author danielmunteanu
 */
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired private final JwtUtility jwtUtility;

  @Autowired private final UsersRepository usersRepository;

  @Autowired private final JwtTokenBlacklist jwtTokenBlacklist;

  /**
   * Constructor for JwtRequestFilter.
   *
   * @param jwtUtility object for jwt logic.
   * @param usersRepository object fot user CRUD logic.
   * @param jwtTokenBlacklist object for blacklist logic.
   */
  public JwtRequestFilter(
      JwtUtility jwtUtility, UsersRepository usersRepository, JwtTokenBlacklist jwtTokenBlacklist) {
    this.jwtUtility = jwtUtility;
    this.usersRepository = usersRepository;
    this.jwtTokenBlacklist = jwtTokenBlacklist;
  }

  /**
   * Filters incoming HTTP requests to authenticate users based on JWT tokens. Extracts the JWT
   * token from the "Authorization" header and validates it. If the token is valid and not
   * blacklisted, it authenticates the user. By setting the authentication in the SecurityContext.
   * If the token is blacklisted or invalid, request is forbidden.
   *
   * @param request the HTTP request containing the JWT token in the "Authorization" header.
   * @param response the HTTP response.
   * @param chain the filter chain to continue processing the request.
   * @throws ServletException if an error occurs during request processing.
   * @throws IOException if an input or output error occurs.
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    final String authorizationHeader = request.getHeader("Authorization");
    String username = null;
    String jwt = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      username = jwtUtility.extractUsername(jwt);
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      if (jwtTokenBlacklist.searchBlacklist(jwt)) {
        System.out.println("Token is blacklisted.");
        chain.doFilter(request, response);
        return;
      }
      Optional<Users> userOpt = usersRepository.findById(username);
      if (userOpt.isPresent()) {
        Users user = userOpt.get();
        if (jwtUtility.validateToken(jwt, username)) {
          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(user, null, null);
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    }
    chain.doFilter(request, response);
  }
}
