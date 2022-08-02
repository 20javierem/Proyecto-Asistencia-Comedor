package com.moreno.utilitiesTables.buttonEditors;

import com.moreno.models.Diner;
import com.moreno.utilities.Utilities;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.tablesModels.DinerDaysAttendancesTableModel;
import com.moreno.utilitiesTables.tablesModels.DinerTableModel;
import com.moreno.views.dialogs.DDiner;
import com.moreno.views.tabs.TabRecordAttendanceDiner;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonEditorDinersAttendances extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButtonAction button;
    private JTable table;

    public JButtonEditorDinersAttendances(JTable table) {
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
            Diner diner=((DinerDaysAttendancesTableModel) table.getModel()).get(table.convertRowIndexToModel(table.getSelectedRow())).getDiner();
            if(Utilities.getTabbedPane().indexOfTab("Historial de asistencia "+diner.getDni())==-1){
                TabRecordAttendanceDiner tabRecordAttendanceDiner=new TabRecordAttendanceDiner(diner);
                Utilities.getTabbedPane().addTab(tabRecordAttendanceDiner.getTabPane().getTitle(),tabRecordAttendanceDiner.getTabPane());
            }
            Utilities.getTabbedPane().setSelectedIndex(Utilities.getTabbedPane().indexOfTab("Historial de asistencia "+diner.getDni()));
        }
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
