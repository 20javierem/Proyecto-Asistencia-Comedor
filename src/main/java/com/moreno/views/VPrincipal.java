package com.moreno.views;

import com.formdev.flatlaf.extras.components.FlatToggleButton;
import com.moreno.App;
import com.moreno.controllers.DayAttendances;
import com.moreno.controllers.Diners;
import com.moreno.custom.*;
import com.moreno.models.DayAttendance;
import com.moreno.models.Diner;
import com.moreno.utilities.Propiedades;
import com.moreno.utilities.Utilities;
import com.moreno.views.dialogs.DExport;
import com.moreno.views.dialogs.DSettings;
import com.moreno.views.menus.MenuAttendance;
import com.moreno.views.menus.MenuDiners;
import com.moreno.views.menus.MenuReports;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class VPrincipal extends JFrame{
    private JPanel contentPane;
    private JMenu btnMenuInicio;
    private JButton btnAllDiners;
    private com.formdev.flatlaf.extras.components.FlatToggleButton btnAttendance;
    private com.formdev.flatlaf.extras.components.FlatToggleButton btnDiners;
    private JPanel paneNotify;
    private TabbedPane tabbedPane;
    private JSplitPane splitPane;
    private JButton btnNewAttendance;
    private JPanel cPane;
    private JPanel panelControles;
    private JPanel paneMenus;
    private JButton btnExit;
    private com.formdev.flatlaf.extras.components.FlatToggleButton btnReports;
    private JLabel lblIzquiera;
    private Propiedades propiedades;
    private MenuDiners menuDiners;
    private MenuReports menuReports;
    private MenuAttendance menuAttendance;
    public static List<Diner> diners;
    public static DayAttendance attendancesToday;

    public VPrincipal(Propiedades propiedades){
        this.propiedades=propiedades;
        initComponents();
        btnAllDiners.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuDiners.loadAllDiners();
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
        btnReports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMenuReports();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeSesion();
            }
        });
    }
    private void closeSesion(){
        int siono=JOptionPane.showOptionDialog(Utilities.getJFrame(),"¿Cerrar sesión?","MENSAJE",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,  null,new Object[] { "Si", "No"},"Si");
        if(siono==0){
            dispose();
        }

    }
    private void loadMenuReports(){
        splitPane.setRightComponent(menuReports.getContentPane());
    }
    private void loadMenuDiners(){
        splitPane.setRightComponent(menuDiners.getContentPane());
    }
    private void loadMenuAttendance(){
        splitPane.setRightComponent(menuAttendance.getContentPane());
    }

    private void loadLists(){
        diners= Diners.getTodos();
        attendancesToday= DayAttendances.getOfDate(new Date());
    }

    private void initComponents(){
        setContentPane(contentPane);
        setTitle("Software de asistencia");
        Image icon = (new ImageIcon(App.class.getResource("Images/icon.png"))).getImage();
        setIconImage(icon);
        Utilities.setJFrame(this);
        Utilities.setTabbedPane(tabbedPane);
        Utilities.setPropiedades(propiedades);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        loadLists();
        loadMenuItems();
        cargarCursores();
        loadMenus();
        loadMenuDiners();
        menuDiners.loadAllDiners();
        Utilities.lblIzquiera=lblIzquiera;
        pack();
        setLocationRelativeTo(null);
    }

    private void loadMenus(){
        menuDiners=new MenuDiners();
        menuAttendance=new MenuAttendance();
        menuReports=new MenuReports();
    }
    private void cargarCursores(){
        btnAllDiners.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNewAttendance.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAttendance.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDiners.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReports.setCursor(new Cursor(Cursor.HAND_CURSOR));
        paneNotify.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    private void loadMenuItems(){
        JMenuItem menuSettings=new JMenuItem("Configuraciones");
        JMenuItem menuExport=new JMenuItem("Exportar");
        JMenuItem menuHelp=new JMenuItem("Ayuda");
        menuSettings.setIcon(new ImageIcon(App.class.getResource("Icons/x16/settings.png")));
        menuSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSettings();
            }
        });
        menuExport.setIcon(new ImageIcon(App.class.getResource("Icons/x16/export.png")));
        menuExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadExport();
            }
        });
        menuHelp.setIcon(new ImageIcon(App.class.getResource("Icons/x16/export.png")));
        menuHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnMenuInicio.add(menuSettings);
        btnMenuInicio.add(menuExport);
        btnMenuInicio.add(menuHelp);
    }
    private void loadSettings(){
        DSettings dSettings=new DSettings(this);
        dSettings.setVisible(true);
    }
    private void loadExport(){
        DExport dExport=new DExport();
        dExport.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        paneNotify=new FondoPanel("Icons/x32/notificacion.png",new Dimension(32,32));
        panelControles=new CustomPane(2);
        panelControles.updateUI();
        cPane=new CustomPane(2);
        cPane.updateUI();
        btnDiners=new CToggleButton();
        btnAttendance=new CToggleButton();
        btnReports=new CToggleButton();
    }
}
