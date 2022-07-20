package com.moreno.utilities;

import com.moreno.Notify;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Exportar {
    private static File file;
    private static HSSFWorkbook Excel;
    private static FileOutputStream outputStream;
    private static JFileChooser chooser;
    private static HSSFSheet hoja;
    private static HSSFCellStyle styleCabecera;

    public static void exportar(String titulo,List<String> nombreColumnas, List<Object[]> datos){
        if(!datos.isEmpty()){
            if(pedirNombre()){
                hoja=Excel.createSheet(titulo);
                instaciarEstilo();
                for (int i = 0; i <datos.size(); i++) {
                    Object[] object=datos.get(i);
                    hoja.autoSizeColumn(2);
                    HSSFRow dato = hoja.createRow(i+4);
                    dato.createCell(2).setCellValue(String.valueOf(i+1));
                    dato.getCell(2).setCellStyle(styleCabecera);
                    for (int j = 0; j < object.length; j++) {
                        dato.createCell(j+3).setCellValue(String.valueOf(object[j]));
                    }
                }
                HSSFRow cabacera = hoja.createRow(3);
                for(int i=0;i< nombreColumnas.size();i++){
                    cabacera.createCell(i+3).setCellValue(nombreColumnas.get(i));
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

    private static boolean  pedirNombre(){
        chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos excel", "xml","xls","xlsm","xla","xlr","xlw","xlt","xlsx","xlsb","xltx","xltm");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            instanciarFile(chooser.getSelectedFile().toString().concat(".xls"));
            return true;
        }
        return false;
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
            Runtime.getRuntime().exec("cmd /c start "+file.getAbsolutePath());
        }catch(IOException  e){
            e.printStackTrace();
        }
    }
}
