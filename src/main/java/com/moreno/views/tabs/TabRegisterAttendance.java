package com.moreno.views.tabs;

import com.moreno.App;
import com.moreno.Notify;
import com.moreno.controllers.Attendances;
import com.moreno.controllers.Diners;
import com.moreno.custom.TabPane;
import com.moreno.models.Attendance;
import com.moreno.models.Diner;
import com.moreno.utilities.Utilities;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.tablesCellRendered.AttendanceCellRendered;
import com.moreno.utilitiesTables.tablesModels.AttendanceTableModel;
import com.moreno.views.VPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

public class TabRegisterAttendance {
    private TabPane tabPane;
    private JTable table;
    private JTextField txtDiner;
    private AttendanceTableModel model;

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
            Diner diner= Diners.getByCode(code);
            if(diner!=null){
                if(Attendances.getOfDinerAndDate(diner,Utilities.getDate(new Date()))==null){
                    Attendance attendance=new Attendance();
                    attendance.setDate(new Date());
                    attendance.setDiner(diner);
                    attendance.save();
                    VPrincipal.attendancesToday.add(attendance);
                    Notify.sendNotify(Utilities.getJFrame(), Notify.Type.SUCCESS, Notify.Location.BOTTOM_RIGHT,"ÉXITO","Asistencia registrada");
                    UtilitiesTables.actualizarTabla(table);
                }else{
                    Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE","El comensal ya registró su asistencia");
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
                UtilitiesTables.actualizarTabla(table);
            }
        });
        loadPlaceHolders();
        loadTable();
    }
    private void loadTable(){
        model=new AttendanceTableModel(VPrincipal.attendancesToday);
        table.setModel(model);
        UtilitiesTables.headerNegrita(table);
        AttendanceCellRendered.setCellRenderer(table);
    }
    private void loadPlaceHolders(){
        txtDiner.putClientProperty("JTextField.placeholderText","Código...");
        txtDiner.putClientProperty("JTextField.leadingIcon",new ImageIcon(App.class.getResource("Icons/x24/lupa.png")));
    }
    public TabPane getTabPane(){
        return tabPane;
    }
}
