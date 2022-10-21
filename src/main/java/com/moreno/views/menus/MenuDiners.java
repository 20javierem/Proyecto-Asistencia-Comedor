package com.moreno.views.menus;

import com.moreno.custom.CustomPane;
import com.moreno.utilities.Utilities;
import com.moreno.views.tabs.TabAllDiners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuDiners {
    private JPanel contentPane;
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
        if (Utilities.getTabbedPane().indexOfComponent(tabStudents.getTabPane())==-1) {
            tabStudents = new TabAllDiners();
            tabStudents.getTabPane().setOption(btnAllDiners);
            Utilities.getTabbedPane().addTab(tabStudents.getTabPane().getTitle(), tabStudents.getTabPane().getIcon(), tabStudents.getTabPane());
        }
        Utilities.getTabbedPane().setSelectedComponent(tabStudents.getTabPane());
    }

    public JPanel getContentPane() {
        contentPane.updateUI();
        btnAllDiners.updateUI();
        return contentPane;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        contentPane=new CustomPane();
    }
}
