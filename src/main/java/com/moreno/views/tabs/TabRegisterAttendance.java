package com.moreno.views.tabs;

import com.moreno.Notify;
import com.moreno.controllers.DayAttendances;
import com.moreno.controllers.Diners;
import com.moreno.custom.TabPane;
import com.moreno.custom.TextFieldSearch;
import com.moreno.models.DayAttendance;
import com.moreno.models.DinerAttendance;
import com.moreno.models.Diner;
import com.moreno.utilities.Moreno;
import com.moreno.utilities.Utilities;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.tablesCellRendered.DinerDayAttendanceCellRendered;
import com.moreno.utilitiesTables.tablesModels.DinerDayAttendanceTableModel;
import com.moreno.views.VPrincipal;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.Date;
import java.util.HashMap;

public class TabRegisterAttendance {
    private TabPane tabPane;
    private JTable table;
    private TextFieldSearch txtDiner;
    private JLabel lblAttendances;
    private JLabel lblFaltaron;
    private JDateChooser dateChosser;
    private JComboBox cbbState;
    private JButton btnSave;
    private JTextField txtLunch;
    private JTextField txtDessert;
    private JTextField txtBeverage;
    private JButton btnSave2;
    private DinerDayAttendanceTableModel model;
    private HashMap<String,DinerAttendance> attendanceHashMap=new HashMap<>();
    private DayAttendance dayAttendance;
    private String date="";

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
        ((JTextField)dateChosser.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(!date.equals(((JTextField)dateChosser.getDateEditor()).getText())){
                    loadRecordsAttendances();
                    date=((JTextField)dateChosser.getDateEditor()).getText();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        btnSave2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }
    private void save(){
        dayAttendance.setLaunch(txtLunch.getText().trim());
        dayAttendance.setDessert(txtDessert.getText().trim());
        dayAttendance.setBeverage(txtBeverage.getText().trim());
        if(cbbState.getSelectedIndex()!=0){
            int siono=JOptionPane.showOptionDialog(Utilities.getJFrame(),"¿Esta acción no se puede deshacer?","CAMBIAR A FERIADO",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,  null,new Object[] { "Si", "No"},"Si");
            if(siono==0){
                dayAttendance.setState(cbbState.getSelectedIndex()==0);
                dayAttendance.getAttendances().forEach(dinerAttendance -> {
                    dinerAttendance.setAttended(true);
                    dinerAttendance.save();
                });
            }
        }
        dayAttendance.calculateTotals();
        loadCalculateTotals();
        dayAttendance.save();
        btnSave.setEnabled(dayAttendance.isState());
        txtDiner.setEnabled(dayAttendance.isState());
    }
    private void loadRecordsAttendances(){
        if(dateChosser.getDate() != null){
            DayAttendance dayAttendance= DayAttendances.getOfDate(dateChosser.getDate());
            if(dayAttendance!=null){
                this.dayAttendance=dayAttendance;
                loadAttendance();
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE","No se encontraron registros");
            }
        }
    }
    private void loadAttendance(){
        if(dayAttendance==null) {
            dayAttendance = new DayAttendance(new Date());
            dayAttendance.save();
            dayAttendance.getAttendances().forEach(Moreno::save);
            VPrincipal.attendancesToday=dayAttendance;
        }
        txtLunch.setText(dayAttendance.getLaunch());
        txtDessert.setText(dayAttendance.getDessert());
        txtBeverage.setText(dayAttendance.getBeverage());
        cbbState.setSelectedItem(dayAttendance.isState()?"NORMAL":"FERIADO");
        btnSave.setEnabled(dayAttendance.isState());
        btnSave2.setEnabled(dayAttendance.isState());
        txtDiner.setEnabled(dayAttendance.isState());
        txtLunch.setEnabled(dayAttendance.isState());
        loadTable();
        Notify.sendNotify(Utilities.getJFrame(), Notify.Type.SUCCESS, Notify.Location.BOTTOM_RIGHT,"ÉXITO","Registros cargados");
    }

    private void registerAttendance(){
        String code= txtDiner.getText().trim();
        if(!code.isBlank()){
            Diner diner= Diners.getByDni(code);
            System.out.println(code);
            System.out.println(Diners.get(1).getDni());
            if(diner!=null){
                if(diner.isActive()){
                    DinerAttendance dinerAttendance=attendanceHashMap.get(diner.getDni());
                    if(!dinerAttendance.isAttended()){
                        dinerAttendance.setAttended(true);
                        dinerAttendance.save();
                        dayAttendance.calculateTotals();
                        dayAttendance.save();
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
        txtDiner.setSelectionStart(0);
        txtDiner.setSelectionEnd(txtDiner.getText().length());
    }
    private void initComponents(){
        tabPane.setTitle("Registrar asistencia");
        dayAttendance=VPrincipal.attendancesToday;
        loadAttendance();
        tabPane.getActions().addActionListener(e -> {
            loadHashMap();
            UtilitiesTables.actualizarTabla(table);
            loadCalculateTotals();
        });
        txtDiner.setPlaceHolderText("DNI...");
    }
    private void loadTable(){
        loadHashMap();
        model=new DinerDayAttendanceTableModel(dayAttendance.getAttendances());
        table.setModel(model);
        UtilitiesTables.headerNegrita(table);
        DinerDayAttendanceCellRendered.setCellRenderer(table);
        loadCalculateTotals();
    }
    private void loadHashMap(){
        for (DinerAttendance dinerAttendance:dayAttendance.getAttendances()){
            attendanceHashMap.put(dinerAttendance.getDiner().getDni(),dinerAttendance);
        }
    }
    private void loadCalculateTotals(){
        lblAttendances.setText(dayAttendance.getTotalDinerAttendance()+" : "+dayAttendance.getPercentageAtendet());
        lblFaltaron.setText(dayAttendance.getTotalDinerNotAttendance()+" : "+dayAttendance.getPercentageNotAtendet());
    }
    public TabPane getTabPane(){
        return tabPane;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        dateChosser =new JDateChooser(new Date());
        dateChosser.setDateFormatString(Utilities.getFormatoFecha());
    }
}
