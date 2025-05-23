package co.edu.uniquindio.proyectofinalprogramacion.model;

public class Medicamento {
    private String id;
    private String nombre;
    private String descripcion;
    private int cantidadDisponible;

    public Medicamento(String id, String nombre, String descripcion, int cantidadDisponible) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public int getCantidadDisponible() { return cantidadDisponible; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setCantidadDisponible(int cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }
}