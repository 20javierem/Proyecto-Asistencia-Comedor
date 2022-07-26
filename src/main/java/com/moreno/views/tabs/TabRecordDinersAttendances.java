package com.moreno.views.tabs;

import com.moreno.Notify;
import com.moreno.controllers.DayAttendances;
import com.moreno.controllers.DinerAttendances;
import com.moreno.custom.TabPane;
import com.moreno.models.DayAttendance;
import com.moreno.models.Diner;
import com.moreno.models.DinerAttendance;
import com.moreno.models.DinerDaysAttendances;
import com.moreno.utilities.Utilities;
import com.moreno.utilitiesReports.UtilitiesReports;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.buttonEditors.JButtonEditorDayAttendance;
import com.moreno.utilitiesTables.tablesCellRendered.DayAttendancesCellRendered;
import com.moreno.utilitiesTables.tablesCellRendered.DinerDaysAttendancesCellRendered;
import com.moreno.utilitiesTables.tablesModels.DayAttendancesTableModel;
import com.moreno.utilitiesTables.tablesModels.DinerDaysAttendancesTableModel;
import com.moreno.views.VPrincipal;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TabRecordDinersAttendances {
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
    private JLabel lblTotalAttendances;
    private JLabel lbltotalNotAttendances;
    private JCheckBox ckIncludeInactives;
    private DinerDaysAttendancesTableModel model;
    private int totalAttendances=0;
    private int totalNotAttendances=0;

    public TabRecordDinersAttendances(){
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
    private void generateReport(){
        if(model.getRowCount()>0){
            btnGenerateReport.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            UtilitiesReports.generateReportDinersAttendances(model.get(0).getDiner().getAttendances().get(0).getDayAttendance().getDate(), model.get(model.getRowCount()-1).getDiner().getAttendances().get(0).getDayAttendance().getDate(),model.getVector(),totalAttendances,totalNotAttendances);
            btnGenerateReport.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }else{
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE","No se encontraron registros");
        }
    }
    private void calculateTotals(){
        totalAttendances=0;
        totalNotAttendances=0;
        for (DinerDaysAttendances dinerDaysAttendances:model.getVector()){
            totalAttendances+=dinerDaysAttendances.getTotalAttendances();
            totalNotAttendances+= dinerDaysAttendances.getTotalNotAttendances();
        }
        lblTotalAttendances.setText(String.valueOf(totalAttendances));
        lbltotalNotAttendances.setText(String.valueOf(totalNotAttendances));
    }
    private void initComponents(){
        tabPane.setTitle("Historial Comensales");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
                calculateTotals();
            }
        });
        loadPlaceHolders();
        Calendar start= Calendar.getInstance();
        start.set(Calendar.DATE,1);
        loadTable(getList(start.getTime(),new Date()));
    }
    private void getRecords(){
        btnBuscar.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        Date start = null;
        Date end = null;
        if(paneEntreFecha.isVisible()){
            if(fechaInicio.getDate()!=null&&fechaFin.getDate()!=null){
                start=fechaInicio.getDate();
                end=fechaFin.getDate();
                loadTable(getList(start,end));
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE", "Asistencias cargadas");
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR", "No seleccionó las fechas");
            }
        }
        if(paneDesdeFecha.isVisible()){
            if(fechaDesde.getDate()!=null){
                start=fechaDesde.getDate();
                loadTable(getList(start,end));
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE", "Asistencias cargadas");
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR", "No seleccionó la fecha");
            }
        }
        if(paneHastaFecha.isVisible()){
            if(fechaHasta.getDate()!=null){
                end=fechaHasta.getDate();
                loadTable(getList(start,end));
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE", "Asistencias cargadas");
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR", "No seleccionó la fecha");
            }
        }
        btnBuscar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private List<DinerDaysAttendances> getList(Date start,Date end){
        List<DinerDaysAttendances> dinerDaysAttendances=new ArrayList<>();
        List<Diner> diners=VPrincipal.dinersActives;
        if(ckIncludeInactives.isSelected()){
            diners=VPrincipal.diners;
        }
        if(start!=null&&end!=null){
            for (Diner diner:diners){
                List<DinerAttendance> dinerAttendances=DinerAttendances.getByDinerAndStartAndEnd(diner,start,end);
                dinerDaysAttendances.add(new DinerDaysAttendances(diner,dinerAttendances));
            }
        }else if(start!=null){
            for (Diner diner:diners){
                List<DinerAttendance> dinerAttendances=DinerAttendances.getByDinerAndAfer(diner,start);
                dinerDaysAttendances.add(new DinerDaysAttendances(diner,dinerAttendances));
            }
        }else{
            for (Diner diner:diners){
                List<DinerAttendance> dinerAttendances=DinerAttendances.getByDinerAndBefore(diner,end);
                dinerDaysAttendances.add(new DinerDaysAttendances(diner,dinerAttendances));
            }
        }
        return dinerDaysAttendances;
    }

    private void loadPlaceHolders(){
        fechaInicio.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Inicio...");
        fechaFin.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Fin...");
        fechaDesde.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Desde...");
        fechaHasta.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Hasta...");
    }
    private void loadTable(List<DinerDaysAttendances> list){
        model=new DinerDaysAttendancesTableModel(list);
        table.setModel(model);
        UtilitiesTables.headerNegrita(table);
        DinerDaysAttendancesCellRendered.setCellRenderer(table);
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
