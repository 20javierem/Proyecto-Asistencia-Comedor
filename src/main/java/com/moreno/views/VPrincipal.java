package com.moreno.views;

import com.moreno.App;
import com.moreno.controllers.Diners;
import com.moreno.custom.CPane;
import com.moreno.custom.FondoPanel;
import com.moreno.custom.TabbedPane;
import com.moreno.models.Diner;
import com.moreno.utilities.Propiedades;
import com.moreno.utilities.Utilities;
import com.moreno.views.dialogs.DSettings;
import com.moreno.views.menus.MenuAttendance;
import com.moreno.views.menus.MenuDiners;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VPrincipal extends JFrame{
    private JPanel contentPane;
    private JMenu btnMenuInicio;
    private JButton btnStudents;
    private JButton btnAttendance;
    private JButton btnDiners;
    private JButton btnGestionar;
    private JPanel paneNotify;
    private TabbedPane tabbedPane;
    private JSplitPane splitPane;
    private JButton btnNewAttendance;
    private JPanel cPane;
    private JPanel panelControles;
    private JPanel paneMenus;
    private JButton btnExit;
    private JLabel lblNotify;
    private Propiedades propiedades;
    private MenuDiners menuDiners;
    private MenuAttendance menuAttendance;
    public static List<Diner> diners;

    public VPrincipal(Propiedades propiedades){
        this.propiedades=propiedades;
        initComponents();
        btnStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuDiners.loadActiveDiners();
            }
        });
        btnDiners.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMenuDiners();
            }
        });
        btnAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMenuAttendance();
            }
        });
        btnNewAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuAttendance.loadRegisterAttendance();
            }
        });
    }
    private void loadLists(){
        diners= Diners.getTodos();
    }
    private void loadMenuDiners(){
        Utilities.updateComponents(menuDiners.getContentPane());
        despintar(btnAttendance,new ImageIcon(App.class.getResource("Icons/x32/calendarioDefault.png")));
        despintar(btnGestionar,new ImageIcon(App.class.getResource("Icons/x32/gestionarDefault.png")));
        pintar(btnDiners,new ImageIcon(App.class.getResource("Icons/x32/studentsSelected.png")));
        splitPane.setRightComponent(null);
        splitPane.setRightComponent(menuDiners.getContentPane());
        contentPane.updateUI();
    }
    private void loadMenuAttendance(){
        Utilities.updateComponents(menuAttendance.getContentPane());
        despintar(btnDiners,new ImageIcon(App.class.getResource("Icons/x32/studentsDefault.png")));
        despintar(btnGestionar,new ImageIcon(App.class.getResource("Icons/x32/gestionarDefault.png")));
        pintar(btnAttendance,new ImageIcon(App.class.getResource("Icons/x32/calendarioSelected.png")));
        splitPane.setRightComponent(null);
        splitPane.setRightComponent(menuAttendance.getContentPane());
        contentPane.updateUI();
    }
    private void despintar(JButton boton,Icon icon){
        boton.setIcon(icon);
        boton.setContentAreaFilled(false);
        ((JPanel)boton.getParent()).setOpaque(false);
    }

    private void pintar(JButton boton,Icon icon){
        boton.setIcon(icon);
        boton.setContentAreaFilled(true);
        ((JPanel)boton.getParent()).setOpaque(true);
    }
    private void initComponents(){
        setContentPane(contentPane);
        setTitle("Software de asistencia");
        Utilities.setJFrame(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        loadLists();
        loadMenuItems();
        quitarBordes();
        cargarCursores();
        loadMenus();
        loadMenuDiners();
        menuDiners.loadActiveDiners();
        pack();
        setLocationRelativeTo(null);
    }
    private void loadMenus(){
        menuDiners=new MenuDiners(tabbedPane);
        menuAttendance=new MenuAttendance(tabbedPane);
    }
    private void quitarBordes(){
        Border border=BorderFactory.createEmptyBorder();
        btnAttendance.setBorder(border);
        btnDiners.setBorder(border);
        btnGestionar.setBorder(border);
        border.getBorderInsets(btnGestionar).set(0,4,0,4);
        border.getBorderInsets(btnAttendance).set(0,4,0,4);
        border.getBorderInsets(btnDiners).set(0,4,0,4);
    }
    private void cargarCursores(){
        btnStudents.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNewAttendance.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAttendance.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDiners.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGestionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        paneNotify.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    private void loadMenuItems(){
        JMenuItem menuSettings=new JMenuItem("configuraciones");
        menuSettings.setIcon(new ImageIcon(App.class.getResource("Icons/x16/settings.png")));
        menuSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSettings();
            }
        });
        btnMenuInicio.add(menuSettings);
    }
    private void loadSettings(){
        DSettings dSettings=new DSettings(this,propiedades);
        dSettings.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        paneNotify=new FondoPanel("Icons/x32/notificacion.png",new Dimension(32,32));
        panelControles=new CPane(2);
        panelControles.updateUI();
        cPane=new CPane(2);
        cPane.updateUI();
    }
}
