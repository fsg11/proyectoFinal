package co.edu.uniquindio.proyectofinalprogramacion.model;

public class Sala {
    private String id;
    private String nombre;
    private int capacidad;

    public Sala(String id, String nombre, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getCapacidad() { return capacidad; }
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

}