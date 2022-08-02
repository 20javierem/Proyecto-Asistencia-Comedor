package com.moreno.utilitiesTables.tablesModels;

import com.moreno.models.Diner;
import com.moreno.utilitiesTables.buttonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DinerTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID","DNI","APELLIDOS","NOMBRES","SEXO","CELULAR","ESTADO",""};
    private Class[] m_colTypes = {Integer.class, String.class, String.class,String.class, String.class, String.class, String.class,JButton.class};
    private List<Diner> vector;

    public DinerTableModel(List<Diner> vector){
        this.vector=vector;
    }
    @Override
    public int getRowCount() {
        return vector.size();
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    @Override
    public Class getColumnClass(int col) {
        return m_colTypes[col];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Diner diner=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return diner.getId();
            case 1:
                return diner.getDni();
            case 2:
                return diner.getLastNames();
            case 3:
                return diner.getNames();
            case 4:
                return diner.getSex();
            case 5:
                return diner.getPhone();
            case 6:
                return diner.isActive()?"ACTIVO":"INACTIVO";
            default:
                return new JButtonAction("x16/editar.png");
        }
    }
    public Diner get(int index){
        return vector.get(index);
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (m_colTypes[columnIndex].equals(JButton.class)) {
            return true;
        }
        return false;
    }
}
