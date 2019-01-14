package com.example.salvo2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Date;

@SpringBootApplication
public class Salvo2Application {

	public static void main(String[] args) {
		SpringApplication.run(Salvo2Application.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
											GameRepository gameRepository,
											GamePlayerRepository gamePlayerRepository) {
		return (args) -> {
			Player p1 = new Player ("Jack", "Bauer", "j.bauer@ctu.gov");
			Player p2 = new Player ("Chloe", "O'Brian", "c.brian@ctu.gov");
			Player p3 = new Player ("Kim", "Bauer", "kim_bauer@gmail.com");
			Player p4 = new Player ("David", "Palmer" , "t.almeida@ctu.gov");

			playerRepository.save(p1);
			playerRepository.save(p2);
			playerRepository.save(p3);
			playerRepository.save(p4);

			Date date = new Date();
			Game g1 = new Game (date);
			Game g2 = new Game (Date.from(date.toInstant().plusSeconds(3600)));
			Game g3 = new Game (Date.from(date.toInstant().plusSeconds(7200)));
			gameRepository.save(g1);
			gameRepository.save(g2);
			gameRepository.save(g3);

			GamePlayer gp1 = new GamePlayer(p1,g1);

			gamePlayerRepository.save(gp1);



		};
	}
}

