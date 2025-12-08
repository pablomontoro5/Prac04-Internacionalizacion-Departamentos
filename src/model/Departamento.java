package model;

public class Departamento {
    private static int contador = 1;

    private int id;
    private String nombre;
    private String localidad;

    public Departamento(String nombre, String localidad) {
        this.id = contador++;
        this.nombre = nombre;
        this.localidad = localidad;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getLocalidad() { return localidad; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }

}
