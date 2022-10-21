package com.moreno.controllers;

import com.moreno.models.DinerAttendance;
import com.moreno.models.Diner;
import com.moreno.utilities.Mongo;
import com.moreno.utilities.Utilities;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.Vector;

public class DinerAttendances extends Mongo {
    private static Root<DinerAttendance> root;
    private static CriteriaQuery<DinerAttendance> criteria;
    private static Vector<DinerAttendance> todos;

    public static DinerAttendance get(Integer id) {
        DinerAttendance election = entityManager.find(DinerAttendance.class, id);
        return election;
    }

    public static Vector<DinerAttendance> getTodos(){
        criteria = builder.createQuery(DinerAttendance.class);
        criteria.select(criteria.from(DinerAttendance.class));
        todos = new Vector<>(entityManager.createQuery(criteria).getResultList());
        return todos;
    }

    public static Vector<DinerAttendance> getByDinerAndStartAndEnd(Diner diner,Date start,Date end){
        criteria = builder.createQuery(DinerAttendance.class);
        root=criteria.from(DinerAttendance.class);
        criteria.select(root).where(builder.and(
                builder.equal(root.get("diner"),diner),
                builder.between(root.get("dayAttendance").get("date"),Utilities.getDateStart(start),Utilities.getDateEnd(end))
                )
        );
        todos=new Vector<>(entityManager.createQuery(criteria).getResultList());
        return todos;
    }

    public static Vector<DinerAttendance> getByDinerAndBefore(Diner diner,Date end){
        criteria = builder.createQuery(DinerAttendance.class);
        root=criteria.from(DinerAttendance.class);
        criteria.select(root).where(builder.and(
                builder.equal(root.get("diner"),diner),
                builder.lessThan(root.get("dayAttendance").get("date"),Utilities.getDateLessThan(end))
                )
        );
        todos=new Vector<>(entityManager.createQuery(criteria).getResultList());
        return todos;
    }

    public static Vector<DinerAttendance> getByDinerAndAfer(Diner diner,Date start){
        criteria = builder.createQuery(DinerAttendance.class);
        root=criteria.from(DinerAttendance.class);
        criteria.select(root).where(builder.and(
                builder.equal(root.get("diner"),diner),
                builder.greaterThan(root.get("dayAttendance").get("date"),Utilities.getDateGreaterThan(start))
                )
        );
        todos=new Vector<>(entityManager.createQuery(criteria).getResultList());
        return todos;
    }
}