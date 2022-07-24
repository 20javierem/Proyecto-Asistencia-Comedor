package com.moreno.utilitiesReports;

import com.moreno.App;
import com.moreno.Notify;
import com.moreno.custom.TabbedPane;
import com.moreno.models.DayAttendance;
import com.moreno.utilities.Utilities;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerPanel;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class UtilitiesReports {

    public static void generateReportAttendances(Date start,Date end, List<DayAttendance> attendanceList, Boolean toPrint) {
        InputStream pathReport = App.class.getResourceAsStream("JasperReport/ReportDiners.jasper");
        try {
            if(pathReport!=null){
                List<DayAttendance> list=new ArrayList<>(attendanceList);
                list.add(0,new DayAttendance());
                JasperReport report=(JasperReport) JRLoader.loadObject(pathReport);
                JRBeanArrayDataSource sp=new JRBeanArrayDataSource(list.toArray());
                Map<String,Object> parameters=new HashMap<>();
                parameters.put("periodStart",Utilities.formatoFecha.format(start));
                parameters.put("periodEnd",Utilities.formatoFecha.format(end));
                parameters.put("dayAttendances",sp);
                JasperViewer viewer = getjasperViewer(report,parameters,sp,true);
                if(viewer!=null){
                    viewer.setTitle("Reporte ("+ Utilities.formatoFecha.format(start)+") al ("+Utilities.formatoFecha.format(end)+")");
                    if(Utilities.getTabbedPane().indexOfTab(viewer.getTitle())==-1){
                        Utilities.getTabbedPane().addTab(viewer.getTitle(), viewer.getRootPane());
                        Utilities.getTabbedPane().setSelectedComponent(viewer.getRootPane());
                    }
                    Utilities.getTabbedPane().setSelectedIndex(Utilities.getTabbedPane().indexOfTab(viewer.getTitle()));
                }else{
                    Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR","Sucedio un error inesperado");
                }
            }else{
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR","No se encontró la plantilla");
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    public static JasperViewer getjasperViewer(JasperReport report, Map<String, Object> parameters, JRBeanArrayDataSource sp, boolean isExitOnClose){
        try {
            JasperViewer jasperViewer=new JasperViewer(JasperFillManager.fillReport(report,parameters,sp),isExitOnClose);
            JPanel panel= (JPanel) jasperViewer.getRootPane().getContentPane().getComponent(0);
            JRViewer visor= (JRViewer) panel.getComponent(0);
            JRViewerToolbar toolbar= (JRViewerToolbar) visor.getComponent(0);
            toolbar.getInsets().set(2,2,2,2);
            toolbar.setLayout(new FlowLayout(FlowLayout.CENTER,2,3));
            toolbar.setMaximumSize(new Dimension(toolbar.getWidth(),46));
            toolbar.setMinimumSize(new Dimension(toolbar.getWidth(),46));
            toolbar.setPreferredSize(new Dimension(toolbar.getWidth(),46));
            JRViewerPanel jrViewer= (JRViewerPanel) visor.getComponent(1);
            JScrollPane jScrollPane= (JScrollPane) jrViewer.getComponent(0);
            jScrollPane.setBorder(BorderFactory.createEmptyBorder());
            ((JPanel)visor.getComponent(2)).setLayout(new FlowLayout(FlowLayout.CENTER));
            Font font=new Font(new JTextField().getFont().getFontName(),Font.PLAIN,14);
            ((JPanel)visor.getComponent(2)).getComponent(0).setFont(font);
            for (Component component: toolbar.getComponents()){
                if(component instanceof JTextField||component instanceof JComboBox){
                    component.setMaximumSize(new Dimension(component.getMaximumSize().width,40));
                    component.setPreferredSize(new Dimension(component.getMaximumSize().width,40));
                    component.setMinimumSize(new Dimension(component.getMaximumSize().width,40));
                }else{
                    component.setMaximumSize(new Dimension(40,40));
                    component.setPreferredSize(new Dimension(40,40));
                    component.setMinimumSize(new Dimension(40,40));
                }
            }
            JButton mrZoom=(JButton)toolbar.getComponent(14);
            JButton mnZoom=(JButton)toolbar.getComponent(15);
            jScrollPane.addMouseWheelListener(e -> {
                if(e.isControlDown()){
                    if (e.getWheelRotation() < 0) {
                        mrZoom.doClick();
                    } else {
                        mnZoom.doClick();
                    }
                }
            });
            return jasperViewer;
        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }
}
