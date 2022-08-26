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
    private JButton btnHecho;
    private JButton btnSave;
    private Propiedades propiedades=Utilities.getPropiedades();
    private final VPrincipal vPrincipal;

    public DSettings(VPrincipal vPrincipal){
        super(Utilities.getJFrame(),"Configuraciones",true);
        this.vPrincipal=vPrincipal;
        initComponents();
        btnAplyThme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTheme(String.valueOf(cbbTema.getSelectedItem()));
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
        changeTheme(propiedades.getTema());
        dispose();
    }
    private void changeTheme(String theme){
        Utilities.setTema(theme);
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
        getRootPane().setDefaultButton(btnSave);
        setLocationRelativeTo(Utilities.getJFrame());
    }
}
