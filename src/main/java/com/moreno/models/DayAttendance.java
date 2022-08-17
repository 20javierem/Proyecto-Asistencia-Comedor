package com.moreno.models;

import com.google.firebase.database.*;
import com.moreno.Notify;
import com.moreno.common.Common;
import com.moreno.controllers.Diners;
import com.moreno.modelsFirebase.FBDayAttendance;
import com.moreno.modelsFirebase.FBDinerAttendance;
import com.moreno.utilities.Moreno;
import com.moreno.utilities.Utilities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "Day_attendance_tbl")
public class DayAttendance extends Moreno {

    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;

    private Date date;

    @NotNull
    private boolean state=true;

    @OneToMany(mappedBy = "dayAttendance")
    private List<DinerAttendance> attendances=new ArrayList<>();

    private Integer totalDinerAttendance=0;

    private Integer totalDinerNotAttendance=0;

    private String percentageAtendet="0%";

    private String percentageNotAtendet="100%";

    private String launch="";

    private String beverage="";

    private String dessert="";

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getStringDate(){
        return Utilities.formatoFecha.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTotalDinerAttendance() {
        return totalDinerAttendance;
    }

    public String getLaunch() {
        return launch;
    }

    public void setLaunch(String launch) {
        this.launch = launch;
    }

    public String getBeverage() {
        return beverage;
    }

    public void setBeverage(String beverage) {
        this.beverage = beverage;
    }

    public String getDessert() {
        return dessert;
    }

    public void setDessert(String dessert) {
        this.dessert = dessert;
    }

    public void setTotalDinerAttendance(Integer totalDinerAttendance) {
        this.totalDinerAttendance = totalDinerAttendance;
    }

    public Integer getTotalDinerNotAttendance() {
        return totalDinerNotAttendance;
    }

    public List<DinerAttendance> getAttendances() {
        return attendances;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void calculateTotals(){
        int totalAssisted=0;
        int totalNotAssited=0;
        for(DinerAttendance dinerAttendance:getAttendances()){
            if(dinerAttendance.isAttended()){
                totalAssisted++;
            }else{
                totalNotAssited++;
            }
        }
        totalDinerAttendance=totalAssisted;
        totalDinerNotAttendance=totalNotAssited;
        percentageAtendet=Utilities.numberFormat.format(((double) (getTotalDinerAttendance()*100)) / getAttendances().size())+"%";
        percentageNotAtendet=Utilities.numberFormat.format(((double) (getTotalDinerNotAttendance()*100)) / getAttendances().size())+"%";
    }

    public String getPercentageAtendet() {
        return percentageAtendet;
    }

    public String getPercentageNotAtendet() {
        return percentageNotAtendet;
    }


    public DayAttendance() {

    }

    public DayAttendance(Date date){
        this.date=date;
        for (Diner diner: Diners.getActives()){
            DinerAttendance dinerAttendance=new DinerAttendance(diner,this);
            attendances.add(dinerAttendance);
        }
        calculateTotals();
    }

    @Override
    public void save() {
        super.save();
        DatabaseReference reference= Common.getDatabase().getReference("dayAttendance_tbl");
        FBDayAttendance fbDayAttendance=new FBDayAttendance(this);
        reference.child(Utilities.formatoFecha.format(getDate())).setValue(fbDayAttendance, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Utilities.lblIzquiera.setText("Éxito -> cambios guardados");
            }
        });
    }
}
