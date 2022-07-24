package com.moreno.utilitiesTables.buttonEditors;

import com.moreno.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JButtonAction extends JButton {

    public JButtonAction(String icon, String text) {
        setIcon(new ImageIcon(App.class.getResource("Icons/"+icon)));
        setText(text);
        setHorizontalTextPosition(4);
        initialize();
    }
    public JButtonAction(String icon) {
        setIcon(new ImageIcon(App.class.getResource("Icons/"+icon)));
        initialize();
    }
    public JButtonAction(String icon, Color background) {
        setBackground(background);
        setBorder(BorderFactory.createEmptyBorder());
        setIcon(new ImageIcon(App.class.getResource("Icons/"+icon)));
        initialize();
    }
    public JButtonAction(String icon,String text, Color background) {
        setBackground(background);
        setBorder(BorderFactory.createEmptyBorder());
        setIcon(new ImageIcon(App.class.getResource("Icons/"+icon)));
        setText(text);
        setHorizontalTextPosition(4);
        initialize();
    }
    private void initialize() {
        setOpaque(false);
        setBorderPainted(false);
        setFocusable(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(new JTable().getSelectionBackground());
            }
        });
    }
    public void changeIcon(String icon){
        setIcon(new ImageIcon(App.class.getResource("Icons/"+icon)));
    }
}