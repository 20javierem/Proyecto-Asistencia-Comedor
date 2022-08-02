package com.moreno.utilitiesTables.tablesModels;

import com.moreno.models.DinerDaysAttendances;
import com.moreno.utilitiesTables.buttonEditors.JButtonAction;
import org.jfree.ui.action.ActionButton;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DinerDaysAttendancesTableModel extends AbstractTableModel {
    private String[] columnNames = {"DNI","NOMBRES","APELLIDOS","CELULAR","FALTAS","FALTAS %",""};
    private Class[] m_colTypes = {String.class, String.class, String.class, String.class,String.class,String.class,JButton.class};
    private List<DinerDaysAttendances> vector;

    public DinerDaysAttendancesTableModel(List<DinerDaysAttendances> vector){
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
        DinerDaysAttendances dinerDaysAttendances =vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return dinerDaysAttendances.getDiner().getDni();
            case 1:
                return dinerDaysAttendances.getDiner().getNames();
            case 2:
                return dinerDaysAttendances.getDiner().getLastNames();
            case 3:
                return dinerDaysAttendances.getDiner().getPhone();
            case 4:
                return dinerDaysAttendances.getTotalNotAttendances();
            case 5:
                return dinerDaysAttendances.getTotalNotAttendancesPercentage();
            default:
                return new JButtonAction("x16/mostrarContraseña.png");
        }
    }

    public List<DinerDaysAttendances> getVector() {
        return vector;
    }

    public DinerDaysAttendances get(int index){
        return vector.get(index);
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (m_colTypes[columnIndex].equals(JButton.class)){
            return true;
        }
        return false;
    }
}