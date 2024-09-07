package com.github.grngoo.AutoAuctions.Security;

import com.github.grngoo.AutoAuctions.Models.Users;
import com.github.grngoo.AutoAuctions.Repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Filter to validate JWT tokens and set authentication in the security context.
 */
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private UsersRepository usersRepository;

    /**
     * Constructs a JwtRequestFilter with the specified JwtUtility and UsersRepository.
     *
     * @param jwtUtil the JwtUtility to use for token operations
     * @param usersRepository the UsersRepository to access user data
     */
    public JwtRequestFilter(JwtUtility jwtUtil, UsersRepository usersRepository) {
        this.jwtUtility = jwtUtil;
        this.usersRepository = usersRepository;
    }

    /**
     * Processes the request and validates the JWT token if present.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param chain the filter chain to pass the request and response
     * @throws ServletException if an error occurs during filtering
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtility.extractUsername(jwt);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Users> userOpt = usersRepository.findById(username);
            if (userOpt.isPresent()) {
                Users user = userOpt.get();
                if (jwtUtility.validateToken(jwt, username)) {
                    UsernamePasswordAuthenticationToken authenticationToken;
                    authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
