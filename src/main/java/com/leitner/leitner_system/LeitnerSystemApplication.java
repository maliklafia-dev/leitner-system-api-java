package com.leitner.leitner_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.leitner.leitner_system")
public class LeitnerSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeitnerSystemApplication.class, args);
	}

}
