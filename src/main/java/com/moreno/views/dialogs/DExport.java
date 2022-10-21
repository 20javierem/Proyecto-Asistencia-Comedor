package com.moreno.views.dialogs;

import com.moreno.Notify;
import com.moreno.utilities.UtilitiesCsv;
import com.moreno.utilities.Utilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DExport extends JDialog{
    private JPanel contentPane;
    private JButton btnHecho;
    private JButton btnExportDiner;
    private JButton btnExportDayAttendances;
    private JCheckBox ckLastNames;
    private JCheckBox ckDni;
    private JCheckBox ckSex;
    private JCheckBox ckPhone;
    private JCheckBox ckState;
    private JCheckBox ckNames;
    private JCheckBox ckIdDiner;
    private JCheckBox ckIdAttendanceDiner;
    private JCheckBox ckDiner;
    private JCheckBox ckAttendanceDay;
    private JCheckBox ckIdAttendanceDay;
    private JCheckBox ckDate;
    private JCheckBox ckTotalAttendances;
    private JCheckBox clTotalNotAttendances;
    private JCheckBox ckPercentageAtendet;
    private JCheckBox ckPercentageNotAtendet;
    private JButton btnExportDinerAttendances;
    private JCheckBox ckUser;
    private JCheckBox ckPasword;

    public DExport(){
        super(Utilities.getJFrame(),"Exportar",true);
        initComponents();
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        btnExportDiner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportDiners();
            }
        });
        btnExportDinerAttendances.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportDinerAttendances();
            }
        });
        btnExportDayAttendances.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportDayAttendances();
            }
        });
    }
    private void exportDiners(){
        List<String> columns=new ArrayList<>();
        if(ckIdDiner.isSelected()){
            columns.add("id");
        }
        if(ckDni.isSelected()){
            columns.add("dni");
        }
        if(ckLastNames.isSelected()){
            columns.add("lastNames");
        }
        if(ckNames.isSelected()){
            columns.add("names");
        }
        if(ckSex.isSelected()){
            columns.add("male");
        }
        if(ckPhone.isSelected()){
            columns.add("phone");
        }
        if(ckState.isSelected()){
            columns.add("active");
        }
        if(ckUser.isSelected()){
            columns.add("nameUser");
        }
        if(ckPasword.isSelected()){
            columns.add("pasword");
        }
        if(!columns.isEmpty()){
            UtilitiesCsv.exportDiners(columns);
        }else{
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.TOP_CENTER,"ADVERTENCIA","Debe seleccionar las columnas");
        }
    }
    private void exportDayAttendances(){
        List<String> columns=new ArrayList<>();
        if(ckIdAttendanceDay.isSelected()){
            columns.add("id");
        }
        if(ckDate.isSelected()){
            columns.add("date");
        }
        if(ckTotalAttendances.isSelected()){
            columns.add("totalDinerAttendance");
        }
        if(clTotalNotAttendances.isSelected()){
            columns.add("totalDinerNotAttendance");
        }
        if(ckPercentageAtendet.isSelected()){
            columns.add("percentageAtendet");
        }
        if(ckPercentageNotAtendet.isSelected()){
            columns.add("percentageNotAtendet");
        }
        if(!columns.isEmpty()){
            UtilitiesCsv.exportAttendancesDays(columns);
        }else{
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.TOP_CENTER,"ADVERTENCIA","Debe seleccionar las columnas");
        }
    }

    private void exportDinerAttendances(){
        List<String> columns=new ArrayList<>();
        if(ckIdAttendanceDiner.isSelected()){
            columns.add("id");
        }
        if(ckDiner.isSelected()){
            columns.add("diner");
        }
        if(ckAttendanceDay.isSelected()){
            columns.add("dayAttendance");
        }
        if(!columns.isEmpty()){
            UtilitiesCsv.exportDinersAttendances(columns);
        }else{
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.TOP_CENTER,"ADVERTENCIA","Debe seleccionar las columnas");
        }
    }
    private void onCancel(){
        dispose();
    }
    private void initComponents(){
        setContentPane(contentPane);
        pack();
        getRootPane().setDefaultButton(btnHecho);
        setLocationRelativeTo(Utilities.getJFrame());

    }
}
