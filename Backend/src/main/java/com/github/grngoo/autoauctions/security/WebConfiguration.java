package com.github.grngoo.autoauctions.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Spring MVC. Configures CORS settings for the application.
 *
 * @author danielmunteanu
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  /**
   * Adds CORS mappings to the registry.
   * Specified allowed origin for api request to backend.
   *
   * @param registry the CorsRegistry to configure.
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins("http://localhost:3000")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}
