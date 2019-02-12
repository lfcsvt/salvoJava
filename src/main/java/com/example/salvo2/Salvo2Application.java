package com.example.salvo2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
									  		ShipRepository shipRepository,
									  		SalvoRepository salvoRepository,
									  		ScoreRepository scoreRepository) {
		return (args) -> {
			Player p1 = new Player ("Jack Bauer", "j.bauer@ctu.gov", "24");
			Player p2 = new Player ("Chloe O'Brian", "c.brian@ctu.gov", "42");
			Player p3 = new Player ("Kim Bauer", "kim_bauer@gmail.com", "kb");
			Player p4 = new Player ("David Palmer" , "t.almeida@ctu.gov", "mole");
			Player p5 = new Player ("John Smith" , "j.smith@ctu.gov","john" );

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


			Salvo slv1 = new Salvo(1);
			slv1.setLocations(new ArrayList<String>(Arrays.asList("SF3")));
			gp1.makeSalvo(slv1);
			Salvo slv2 = new Salvo(2);
			slv2.setLocations(new ArrayList<String>(Arrays.asList("SA7")));
			gp1.makeSalvo(slv2);
			Salvo slv3 = new Salvo(3);
			slv3.setLocations(new ArrayList<String>(Arrays.asList("SH6")));
			gp1.makeSalvo(slv3);
			Salvo slv4 = new Salvo(4);
			slv4.setLocations(new ArrayList<String>(Arrays.asList("SD9")));
			gp1.makeSalvo(slv4);
			Salvo slv5 = new Salvo(5);
			slv5.setLocations(new ArrayList<String>(Arrays.asList("SJ8")));
			gp1.makeSalvo(slv5);

			Salvo slv6 = new Salvo(1);
			slv6.setLocations(new ArrayList<String>(Arrays.asList("SB10")));
			gp2.makeSalvo(slv6);
			Salvo slv7 = new Salvo(2);
			slv7.setLocations(new ArrayList<String>(Arrays.asList("SC10")));
			gp2.makeSalvo(slv7);
			Salvo slv8 = new Salvo(3);
			slv8.setLocations(new ArrayList<String>(Arrays.asList("SH3")));
			gp2.makeSalvo(slv8);
			Salvo slv9 = new Salvo(4);
			slv9.setLocations(new ArrayList<String>(Arrays.asList("SH2")));
			gp2.makeSalvo(slv9);
			Salvo slv10 = new Salvo(5);
			slv10.setLocations(new ArrayList<String>(Arrays.asList("SF6")));
			gp2.makeSalvo(slv10);

			Salvo slv11 = new Salvo(1);
			slv11.setLocations(new ArrayList<String>(Arrays.asList("SD10")));
			gp3.makeSalvo(slv11);
			Salvo slv12 = new Salvo(2);
			slv12.setLocations(new ArrayList<String>(Arrays.asList("SA3")));
			gp3.makeSalvo(slv12);
			Salvo slv13 = new Salvo(3);
			slv13.setLocations(new ArrayList<String>(Arrays.asList("SH3")));
			gp3.makeSalvo(slv13);
			Salvo slv14 = new Salvo(4);
			slv14.setLocations(new ArrayList<String>(Arrays.asList("SI7")));
			gp3.makeSalvo(slv14);
			Salvo slv15 = new Salvo(5);
			slv15.setLocations(new ArrayList<String>(Arrays.asList("SJ6")));
			gp3.makeSalvo(slv15);


			salvoRepository.save(slv1);
			salvoRepository.save(slv2);
			salvoRepository.save(slv3);
			salvoRepository.save(slv4);
			salvoRepository.save(slv5);
			salvoRepository.save(slv6);
			salvoRepository.save(slv7);
			salvoRepository.save(slv8);
			salvoRepository.save(slv9);
			salvoRepository.save(slv10);
			salvoRepository.save(slv11);
			salvoRepository.save(slv12);
			salvoRepository.save(slv13);
			salvoRepository.save(slv14);
			salvoRepository.save(slv15);

			Score sc1 = new Score(p1, g1, 1.0);
			Score sc2 = new Score(p2, g1, 0.0);
			Score sc3 = new Score(p1, g2, 0.5);
			Score sc4 = new Score(p2, g2, 0.5);
			Score sc5 = new Score(p2, g3, 1.0);
			Score sc6 = new Score(p3, g3, 0.0);
			Score sc7 = new Score(p2, g1, 1.0);
			Score sc8 = new Score(p1, g1, 1.0);
			Score sc9 = new Score(p1, g1, 0.5);
			Score sc10 = new Score(p1, g1, 0.0);


			scoreRepository.save(sc1);
			scoreRepository.save(sc2);
			scoreRepository.save(sc3);
			scoreRepository.save(sc4);
			scoreRepository.save(sc5);
			scoreRepository.save(sc6);
			scoreRepository.save(sc7);
			scoreRepository.save(sc8);
			scoreRepository.save(sc9);
			scoreRepository.save(sc10);


		};
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userName-> {
			System.out.println(userName);
			Player player = playerRepository.findByUserName(userName);
			if (player != null) {
				return new User(player.getUserName(), player.getUserPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + userName);
			}
		});
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()

				.antMatchers("/web/games.html").permitAll()
				.antMatchers("/web/styles/games.css").permitAll()
				.antMatchers("/web/scripts/games.js").permitAll()
				.antMatchers("/api/games").permitAll()
				.antMatchers("/api/login").permitAll()
				.antMatchers("/api/leaderboard").permitAll()
				.antMatchers("/api/game_view/*").hasAuthority("USER");
//				.antMatchers("/**").hasAuthority("USER");

		http.formLogin()
				.usernameParameter("userName")
				.passwordParameter("userPassword")
				.loginPage("/api/login");

		http.logout()
				.logoutUrl("/api/logout");


		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}
