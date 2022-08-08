package com.moreno.utilitiesTables.buttonEditors;

import com.moreno.Notify;
import com.moreno.models.DayAttendance;
import com.moreno.models.Diner;
import com.moreno.utilities.Utilities;
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
        DayAttendance dayAttendance=((DayAttendancesTableModel) table.getModel()).get(table.convertRowIndexToModel(table.getSelectedRow()));
        if(dayAttendance.isState()){
            button.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            UtilitiesReports.generateReportDayAttendance(dayAttendance);
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }else{
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE","El día seleccionado es feriado");
        }
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
