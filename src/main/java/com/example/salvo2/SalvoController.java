package com.example.salvo2;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepo;

    @RequestMapping("/games")
//    public List<Game> getAll() { return gameRepo.findAll();}
    public List<Object> getAll() {
        System.out.println("in getAll");
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
                    System.out.println(gamePlayer.toString());
                    gPlayerDataObject.add(new LinkedHashMap<String, Object>() {{
                                              put("gp_id", gamePlayer.getId());
                                              put("player", makePlayerDTO(gamePlayer.getPlayer()));
                                          }}
                    );
                });
        System.out.println("gPlayerDataObject");
        System.out.println(gPlayerDataObject);
        return gPlayerDataObject;
    }







    
}
