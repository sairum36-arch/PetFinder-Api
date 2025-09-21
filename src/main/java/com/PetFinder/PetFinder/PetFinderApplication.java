package com.PetFinder.PetFinder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ComponentScan(basePackages = "com.PetFinder.PetFinder")
@SpringBootApplication
public class PetFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetFinderApplication.class, args);
	}



}
