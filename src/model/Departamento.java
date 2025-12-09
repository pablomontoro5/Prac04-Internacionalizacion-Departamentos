package model;

import data.IdStorage;

public class Departamento {

    private static int contador = 1;

    private int id;
    private String nombre;
    private String localidad;

    public Departamento(String nombre, String localidad) {
        this.id = contador++;  // ID autoincremental manejado en memoria
        this.nombre = nombre;
        this.localidad = localidad;

        IdStorage.saveLastId(this.id); // actualizar archivo id.txt
    }

    // ---- NECESARIOS PARA Data.java ----

    public void setId(int id) {
        this.id = id;
    }

    public static void setContador(int nuevoValor) {
        contador = nuevoValor;
    }

    public static int getContador() {
        return contador;
    }

    // ---- GETTERS / SETTERS ----
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getLocalidad() { return localidad; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }
}
