package com.example.salvo2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Salvo2Application {

	public static void main(String[] args) {
		SpringApplication.run(Salvo2Application.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Player ("Jack", "Bauer", "j.bauer@ctu.gov"));
			repository.save(new Player ("Chloe", "O'Brian", "c.brian@ctu.gov"));
			repository.save(new Player ("Kim", "Bauer", "kim_bauer@gmail.com"));
			repository.save(new Player ("David", "Palmer" , "t.almeida@ctu.gov"));

		};
	}

}

