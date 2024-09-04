package com.github.grngoo.AutoAuctions.Configurations;

import com.github.grngoo.AutoAuctions.Services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity to modify
     * @return the SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .permitAll()
            )
            .userDetailsService(customUserDetailsService)
            .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    /**
     * Create a new instance of passwordEncoder.
     * Used in Services related to user to securely manage passwords.
     *
     * @return new instance of passwordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

