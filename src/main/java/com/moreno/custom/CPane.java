package com.moreno.custom;

import javax.swing.*;

public class CPane extends JPanel {
    private int dark=1;

    public CPane(int dark){
        this.dark=dark;
    }
    public CPane(){

    }
    @Override
    public void updateUI() {
        JPanel panel=new JPanel();
        super.updateUI();
        for (int i=0;i<dark;i++){
            panel.setBackground(panel.getBackground().darker());
        }
        setBackground(panel.getBackground());
    }
}
