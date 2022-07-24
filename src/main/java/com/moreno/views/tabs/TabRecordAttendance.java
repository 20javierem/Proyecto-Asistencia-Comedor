package com.moreno.views.tabs;

import com.moreno.Notify;
import com.moreno.controllers.DayAttendances;
import com.moreno.custom.TabPane;
import com.moreno.custom.TxtSearch;
import com.moreno.models.DayAttendance;
import com.moreno.utilities.Utilities;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.tablesCellRendered.DayAttendancesCellRendered;
import com.moreno.utilitiesTables.tablesModels.DayAttendancesTableModel;
import com.moreno.views.VPrincipal;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TabRecordAttendance {
    private TabPane tabPane;
    private JTable table;
    private JComboBox cbbDate;
    private JPanel paneEntreFecha;
    private JDateChooser fechaInicio;
    private JDateChooser fechaFin;
    private JPanel paneHastaFecha;
    private JDateChooser fechaHasta;
    private JPanel paneDesdeFecha;
    private JDateChooser fechaDesde;
    private JButton btnBuscar;
    private JButton btnExport;
    private DayAttendancesTableModel model;

    public TabRecordAttendance(){
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
    }
    private void initComponents(){
        tabPane.setTitle("Historial de asistencia");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
            }
        });
        loadPlaceHolders();
        loadTable(VPrincipal.attendancesOfMonth);
    }
    private void getRecords(){
        Date start = null;
        Date end = null;
        if(paneEntreFecha.isVisible()){
            if(fechaInicio.getDate()!=null){
                start=fechaInicio.getDate();
            }
            if(fechaFin.getDate()!=null){
                end=fechaFin.getDate();
            }
        }
        if(paneDesdeFecha.isVisible()){
            if(fechaDesde.getDate()!=null){
                start=fechaDesde.getDate();
            }
        }
        if(paneHastaFecha.isVisible()){
            if(fechaHasta.getDate()!=null){
                end=fechaHasta.getDate();
            }
        }
        if(start!=null||end!=null){
            btnBuscar.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if(start==null){
                loadTable(DayAttendances.getBefore(end));
            }else if(end==null){
                loadTable(DayAttendances.getAfter(start));
            }else{
                loadTable(DayAttendances.getByRangeOfDate(start,end));
            }
            btnBuscar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE", "Asistencias cargadas");
        }else{
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR", "No seleccionó la fecha");
        }
    }
    private void loadPlaceHolders(){
        fechaInicio.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Inicio...");
        fechaFin.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Fin...");
        fechaDesde.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Desde...");
        fechaHasta.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Hasta...");
    }
    private void loadTable(List<DayAttendance> list){
        model=new DayAttendancesTableModel(list);
        table.setModel(model);
        UtilitiesTables.headerNegrita(table);
        DayAttendancesCellRendered.setCellRenderer(table);
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
