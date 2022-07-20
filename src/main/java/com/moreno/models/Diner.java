package com.moreno.models;

import com.moreno.utilities.Moreno;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Diner extends Moreno {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;

    @NotBlank(message = "Nombres")
    private String names;

    @NotBlank(message = "Apellidos")
    private String lastNames;

    @NotBlank(message = "código")
    private String code;

    @NotBlank(message = "DNI")
    private String dni;

    @NotBlank(message = "Celular")
    private String phone;

    @OneToMany(mappedBy = "diner")
    private List<Attendance> attendances=new ArrayList<>();

    private boolean active=false;

    public Integer getId() {
        return id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<Attendance> getAttendances() {
        return attendances;
    }


    public static Diner createDiner(String[] attributes){
        Diner diner=new Diner();
        diner.setDni(attributes[0]);
        diner.setNames(attributes[1]);
        diner.setLastNames(attributes[2]);
        diner.setCode(attributes[3]);
        diner.setPhone(attributes[4]);
        return diner;
    }

}
