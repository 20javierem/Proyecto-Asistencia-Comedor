package com.moreno.controllers;

import com.moreno.models.Attendance;
import com.moreno.models.Diner;
import com.moreno.utilities.Moreno;
import com.moreno.utilities.Utilities;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Calendar;
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
    public static Attendance getOfDinerAndDate(Diner diner,Date date){
        Calendar start=Calendar.getInstance();
        start.setTime(date);
        Calendar end=Calendar.getInstance();
        end.setTime(date);
        end.add(Calendar.DATE,1);
        criteria = builder.createQuery(Attendance.class);
        root=criteria.from(Attendance.class);
        criteria.select(root).where(builder.and(
                builder.between(root.get("date"), start.getTime(),end.getTime()),
                builder.equal(root.get("diner"),diner)));
        return session.createQuery(criteria).uniqueResult();
    }
    public static Vector<Attendance> getOfDate(Date start,Date end){
        criteria = builder.createQuery(Attendance.class);
        root=criteria.from(Attendance.class);
        criteria.select(root).where(builder.between(root.get("date"), start,end));
        return new Vector<>(session.createQuery(criteria).getResultList());
    }
}