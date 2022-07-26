package com.moreno.views.dialogs;

import com.moreno.Notify;
import com.moreno.models.Diner;
import com.moreno.models.DinerAttendance;
import com.moreno.utilities.Utilities;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.validators.DinerValidator;
import com.moreno.views.VPrincipal;
import com.moreno.views.tabs.TabAllDiners;
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
    private JComboBox cbbSex;
    private JComboBox cbbState;
    private JTextField txtUser;
    private JPasswordField txtPassword;
    private Diner diner;
    private boolean update=false;
    private JTable table;
    private TabAllDiners tabAllDiners;

    public DDiner(TabAllDiners tabAllDiners,JTable table){
        this(tabAllDiners,new Diner(),table);
    }
    public DDiner(TabAllDiners tabAllDiners,Diner diner, JTable table){
        super(Utilities.getJFrame(),"Nuevo comensal",true);
        this.diner=diner;
        this.table=table;
        this.tabAllDiners=tabAllDiners;
        initComponents();
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
        setLocationRelativeTo(Utilities.getJFrame());
    }
    private void onCancel(){
        if(update){
//            diner.refresh();
        }
        dispose();
    }
    private void save(){
        diner.setDni(txtDni.getText().trim());
        diner.setNames(txtNames.getText().trim());
        diner.setLastNames(txtLastNames.getText().trim());
        diner.setMale(cbbSex.getSelectedIndex()==1);
        diner.setPhone(txtPhone.getText().trim());
        diner.setActive(cbbState.getSelectedIndex()==0);
        diner.setNameUser(txtUser.getText().trim());
        diner.setPasword(Utilities.encriptar(new String(txtPassword.getPassword())));
        Set<ConstraintViolation<Diner>> errors = DinerValidator.loadViolations(diner);
        if (errors.isEmpty()) {
            diner.save();
            if(update){
                updateTable();
                dispose();
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.SUCCESS, Notify.Location.TOP_CENTER,"ÉXITO","Cambios guardados");
            }else{
                VPrincipal.diners.add(diner);
                if(diner.isActive()){
                    if(VPrincipal.attendancesToday!=null){
                        DinerAttendance dinerAttendance=new DinerAttendance(diner,VPrincipal.attendancesToday);
                        VPrincipal.attendancesToday.getAttendances().add(dinerAttendance);
                        dinerAttendance.setAttended(!VPrincipal.attendancesToday.isState());
                        VPrincipal.attendancesToday.calculateTotals();
                        dinerAttendance.save();
                    }
                }
                updateTable();
                diner=new Diner();
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.SUCCESS, Notify.Location.TOP_CENTER,"ÉXITO","Comensal creado");
                loadDiner();
            }
        } else {
            DinerValidator.mostrarErrores(errors);
        }
    }
    private void updateTable(){
        if(table!=null){
            UtilitiesTables.actualizarTabla(table);
            tabAllDiners.filter();
        }
    }
    private void loadDiner(){
        txtNames.setText(diner.getNames());
        txtLastNames.setText(diner.getLastNames());
        txtDni.setText(diner.getDni());
        cbbSex.setSelectedIndex(diner.isMale()?1:0);
        txtPhone.setText(diner.getPhone());
        cbbState.setSelectedIndex(diner.isActive()?0:1);
        txtUser.setText(diner.getNameUser());
        txtPassword.setText(Utilities.desencriptar(diner.getPasword()));
    }
}
