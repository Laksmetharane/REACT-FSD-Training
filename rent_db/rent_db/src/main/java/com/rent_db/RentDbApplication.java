package com.rent_db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class RentDbApplication {

	public static void main(String[] args) {

		SpringApplication.run(RentDbApplication.class, args);
	}

}
