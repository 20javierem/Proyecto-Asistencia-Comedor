package com.moreno.views.dialogs;

import com.moreno.Notify;
import com.moreno.models.Attendance;
import com.moreno.models.Diner;
import com.moreno.utilities.Utilities;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class DAttendance extends JDialog{
    private JPanel contentPane;
    private JLabel lblNames;
    private JButton btnHecho;
    private JButton btnSave;
    private JDateChooser jdate;
    private JLabel lblLastNames;
    private Diner diner;

    public DAttendance(Diner diner) {
        super(Utilities.getJFrame(),"Registrar asistencia",true);
        this.diner=diner;
        initComponents();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerAttendance();
            }
        });
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }
    private void onCancel(){
        dispose();
    }
    private void registerAttendance(){
        Attendance attendance=new Attendance();
        attendance.setDate(jdate.getDate());
        attendance.save();
        dispose();
        Notify.sendNotify(Utilities.getJFrame(), Notify.Type.SUCCESS, Notify.Location.BOTTOM_RIGHT,"ÉXITO","Asistencia registrada");
    }
    private void initComponents(){
        lblNames.setText(diner.getCode());
        lblLastNames.setText(diner.getLastNames());
        setContentPane(contentPane);
        getRootPane().setDefaultButton(btnSave);
        setLocationRelativeTo(null);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        jdate =new JDateChooser(new Date());
    }
}
