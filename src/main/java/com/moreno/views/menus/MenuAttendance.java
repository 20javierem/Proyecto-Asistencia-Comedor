package com.moreno.views.menus;

import com.moreno.custom.CPane;
import com.moreno.custom.TabbedPane;
import com.moreno.utilities.Utilities;
import com.moreno.views.tabs.TabRecordAttendance;
import com.moreno.views.tabs.TabRegisterAttendance;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAttendance {
    private JPanel contentPane;
    private JToggleButton btnRegisterAttendance;
    private JToggleButton btnRecordAttendance;
    private TabRegisterAttendance tabRegisterAttendance;
    private TabRecordAttendance tabRecordAttendance;

    public MenuAttendance(){
        btnRegisterAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRegisterAttendance();
            }
        });
        btnRecordAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRecordAttendace();
            }
        });
    }

    public void loadRegisterAttendance(){
        Utilities.despintarButton(btnRecordAttendance);
        Utilities.buttonSelected(btnRegisterAttendance);
        if (tabRegisterAttendance == null) {
            tabRegisterAttendance = new TabRegisterAttendance();
        }
        if (Utilities.getTabbedPane().indexOfTab(tabRegisterAttendance.getTabPane().getTitle())==-1) {
            tabRegisterAttendance = new TabRegisterAttendance();
            tabRegisterAttendance.getTabPane().setOption(btnRegisterAttendance);
            Utilities.getTabbedPane().addTab(tabRegisterAttendance.getTabPane().getTitle(), tabRegisterAttendance.getTabPane().getIcon(), tabRegisterAttendance.getTabPane());
        }
        Utilities.getTabbedPane().setSelectedIndex(Utilities.getTabbedPane().indexOfTab(tabRegisterAttendance.getTabPane().getTitle()));
    }
    public void loadRecordAttendace(){
        Utilities.despintarButton(btnRegisterAttendance);
        Utilities.buttonSelected(btnRecordAttendance);
        if (tabRecordAttendance == null) {
            tabRecordAttendance = new TabRecordAttendance();
        }
        if (Utilities.getTabbedPane().indexOfTab(tabRecordAttendance.getTabPane().getTitle())==-1) {
            tabRecordAttendance = new TabRecordAttendance();
            tabRecordAttendance.getTabPane().setOption(btnRecordAttendance);
            Utilities.getTabbedPane().addTab(tabRecordAttendance.getTabPane().getTitle(), tabRecordAttendance.getTabPane().getIcon(), tabRecordAttendance.getTabPane());
        }
        Utilities.getTabbedPane().setSelectedIndex(Utilities.getTabbedPane().indexOfTab(tabRecordAttendance.getTabPane().getTitle()));
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        contentPane=new CPane();
    }
}
