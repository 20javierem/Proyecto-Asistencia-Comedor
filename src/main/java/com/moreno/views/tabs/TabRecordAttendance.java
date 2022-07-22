package com.moreno.views.tabs;

import com.moreno.App;
import com.moreno.custom.TabPane;
import com.moreno.custom.TxtSearch;
import com.moreno.utilitiesTables.UtilitiesTables;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabRecordAttendance {
    private TabPane tabPane;
    private JTable table;
    private JComboBox cbbSex;
    private TxtSearch txtSearch;
    private JButton restablecerButton;
    private JButton btnClearFilters;
    private JComboBox comboBox1;

    public TabRecordAttendance(){
        initComponents();
    }
    private void initComponents(){
        tabPane.setTitle("Historial de asistencia");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
            }
        });
        txtSearch.setPlaceHolderText("Buscar...");
    }
    public TabPane getTabPane(){
        return tabPane;
    }
}
