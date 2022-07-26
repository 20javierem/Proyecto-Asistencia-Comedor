package com.moreno.models;

import com.moreno.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

public class DinerDaysAttendances {

    public DinerDaysAttendances(Diner diner, List<DinerAttendance> dinerAttendances) {
        this.diner = diner;
        this.dinerAttendances = dinerAttendances;
        calculateTotals();
    }

    private Diner diner;
    private Integer totalAttendances=0;
    private Integer totalNotAttendances=0;
    private String totalNotAttendancesPercentage;
    private List<DinerAttendance> dinerAttendances;
    public String getDinerName(){
        return diner.getLastNames()+", "+diner.getNames();
    }
    public String getDinerDNI(){
        return diner.getDni();
    }
    public Diner getDiner() {
        return diner;
    }

    public void setDiner(Diner diner) {
        this.diner = diner;
    }

    public Integer getTotalNotAttendances() {
        return totalNotAttendances;
    }

    public Integer getTotalAttendances() {
        return totalAttendances;
    }

    public void setTotalAttendances(Integer totalAttendances) {
        this.totalAttendances = totalAttendances;
    }

    public void setTotalNotAttendances(Integer totalNotAttendances) {
        this.totalNotAttendances = totalNotAttendances;
    }

    public String getTotalNotAttendancesPercentage() {
        return totalNotAttendancesPercentage;
    }

    public List<DinerAttendance> getDinerAttendances() {
        return dinerAttendances;
    }

    public void setTotalNotAttendancesPercentage(String totalNotAttendancesPercentage) {
        this.totalNotAttendancesPercentage = totalNotAttendancesPercentage;
    }

    private void calculateTotals(){
        for (DinerAttendance dinerAttendance:dinerAttendances){
            if(dinerAttendance.isAttended()){
                totalAttendances++;
            }else{
                totalNotAttendances++;
            }
        }
        totalNotAttendancesPercentage= Utilities.numberFormat.format(((double) (totalNotAttendances*100)) / (dinerAttendances.size()))+"%";
    }
}
