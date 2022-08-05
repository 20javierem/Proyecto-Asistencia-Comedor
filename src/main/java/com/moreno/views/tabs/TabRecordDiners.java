package com.moreno.views.tabs;

import com.moreno.Notify;
import com.moreno.custom.TabPane;
import com.moreno.custom.TextFieldSearch;
import com.moreno.models.Diner;
import com.moreno.utilities.Utilities;
import com.moreno.utilitiesReports.UtilitiesReports;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.tablesCellRendered.DinerCellRendered;
import com.moreno.utilitiesTables.tablesModels.DinerTableModel;
import com.moreno.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabRecordDiners {
    private TabPane tabPane;
    private JComboBox cbbState;
    private JButton btnClearFilters;
    private JButton btnImportDiners;
    private TextFieldSearch textFieldSearch;
    private JTable table;
    private JComboBox cbbSex;
    private DinerTableModel model;
    private TableRowSorter<DinerTableModel> modeloOrdenado;
    private Map<Integer, String> listaFiltros = new HashMap<Integer, String>();
    private List<RowFilter<DinerTableModel, String>> filtros = new ArrayList<>();
    private RowFilter filtroand;

    public TabRecordDiners(){
        initComponents();
        textFieldSearch.getBtnClearSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldSearch.setText(null);
                filter();
            }
        });
        textFieldSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                filter();
            }
        });
        cbbState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter();
            }
        });
        cbbSex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter();
            }
        });
        btnImportDiners.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });
        btnClearFilters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFilters();
            }
        });
    }
    private void initComponents(){
        tabPane.setTitle("Todos los Comensales");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
            }
        });
        textFieldSearch.setPlaceHolderText("Buscar...");
        loadTable();
    }
    private void clearFilters(){
        cbbSex.setSelectedIndex(0);
        cbbState.setSelectedIndex(0);
        textFieldSearch.getBtnClearSearch().doClick();
    }
    private void generateReport(){
        List<Diner> diners=new ArrayList<>();
        for (int i=0;i<table.getRowCount();i++){
            diners.add(model.get(table.convertRowIndexToModel(i)));
        }
        if(!diners.isEmpty()){
            UtilitiesReports.generateReportDiners(diners);
        }else{
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE","No se encontraron registros");
        }
    }
    public void filter() {
        filtros.clear();
        listaFiltros.clear();

        if (cbbSex.getSelectedIndex()!=0) {
            filtros.add(RowFilter.regexFilter(cbbSex.getSelectedItem().toString(), 4));
        }
        if (cbbState.getSelectedIndex()!=0) {
            filtros.add(RowFilter.regexFilter(cbbState.getSelectedItem().toString(), 6));
        }
        if(!textFieldSearch.getText().isBlank()){
            filtros.add(RowFilter.regexFilter(textFieldSearch.getText(), 1,2,3,5));
            listaFiltros.put(1, textFieldSearch.getText());
            listaFiltros.put(2, textFieldSearch.getText());
            listaFiltros.put(3, textFieldSearch.getText());
            listaFiltros.put(5, textFieldSearch.getText());
        }
        filtroand = RowFilter.andFilter(filtros);
        modeloOrdenado.setRowFilter(filtroand);
    }
    private void loadTable(){
        model=new DinerTableModel(VPrincipal.diners);
        table.setModel(model);
        modeloOrdenado = new TableRowSorter<>(model);
        table.setRowSorter(modeloOrdenado);
        UtilitiesTables.headerNegrita(table);
        DinerCellRendered.setCellRenderer(table,listaFiltros);
        table.removeColumn(table.getColumn(""));
    }
    public TabPane getTabPane(){
        return tabPane;
    }
}
