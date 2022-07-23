package com.moreno.views.tabs;

import com.moreno.custom.TabPane;
import com.moreno.custom.TxtSearch;
import com.moreno.models.Diner;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;

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
    private JTable table;
    private JButton btnExport;
    private Diner diner;

    public TabRecordAttendanceDiner(Diner diner){
        this.diner=diner;
        initComponents();
    }
    private void initComponents(){
        tabPane.setTitle("Reporte comensal: "+diner.getDni());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
