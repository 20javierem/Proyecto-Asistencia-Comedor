package com.moreno.views.menus;

import com.moreno.custom.CustomPane;
import com.moreno.utilities.Utilities;
import com.moreno.views.tabs.TabRecordDinersAttendances;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuReports {
    private JPanel contentPane;
    private JToggleButton btnAllDiners;
    private JToggleButton btnByDiner;
    private TabRecordDinersAttendances tabRecordDinersAttendances;

    public MenuReports(){
        btnAllDiners.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAllDiners();
            }
        });
    }

    public void loadAllDiners(){
        Utilities.buttonSelected(btnAllDiners);
        if (tabRecordDinersAttendances == null) {
            tabRecordDinersAttendances = new TabRecordDinersAttendances();
        }
        if (Utilities.getTabbedPane().indexOfTab(tabRecordDinersAttendances.getTabPane().getTitle())==-1) {
            tabRecordDinersAttendances = new TabRecordDinersAttendances();
            tabRecordDinersAttendances.getTabPane().setOption(btnAllDiners);
            Utilities.getTabbedPane().addTab(tabRecordDinersAttendances.getTabPane().getTitle(), tabRecordDinersAttendances.getTabPane().getIcon(), tabRecordDinersAttendances.getTabPane());
        }
        Utilities.getTabbedPane().setSelectedIndex(Utilities.getTabbedPane().indexOfTab(tabRecordDinersAttendances.getTabPane().getTitle()));
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        contentPane=new CustomPane();
    }
}
