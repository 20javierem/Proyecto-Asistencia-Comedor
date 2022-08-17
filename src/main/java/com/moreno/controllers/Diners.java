package com.moreno.controllers;

import com.google.firebase.database.*;
import com.moreno.models.Diner;
import com.moreno.models.DinerAttendance;
import com.moreno.utilities.Moreno;
import com.moreno.utilities.Utilities;
import com.moreno.views.VPrincipal;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

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
    public static void all(){
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference("diner_tbl");
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Diner dinerUpdate=dataSnapshot.getValue(Diner.class);
                    System.out.println(dinerUpdate);
                    Diner cache= Moreno.session.get(Diner.class,dinerUpdate.getId());
                    if(cache==null){
                        VPrincipal.diners.add(dinerUpdate);
                        dinerUpdate.save();
                    }else{
                        update(cache,dinerUpdate);
                    }
                    Utilities.getTabbedPane().updateTab();
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Diner dinerUpdate=dataSnapshot.getValue(Diner.class);
                    Diner cache= Moreno.session.get(Diner.class,dinerUpdate.getId());
                    update(cache,dinerUpdate);
                    Utilities.getTabbedPane().updateTab();
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void update(Diner cache,Diner dinerUpdate) {
        cache= Moreno.session.get(Diner.class,dinerUpdate.getId());
        cache.setActive(dinerUpdate.isActive());
        cache.setMale(dinerUpdate.isMale());
        cache.setPhone(dinerUpdate.getPhone());
        cache.setNames(dinerUpdate.getNames());
        cache.setLastNames(dinerUpdate.getLastNames());
        cache.save();
    }
}

