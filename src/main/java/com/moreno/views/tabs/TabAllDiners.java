package com.moreno.views.tabs;

import com.moreno.custom.TabPane;
import com.moreno.utilities.CSVReader;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.buttonEditors.JButtonEditorDiner;
import com.moreno.utilitiesTables.tablesCellRendered.DinerCellRendered;
import com.moreno.utilitiesTables.tablesModels.DinerTableModel;
import com.moreno.views.VPrincipal;
import com.moreno.views.dialogs.DDiner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabAllDiners {
    private TabPane tabPane;
    private JTable table;
    private JButton nuevoComensalButton;
    private JButton btnImportDiners;
    private DinerTableModel model;

    public TabAllDiners(){
        initComponents();
        nuevoComensalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNewDiner();
            }
        });
        btnImportDiners.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CSVReader.importDiners(table);
            }
        });
    }
    private void loadNewDiner(){
        DDiner dDiner=new DDiner(table);
        dDiner.setVisible(true);
    }
    private void initComponents(){
        tabPane.setTitle("Todos los comensales");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
            }
        });
        loadTable();
    }
    private void loadTable(){
        model=new DinerTableModel(VPrincipal.diners);
        table.setModel(model);
        UtilitiesTables.headerNegrita(table);
        DinerCellRendered.setCellRenderer(table);
        table.getColumnModel().getColumn(model.getColumnCount() - 1).setCellEditor(new JButtonEditorDiner(table));
    }
    public TabPane getTabPane(){
        return tabPane;
    }
}
