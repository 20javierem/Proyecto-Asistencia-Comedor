package com.moreno.models;

import com.moreno.utilities.Mongo;
import com.moreno.utilities.Utilities;

import javax.persistence.*;

@Entity
@Table(name = "DINERATTENDANCE", schema = "KunderaExamples@twingo")
public class DinerAttendance extends Mongo {
    @Id
    @Column(name="DINERATTENDANCE_ID")
    private Integer id;

    @Embedded
    private Diner diner;

    @Column(name="ATTENDED")
    private boolean attended=false;

    @ManyToOne
    private DayAttendance dayAttendance;

    public DinerAttendance() {

    }


    public String getDinerNames(){
        return diner.getLastNames()+" "+diner.getNames();
    }
    public String getDinerDni(){
        return diner.getDni();
    }
    public String getAttendet(){
        return attended?"ASISTIÓ":"FALTA";
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
    public String getDateAttendance(){
        return dayAttendance.getStringDate();
    }
    public String getDateName(){
        return Utilities.dayFormat.format(dayAttendance.getDate()).toUpperCase();
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

    @Override
    public void save() {
        super.save();
    }
}
