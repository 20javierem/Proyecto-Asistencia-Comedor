package com.moreno.models;

import com.moreno.controllers.Diners;
import com.moreno.utilities.Moreno;
import jakarta.persistence.*;
import jdk.jfr.Timespan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class DayAttendance extends Moreno {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "dayAttendance")
    private List<DinerAttendance> attendances=new ArrayList<>();

    public DayAttendance() {
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<DinerAttendance> getAttendances() {
        return attendances;
    }

    public DayAttendance(Date date){
        this.date=date;
        for (Diner diner: Diners.getActives()){
            DinerAttendance dinerAttendance=new DinerAttendance(diner,this);
            attendances.add(dinerAttendance);
        }
    }
}
