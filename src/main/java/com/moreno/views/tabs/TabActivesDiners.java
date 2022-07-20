package com.moreno.views.tabs;

import com.moreno.custom.TabPane;
import com.moreno.utilitiesTables.UtilitiesTables;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabActivesDiners {

    private TabPane tabPane;
    private JTable table;
    private JTextField txtSearch;

    public TabActivesDiners(){
        initComponents();
    }
    private void initComponents(){
        tabPane.setTitle("Comensales activos");
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
