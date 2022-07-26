package com.moreno.views.menus;

import com.moreno.custom.CPane;
import com.moreno.custom.TabbedPane;
import com.moreno.utilities.Utilities;
import com.moreno.views.tabs.TabAllDiners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuDiners {
    private JPanel contentPane;
    private JToggleButton btnActiveDiners;
    private JToggleButton btnAllDiners;
    private TabAllDiners tabStudents;

    public MenuDiners(){
        btnAllDiners.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAllDiners();
            }
        });
            }

    public void loadAllDiners(){
        Utilities.buttonSelected(btnAllDiners);
        if (tabStudents == null) {
            tabStudents = new TabAllDiners();
        }
        if (Utilities.getTabbedPane().indexOfTab(tabStudents.getTabPane().getTitle())==-1) {
            tabStudents = new TabAllDiners();
            tabStudents.getTabPane().setOption(btnAllDiners);
            Utilities.getTabbedPane().addTab(tabStudents.getTabPane().getTitle(), tabStudents.getTabPane().getIcon(), tabStudents.getTabPane());
        }
        Utilities.getTabbedPane().setSelectedIndex(Utilities.getTabbedPane().indexOfTab(tabStudents.getTabPane().getTitle()));
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        contentPane=new CPane();
    }
}
