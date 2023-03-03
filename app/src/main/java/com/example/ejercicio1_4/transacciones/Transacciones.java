package com.example.ejercicio1_4.transacciones;

public class Transacciones {

    public static final String NameDatabase = "EJE14";
    //Tabla de la base de datos

    public static final String id = "id";
    public static final String tablafotos = "pictures";

    public static final String Nombre = "Nombre";
    public static final String descripcion = "descripcion";
    public static final String imagen = "imagen";

    //Transacciones de la base de datos EX01DB
    public static final String CreateTBFotos = "CREATE TABLE pictures (id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, descripcion TEXT, imagen BLOB)";

    public static final  String DropTableFotos= "DROP TABLE IF EXISTS fotos";

    //Helpers
    public static final  String Empty= "";
}

