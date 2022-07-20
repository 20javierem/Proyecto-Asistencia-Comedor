package com.moreno.utilitiesTables.tablesModels;

import com.moreno.models.Attendance;
import com.moreno.models.Diner;
import com.moreno.utilitiesTables.buttonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class AttendanceTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID","FECHA","NOMBRES","APELLIDOS","CÓDIGO","DNI","CELULAR"};
    private Class[] m_colTypes = {Integer.class, Date.class, Integer.class, Integer.class, String.class, Integer.class, String.class};
    private List<Attendance> vector;

    public AttendanceTableModel(List<Attendance> vector){
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
        Attendance attendance=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return attendance.getDiner().getId();
            case 1:
                return attendance.getDate();
            case 2:
                return attendance.getDiner().getNames();
            case 3:
                return attendance.getDiner().getLastNames();
            case 4:
                return attendance.getDiner().getCode();
            case 5:
                return attendance.getDiner().getDni();
            default:
                return attendance.getDiner().getPhone();
        }
    }
    public Attendance get(int index){
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
