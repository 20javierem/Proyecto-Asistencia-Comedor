package com.moreno.utilitiesTables.buttonEditors;

import com.moreno.Notify;
import com.moreno.models.Diner;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.tablesModels.DinerTableModel;
import com.moreno.views.dialogs.DDiner;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonEditorDiner extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButtonAction button;
    private JTable table;

    public JButtonEditorDiner(JTable table) {
        this.table=table;
        button=new JButtonAction("x16/editar.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if(table.getSelectedRow()!=-1){
            Diner diner=((DinerTableModel) table.getModel()).get(table.convertRowIndexToModel(table.getSelectedRow()));
            DDiner dDiner=new DDiner(diner,table);
            dDiner.setVisible(true);
            UtilitiesTables.actualizarTabla(table);
        }
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
