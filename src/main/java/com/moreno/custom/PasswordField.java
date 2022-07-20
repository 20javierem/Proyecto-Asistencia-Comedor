package com.moreno.custom;

import com.moreno.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PasswordField extends JPasswordField {
    private JButton btnShowPasword;

    public PasswordField(){
        loadShowPasword();
    }
    private void loadShowPasword() {
        putClientProperty("JTextField.placeholderText","Contraseña");
        btnShowPasword=new JButton();
        btnShowPasword.setVisible(false);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (getPassword().length > 0) {
                    btnShowPasword.setVisible(true);
                } else {
                    btnShowPasword.setVisible(false);
                }
            }
        });
        btnShowPasword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (getEchoChar() == '•') {
                    setEchoChar((char) 0);
                    btnShowPasword.setIcon(new ImageIcon(App.class.getResource("Icons/x16/mostrarContraseña.png")));
                } else {
                    setEchoChar('•');
                    btnShowPasword.setIcon(new ImageIcon(App.class.getResource("Icons/x16/ocultarContraseña.png")));
                }
            }
        });
        btnShowPasword.setIcon(new ImageIcon(App.class.getResource("Icons/x16/ocultarContraseña.png")));
        btnShowPasword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        putClientProperty("JTextField.trailingComponent",btnShowPasword);
    }
}
