package com.moreno.utilities.export;

import com.moreno.Notify;
import com.moreno.utilities.Utilities;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Export {
    private static File file;
    private static HSSFWorkbook Excel;
    private static FileOutputStream outputStream;
    private static JFileChooser chooser;
    private static HSSFSheet hoja;
    private static HSSFCellStyle styleCabecera;
    private static CellStyle dateFormat;
    public static void exportar(String titulo,String[] nombreColumnas, List<Object[]> datos){
        if(!datos.isEmpty()){
            if(pedirNombre(titulo)){
                hoja=Excel.createSheet(titulo);
                instaciarEstilo();
                instaciarEstiloDate();
                for (int i = 0; i <datos.size(); i++) {
                    Object[] object=datos.get(i);
                    hoja.autoSizeColumn(2);
                    HSSFRow row = hoja.createRow(i+4);
                    row.createCell(2).setCellValue(String.valueOf(i+1));
                    row.getCell(2).setCellStyle(styleCabecera);
                    for (int j = 0; j < object.length; j++) {
                        if(object[j] instanceof Date){
                            row.createCell(j+3).setCellValue((Date) object[j]);
                            row.getCell(j+3).setCellStyle(dateFormat);
                            continue;
                        }
                        if(object[j] instanceof Double){
                            row.createCell(j+3).setCellValue((Double) object[j]);
                            continue;
                        }
                        row.createCell(j+3).setCellValue(String.valueOf(object[j]));
                    }
                }
                HSSFRow cabacera = hoja.createRow(3);
                for(int i=0;i< nombreColumnas.length;i++){
                    cabacera.createCell(i+3).setCellValue(nombreColumnas[i]);
                    cabacera.getCell(i+3).setCellStyle(styleCabecera);
                    hoja.autoSizeColumn(i+3);
                }
                escribirExcel();
                cerrarExccel();
                abrirExcel();
            }
        }else{
            Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR","No hay datos");
        }
    }

    private static boolean  pedirNombre(String titulo){
        chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos excel", "xml","xls","xlsm","xla","xlr","xlw","xlt","xlsx","xlsb","xltx","xltm");
        chooser.setFileFilter(filter);
        chooser.setSelectedFile(new File(titulo));
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String destino=chooser.getSelectedFile().toString();
            if(destino.contains(".")){
                destino=destino.substring(0,destino.indexOf("."));
            }
            instanciarFile(destino.concat(".xls"));
            return true;
        }
        return false;
    }
    public static List<Object[]> getDatos(JTable table) {
        List<Object[]> datos = new ArrayList<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            Object[] familiar = new Object[table.getModel().getColumnCount()];
            for (int j = 0; j <table.getModel().getColumnCount(); j++) {
                Object object=table.getModel().getValueAt(table.convertRowIndexToModel(i), j);
                familiar[j] =object;
            }
            datos.add(familiar);
        }
        return datos;
    }
    private static void instaciarEstilo(){
        styleCabecera=Excel.createCellStyle();
        styleCabecera.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font= Excel.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBold(true);
        styleCabecera.setFont(font);
        styleCabecera.setBorderBottom(BorderStyle.MEDIUM);
        styleCabecera.setBorderLeft(BorderStyle.MEDIUM);
        styleCabecera.setBorderRight(BorderStyle.MEDIUM);
        styleCabecera.setBorderTop(BorderStyle.MEDIUM);
    }
    private static void instaciarEstiloDate(){
        dateFormat = Excel.createCellStyle();
        CreationHelper createHelper = Excel.getCreationHelper();
        dateFormat.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy hh:mm"));
    }
    private static void cerrarExccel() {
        try {
            Excel.close();
            outputStream.close();
        } catch ( IOException ex) {
            ex.printStackTrace();
        }
    }
    private static void escribirExcel(){
        try {
            Excel.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void instanciarFile(String nombreArchivo){
        try {
            file=new File(nombreArchivo);
            outputStream = new FileOutputStream(file);
            Excel =new HSSFWorkbook();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void abrirExcel(){
        try{
            System.out.println(String.format("cmd /c start %s\\\"%s\"", file.getParentFile().getPath(), file.getName()));
            Runtime.getRuntime().exec(String.format("cmd /c start %s\\\"%s\"", file.getParentFile().getPath(), file.getName()));
        }catch(IOException  e){
            e.printStackTrace();
        }
    }
}
