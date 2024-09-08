package com.github.grngoo.autoauctions.security;

import com.github.grngoo.autoauctions.repositories.UsersRepository;
import com.github.grngoo.autoauctions.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security Configuration.
 *
 * @author danielmunteanu
 */
@Configuration
public class SecurityConfiguration {

  @Autowired CustomUserDetailsService customUserDetailsService;

  @Autowired private JwtUtility jwtUtility;

  @Autowired private JwtTokenBlacklist jwtTokenBlackList;

  @Autowired private UsersRepository usersRepository;

  /**
   * Configures the security filter chain.
   *
   * @param http the HttpSecurity to modify
   * @return the SecurityFilterChain
   * @throws Exception if an error occurs while configuring the security filter chain
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auths ->
                auths
                    .requestMatchers(
                        "/api/user/login",
                        "/api/user/register",
                        "api/manufacturer/**",
                        "api/model/search",
                        "api/car/search",
                        "api/listing/public/**",
                        "api/bid/public/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .formLogin(form -> form.loginPage("/login").permitAll())
        .formLogin(AbstractHttpConfigurer::disable) // to be removed later
        .userDetailsService(customUserDetailsService)
        .logout(LogoutConfigurer::permitAll);
    http.addFilterBefore(
        new JwtRequestFilter(jwtUtility, usersRepository, jwtTokenBlackList),
        UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  /**
   * Create a new instance of passwordEncoder. Used in Services related to user to securely manage
   * passwords.
   *
   * @return new instance of passwordEncoder.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Scheduled process that removes expired tokens every 30 minutes. Helps optimise our Blacklist
   * (reduce overall size).
   */
  @Scheduled(fixedRate = 30 * 60 * 1000)
  public void cleanupBlacklist() {
    jwtTokenBlackList.removeExpiredTokens();
    System.out.println(
        "Every (30m/0.5h) Scheduled Process: Auto Removal of Expired Tokens in Blacklist");
  }
}
