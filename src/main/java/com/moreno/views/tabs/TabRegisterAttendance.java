package com.moreno.views.tabs;

import com.moreno.custom.TabPane;
import com.moreno.utilities.utilitiesTables.UtilitiesTables;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabRegisterAttendance {
    private TabPane tabPane;
    private JButton nuevoEstudianteButton;
    private JTable table;
    private JTextField txtDiner;

    public TabRegisterAttendance(){
        initComponents();
    }

    private void initComponents(){
        tabPane.setTitle("Registrar asistencia");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
            }
        });
    }

    public TabPane getTabPane(){
        return tabPane;
    }
}
