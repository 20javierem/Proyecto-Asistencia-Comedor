package com.moreno.views.dialogs;

import com.moreno.Notify;
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
    private Propiedades propiedades=Utilities.getPropiedades();
    private VPrincipal vPrincipal;

    public DSettings(VPrincipal vPrincipal){
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
        propiedades.setTema((String) cbbTema.getSelectedItem());
        propiedades.setNameInstitute(txtNameInstitution.getText());
        propiedades.save();
        dispose();
        Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE","Cambios guardados");
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
        txtNameInstitution.setText(propiedades.getNameInstitute());
    }
    private void initComponents(){
        setContentPane(contentPane);
        loadSettings();
        pack();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnSave);
    }
}
