package com.example.salvo2;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String cDate;



    public Game () { }

    public Game(String gameDate) {
        cDate = gameDate;


    }
    public String getcDate() {
        return cDate;
    }

    public void setcDate(String gameDate) {
        this.cDate = gameDate;
    }

//    public String toString() {
//        return cDate;
//    }
}
