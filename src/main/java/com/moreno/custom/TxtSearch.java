package com.moreno.custom;

import com.moreno.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TxtSearch extends JTextField {
    private JButton btnClearSearch=new JButton();

    public TxtSearch(){
        putClientProperty("JTextField.leadingIcon",new ImageIcon(App.class.getResource("Icons/x24/lupa.png")));
        btnClearSearch.setIcon(new ImageIcon(App.class.getResource("Icons/x24/cerrar.png")));
        btnClearSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        putClientProperty("JTextField.trailingComponent",btnClearSearch);
        btnClearSearch.setVisible(false);
        btnClearSearch.addActionListener(e -> {
            setText(null);
            btnClearSearch.setVisible(false);
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                btnClearSearch.setVisible(getText().length() > 0);
            }
        });
    }
    public JButton getBtnClearSearch(){
        return btnClearSearch;
    }
    public void setPlaceHolderText(String text){
        putClientProperty("JTextField.placeholderText",text);
    }
}
