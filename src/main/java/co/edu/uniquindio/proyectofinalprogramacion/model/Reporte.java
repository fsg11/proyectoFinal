package co.edu.uniquindio.proyectofinalprogramacion.model;

public class Reporte {
    private String id;
    private String descripcion;
    private String tipo; // Ejemplo: "Citas", "Ocupación", etc.

    public Reporte(String id, String descripcion, String tipo) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public String getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public String getTipo() { return tipo; }
    public void setId(String id) { this.id = id; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}