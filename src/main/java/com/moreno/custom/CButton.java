package com.moreno.custom;

import javax.swing.*;

public class CButton extends JButton {
    @Override
    public void updateUI() {
        super.updateUI();
        setBackground(new JPanel().getBackground().darker());
    }
}
