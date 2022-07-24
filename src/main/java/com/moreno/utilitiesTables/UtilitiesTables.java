package com.moreno.utilitiesTables;

import com.moreno.utilities.Utilities;
import com.moreno.utilitiesTables.buttonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.util.Date;
import java.util.Map;

public class UtilitiesTables {

    public static void actualizarTabla(JTable tabla){
        SwingUtilities.invokeLater(() -> {
            tabla.updateUI();
            UtilitiesTables.headerNegrita(tabla);
        });
    }

    public static void headerNegrita(JTable table){
        ((DefaultTableCellRenderer) (table.getTableHeader()).getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer header = new DefaultTableCellRenderer();
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        header.setForeground(Color.white);
        header.setBackground(new Color(0xFF000000, true));
        header.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setHeaderRenderer(header);
        }
        table.getTableHeader().setReorderingAllowed(false);
    }

    public static JTextField buscarTexto(Map<Integer, String> listaFiltros, Object value, int column, DefaultTableCellRenderer defaultTableCellRenderer) {
        JTextField componente=new JTextField();
        componente.setBorder(defaultTableCellRenderer.getBorder());
        componente.setBackground(defaultTableCellRenderer.getBackground());
        componente.setForeground(defaultTableCellRenderer.getForeground());
        if(value instanceof Date){
            componente.setText(Utilities.formatoFecha.format(value));
        }else{
            if(value instanceof Double){
                componente.setText(Utilities.moneda.format(value));
            }else{
                componente.setText(String.valueOf(value));
            }
        }
        if(listaFiltros!=null){
            Highlighter hilit = new DefaultHighlighter();
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(componente.getSelectionColor());
            componente.setHighlighter(hilit);
            if(listaFiltros.get(column)!=null){
                String s = listaFiltros.get(column);
                if (s.length() > 0) {
                    String contenido = componente.getText();
                    int index = contenido.indexOf(s);
                    if (index >= 0) {
                        try {
                            int end = index + s.length();
                            hilit.addHighlight(index, end, painter);
                            componente.setCaretPosition(end);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return componente;
    }
    public static Component isButonSelected(boolean isSelected, String icono,JTable table){
        if(isSelected){
            return new JButtonAction(icono,table.getSelectionBackground());
        }else{
            return new JButtonAction(icono,table.getBackground());
        }
    }
    public static Component isButonSelected(boolean isSelected, String icono,String text,JTable table){
        if(isSelected){
            return new JButtonAction(icono,text,table.getSelectionBackground());
        }else{
            return new JButtonAction(icono,text,table.getBackground());
        }
    }
    public static void pintarComponente(Component component,boolean estado,boolean isSelected){
        if(!estado){
            if(!isSelected){
                component.setBackground(new Color(0xFFFF0000, true));
                component.setForeground(new Color(0xFFFFFF));
            }
        }
    }
}
