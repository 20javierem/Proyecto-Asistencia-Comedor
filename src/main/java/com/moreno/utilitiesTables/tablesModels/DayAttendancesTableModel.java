package com.moreno.utilitiesTables.tablesModels;

import com.moreno.models.DayAttendance;
import com.moreno.utilities.Utilities;
import com.moreno.utilitiesTables.buttonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class DayAttendancesTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID","FECHA","DÍA","ASISTIERON","FALTARON","% ASISTIERON","% FALTARON","DETALLE"};
    private Class[] m_colTypes = {Integer.class, Date.class, String.class,String.class, String.class,String.class,String.class, JButton.class};
    private List<DayAttendance> vector;

    public DayAttendancesTableModel(List<DayAttendance> vector){
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
        DayAttendance dayAttendance =vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return dayAttendance.getId();
            case 1:
                return dayAttendance.getDate();
            case 2:
                return Utilities.dayFormat.format(dayAttendance.getDate()).toUpperCase();
            case 3:
                return dayAttendance.getTotalDinerAttendance();
            case 4:
                return dayAttendance.getTotalDinerNotAttendance();
            case 5:
                return dayAttendance.getPercentageAtendet();
            case 6:
                return dayAttendance.getPercentageNotAtendet();
            default:
                return new JButtonAction("x16/mostrarContraseña.png","Detalle");
        }
    }
    public DayAttendance get(int index){
        return vector.get(index);
    }

    public List<DayAttendance> getVector(){
        return vector;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (m_colTypes[columnIndex].equals(JButton.class)){
            return true;
        }
        return false;
    }
}
