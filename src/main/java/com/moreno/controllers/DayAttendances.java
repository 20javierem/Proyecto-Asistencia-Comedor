package com.moreno.controllers;

import com.moreno.models.DayAttendance;
import com.moreno.utilities.Mongo;
import com.moreno.utilities.Utilities;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.Vector;

public class DayAttendances extends Mongo {
    private static Root<DayAttendance> root;
    private static CriteriaQuery<DayAttendance> criteria;
    private static Vector<DayAttendance> todos;

    public static DayAttendance get(Integer id) {
        DayAttendance dayAttendance = entityManager.find(DayAttendance.class, id);
        return dayAttendance;
    }

    public static Vector<DayAttendance> getTodos(){
        criteria = builder.createQuery(DayAttendance.class);
        criteria.select(criteria.from(DayAttendance.class));
        todos = new Vector<>(entityManager.createQuery(criteria).getResultList());
        return todos;
    }

    public static DayAttendance getOfDate(Date date){
        criteria = builder.createQuery(DayAttendance.class);
        root=criteria.from(DayAttendance.class);
        criteria.select(root).where(builder.between(root.get("date"),Utilities.getDateStart(date),Utilities.getDateEnd(date)));
        return entityManager.createQuery(criteria).getSingleResult();
    }

    public static Vector<DayAttendance> getByRangeOfDate(Date start,Date end){
        criteria = builder.createQuery(DayAttendance.class);
        root=criteria.from(DayAttendance.class);
        criteria.select(root).where(builder.between(root.get("date"),Utilities.getDateStart(start),Utilities.getDateEnd(end)));
        todos=new Vector<>(entityManager.createQuery(criteria).getResultList());
        return todos;
    }

    public static Vector<DayAttendance> getBefore(Date end){
        criteria = builder.createQuery(DayAttendance.class);
        root=criteria.from(DayAttendance.class);
        criteria.select(root).where(builder.lessThan(root.get("date"),Utilities.getDateLessThan(end)));
        todos=new Vector<>(entityManager.createQuery(criteria).getResultList());
        return todos;
    }

    public static Vector<DayAttendance> getAfter(Date start){
        criteria = builder.createQuery(DayAttendance.class);
        root=criteria.from(DayAttendance.class);
        criteria.select(root).where(builder.greaterThan(root.get("date"),Utilities.getDateGreaterThan(start)));
        todos=new Vector<>(entityManager.createQuery(criteria).getResultList());
        return todos;
    }
}
