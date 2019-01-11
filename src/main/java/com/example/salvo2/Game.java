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
    private Date cDate;


    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;



    public Game () { }

    public Game(Date date) {
        cDate = date;


    }
    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date date) {
        this.cDate = date;
    }

    public void addGamePLayer(GamePlayer gamePlayer) {
        gamePlayer.setGame(this);
        gamePlayers.add(gamePlayer);
    }


//    public Date toString() {
//        return cDate;
//    }
}
