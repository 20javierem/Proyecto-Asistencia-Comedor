package com.moreno.controllers;

import com.moreno.models.Diner;
import com.moreno.utilities.Mongo;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

public class Diners extends Mongo {
    private static Root<Diner> root;
    private static CriteriaQuery<Diner> criteria;
    private static Vector<Diner> todos;

    public static Diner get(Integer id) {
        return entityManager.find(Diner.class, id);
    }

    public static Vector<Diner> getTodos(){
        criteria = builder.createQuery(Diner.class);
        criteria.select(criteria.from(Diner.class));
        todos = new Vector<>(entityManager.createQuery(criteria).getResultList());
        return todos;
    }
    public static Integer getLastId(){
        criteria = builder.createQuery(Diner.class);
        criteria.select(criteria.from(Diner.class));
        List<Diner> dinerList = entityManager.createQuery(criteria).getResultList();
        return dinerList.isEmpty()?1:dinerList.get(dinerList.size()-1).getId()+1;
    }
    public static Diner getByDni(String dni){
        criteria = builder.createQuery(Diner.class);
        root=criteria.from(Diner.class);
        criteria.select(root)
                .where(builder.equal(root.get("dni"),dni));
        List<Diner> diners=entityManager.createQuery(criteria).getResultList();
        return !diners.isEmpty()?diners.get(0):null;
    }
    public static Vector<Diner> getActives(){
        criteria = builder.createQuery(Diner.class);
        root=criteria.from(Diner.class);
        criteria.select(root).where(builder.isTrue(root.get("active")));
        return new Vector<>(entityManager.createQuery(criteria).getResultList());
    }

}

