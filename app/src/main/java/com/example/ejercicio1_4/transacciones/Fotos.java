package com.example.ejercicio1_4.transacciones;

import java.sql.Blob;

public class Fotos {
    public Integer id;
    private Blob imagen;

    private String Nombre;
    private String Description;

    public Fotos(Integer id, Blob imagen , String Nombre , String Description){
        this.id = id;
        this.imagen = imagen;
        this.Nombre = Nombre;
        this.Description = Description;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


}
