package com.moreno.views.dialogs;

import com.moreno.utilities.Propiedades;
import com.moreno.utilities.Utilities;
import com.moreno.views.VPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSettings extends JDialog{
    private JPanel contentPane;
    private JComboBox cbbTema;
    private JButton btnAplyThme;
    private JTextField txtNameInstitution;
    private JTextField txtDatabaseUrl;
    private JButton btnTryConection;
    private JButton btnHecho;
    private JButton btnSave;
    private Propiedades propiedades;
    private VPrincipal vPrincipal;

    public DSettings(VPrincipal vPrincipal,Propiedades propiedades){
        super(vPrincipal);
        this.vPrincipal=vPrincipal;
        this.propiedades=propiedades;
        initComponents();
        btnAplyThme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTheme();
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }
    private void save(){
        propiedades.setTema(cbbTema.getSelectedItem().toString());
        propiedades.save();
        dispose();
    }
    private void onCancel(){
        Utilities.setTema(propiedades.getTema());
        Utilities.updateComponents(getRootPane());
        Utilities.updateComponents(vPrincipal.getRootPane());
        dispose();
    }
    private void changeTheme(){
        Utilities.setTema(cbbTema.getSelectedItem().toString());
        Utilities.updateComponents(getRootPane());
        Utilities.updateComponents(vPrincipal.getRootPane());
    }
    private void loadSettings(){
        cbbTema.setSelectedItem(propiedades.getTema());
    }
    private void initComponents(){
        setContentPane(contentPane);
        loadSettings();
        pack();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnSave);
    }
}
