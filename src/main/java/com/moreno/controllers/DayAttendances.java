package com.moreno.controllers;

import com.moreno.models.DayAttendance;
import com.moreno.models.Diner;
import com.moreno.models.DinerAttendance;
import com.moreno.utilities.Moreno;
import com.moreno.utilities.Utilities;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Date;
import java.util.Vector;

public class DayAttendances extends Moreno {
    private static Root<DayAttendance> root;
    private static CriteriaQuery<DayAttendance> criteria;
    private static Vector<DayAttendance> todos;

    public static DayAttendance getOfDate(Date date){
        criteria = builder.createQuery(DayAttendance.class);
        root=criteria.from(DayAttendance.class);
        criteria.select(root).where(builder.between(root.get("date"),Utilities.getDateStart(date),Utilities.getDateEnd(date)));
        return session.createQuery(criteria).uniqueResult();
    }

}
