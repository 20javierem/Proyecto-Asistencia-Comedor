package com.moreno.views.tabs;

import com.moreno.custom.TabPane;
import com.moreno.custom.TxtSearch;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TabAllDiners {
    private TabPane tabPane;
    private JTable table;
    private JButton nuevoComensalButton;
    private JButton btnImportDiners;
    private JComboBox cbbSex;
    private JButton btnClearFilters;
    private TxtSearch txtSearch;
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
        txtSearch.getBtnClearSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter();
            }
        });
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                filter();
            }
        });
    }
    private void loadNewDiner(){
        DDiner dDiner=new DDiner(table);
        dDiner.setVisible(true);
    }
    private void filter(){
        System.out.println("filtró");
    }
    private void initComponents(){
        tabPane.setTitle("Todos los comensales");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
            }
        });
        txtSearch.setPlaceHolderText("Buscar...");
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
