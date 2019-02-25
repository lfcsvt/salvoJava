package com.example.salvo2;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;


@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date cDate = new Date();
    private boolean isOver = false;


    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="game", fetch= FetchType.EAGER)
    Set<Score> score;


    public GamePlayer getOpponent(GamePlayer userGamePlayer) {
        for (GamePlayer notUser: this.gamePlayers) {
            if (userGamePlayer.getId() != notUser.getId()) {
                return notUser;
            }
        }
        return null;
    }

    public Game () { }

    public Game(Date date) {

        this.cDate = new Date();
        this.id = getId();
    }
    public Date getcDate() {
        return cDate;
    }
    public long getId() {
        return id;
    }

    public void setcDate(Date date) {
        this.cDate = date;
    }

    public void addGamePLayer(GamePlayer gamePlayer) {
        gamePlayer.setGame(this);
        gamePlayers.add(gamePlayer);
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }
}
