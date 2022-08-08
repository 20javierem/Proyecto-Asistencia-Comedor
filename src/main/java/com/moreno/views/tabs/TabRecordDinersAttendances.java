package com.moreno.views.tabs;

import com.moreno.App;
import com.moreno.Notify;
import com.moreno.controllers.DayAttendances;
import com.moreno.controllers.DinerAttendances;
import com.moreno.custom.TabPane;
import com.moreno.models.DayAttendance;
import com.moreno.models.Diner;
import com.moreno.models.DinerAttendance;
import com.moreno.models.DinerDaysAttendances;
import com.moreno.utilities.Utilities;
import com.moreno.utilitiesReports.UtilitiesReports;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.utilitiesTables.buttonEditors.JButtonEditorDayAttendance;
import com.moreno.utilitiesTables.buttonEditors.JButtonEditorDinersAttendances;
import com.moreno.utilitiesTables.tablesCellRendered.DayAttendancesCellRendered;
import com.moreno.utilitiesTables.tablesCellRendered.DinerDaysAttendancesCellRendered;
import com.moreno.utilitiesTables.tablesModels.DayAttendancesTableModel;
import com.moreno.utilitiesTables.tablesModels.DinerDaysAttendancesTableModel;
import com.moreno.views.VPrincipal;
import com.toedter.calendar.JDateChooser;
import org.jfree.data.time.Day;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TabRecordDinersAttendances {
    private TabPane tabPane;
    private JComboBox cbbDate;
    private JPanel paneEntreFecha;
    private JDateChooser fechaInicio;
    private JDateChooser fechaFin;
    private JPanel paneHastaFecha;
    private JDateChooser fechaHasta;
    private JPanel paneDesdeFecha;
    private JDateChooser fechaDesde;
    private JButton btnBuscar;
    private JButton btnGenerateReport;
    private JTable table;
    private JLabel lblTotalAttendances;
    private JLabel lbltotalNotAttendances;
    private JCheckBox ckIncludeInactives;
    private DinerDaysAttendancesTableModel model;
    private int totalAttendances=0;
    private int totalNotAttendances=0;
    private Date start;
    private Date end;

    public TabRecordDinersAttendances(){
        initComponents();
        cbbDate.addActionListener(e -> {
            verifyComboDate();
            filter();
        });
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getRecords();
            }
        });
        btnGenerateReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });
    }
    private void generateReport(){
        if(model.getRowCount()>0){
            btnGenerateReport.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if(start!=null&&end!=null){
                UtilitiesReports.generateReportDinersAttendances(start, end,model.getVector(),totalAttendances,totalNotAttendances);
            }else if(start!=null){
                UtilitiesReports.generateReportDinersAttendances(start, new Date(),model.getVector(),totalAttendances,totalNotAttendances);
            }else if(end!=null){
                UtilitiesReports.generateReportDinersAttendances(DayAttendances.get(1).getDate(), end,model.getVector(),totalAttendances,totalNotAttendances);
            }
            btnGenerateReport.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }else{
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE","No se encontraron registros");
        }
    }
    private void calculateTotals(){
        totalAttendances=0;
        totalNotAttendances=0;
        for (DinerDaysAttendances dinerDaysAttendances:model.getVector()){
            totalAttendances+=dinerDaysAttendances.getTotalAttendances();
            totalNotAttendances+= dinerDaysAttendances.getTotalNotAttendances();
        }
        lblTotalAttendances.setText(String.valueOf(totalAttendances));
        lbltotalNotAttendances.setText(String.valueOf(totalNotAttendances));
    }
    private void initComponents(){
        tabPane.setTitle("Asistencia de comensales");
        tabPane.getActions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilitiesTables.actualizarTabla(table);
                calculateTotals();
            }
        });
        loadPlaceHolders();
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.DATE,1);
        start=calendar.getTime();
        end=new Date();
        loadTable(getList(start,end));
        insertarMenuPopUp();
    }
     private void insertarMenuPopUp(){
        JPopupMenu pop_up = new JPopupMenu();
        JMenuItem editarProducto = new JMenuItem("Ver historial de asistencia", new ImageIcon(App.class.getResource("Icons/x16/mostrarContraseña.png")));
        editarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRecordAttendanceDiner();
            }
        });
        pop_up.add(editarProducto);
        table.addMouseListener( new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getButton()==3) {
                    int row = table.rowAtPoint( e.getPoint() );
                    table.setRowSelectionInterval(row,row);
                    pop_up.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
    private void loadRecordAttendanceDiner(){
        Diner diner=model.get(table.convertRowIndexToModel(table.getSelectedRow())).getDiner()  ;
        if(Utilities.getTabbedPane().indexOfTab("Historial de asistencia "+diner.getDni())==-1){
            TabRecordAttendanceDiner tabRecordAttendanceDiner=new TabRecordAttendanceDiner(diner);
            Utilities.getTabbedPane().addTab(tabRecordAttendanceDiner.getTabPane().getTitle(),tabRecordAttendanceDiner.getTabPane());
        }
        Utilities.getTabbedPane().setSelectedIndex(Utilities.getTabbedPane().indexOfTab("Historial de asistencia "+diner.getDni()));
    }
    private void getRecords(){
        btnBuscar.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        start = null;
        end = null;
        if(paneEntreFecha.isVisible()){
            if(fechaInicio.getDate()!=null&&fechaFin.getDate()!=null){
                start=fechaInicio.getDate();
                end=fechaFin.getDate();
                loadTable(getList(start,end));
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE", "Asistencias cargadas");
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR", "No seleccionó las fechas");
            }
        }
        if(paneDesdeFecha.isVisible()){
            if(fechaDesde.getDate()!=null){
                start=fechaDesde.getDate();
                loadTable(getList(start,end));
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE", "Asistencias cargadas");
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR", "No seleccionó la fecha");
            }
        }
        if(paneHastaFecha.isVisible()){
            if(fechaHasta.getDate()!=null){
                end=fechaHasta.getDate();
                loadTable(getList(start,end));
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"MENSAJE", "Asistencias cargadas");
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR", "No seleccionó la fecha");
            }
        }
        btnBuscar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private List<DinerDaysAttendances> getList(Date start,Date end){
        List<DinerDaysAttendances> dinerDaysAttendances=new ArrayList<>();
        List<Diner> diners=new ArrayList<>();
        if(ckIncludeInactives.isSelected()){
            diners=VPrincipal.diners;
        }else{
            for(Diner diner:VPrincipal.diners){
                if(diner.isActive()){
                    diners.add(diner);
                }
            }
        }
        if(start!=null&&end!=null){
            for (Diner diner:diners){
                List<DinerAttendance> dinerAttendances=DinerAttendances.getByDinerAndStartAndEnd(diner,start,end);
                dinerDaysAttendances.add(new DinerDaysAttendances(diner,dinerAttendances));
            }
        }else if(start!=null){
            for (Diner diner:diners){
                List<DinerAttendance> dinerAttendances=DinerAttendances.getByDinerAndAfer(diner,start);
                dinerDaysAttendances.add(new DinerDaysAttendances(diner,dinerAttendances));
            }
        }else{
            for (Diner diner:diners){
                List<DinerAttendance> dinerAttendances=DinerAttendances.getByDinerAndBefore(diner,end);
                dinerDaysAttendances.add(new DinerDaysAttendances(diner,dinerAttendances));
            }
        }
        return dinerDaysAttendances;
    }

    private void loadPlaceHolders(){
        fechaInicio.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Inicio...");
        fechaFin.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Fin...");
        fechaDesde.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Desde...");
        fechaHasta.getDateEditor().getUiComponent().putClientProperty("JTextField.placeholderText","Hasta...");
    }
    private void loadTable(List<DinerDaysAttendances> list){
        model=new DinerDaysAttendancesTableModel(list);
        table.setModel(model);
        UtilitiesTables.headerNegrita(table);
        DinerDaysAttendancesCellRendered.setCellRenderer(table);
        table.getColumnModel().getColumn(model.getColumnCount() - 1).setCellEditor(new JButtonEditorDinersAttendances(table));
        calculateTotals();
    }
    public TabPane getTabPane(){
        return tabPane;
    }
    private void filter(){

    }
    private void verifyComboDate() {
        switch (cbbDate.getSelectedIndex()) {
            case 0:
                paneEntreFecha.setVisible(false);
                paneDesdeFecha.setVisible(false);
                paneHastaFecha.setVisible(false);
                break;
            case 1:
                paneEntreFecha.setVisible(false);
                paneDesdeFecha.setVisible(true);
                paneHastaFecha.setVisible(false);
                break;
            case 2:
                paneEntreFecha.setVisible(false);
                paneDesdeFecha.setVisible(false);
                paneHastaFecha.setVisible(true);
                break;
            case 3:
                paneEntreFecha.setVisible(true);
                paneDesdeFecha.setVisible(false);
                paneHastaFecha.setVisible(false);

                break;
        }
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        fechaInicio = new JDateChooser();
        fechaFin = new JDateChooser();
        fechaDesde = new JDateChooser();
        fechaHasta = new JDateChooser();

        fechaInicio.setDateFormatString(Utilities.getFormatoFecha());
        fechaFin.setDateFormatString(Utilities.getFormatoFecha());
        fechaDesde.setDateFormatString(Utilities.getFormatoFecha());
        fechaHasta.setDateFormatString(Utilities.getFormatoFecha());
    }
}
