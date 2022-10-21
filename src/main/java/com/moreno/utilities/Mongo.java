package com.moreno.utilities;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

public class Mongo {
    public static EntityManager entityManager;
    public static CriteriaBuilder builder;

    public static void initialize() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("twingo");
        entityManager=emf.createEntityManager();
        builder=emf.getCriteriaBuilder();
    }
    public void save(){
        entityManager.persist(this);
        entityManager.clear();
    }
}
