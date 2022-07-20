package com.moreno.views.tabs;

import com.moreno.custom.TabPane;
import com.moreno.utilities.utilitiesTables.UtilitiesTables;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabAllDiners {
    private TabPane tabPane;
    private JTable table;
    private JButton nuevoEstudianteButton;

    public TabAllDiners(){
        initComponents();
    }
    private void initComponents(){
        tabPane.setTitle("Todos los comensales");
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
