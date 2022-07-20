package com.moreno.views.dialogs;

import com.moreno.models.Diner;
import com.moreno.utilities.Moreno;
import com.moreno.utilities.Utilities;
import com.moreno.validators.DinerValidator;
import com.moreno.views.VPrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.event.*;
import java.util.Set;

public class DDiner extends JDialog{
    private JPanel contentPane;
    private JTextField txtNames;
    private JButton btnHecho;
    private JButton btnSave;
    private JTextField txtLastNames;
    private JTextField txtDni;
    private JTextField txtPhone;
    private JTextField txtCode;
    private Diner diner;
    private boolean update=false;

    public DDiner(){
        this(new Diner());
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
    public DDiner(Diner diner){
        super(Utilities.getJFrame(),"Nuevo comensal",true);
        this.diner=diner;
        initComponents();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void initComponents(){
        setContentPane(contentPane);
        if(diner.getId()!=null){
            update=true;
            setTitle("Editar comensal");
            btnHecho.setText("Cancelar");
            btnSave.setText("Guardar");
            loadDiner();
        }
        getRootPane().setDefaultButton(btnSave);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    private void onCancel(){
        if(update){
            diner.refresh();
        }
        dispose();
    }
    private void save(){
        diner.setNames(txtNames.getText().trim());
        diner.setLastNames(txtLastNames.getText().trim());
        diner.setCode(txtCode.getText().trim());
        diner.setDni(txtDni.getText().trim());
        diner.setPhone(txtPhone.getText().trim());
        Set<ConstraintViolation<Diner>> errors = DinerValidator.loadViolations(diner);
        if (errors.isEmpty()) {
            diner.save();
            if(update){
                dispose();
            }else{
                VPrincipal.diners.add(diner);
                diner=new Diner();
                loadDiner();
            }
        } else {
            DinerValidator.mostrarErrores(errors);
        }
    }
    private void loadDiner(){
        txtNames.setText(diner.getNames());
        txtLastNames.setText(diner.getLastNames());
        txtDni.setText(diner.getDni());
        txtCode.setText(diner.getCode());
        txtPhone.setText(diner.getPhone());
    }
}
