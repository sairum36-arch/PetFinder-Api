package com.PetFinder.PetFinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class PetFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetFinderApplication.class, args);
	}

}
