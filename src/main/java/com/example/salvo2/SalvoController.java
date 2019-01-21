package com.example.salvo2;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
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

    @RequestMapping("/games")
//    public List<Game> getAll() { return gameRepo.findAll();}
    public List<Object> getAllGames() {

        return gameRepo
                .findAll()
                .stream()
                .map(game -> makeGameDTO(game))
                .collect(Collectors.toList());
    }

    private Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getcDate());
        dto.put("gamePlayers", getGamePlayer(game));
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

                                          }}
                    );
                });
        return gPlayerDataObject;
    }

    @RequestMapping("/game_view/{gPlayer_Id}")
    public Map<String, Object> getGameView(@PathVariable Long gPlayer_Id) {
        GamePlayer myGPlayer = gamePlayerRepo.findOne(gPlayer_Id);
        Map<String, Object> gameViewDTO = new LinkedHashMap<String, Object>();
        gameViewDTO.put("Game_id", myGPlayer.getGame().getId());
        gameViewDTO.put("gPlayer_id", myGPlayer.getPlayer().getId());
        gameViewDTO.put("created", myGPlayer.getGame().getcDate());
        gameViewDTO.put("gamePlayer", getGamePlayer(myGPlayer.getGame()));
        gameViewDTO.put("ships", makeShipsDTO(myGPlayer));
        gameViewDTO.put("salvoes", getSPlayer(myGPlayer.getGame()));

        return gameViewDTO;
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
    public List<Object> getLeaderBoard() {
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

}
