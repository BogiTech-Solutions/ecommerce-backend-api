package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EcommerceApplication.class);	
	public static void main(String[] args) {
		logger.info("Starting EcommerceApplication...");
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
