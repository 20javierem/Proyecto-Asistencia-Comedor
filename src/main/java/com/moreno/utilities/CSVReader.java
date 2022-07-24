package com.moreno.utilities;

import com.moreno.Notify;
import com.moreno.controllers.Diners;
import com.moreno.models.Diner;
import com.moreno.models.DinerAttendance;
import com.moreno.utilitiesTables.UtilitiesTables;
import com.moreno.validators.DinerValidator;
import com.moreno.views.VPrincipal;
import com.moreno.views.tabs.TabAllDiners;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class CSVReader {

    public static void importDiners(JTable table, TabAllDiners tabAllDiners){
        Path path=pedirNombre((Frame) table.getRootPane().getParent());
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
                                        dinerAttendance.save();
                                        VPrincipal.attendancesToday.getAttendances().add(dinerAttendance);
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
    private static Path  pedirNombre(Frame frame){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Importar Comensales");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showDialog(frame,"Importar") == JFileChooser.APPROVE_OPTION) {
            return Paths.get(chooser.getSelectedFile().getPath());
        }
        return null;
    }
}
