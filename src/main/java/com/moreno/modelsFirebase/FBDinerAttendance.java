package com.moreno.modelsFirebase;

import com.moreno.models.DinerAttendance;

public class FBDinerAttendance {
    private Integer id;
    private Integer diner;
    private Integer dayAttendance;
    private boolean attended=false;

    public FBDinerAttendance(DinerAttendance dinerAttendance){
        setAttended(dinerAttendance.isAttended());
        setDayAttendance(dinerAttendance.getDayAttendance().getId());
        setDiner(dinerAttendance.getDiner().getId());
        setId(dinerAttendance.getId());
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDiner() {
        return diner;
    }

    public void setDiner(Integer diner) {
        this.diner = diner;
    }

    public Integer getDayAttendance() {
        return dayAttendance;
    }

    public void setDayAttendance(Integer dayAttendance) {
        this.dayAttendance = dayAttendance;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }
}
