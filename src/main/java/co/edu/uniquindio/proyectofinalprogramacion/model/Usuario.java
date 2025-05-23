package co.edu.uniquindio.proyectofinalprogramacion.model;

public abstract class Usuario {
    protected String id;
    protected String nombre;
    protected String correo;

    public Usuario(String id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }

    public abstract String getTipoUsuario();
}
