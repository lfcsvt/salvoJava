package com.example.salvo2;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    @RequestMapping("/test/{gamePlayer}")
    public Map<String, Object> getHitSinkInfo(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("turnHitO",infoMe(gamePlayer) );
        dto.put("turnHitMe",infoOpponent(gamePlayer) );
        dto.put("hitOShips",shipsHitMe(gamePlayer) );
        dto.put("hitMyShips",shipsHitOpponent(gamePlayer));
        dto.put("state init",initialState(gamePlayer));
        dto.put("state",gameState(gamePlayer));
//        dto.put("opp",getOpponent(gamePlayer));


        return dto;
    }

    private List<Object> infoMe(GamePlayer gamePlayer) {
        List<Object> hitList = new ArrayList<>();
        if(getMyHits(gamePlayer) != null) {
            getMyHits(gamePlayer).stream().forEach(el -> {
                gamePlayer.getAllSalvos().forEach(elem -> {
                    if (elem.getSalvoLocations().contains(el)) {
                        Map<String, Object> obj = new LinkedHashMap<String, Object>();
                        obj.put("player", gamePlayer.getId());
                        obj.put("turn", elem.getTurn());
                        obj.put("hit", el);
                        // add obj to list
                        hitList.add(obj);
                    }
                });
            });

        }
        return hitList;
    }

    private List<Object> infoOpponent(GamePlayer gamePlayer) {
        GamePlayer opponent = gamePlayer.getGame().getOpponent(gamePlayer);
        List<Object> hitList = new ArrayList<>();
        if(opponent != null) {
            getMyHits(opponent).stream().forEach(el -> {
                opponent.getAllSalvos().forEach(elem -> {
                    if (elem.getSalvoLocations().contains(el)) {
                        Map<String, Object> obj = new LinkedHashMap<String, Object>();
                        obj.put("player", opponent.getId());
                        obj.put("turn", elem.getTurn());
                        obj.put("hit", el);
                        // add obj to list
                        hitList.add(obj);
                    }
                });
            });
        }
        return hitList;
    }

    private List<Object> shipsHitMe(GamePlayer gamePlayer) {
        GamePlayer opponent = gamePlayer.getGame().getOpponent(gamePlayer);
        List<Object> hitList = new ArrayList<>();
        if (opponent != null){
            getMyHits(gamePlayer).stream().forEach(el -> {
                opponent.getAllShips().stream().forEach(ship -> {
                    ship.getShipLocations().stream().forEach(loc -> {
                        if (loc.equals(el)) {
                            Map<String, Object> hitOnShip = new LinkedHashMap<String, Object>();
                            hitOnShip.put("player", gamePlayer.getId());
                            hitOnShip.put("ship", ship.getType());
                            hitOnShip.put("hit", el);
                            hitList.add(hitOnShip);
                        }
                    });
                });
            });
        }
        return hitList;
    }

    private List<Object> shipsHitOpponent(GamePlayer gamePlayer) {
        List<Object> hitList = new ArrayList<>();
        GamePlayer opponent = gamePlayer.getGame().getOpponent(gamePlayer);
        if(opponent != null) {
            getMyHits(opponent).stream().forEach(el -> {
                opponent.getGame().getOpponent(opponent).getAllShips().stream().forEach(ship -> {

                    ship.getShipLocations().stream().forEach(loc -> {
                        if (loc.equals(el)) {
                            Map<String, Object> hitOnShip = new LinkedHashMap<String, Object>();
                            hitOnShip.put("player", opponent.getId());
                            hitOnShip.put("ship", ship.getType());
                            hitOnShip.put("hit", el);
                            hitList.add(hitOnShip);
                        }
                    });
                });
            });
        }

        return hitList;
    }

    private List<Object> getMyHits(GamePlayer myGPlayer) {
        GamePlayer opponent = myGPlayer.getGame().getOpponent(myGPlayer);
        List<Object> myOShips = new ArrayList<>();
        List<Object> myOShipHits = new ArrayList<>();
        List<Object> shipsHits = new ArrayList<>();
        if(opponent != null) {
           opponent.getAllShips().forEach(elem -> {
                elem.getShipLocations().forEach(elem2 -> {
                    myOShips.add(elem2);
                });
            });
            myGPlayer.getAllSalvos().forEach(slv -> {
                slv.getSalvoLocations().forEach(slv2 -> {
                    myOShipHits.add(slv2);
                });
            });

            myOShips.forEach(e -> {
                myOShipHits.forEach(f -> {
                    if (f == e) {
                        shipsHits.add(e);
                    }
                });
            });

            return shipsHits;
        }
        return null;
    }

    @RequestMapping("/game_view/{gPlayer_Id}")
    public Map<String, Object> getGameView(@PathVariable Long gPlayer_Id, Authentication authentication) {
        GamePlayer myGPlayer = gamePlayerRepo.findOne(gPlayer_Id);
//        if(userLogged(authentication)) {
            Map<String, Object> gameViewDTO = new LinkedHashMap<String, Object>();
            gameViewDTO.put("Game_id", myGPlayer.getGame().getId());
            gameViewDTO.put("player_id", myGPlayer.getPlayer().getId());
            gameViewDTO.put("created", myGPlayer.getGame().getcDate());
            gameViewDTO.put("gamePlayer", getGamePlayer(myGPlayer.getGame()));
            gameViewDTO.put("ships", makeShipsDTO(myGPlayer));
            gameViewDTO.put("salvoes", getSPlayer(myGPlayer.getGame()));
            gameViewDTO.put("histObj",getHitSinkInfo(myGPlayer));
            return gameViewDTO;
        }
//        return new ResponseEntity<>(makeMap("Error", "please login"), HttpStatus.UNAUTHORIZED);


//    }

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

    public Player currentUser(Authentication authentication) {
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

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addNewGame(Authentication authentication){

        if(!userLogged(authentication)){
            return new ResponseEntity<>(makeMap("Error", "please login"), HttpStatus.UNAUTHORIZED);
        } else{

            Game game = new Game();
            gameRepo.save(game);
            GamePlayer gamePlayer = new GamePlayer(currentUser (authentication), game);
            gamePlayerRepo.save(gamePlayer);
            return new ResponseEntity<>(addMap("gPlayer_id", gamePlayer.getId()),HttpStatus.CREATED);
        }
    }

    private Map<String, Object> addMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    @RequestMapping(path = "/game/{gameID}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addGamePlayer(@PathVariable long gameID, Authentication authentication){

        if(!userLogged(authentication)){
            return new ResponseEntity<>(makeMap("Error", "please login"), HttpStatus.UNAUTHORIZED);
        }
        Game gameJoin = gameRepo.findOne(gameID);

        if(gameJoin.getGamePlayers().size() == 2){
            return new ResponseEntity<>(addMap("error", "Game has already two players."), HttpStatus.FORBIDDEN);
        }

        Object firstElement = gameJoin.getGamePlayers().stream().findFirst().get().getPlayer();
        Player loggedUser = playerRepo.findByUserName(authentication.getName());

        if(firstElement == loggedUser){
            return new ResponseEntity<>(addMap("error", "Already in game."), HttpStatus.FORBIDDEN);
        }

        if(gameJoin.getGamePlayers().size() == 2 && firstElement == loggedUser){

            return new ResponseEntity<>(addMap("error", "Game has already two players."), HttpStatus.FORBIDDEN);
        }

        else {
            GamePlayer newGamePlayer = new GamePlayer(loggedUser, gameJoin);
            gamePlayerRepo.save(newGamePlayer);

            return new ResponseEntity<>(makeMap("new_GamePlayerID", newGamePlayer.getId()), HttpStatus.CREATED);
        }

    }

    @RequestMapping(path="/games/players/{gPlayer_id}/ships", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> placeShips(
            @PathVariable long gPlayer_id,
            @RequestBody List<Ship> myShipsList,
            Authentication authentication) {

        List<Integer> shipSizes = new ArrayList<>();

        for (Ship ship : myShipsList) {
            shipSizes.add(ship.getShipLocations().size());
        }

        if (!userLogged(authentication)) {
            return new ResponseEntity<>(makeMap("Error", "please login"), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayerRepo.findById(gPlayer_id) == null) {
            return new ResponseEntity<>(makeMap("Error", "game doesn't exist"), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayerRepo.findById(gPlayer_id).iterator().next().getPlayer() != currentUser(authentication)) {
            return new ResponseEntity<>(makeMap("Error", "you cannot move other player's ships"), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayerRepo.findById(gPlayer_id).iterator().next().getAllShips().size() > 0) {
            return new ResponseEntity<>(makeMap("Error", "you cannot add more ships"), HttpStatus.UNAUTHORIZED);
        }

        else {
            GamePlayer gamePlayer = gamePlayerRepo.findOne(gPlayer_id);
            for (Ship newShip : myShipsList) {
                gamePlayer.makeShip(newShip);
                gamePlayer.makeShip(newShip);
                shipRepo.save(newShip);
            }
            return new ResponseEntity<>(makeMap("Success", "the ship was placed"),HttpStatus.CREATED);
        }
    }
    public String salvoLoc = " ";

    @RequestMapping(path="/games/players/{gPlayer_id}/salvos", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>>placeSalvos(
            @PathVariable long gPlayer_id,
            @RequestBody Salvo mySalvo,
            Authentication authentication){

        GamePlayer gamePlayer = gamePlayerRepo.findOne(gPlayer_id);
        if (!userLogged(authentication)) {
            return new ResponseEntity<>(makeMap("Error", "please login"), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayerRepo.findById(gPlayer_id).iterator().next().getPlayer() != currentUser(authentication)) {
            return new ResponseEntity<>(makeMap("Error", "Not the right player"), HttpStatus.UNAUTHORIZED);
        }

        if(mySalvo.getTurn() > gamePlayer.getAllSalvos().size() + 1){
            return new ResponseEntity<>(makeMap("Error", "Wrong turn "), HttpStatus.UNAUTHORIZED);
        }

        if(gamePlayer.getAllSalvos().size() > gamePlayer.getGame().getOpponent(gamePlayer).getAllSalvos().size()){
            return new ResponseEntity<>(makeMap("Error", "Wrong turn "), HttpStatus.UNAUTHORIZED);
        }

        gamePlayer.getAllSalvos().forEach(salvo -> {
            salvo.getSalvoLocations().stream().forEach(el ->{
                    if(mySalvo.getSalvoLocations().iterator().next().equals(el)){
                        salvoLoc = el;
                    }
            });

        });
        if(mySalvo.getSalvoLocations().iterator().next().equals(salvoLoc)){
            return new ResponseEntity<>(makeMap("Error", "Can't fire s shots at same place "), HttpStatus.UNAUTHORIZED);
        }

            gamePlayer.makeSalvo(mySalvo);
            salvoRepo.save(mySalvo);

        return new ResponseEntity<>(makeMap("Success", "salvo fired"),HttpStatus.CREATED);
    }

    private boolean getEnd(GamePlayer gamePlayer) {

        List<Integer> testList = new ArrayList<>();
        gamePlayer.getAllShips().stream().forEach(ship -> {
            testList.add(ship.getShipLocations().size());

        });
        if(testList.stream().mapToInt(Integer::intValue).sum() != 0 || getMyHits(gamePlayer).size() !=0) {
            if (testList.stream().mapToInt(Integer::intValue).sum() == getMyHits(gamePlayer).size()) {
                System.out.println(testList.stream().mapToInt(Integer::intValue).sum());
                gamePlayer.getGame().setOver(true);
            }
        }
        return  gamePlayer.getGame().isOver();
    }

    public String initialState(GamePlayer gamePlayer) {
        GamePlayer opponent = gamePlayer.getGame().getOpponent(gamePlayer);
        if (gamePlayer.getAllShips().size() < 5) {
            return "please place ships";
        } else if (opponent == null) {
            return "waiting for opponent";
        } else if (opponent.getAllShips().size() < 5) {
            return "waiting for opponent to place ships";
        } else {
            return "please shoot a salvo";
        }
    }
    public String gameState(GamePlayer gamePlayer) {
        GamePlayer opponent = gamePlayer.getGame().getOpponent(gamePlayer);
        if (!getEnd(gamePlayer) || !getEnd(opponent)) {
            if (gamePlayer.getAllSalvos().size() == opponent.getAllSalvos().size()) {
                return "please shoot a salvo";
            } else if (gamePlayer.getAllSalvos().size() > opponent.getAllSalvos().size()) {
                return "waiting for opponent to shoot a salvo";
            } else {
                return "opponent is waiting for you to shoot a salvo";
            }
        }
        return "game is over";
    }
    public GamePlayer getOpponent(GamePlayer gamePlayer){
       return gamePlayer.getGame().getOpponent(gamePlayer);

    }

}

