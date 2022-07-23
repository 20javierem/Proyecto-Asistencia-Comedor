package com.moreno.controllers;

import com.moreno.models.DayAttendance;
import com.moreno.models.DinerAttendance;
import com.moreno.models.Diner;
import com.moreno.utilities.Moreno;
import com.moreno.utilities.Utilities;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Date;
import java.util.Vector;

public class DinnerAttendance extends Moreno {
    private static Root<DinerAttendance> root;
    private static CriteriaQuery<DinerAttendance> criteria;
    private static Vector<DinerAttendance> todos;

    public static DinerAttendance get(Integer id) {
        DinerAttendance election = session.find(DinerAttendance.class, id, LockModeType.NONE);
        return election;
    }

    public static Vector<DinerAttendance> getTodos(){
        criteria = builder.createQuery(DinerAttendance.class);
        criteria.select(criteria.from(DinerAttendance.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }

}