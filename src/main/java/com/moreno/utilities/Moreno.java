package com.moreno.utilities;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class Moreno {
    public static Session session;
    protected static CriteriaBuilder builder;

    private static void initialize() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();
        builder = session.getCriteriaBuilder();
    }

    public void refresh(){
        session.refresh(this);
    }

    public void save(){
        session.beginTransaction();
        session.persist(this);
        session.getTransaction().commit();
    }
    public static void close(){
        session.close();
    }
}