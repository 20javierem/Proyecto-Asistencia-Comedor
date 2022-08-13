package com.moreno.controllers;

import com.google.firebase.database.*;
import com.moreno.models.Diner;
import com.moreno.models.DinerAttendance;
import com.moreno.utilities.Moreno;
import com.moreno.views.VPrincipal;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.*;

public class Diners extends Moreno {
    private static Root<Diner> root;
    private static CriteriaQuery<Diner> criteria;
    private static Vector<Diner> todos;

    public static Diner get(Integer id) {
        Diner election = session.find(Diner.class, id, LockModeType.NONE);
        return election;
    }

    public static Vector<Diner> getTodos(){
        criteria = builder.createQuery(Diner.class);
        criteria.select(criteria.from(Diner.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
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
    public static List<Diner> all(){
        List<Diner> diners=new ArrayList<>();
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference("Diner_tbl");
            Map<Integer,Diner> dinerMap=new HashMap<>();
            reference.orderByChild("id").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Diner diner=dataSnapshot.getValue(Diner.class);
                    diners.add(diner);
                    dinerMap.put(diner.getId(),diner);
                    diner.save();
                    System.out.println(diner.getId());
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Diner dinerUpdate=dataSnapshot.getValue(Diner.class);
                    Diner diner=dinerMap.get(dinerUpdate.getId());
                    diner=diners.get(diners.indexOf(diner));
                    diner.setActive(dinerUpdate.isActive());
                    diner.setMale(dinerUpdate.isMale());
                    diner.setPhone(dinerUpdate.getPhone());
                    diner.setNames(dinerUpdate.getNames());
                    diner.setLastNames(diner.getLastNames());
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Diner dinerUpdate=dataSnapshot.getValue(Diner.class);
                    Diner diner=dinerMap.get(dinerUpdate.getId());
                    diners.remove(diner);
                    dinerMap.remove(dinerUpdate.getId());
                }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return diners;
        }catch (Exception e){
            e.printStackTrace();
        }
        return diners;
    }
}

