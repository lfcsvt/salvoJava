package com.example.salvo2;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.util.Set;
import java.util.HashSet;



@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy="gamePlayer", fetch = FetchType.EAGER)
    Set<Ship> allShips = new HashSet<>();

    @OneToMany(mappedBy="gamePlayer", fetch = FetchType.EAGER)
    Set<Salvo> allSalvoes = new HashSet<>();


    public GamePlayer () { }

    public GamePlayer (Player player, Game game) {
        this.player = player;
        this.game = game;

    }

    public long getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "GP " + this.id + " Name " + this.player.getUserName();
    }

    public void setAllShips(Set<Ship> allShips) {
        this.allShips = allShips;
    }

    public Set<Ship> getAllShips() {
        return allShips;
    }

    public void setAllSalvoes(Set<Salvo> allSalvoes) {
        this.allSalvoes = allSalvoes;
    }

    public Set<Salvo> getAllSalvos() {
        return allSalvoes;
    }

    public void makeShip(Ship ship) {
        ship.setGamePlayer(this);
        allShips.add(ship);
    }

    public void makeSalvo(Salvo salvo) {
        salvo.setGamePlayer(this);
        allSalvoes.add(salvo);
    }
}

