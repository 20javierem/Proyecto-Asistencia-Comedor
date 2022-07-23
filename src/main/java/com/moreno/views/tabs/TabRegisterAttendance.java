package com.moreno.views.tabs;

import com.moreno.Notify;
import com.moreno.controllers.Diners;
import com.moreno.custom.TabPane;
import com.moreno.custom.TxtSearch;
import com.moreno.models.DayAttendance;
import com.moreno.models.DinerAttendance;
import com.moreno.models.Diner;
import com.moreno.utilities.Moreno;
import com.moreno.utilities.Utilities;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.tablesCellRendered.AttendanceCellRendered;
import com.moreno.utilitiesTables.tablesModels.DinerAttendanceTableModel;
import com.moreno.views.VPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.HashMap;

public class TabRegisterAttendance {
    private TabPane tabPane;
    private JTable table;
    private TxtSearch txtDiner;
    private JLabel lblAsistieron;
    private JLabel lblFaltaron;
    private DinerAttendanceTableModel model;
    private HashMap<String,DinerAttendance> attendanceHashMap=new HashMap<>();

    public TabRegisterAttendance(){
        initComponents();
        txtDiner.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    registerAttendance();
                }
            }
        });
    }

    private void registerAttendance(){
        String code= txtDiner.getText().trim();
        if(!code.isBlank()){
            Diner diner= Diners.getByDni(code);
            if(diner!=null){
                if(diner.isActive()){
                    DinerAttendance dinerAttendance=attendanceHashMap.get(diner.getDni());
                    if(!dinerAttendance.isAttended()){
                        dinerAttendance.setAttended(true);
                        dinerAttendance.save();
                        Notify.sendNotify(Utilities.getJFrame(), Notify.Type.SUCCESS, Notify.Location.BOTTOM_RIGHT,"ÉXITO","Asistencia registrada");
                        UtilitiesTables.actualizarTabla(table);
                        loadCalculateTotals();
                    }else{
                        Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE","El comensal ya registró su asistencia");
                    }
                }else{
                    Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR","El comensal está inactivo");
                }
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ALERTA","No se encontró al comensal");
            }
        }
    }
    private void initComponents(){
        tabPane.setTitle("Registrar asistencia");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadHashMap();
                UtilitiesTables.actualizarTabla(table);
                loadCalculateTotals();
            }
        });
        txtDiner.setPlaceHolderText("DNI...");
        loadTable();
    }

    private void loadTable(){
        if(VPrincipal.attendancesToday==null) {
            VPrincipal.attendancesToday = new DayAttendance(new Date());
            VPrincipal.attendancesToday.save();
            VPrincipal.attendancesToday.getAttendances().forEach(Moreno::save);
        }
        loadHashMap();
        model=new DinerAttendanceTableModel(VPrincipal.attendancesToday.getAttendances());
        table.setModel(model);
        UtilitiesTables.headerNegrita(table);
        AttendanceCellRendered.setCellRenderer(table);
        loadCalculateTotals();
    }
    private void loadHashMap(){
        for (DinerAttendance dinerAttendance:VPrincipal.attendancesToday.getAttendances()){
            attendanceHashMap.put(dinerAttendance.getDiner().getDni(),dinerAttendance);
        }
    }
    private void loadCalculateTotals(){
        int totalAsistieron=0;
        int totalFaltaron=0;
        for (DinerAttendance dinerAttendance:VPrincipal.attendancesToday.getAttendances()){
            if (dinerAttendance.isAttended()){
                totalAsistieron++;
            }else{
                totalFaltaron++;
            }
        }
        double totalFaltaonPorcentual= (double) (totalAsistieron*100) / VPrincipal.attendancesToday.getAttendances().size();
        double totalFaltaronPorcentual= (double) (totalFaltaron*100) / VPrincipal.attendancesToday.getAttendances().size();

        lblAsistieron.setText("Asistieron: "+totalAsistieron+" : "+Utilities.numberFormat.format(totalFaltaonPorcentual)+"%");
        lblFaltaron.setText("Faltaron: "+totalFaltaron+" : "+Utilities.numberFormat.format(totalFaltaronPorcentual)+"%");
    }
    public TabPane getTabPane(){
        return tabPane;
    }
}
