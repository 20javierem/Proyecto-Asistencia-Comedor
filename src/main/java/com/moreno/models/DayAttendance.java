package com.moreno.models;

import com.moreno.controllers.Diners;
import com.moreno.utilities.Moreno;
import com.moreno.utilities.Utilities;
import com.moreno.views.VPrincipal;
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

    private Integer totalDinerAttendance=0;

    private Integer totalDinerNotAttendance=0;

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

    public Integer getTotalDinerAttendance() {
        return totalDinerAttendance;
    }

    public void setTotalDinerAttendance(Integer totalDinerAttendance) {
        this.totalDinerAttendance = totalDinerAttendance;
    }

    public Integer getTotalDinerNotAttendance() {
        return totalDinerNotAttendance;
    }

    public void setTotalDinerNotAttendance(Integer totalDinerNotAttendance) {
        this.totalDinerNotAttendance = totalDinerNotAttendance;
    }

    public List<DinerAttendance> getAttendances() {
        return attendances;
    }

    public String getPercentageAtendet(){
        double totalFaltaonPorcentual= (double) (getTotalDinerAttendance()*100) / getAttendances().size();
        return Utilities.numberFormat.format(totalFaltaonPorcentual)+"%";
    }
    public String getPercentageNotAtendet(){
        double totalFaltaonPorcentual= (double) (getTotalDinerNotAttendance()*100) / getAttendances().size();
        return Utilities.numberFormat.format(totalFaltaonPorcentual)+"%";
    }
    public DayAttendance(Date date){
        this.date=date;
        for (Diner diner: Diners.getActives()){
            DinerAttendance dinerAttendance=new DinerAttendance(diner,this);
            attendances.add(dinerAttendance);
        }
        setTotalDinerNotAttendance(Diners.getActives().size());
    }
}
