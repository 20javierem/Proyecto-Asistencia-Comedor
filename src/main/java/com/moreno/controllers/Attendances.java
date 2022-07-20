package com.moreno.controllers;

import com.moreno.models.Attendance;
import com.moreno.models.Diner;
import com.moreno.utilities.Moreno;
import com.moreno.utilities.Utilities;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Date;
import java.util.Vector;

public class Attendances extends Moreno {
    private static Root<Attendance> root;
    private static CriteriaQuery<Attendance> criteria;
    private static Vector<Attendance> todos;

    public static Attendance get(Integer id) {
        Attendance election = session.find(Attendance.class, id, LockModeType.NONE);
        return election;
    }

    public static Vector<Attendance> getTodos(){
        criteria = builder.createQuery(Attendance.class);
        criteria.select(criteria.from(Attendance.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }

    public static Vector<Attendance> getOfDate(Date start,Date end){
        criteria = builder.createQuery(Attendance.class);
        root=criteria.from(Attendance.class);
        criteria.select(root).where(builder.between(root.get("date"), Utilities.getDate(start),Utilities.getDate(end)));
        return new Vector<>(session.createQuery(criteria).getResultList());
    }
}