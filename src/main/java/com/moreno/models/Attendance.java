package com.moreno.models;

import com.moreno.utilities.Moreno;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
public class Attendance extends Moreno {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;

    @ManyToOne
    private Diner diner;

    private Date date;

    public Integer getId() {
        return id;
    }

    public Diner getDiner() {
        return diner;
    }

    public void setDiner(Diner diner) {
        this.diner = diner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
