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
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class CSVReader {

    public static void importDiners(JTable table, TabAllDiners tabAllDiners){
        Path path=pedirNombre(true);
        if(path!=null){
            try(BufferedReader bufferedReader= Files.newBufferedReader(path, StandardCharsets.US_ASCII)) {
                String line = bufferedReader.readLine();
                while (line!=null){
                    String[] attributes = line.split(",");
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
                    line = bufferedReader.readLine();
                }
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"ÉXITO","Comensales importados");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void exportDiners(List<String> columns){
        Path path=pedirNombre(false);
        if(path!=null){
            if(!path.toString().endsWith(".txt")){
                   path= Path.of(path + ".txt");
            }
            try {
                FileWriter writer = new FileWriter(String.valueOf(path));
                List<Diner> diners=Diners.getTodos();
                ColumnPositionMappingStrategy mappingStrategy= new ColumnPositionMappingStrategy();
                mappingStrategy.setType(Diner.class);
                mappingStrategy.setColumnMapping(columns.toArray(new String[0]));
                StatefulBeanToCsvBuilder builder= new StatefulBeanToCsvBuilder(writer);
                StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy).build();
                beanWriter.write(diners);
                writer.close();
                Notify.sendNotify(Utilities.getJFrame(), Notify.Type.INFO, Notify.Location.BOTTOM_RIGHT,"ÉXITO","Comensales exportados");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void exportAttendancesDays(List<String> columns){
        Path path=pedirNombre(false);
        if(path!=null){
            if(!path.toString().endsWith(".txt")){
                path= Path.of(path + ".txt");
            }
            try {
                FileWriter writer = new FileWriter(String.valueOf(path));
                List<DayAttendance> attendances= DayAttendances.getTodos();
                ColumnPositionMappingStrategy mappingStrategy= new ColumnPositionMappingStrategy();
                mappingStrategy.setType(DayAttendance.class);
                mappingStrategy.setColumnMapping(columns.toArray(new String[0]));
                StatefulBeanToCsvBuilder builder= new StatefulBeanToCsvBuilder(writer);
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
        Path path=pedirNombre(false);
        if(path!=null){
            if(!path.toString().endsWith(".txt")){
                path= Path.of(path + ".txt");
            }
            try {
                FileWriter writer = new FileWriter(String.valueOf(path));
                List<DinerAttendance> attendances= DinerAttendances.getTodos();
                ColumnPositionMappingStrategy mappingStrategy= new ColumnPositionMappingStrategy();
                mappingStrategy.setType(DinerAttendance.class);
                mappingStrategy.setColumnMapping(columns.toArray(new String[0]));
                StatefulBeanToCsvBuilder builder= new StatefulBeanToCsvBuilder(writer);
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

    private static Path pedirNombre(boolean pedir){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Importar Comensales");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        if(pedir){
            if (chooser.showDialog(Utilities.getJFrame(),"Importar") == JFileChooser.APPROVE_OPTION) {
                return Paths.get(chooser.getSelectedFile().getPath());
            }
        }else{
            if (chooser.showDialog(Utilities.getJFrame(),"Exportar") == JFileChooser.APPROVE_OPTION) {
                return Paths.get(chooser.getSelectedFile().getPath());
            }
        }
        return null;
    }
}
