package com.moreno.views.tabs;

import com.moreno.custom.TabPane;
import com.moreno.custom.TxtSearch;
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
    private TxtSearch txtSearch;
    private JTable table;
    private JComboBox cbbSex;
    private DinerTableModel model;
    private TableRowSorter<DinerTableModel> modeloOrdenado;
    private Map<Integer, String> listaFiltros = new HashMap<Integer, String>();
    private List<RowFilter<DinerTableModel, String>> filtros = new ArrayList<>();
    private RowFilter filtroand;

    public TabRecordDiners(){
        initComponents();
        txtSearch.getBtnClearSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtSearch.setText(null);
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
    }
    private void initComponents(){
        tabPane.setTitle("Todos los Comensales");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
            }
        });
        txtSearch.setPlaceHolderText("Buscar...");
        loadTable();
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
        if(!txtSearch.getText().isBlank()){
            filtros.add(RowFilter.regexFilter(txtSearch.getText(), 1,2,3,5));
            listaFiltros.put(1,txtSearch.getText());
            listaFiltros.put(2,txtSearch.getText());
            listaFiltros.put(3,txtSearch.getText());
            listaFiltros.put(5,txtSearch.getText());
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
