package com.moreno.utilitiesTables.tablesCellRendered;

import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.buttonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Map;

import static com.moreno.utilitiesTables.UtilitiesTables.buscarTexto;

public class DinerCellRendered extends DefaultTableCellRenderer {
    private Map<Integer, String> listaFiltros;
    public DinerCellRendered(Map<Integer, String> listaFiltros){
        this.listaFiltros=listaFiltros;
    }
    public static void setCellRenderer(JTable table,Map<Integer, String> listaFiltros){
        DinerCellRendered cellRendered=new DinerCellRendered(listaFiltros);
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
            JTextField componente=buscarTexto(listaFiltros,value,column,this);
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
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(100);
                    table.getColumn(table.getColumnName(column)).setMinWidth(100);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(100);
                    break;
                case "ESTADO":
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(90);
                    table.getColumn(table.getColumnName(column)).setMinWidth(90);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(90);
                    break;
                default:
                    break;
            }
            return componente;
        }
    }
}
