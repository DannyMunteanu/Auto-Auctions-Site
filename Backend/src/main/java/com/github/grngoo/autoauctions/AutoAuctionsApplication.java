package com.github.grngoo.autoauctions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class fot spring backend.
 *
 * @author danielmunteanu
 */
@SpringBootApplication
@EnableScheduling
public class AutoAuctionsApplication {

  /**
   * Main method run to start application.
   *
   * @param args N/A.
   */
  public static void main(String[] args) {
    SpringApplication.run(AutoAuctionsApplication.class, args);
  }
}
