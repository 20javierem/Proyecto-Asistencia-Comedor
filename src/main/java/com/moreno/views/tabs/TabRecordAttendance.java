package com.moreno.views.tabs;

import com.moreno.custom.TabPane;
import com.moreno.custom.TxtSearch;
import com.moreno.utilities.Utilities;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabRecordAttendance {
    private TabPane tabPane;
    private JTable table;
    private JComboBox cbbSex;
    private TxtSearch txtSearch;
    private JButton btnClearFilters;
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

    public TabRecordAttendance(){
        initComponents();
        cbbDate.addActionListener(e -> {
            verifyComboDate();
            filter();
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
    }
    private void loadPlaceHolders(){
        txtSearch.setPlaceHolderText("Buscar...");
        fechaInicio.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Inicio...");
        fechaFin.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Fin...");
        fechaDesde.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Desde...");
        fechaHasta.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Hasta...");



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
