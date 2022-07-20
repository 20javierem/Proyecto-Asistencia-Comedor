package com.moreno.custom;


import javax.swing.*;
public class TabPane extends JPanel {
    private String title;
    private Icon icon;
    private JToggleButton option;
    private JButton actions=new JButton();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public JToggleButton getOption() {
        return option;
    }

    public void setOption(JToggleButton option) {
        this.option = option;
    }

    public JButton getActions() {
        return actions;
    }

    public void update() {
        actions.doClick();
    }

}
