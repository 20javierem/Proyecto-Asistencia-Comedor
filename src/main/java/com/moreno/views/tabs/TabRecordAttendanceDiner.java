package com.moreno.views.tabs;

import com.moreno.Notify;
import com.moreno.controllers.DinerAttendances;
import com.moreno.custom.TabPane;
import com.moreno.models.Diner;
import com.moreno.models.DinerAttendance;
import com.moreno.utilities.Utilities;
import com.moreno.utilitiesReports.UtilitiesReports;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.buttonEditors.JButtonEditorDayAttendance;
import com.moreno.utilitiesTables.tablesCellRendered.DayAttendancesCellRendered;
import com.moreno.utilitiesTables.tablesCellRendered.DinerDayAttendanceCellRendered;
import com.moreno.utilitiesTables.tablesModels.DinerDayAttendanceTableModel;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TabRecordAttendanceDiner {
    private TabPane tabPane;
    private JComboBox cbbDate;
    private JPanel paneEntreFecha;
    private JDateChooser fechaInicio;
    private JDateChooser fechaFin;
    private JPanel paneHastaFecha;
    private JDateChooser fechaHasta;
    private JPanel paneDesdeFecha;
    private JDateChooser fechaDesde;
    private JButton btnBuscar;
    private JButton btnGenerateReport;
    private JTable table;
    private JLabel lbltotalNotAttendances;
    private JLabel lblDiner;
    private JLabel lblDniDiner;
    private JLabel lblState;
    private JLabel lblPhone;
    private DinerDayAttendanceTableModel model;
    private Diner diner;
    private int totalNotAttendances;

    public TabRecordAttendanceDiner(Diner diner){
        this.diner=diner;
        initComponents();
        cbbDate.addActionListener(e -> {
            verifyComboDate();
            filter();
        });
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getRecords();
            }
        });
        btnGenerateReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });
    }
    private void loadDiner(){
        lblDiner.setText(diner.getLastNames()+" "+diner.getNames());
        lblDniDiner.setText(diner.getDni());
        lblPhone.setText(diner.getPhone());
        lblState.setText(diner.isActive()?"ACTIVO":"INACTIVO");
    }
    private void generateReport(){
        if(model.getRowCount()>0){
            btnGenerateReport.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            UtilitiesReports.generateReportDinerAttendances(model.get(0).getDayAttendance().getDate(),model.get(model.getRowCount()-1).getDayAttendance().getDate(), model.getVector(),totalNotAttendances);
            btnGenerateReport.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }else{
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE","No se encontraron registros");
        }
    }
    private void calculateTotals(){
        totalNotAttendances=0;
        for (DinerAttendance dinerAttendance:model.getVector()){
            if(!dinerAttendance.isAttended()) {
                totalNotAttendances++;
            }
        }
        lbltotalNotAttendances.setText(String.valueOf(totalNotAttendances));
        lbltotalNotAttendances.setText(totalNotAttendances+" : "+Utilities.numberFormat.format(((double) (totalNotAttendances*100)) / model.getVector().size())+"%");
    }
    private void initComponents(){
        tabPane.setTitle("Historial de asistencia "+diner.getDni());
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
                calculateTotals();
            }
        });
        loadDiner();
        loadPlaceHolders();
        Calendar start= Calendar.getInstance();
        start.set(Calendar.DATE,1);
        loadTable(DinerAttendances.getByDinerAndStartAndEnd(diner,start.getTime(),new Date()));
    }
    private void getRecords(){
        btnBuscar.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        Date start;
        Date end;
        if(paneEntreFecha.isVisible()){
            if(fechaInicio.getDate()!=null&&fechaFin.getDate()!=null){
                start=fechaInicio.getDate();
                end=fechaFin.getDate();
                loadTable(DinerAttendances.getByDinerAndStartAndEnd(diner,start,end));
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE", "Asistencias cargadas");
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR", "No seleccionó las fechas");
            }
        }
        if(paneDesdeFecha.isVisible()){
            if(fechaDesde.getDate()!=null){
                start=fechaDesde.getDate();
                loadTable(DinerAttendances.getByDinerAndAfer(diner,start));
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE", "Asistencias cargadas");
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR", "No seleccionó la fecha");
            }
        }
        if(paneHastaFecha.isVisible()){
            if(fechaHasta.getDate()!=null){
                end=fechaHasta.getDate();
                loadTable(DinerAttendances.getByDinerAndBefore(diner,end));
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE", "Asistencias cargadas");
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR", "No seleccionó la fecha");
            }
        }
        btnBuscar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    private void loadPlaceHolders(){
        fechaInicio.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Inicio...");
        fechaFin.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Fin...");
        fechaDesde.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Desde...");
        fechaHasta.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Hasta...");
    }
    private void loadTable(List<DinerAttendance> list){
        model=new DinerDayAttendanceTableModel(list);
        table.setModel(model);
        UtilitiesTables.headerNegrita(table);
        DinerDayAttendanceCellRendered.setCellRenderer(table);
        table.getColumnModel().getColumn(model.getColumnCount() - 1).setCellEditor(new JButtonEditorDayAttendance(table));
        calculateTotals();
    }
    public TabPane getTabPane(){
        return tabPane;
    }
    private void filter(){

    }
    private void verifyComboDate() {
        switch (cbbDate.getSelectedIndex()) {
            case 0:
                paneEntreFecha.setVisible(false);
                paneDesdeFecha.setVisible(false);
                paneHastaFecha.setVisible(false);
                break;
            case 1:
                paneEntreFecha.setVisible(false);
                paneDesdeFecha.setVisible(true);
                paneHastaFecha.setVisible(false);
                break;
            case 2:
                paneEntreFecha.setVisible(false);
                paneDesdeFecha.setVisible(false);
                paneHastaFecha.setVisible(true);
                break;
            case 3:
                paneEntreFecha.setVisible(true);
                paneDesdeFecha.setVisible(false);
                paneHastaFecha.setVisible(false);

                break;
        }
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        fechaInicio = new JDateChooser();
        fechaFin = new JDateChooser();
        fechaDesde = new JDateChooser();
        fechaHasta = new JDateChooser();

        fechaInicio.setDateFormatString(Utilities.getFormatoFecha());
        fechaFin.setDateFormatString(Utilities.getFormatoFecha());
        fechaDesde.setDateFormatString(Utilities.getFormatoFecha());
        fechaHasta.setDateFormatString(Utilities.getFormatoFecha());
    }
}
