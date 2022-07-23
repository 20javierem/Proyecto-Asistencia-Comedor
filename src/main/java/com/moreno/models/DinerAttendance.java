package com.moreno.models;

import com.moreno.utilities.Moreno;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
public class DinerAttendance extends Moreno {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;

    @ManyToOne
    private Diner diner;

    @ManyToOne
    private DayAttendance dayAttendance;

    private boolean attended=false;

    public DinerAttendance() {

    }

    public Integer getId() {
        return id;
    }

    public Diner getDiner() {
        return diner;
    }

    public void setDiner(Diner diner) {
        this.diner = diner;
    }

    public DayAttendance getDayAttendance() {
        return dayAttendance;
    }

    public void setDayAttendance(DayAttendance dayAttendance) {
        this.dayAttendance = dayAttendance;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public DinerAttendance(Diner diner, DayAttendance dayAttendance) {
        this.diner = diner;
        this.dayAttendance = dayAttendance;
    }
}
