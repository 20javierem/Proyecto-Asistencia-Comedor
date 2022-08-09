package com.moreno.views.tabs;

import com.moreno.custom.TabPane;
import com.moreno.custom.TextFieldSearch;
import com.moreno.utilities.UtilitiesCsv;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.buttonEditors.JButtonEditorDiner;
import com.moreno.utilitiesTables.tablesCellRendered.DinerCellRendered;
import com.moreno.utilitiesTables.tablesModels.DinerTableModel;
import com.moreno.views.VPrincipal;
import com.moreno.views.dialogs.DDiner;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabAllDiners {
    private TabPane tabPane;
    private JTable table;
    private JButton nuevoComensalButton;
    private JButton btnImportDiners;
    private JComboBox cbbSex;
    private JButton btnClearFilters;
    private TextFieldSearch textFieldSearch;
    private JComboBox cbbState;
    private DinerTableModel model;
    private TableRowSorter<DinerTableModel> modeloOrdenado;
    private Map<Integer, String> listaFiltros = new HashMap<Integer, String>();
    private List<RowFilter<DinerTableModel, String>> filtros = new ArrayList<>();
    private RowFilter filtroand;

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
                loadImportDiners();
            }
        });
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
        cbbSex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter();
            }
        });
        btnClearFilters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFilters();
            }
        });
        cbbState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter();
            }
        });
    }
    private void loadImportDiners(){
        UtilitiesCsv.importDiners(table,this);
    }
    private void clearFilters(){
        cbbSex.setSelectedIndex(0);
        textFieldSearch.getBtnClearSearch().doClick();
    }
    private void loadNewDiner(){
        DDiner dDiner=new DDiner(this,table);
        dDiner.setVisible(true);
    }
    public void filter() {
        filtros.clear();
        listaFiltros.clear();
        if (cbbSex.getSelectedIndex()!=0) {
            filtros.add(RowFilter.regexFilter(cbbSex.getSelectedItem().toString(), 4));
        }
        if (cbbState.getSelectedIndex()!=0) {
            filtros.add(RowFilter.regexFilter(cbbState.getSelectedIndex()==1?"SI":"NO", 6));
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
    private void initComponents(){
        tabPane.setTitle("Todos los comensales");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
                filter();
            }
        });
        textFieldSearch.setPlaceHolderText("Buscar...");
        loadTable();
    }

    private void loadTable(){
        model=new DinerTableModel(VPrincipal.diners);
        table.setModel(model);
        modeloOrdenado = new TableRowSorter<>(model);
        table.setRowSorter(modeloOrdenado);
        UtilitiesTables.headerNegrita(table);
        DinerCellRendered.setCellRenderer(table,listaFiltros);
        table.getColumnModel().getColumn(model.getColumnCount() - 1).setCellEditor(new JButtonEditorDiner(this,table));
    }
    public TabPane getTabPane(){
        return tabPane;
    }

}
