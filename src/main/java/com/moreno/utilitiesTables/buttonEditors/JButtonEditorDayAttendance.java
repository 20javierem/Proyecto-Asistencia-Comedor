package com.moreno.utilitiesTables.buttonEditors;

import com.moreno.models.DayAttendance;
import com.moreno.models.Diner;
import com.moreno.utilitiesReports.UtilitiesReports;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.tablesModels.DayAttendancesTableModel;
import com.moreno.utilitiesTables.tablesModels.DinerTableModel;
import com.moreno.views.dialogs.DDiner;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonEditorDayAttendance extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButtonAction button;
    private JTable table;

    public JButtonEditorDayAttendance(JTable table) {

        this.table=table;
        button=new JButtonAction("x16/mostrarContraseña.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if(table.getSelectedRow()!=-1){
            button.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            DayAttendance dayAttendance=((DayAttendancesTableModel) table.getModel()).get(table.convertRowIndexToModel(table.getSelectedRow()));
            UtilitiesReports.generateReportDayAttendance(dayAttendance);
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
