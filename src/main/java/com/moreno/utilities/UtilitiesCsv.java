package com.moreno.utilities;

import com.moreno.Notify;
import com.moreno.controllers.DayAttendances;
import com.moreno.controllers.DinerAttendances;
import com.moreno.controllers.Diners;
import com.moreno.models.DayAttendance;
import com.moreno.models.Diner;
import com.moreno.models.DinerAttendance;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.validators.DinerValidator;
import com.moreno.views.VPrincipal;
import com.moreno.views.tabs.TabAllDiners;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class UtilitiesCsv {

    public static void importDiners(JTable table, TabAllDiners tabAllDiners){
        String path=pedirNombre(true);
        if(path!=null){
            try {
            BufferedReader csvReader = new BufferedReader(new FileReader(path));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] attributes;
                attributes = row.split(";");
                if(attributes.length==6){
                    Diner diner = new Diner(attributes);
                    Set<ConstraintViolation<Diner>> errors = DinerValidator.loadViolations(diner);
                    if (errors.isEmpty()) {
                        if(Diners.getByDni(diner.getDni())==null){
                            diner.save();
                            VPrincipal.diners.add(diner);
                            if(diner.isActive()){
                                if(VPrincipal.attendancesToday!=null){
                                    DinerAttendance dinerAttendance=new DinerAttendance(diner,VPrincipal.attendancesToday);
                                    VPrincipal.attendancesToday.getAttendances().add(dinerAttendance);
                                    dinerAttendance.setAttended(!VPrincipal.attendancesToday.isState());
                                    dinerAttendance.save();
                                    VPrincipal.attendancesToday.calculateTotals();
                                    VPrincipal.attendancesToday.save();
                                }
                            }
                            UtilitiesTables.actualizarTabla(table);
                            tabAllDiners.filter();
                        }
                    }else{
                        DinerValidator.mostrarErrores(errors);
                    }
                }
            }
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"ÉXITO","Comensales importados");
            csvReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void exportDiners(List<String> columns){
        String path=pedirNombre(false);
        if(path!=null){
            try {
                FileWriter fileWriter = new FileWriter(path);
                List<Diner> diners=Diners.getTodos();
                ColumnPositionMappingStrategy mappingStrategy= new ColumnPositionMappingStrategy();
                mappingStrategy.setType(Diner.class);
                mappingStrategy.setColumnMapping(columns.toArray(new String[0]));
                StatefulBeanToCsvBuilder builder= new StatefulBeanToCsvBuilder(fileWriter).withSeparator(';').withApplyQuotesToAll(false);
                StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy).build();
                beanWriter.write(diners);
                fileWriter.close();
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"ÉXITO","Comensales exportados");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void exportAttendancesDays(List<String> columns){
        String path=pedirNombre(false);
        if(path!=null){
            try {
                FileWriter writer = new FileWriter(path);
                List<DayAttendance> attendances= DayAttendances.getTodos();
                ColumnPositionMappingStrategy mappingStrategy= new ColumnPositionMappingStrategy();
                mappingStrategy.setType(DayAttendance.class);
                mappingStrategy.setColumnMapping(columns.toArray(new String[0]));
                StatefulBeanToCsvBuilder builder= new StatefulBeanToCsvBuilder(writer).withSeparator(';').withApplyQuotesToAll(false);
                StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy).build();
                beanWriter.write(attendances);
                writer.close();
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"ÉXITO","Comensales exportados");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void exportDinersAttendances(List<String> columns){
        String path=pedirNombre(false);
        if(path!=null){
            try {
                FileWriter writer = new FileWriter(path);
                List<DinerAttendance> attendances= DinerAttendances.getTodos();
                ColumnPositionMappingStrategy mappingStrategy= new ColumnPositionMappingStrategy();
                mappingStrategy.setType(DinerAttendance.class);
                mappingStrategy.setColumnMapping(columns.toArray(new String[0]));
                StatefulBeanToCsvBuilder builder= new StatefulBeanToCsvBuilder(writer).withSeparator(';').withApplyQuotesToAll(false);
                StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy).build();
                beanWriter.write(attendances);
                writer.close();
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"ÉXITO","Comensales exportados");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String pedirNombre(boolean pedir){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Importar Comensales");
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter(".csv", "csv");
        chooser.setFileFilter(filter1);
        chooser.setAcceptAllFileFilterUsed(false);
        if(pedir){
            if (chooser.showDialog(Utilities.getJFrame(),"Importar") == JFileChooser.APPROVE_OPTION) {
                return chooser.getSelectedFile().getPath();
            }
        }else{
            if (chooser.showSaveDialog(Utilities.getJFrame()) == JFileChooser.APPROVE_OPTION) {
                String path=chooser.getSelectedFile().getPath();
                if(!path.endsWith("csv")){
                    path+=".csv";
                }
                System.out.println(path);
                return path;
            }
        }
        return null;
    }
}
