package com.github.grngoo.AutoAuctions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutoAuctionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoAuctionsApplication.class, args);
	}

}
