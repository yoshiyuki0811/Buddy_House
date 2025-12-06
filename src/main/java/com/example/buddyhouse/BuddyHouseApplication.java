package com.example.buddyhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BuddyHouseApplication {

	public static void main(String[] args) {

		SpringApplication.run(BuddyHouseApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hash = encoder.encode("password123");
		System.out.println(hash);
	}


}
