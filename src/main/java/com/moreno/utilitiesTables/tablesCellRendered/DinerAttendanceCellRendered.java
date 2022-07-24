package com.moreno.utilitiesTables.tablesCellRendered;

import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.buttonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import static com.moreno.utilitiesTables.UtilitiesTables.buscarTexto;

public class DinerAttendanceCellRendered extends DefaultTableCellRenderer {

    public static void setCellRenderer(JTable table){
        DinerAttendanceCellRendered cellRendered=new DinerAttendanceCellRendered();
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(cellRendered);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getColumnClass(column).equals(JButton.class)){
            table.getColumnModel().getColumn(column).setMaxWidth(25);
            table.getColumnModel().getColumn(column).setMinWidth(25);
            table.getColumnModel().getColumn(column).setPreferredWidth(25);
            return UtilitiesTables.isButonSelected(isSelected,"x16/editar.png",table);
        }else{
            JTextField componente=buscarTexto(null,value,column,this);
            switch(table.getColumnName(column)){
                case "ID":
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(40);
                    table.getColumn(table.getColumnName(column)).setMinWidth(40);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(40);
                    break;
                case "DNI":
                case "CELULAR":
                case "SEXO":
                case "ASISTENCIA":
                case "FECHA":
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(100);
                    table.getColumn(table.getColumnName(column)).setMinWidth(100);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(100);
                    break;
                default:
                    break;
            }
            return componente;
        }
    }
}
