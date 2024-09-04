package com.github.grngoo.AutoAuctions.Configurations;

import com.github.grngoo.AutoAuctions.Services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Configuration
 *
 * @author danielmunteanu
 */
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
            .csrf(AbstractHttpConfigurer::disable)
            //for now making all pages accessible for testing
            .authorizeHttpRequests(auths -> auths
                    .anyRequest().permitAll()
                    /*
                    .requestMatchers("/api/user/login", "/api/user/register").permitAll()
                    .anyRequest().authenticated()
                     */
            )
            /*.formLogin(form -> form
                    .loginPage("/login")
                    .permitAll()
            )*/
            .formLogin(AbstractHttpConfigurer::disable)//to be removed later
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

