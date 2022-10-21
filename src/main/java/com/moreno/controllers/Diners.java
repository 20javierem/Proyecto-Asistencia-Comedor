package com.moreno.controllers;

import com.moreno.models.Diner;
import com.moreno.utilities.Moreno;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.*;

public class Diners extends Moreno {
    private static Root<Diner> root;
    private static CriteriaQuery<Diner> criteria;
    private static Vector<Diner> todos;

    public static Diner get(Integer id) {
        return session.find(Diner.class, id, LockModeType.NONE);
    }

    public static Vector<Diner> getTodos(){
        criteria = builder.createQuery(Diner.class);
        criteria.select(criteria.from(Diner.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }
    public static Integer getLastId(){
        criteria = builder.createQuery(Diner.class);
        criteria.select(criteria.from(Diner.class));
        List<Diner> dinerList = session.createQuery(criteria).getResultList();
        return dinerList.isEmpty()?1:dinerList.get(dinerList.size()-1).getId()+1;
    }
    public static Diner getByDni(String dni){
        criteria = builder.createQuery(Diner.class);
        root=criteria.from(Diner.class);
        criteria.select(root)
                .where(builder.equal(root.get("dni"),dni));
        List<Diner> diners=session.createQuery(criteria).getResultList();
        return !diners.isEmpty()?diners.get(0):null;
    }
    public static Vector<Diner> getActives(){
        criteria = builder.createQuery(Diner.class);
        root=criteria.from(Diner.class);
        criteria.select(root).where(builder.isTrue(root.get("active")));
        return new Vector<>(session.createQuery(criteria).getResultList());
    }

}

