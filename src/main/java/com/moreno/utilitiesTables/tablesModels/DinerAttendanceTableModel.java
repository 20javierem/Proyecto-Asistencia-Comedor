package com.moreno.utilitiesTables.tablesModels;

import com.moreno.models.DinerAttendance;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class DinerAttendanceTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID","FECHA","NOMBRES","APELLIDOS","SEXO","DNI","CELULAR","ASISTIÓ"};
    private Class[] m_colTypes = {Integer.class, Date.class, String.class,String.class, String.class, String.class, String.class,String.class};
    private List<DinerAttendance> vector;

    public DinerAttendanceTableModel(List<DinerAttendance> vector){
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
        DinerAttendance dinerAttendance =vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return dinerAttendance.getDiner().getId();
            case 1:
                return dinerAttendance.getDayAttendance().getDate();
            case 2:
                return dinerAttendance.getDiner().getNames();
            case 3:
                return dinerAttendance.getDiner().getLastNames();
            case 4:
                return dinerAttendance.getDiner().isMale()?"MASCULINO":"FEMENINO";
            case 5:
                return dinerAttendance.getDiner().getDni();
            case 6:
                return dinerAttendance.getDiner().getPhone();
            default:
                return dinerAttendance.isAttended()?"ASISTIÓ":"FALTA";
        }
    }
    public DinerAttendance get(int index){
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
