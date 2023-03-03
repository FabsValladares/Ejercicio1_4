package com.example.ejercicio1_4.transacciones;

import java.sql.Blob;

public class Fotos {


    public Fotos(Integer id,  String Nombre , String descripcion,  Blob imagen ){
        this.id = id;
        this.Nombre = Nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;

    }

    private Integer id;
    private Blob imagen;

    private String Nombre;
    private String descripcion;

    public Fotos(){
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
        return descripcion;
    }

    public void setDescription(String descripcion) {
        this.descripcion = descripcion;
    }


}
