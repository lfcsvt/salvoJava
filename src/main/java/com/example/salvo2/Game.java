package com.example.salvo2;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date cDate;



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

//    public Date toString() {
//        return cDate;
//    }
}
