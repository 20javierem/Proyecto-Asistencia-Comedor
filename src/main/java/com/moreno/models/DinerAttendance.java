package com.moreno.models;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moreno.common.Common;
import com.moreno.modelsFirebase.FBDinerAttendance;
import com.moreno.utilities.Moreno;
import com.moreno.utilities.Utilities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity(name = "diner_attedance_tbl")
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

        DatabaseReference reference= Common.getDatabase().getReference("dinnerAttendance_tbl");
        reference.child(String.valueOf(getId())).setValue(new FBDinerAttendance(this), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

            }
        });
    }
}
