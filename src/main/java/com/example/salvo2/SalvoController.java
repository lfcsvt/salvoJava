package com.example.salvo2;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private PlayerRepository playerRepo;
    @Autowired
    private GamePlayerRepository gamePlayerRepo;
    @Autowired
    private ShipRepository shipRepo;

    @Autowired
    private SalvoRepository salvoRepo;

    @Autowired
    private ScoreRepository scoreRepo;

    @RequestMapping("/games")
    public Map<String, Object> makeGameAuth (Authentication authentication) {
        Map<String, Object> createGame = new HashMap<>();
        if (notUser(authentication)) {
            createGame.put("loggedIn", null);
            createGame.put("games", getAllGames());
        } else {
            Player loggedUser = getLoggedUser(authentication);
            createGame.put("loggedIn", makePlayerDTO(loggedUser));
            createGame.put("games", getAllGames());

        }
        return createGame;
    }


    private List<Object> getAllGames() {
        return gameRepo.findAll()
                .stream()
                .map(game -> makeGameDTO(game))
                .collect(Collectors.toList());
    }
    private boolean notUser(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    private Player getLoggedUser(Authentication authentication){
        return !notUser(authentication)
                ? playerRepo.findByUserName(authentication.getName())
                : null;
    }

    private Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getcDate());
        dto.put("gamePlayers", getGamePlayer(game));
//        dto.put("allPScores", getAllScores());
        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("user", player.getUserName());
        dto.put("name", player.getFirstName());
        return dto;
    }

    public List<Object> getGamePlayer(Game game) {
        List<Object> gPlayerDataObject = new ArrayList<>();
        game.gamePlayers
                .stream()
                .forEach(gamePlayer -> {
                    gPlayerDataObject.add(new LinkedHashMap<String, Object>() {{
                                              put("gp_id", gamePlayer.getId());
                                              put("player", makePlayerDTO(gamePlayer.getPlayer()));
//                                              put("score", gamePlayer.getGPScore().getScore());
                                          }}
                    );
                });
        return gPlayerDataObject;
    }

    @RequestMapping("/game_view/{gPlayer_Id}")
    public Map<String, Object> getGameView(@PathVariable Long gPlayer_Id, Authentication authentication) {
        GamePlayer myGPlayer = gamePlayerRepo.findOne(gPlayer_Id);
//        if(myGPlayer.getPlayer() == loggedUser(authentication)) {
            Map<String, Object> gameViewDTO = new LinkedHashMap<String, Object>();
            gameViewDTO.put("Game_id", myGPlayer.getGame().getId());
            gameViewDTO.put("gPlayer_id", myGPlayer.getPlayer().getId());
            gameViewDTO.put("created", myGPlayer.getGame().getcDate());
            gameViewDTO.put("gamePlayer", getGamePlayer(myGPlayer.getGame()));
            gameViewDTO.put("ships", makeShipsDTO(myGPlayer));
            gameViewDTO.put("salvoes", getSPlayer(myGPlayer.getGame()));

            return gameViewDTO;
//        }
//        return new ResponseEntity<>(makeMap("Error", "You have to login"), HttpStatus.UNAUTHORIZED);
    }

    private List<Object> makeShipsDTO(GamePlayer myGPlayer) {
        List<Object> myGPShips = new ArrayList<>();
        myGPlayer.getAllShips().forEach(elem -> {
            myGPShips.add(new LinkedHashMap<String, Object>() {{
                put("type", elem.getType());
                put("ships_locations", elem.getShipLocations());
            }});
        });
        return myGPShips;
    }

    private List<Object> makeSalvoesDTO(GamePlayer myGPlayer) {
        List<Object> myGPSalvoes = new ArrayList<>();
        myGPlayer.getAllSalvos().forEach(elem -> {
            myGPSalvoes.add(new LinkedHashMap<String, Object>() {{
                put("turn", elem.getTurn());
//                put("player", elem.getGamePlayer().getPlayer().getId());
                put("salvoes_locations",elem.getSalvoLocations());

            }});
        });
        return myGPSalvoes;
    }

    private Map<String, Object> getPlayerSalvos(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getPlayer());
        return dto;
    }

    public List<Object> getSPlayer(Game game) {
        List<Object> gPlayerDataObject = new ArrayList<>();
        game.gamePlayers
                .stream()
                .forEach(gamePlayer -> {
                    gPlayerDataObject.add(new LinkedHashMap<String, Object>() {{
                                              put("gp_id", gamePlayer.getId());
                                              put("gp_salvoes", makeSalvoesDTO(gamePlayer));

                                          }}
                    );
                });
        return gPlayerDataObject;
    }

    @RequestMapping("/leaderboard")
    public List<Object> leaderboard() {
        return playerRepo.findAll().stream()
                .map(player -> new HashMap<String, Object>(){{
                    put("player_id", player.getId());
                    put("player_name", player.getFirstName());
                    put("scores", player.allScores
                            .stream()
                            .map(score -> score.getScore())
                            .collect(Collectors.toList()));
                }}).collect(Collectors.toList());
    }

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addPlayer(
            @RequestParam String userName,
            @RequestParam String fullName,
            @RequestParam String userPassword) {
        if (userName == "" || fullName == "" || userPassword == "" ) {
            return new ResponseEntity<>(makeMap("Error", "All fields must be filled"), HttpStatus.FORBIDDEN);
        } else if (playerRepo.findByUserName(userName) == null) {
            if (playerRepo.findByUserName(userName) == null) {
                playerRepo.save(new Player(fullName, userName, userPassword));
                return new ResponseEntity<>(makeMap("Success", "Your are now Registered"), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(makeMap("Error", "User already exists"), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(makeMap("Error", "Email is already in use"), HttpStatus.FORBIDDEN);
        }
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", key);
        map.put("message", value);
        return map;
    }

    public Player loggedUser(Authentication authentication) {
        if (userLogged(authentication)) {
            return playerRepo.findByUserName(authentication.getName());
        }
        return null;
    }

    public Boolean userLogged (Authentication authentication) {
        if (authentication == null) {
            return false;
        } else {
            return true;
        }
    }

}
