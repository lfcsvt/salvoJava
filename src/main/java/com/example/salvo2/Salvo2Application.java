package com.example.salvo2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class Salvo2Application {

	public static void main(String[] args) {
		SpringApplication.run(Salvo2Application.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
											GameRepository gameRepository,
											GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository) {
		return (args) -> {
			Player p1 = new Player ("Jack", "Bauer", "j.bauer@ctu.gov");
			Player p2 = new Player ("Chloe", "O'Brian", "c.brian@ctu.gov");
			Player p3 = new Player ("Kim", "Bauer", "kim_bauer@gmail.com");
			Player p4 = new Player ("David", "Palmer" , "t.almeida@ctu.gov");
			Player p5 = new Player ("John", "Smith" , "j.smith@ctu.gov");

			playerRepository.save(p1);
			playerRepository.save(p2);
			playerRepository.save(p3);
			playerRepository.save(p4);
			playerRepository.save(p5);

			Date date = new Date();
			Game g1 = new Game (date);
			Game g2 = new Game (Date.from(date.toInstant().plusSeconds(3600)));
			Game g3 = new Game (Date.from(date.toInstant().plusSeconds(7200)));
			gameRepository.save(g1);
			gameRepository.save(g2);
			gameRepository.save(g3);

			GamePlayer gp1 = new GamePlayer(p1,g1);
			GamePlayer gp2 = new GamePlayer(p2,g1);
			GamePlayer gp3 = new GamePlayer(p3,g2);
			GamePlayer gp4 = new GamePlayer(p4,g2);
			GamePlayer gp5 = new GamePlayer(p5,g3);

			gamePlayerRepository.save(gp1);
			gamePlayerRepository.save(gp2);
			gamePlayerRepository.save(gp3);
			gamePlayerRepository.save(gp4);
			gamePlayerRepository.save(gp5);

			Ship ship1 = new Ship("Carrier");
			ship1.setLocations(new ArrayList<String>(Arrays.asList("B10", "C10", "D10","E10","F10")));
			gp1.makeShip(ship1);
			Ship ship2 = new Ship("Submarine");
			ship2.setLocations(new ArrayList<String>(Arrays.asList("A2", "A3", "A4")));
			gp1.makeShip(ship2);
			Ship ship3 = new Ship("Destroyer");
			ship3.setLocations(new ArrayList<String>(Arrays.asList("H2", "H3", "H4")));
			gp1.makeShip(ship3);
			Ship ship4 = new Ship("Battleship");
			ship4.setLocations(new ArrayList<String>(Arrays.asList("C2", "D2", "E2", "F2")));
			gp1.makeShip(ship4);
			Ship ship5 = new Ship("Patrol Boat");
			ship5.setLocations(new ArrayList<String>(Arrays.asList("J2", "J3")));
			gp1.makeShip(ship5);

			Ship ship6 = new Ship("Carrier");
			ship6.setLocations(new ArrayList<String>(Arrays.asList("I2", "I3", "I4","I5","I6")));
			gp2.makeShip(ship6);
			Ship ship7 = new Ship("Submarine");
			ship7.setLocations(new ArrayList<String>(Arrays.asList("F2", "F3", "F4")));
			gp2.makeShip(ship7);
			Ship ship8 = new Ship("Destroyer");
			ship8.setLocations(new ArrayList<String>(Arrays.asList("C2", "C3", "C4")));
			gp2.makeShip(ship8);
			Ship ship9 = new Ship("Battleship");
			ship9.setLocations(new ArrayList<String>(Arrays.asList("D2", "D3", "D4", "D5")));
			gp2.makeShip(ship9);
			Ship ship10 = new Ship("Patrol Boat");
			ship10.setLocations(new ArrayList<String>(Arrays.asList("H2", "H3")));
			gp2.makeShip(ship10);

			Ship ship11 = new Ship("Carrier");
			ship11.setLocations(new ArrayList<String>(Arrays.asList("I2", "I3", "I4","I5","I6")));
			gp3.makeShip(ship11);
			Ship ship12 = new Ship("Submarine");
			ship12.setLocations(new ArrayList<String>(Arrays.asList("F2", "F3", "F4")));
			gp3.makeShip(ship12);
			Ship ship13 = new Ship("Destroyer");
			ship13.setLocations(new ArrayList<String>(Arrays.asList("C2", "C3", "C4")));
			gp3.makeShip(ship13);
			Ship ship14 = new Ship("Battleship");
			ship14.setLocations(new ArrayList<String>(Arrays.asList("E2", "E3", "E4", "E5")));
			gp3.makeShip(ship14);
			Ship ship15 = new Ship("Patrol Boat");
			ship15.setLocations(new ArrayList<String>(Arrays.asList("B8", "B9")));
			gp3.makeShip(ship15);

			shipRepository.save(ship1);
			shipRepository.save(ship2);
			shipRepository.save(ship3);
			shipRepository.save(ship4);
			shipRepository.save(ship5);
			shipRepository.save(ship6);
			shipRepository.save(ship7);
			shipRepository.save(ship8);
			shipRepository.save(ship9);
			shipRepository.save(ship10);
			shipRepository.save(ship11);
			shipRepository.save(ship12);
			shipRepository.save(ship13);
			shipRepository.save(ship14);
			shipRepository.save(ship15);
		};
	}
}

