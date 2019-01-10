package com.example.salvo2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalTime;
import java.util.Date;

@SpringBootApplication
public class Salvo2Application {

	public static void main(String[] args) {
		SpringApplication.run(Salvo2Application.class, args);
	}

	@Bean
	public CommandLineRunner initPlayerData(PlayerRepository repository) {
		return (args) -> {
			repository.save(new Player ("Jack", "Bauer", "j.bauer@ctu.gov"));
			repository.save(new Player ("Chloe", "O'Brian", "c.brian@ctu.gov"));
			repository.save(new Player ("Kim", "Bauer", "kim_bauer@gmail.com"));
			repository.save(new Player ("David", "Palmer" , "t.almeida@ctu.gov"));

		};
	}

	@Bean
	public CommandLineRunner initGameData(GameRepository repository) {
		return (args) -> {
			Date date = new Date();
			repository.save(new Game (date));
			repository.save(new Game ( Date.from(date.toInstant().plusSeconds(3600))));
			repository.save(new Game ( Date.from(date.toInstant().plusSeconds(7200))));


		};
	}

}

