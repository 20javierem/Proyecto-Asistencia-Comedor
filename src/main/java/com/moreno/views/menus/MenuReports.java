package com.moreno.views.menus;

import com.moreno.custom.CustomPane;
import com.moreno.utilities.Utilities;
import com.moreno.views.tabs.TabRecordDiners;
import com.moreno.views.tabs.TabRecordDinersAttendances;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuReports {
    private JPanel contentPane;
    private JToggleButton btnAttendancesDiners;
    private JToggleButton btnAllDiners;
    private TabRecordDinersAttendances tabRecordDinersAttendances;
    private TabRecordDiners tabRecordDiners;

    public MenuReports(){
        btnAttendancesDiners.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAttendancesDiners();
            }
        });
        btnAllDiners.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAllDiners();
            }
        });
    }

    public void loadAllDiners(){
        Utilities.despintarButton(btnAttendancesDiners);
        Utilities.buttonSelected(btnAllDiners);
        if (tabRecordDiners == null) {
            tabRecordDiners = new TabRecordDiners();
        }
        if (Utilities.getTabbedPane().indexOfComponent(tabRecordDiners.getTabPane())==-1) {
            tabRecordDiners = new TabRecordDiners();
            tabRecordDiners.getTabPane().setOption(btnAllDiners);
            Utilities.getTabbedPane().addTab(tabRecordDiners.getTabPane().getTitle(), tabRecordDiners.getTabPane().getIcon(), tabRecordDiners.getTabPane());
        }
        Utilities.getTabbedPane().setSelectedComponent(tabRecordDiners.getTabPane());
    }
    public void loadAttendancesDiners(){
        Utilities.despintarButton(btnAllDiners);
        Utilities.buttonSelected(btnAttendancesDiners);
        if (tabRecordDinersAttendances == null) {
            tabRecordDinersAttendances = new TabRecordDinersAttendances();
        }
        if (Utilities.getTabbedPane().indexOfComponent(tabRecordDinersAttendances.getTabPane())==-1) {
            tabRecordDinersAttendances = new TabRecordDinersAttendances();
            tabRecordDinersAttendances.getTabPane().setOption(btnAttendancesDiners);
            Utilities.getTabbedPane().addTab(tabRecordDinersAttendances.getTabPane().getTitle(), tabRecordDinersAttendances.getTabPane().getIcon(), tabRecordDinersAttendances.getTabPane());
        }
        Utilities.getTabbedPane().setSelectedComponent(tabRecordDinersAttendances.getTabPane());
    }
    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        contentPane=new CustomPane();
    }
}
