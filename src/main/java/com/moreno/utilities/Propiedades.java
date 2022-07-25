package com.moreno.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Propiedades {
    private Properties properties;
    private FileInputStream inputStream;
    private File carpeta = new File(System.getProperty("user.home") + "/.SoftwareAsistenciaComedor");
    private File archivo;

    public Propiedades(){
        try {
            inicializar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void inicializar() throws IOException {
        if (!carpeta.exists()) {
            System.out.println("Primera ves");
            carpeta.mkdir();
        }
        archivo= new File(carpeta.getAbsolutePath()+"/config.properties");
        if(!archivo.exists()){
            System.out.println("Primera ves");
            archivo.createNewFile();
            inputStream = new FileInputStream(archivo.getAbsolutePath());
            properties= new Properties();
            properties.load(inputStream);
            inputStream.close();
            setKey("2QXDJJUCSSUW2GC");
            save();
            setUsuario("");
            setContraseña("");
            setNameInstitute("");
            setTema("Claro");
            save();
        }else{
            inputStream = new FileInputStream(archivo.getAbsolutePath());
            properties= new Properties();
            properties.load(inputStream);
            inputStream.close();
        }
    }

    public void save() {
        try {
            FileOutputStream outputStream = new FileOutputStream(archivo);
            properties.store(outputStream, "[Config file]");
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setUsuario(String usuario) {
        properties.put("usuario", usuario);
    }
    public String getUsuario() {
        return properties.getProperty("usuario");
    }
    public void setContraseña(String contraseña){
        properties.put("contrasena",contraseña);
    }
    public String getContraseña(){
        return properties.getProperty("contrasena");
    }
    public String getKey() {
        return properties.getProperty("key");
    }
    public void setKey(String key){
        properties.put("key",key);
    }
    public void setNameInstitute(String nameInstitute){
        properties.put("nameInstitute",nameInstitute);
    }
    public String getNameInstitute(){
        return properties.getProperty("nameInstitute");
    }
    public void setTema(String tema){
        properties.put("tema", tema);
    }
    public String getTema() {
        return properties.getProperty("tema");
    }
}
