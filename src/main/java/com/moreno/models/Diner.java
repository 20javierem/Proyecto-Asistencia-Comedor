package com.moreno.models;

import com.moreno.utilities.Moreno;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "diner_tbl")
public class Diner extends Moreno {

    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;

    @NotBlank(message = "Nombres")
    private String names;

    @NotBlank(message = "Apellidos")
    private String lastNames;

    @NotNull
    private boolean male;

    @NotBlank(message = "DNI")
    private String dni;

    @NotBlank(message = "Celular")
    private String phone;

    @OneToMany(mappedBy = "diner")
    private List<DinerAttendance> dinerAttendances =new ArrayList<>();

    private boolean active=false;

    public Integer getId() {
        return id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names.toUpperCase();
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames.toUpperCase();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<DinerAttendance> getAttendances() {
        return dinerAttendances;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public Diner(){

    }
    public Diner(String[] attributes){
        setDni(attributes[0].trim());
        setLastNames(attributes[1].trim());
        setNames(attributes[2].trim());
        setMale(Boolean.parseBoolean(attributes[3]));
        setPhone(attributes[4].trim());
        setActive(Boolean.parseBoolean(attributes[5]));
    }
    public String getSex(){
        return isMale()?"MASCULINO":"FEMENINO";
    }
    public String getStade(){
        return isActive()?"SI":"NO";
    }
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
