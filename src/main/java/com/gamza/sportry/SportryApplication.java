package com.gamza.sportry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SportryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportryApplication.class, args);
	}

}
