package co.edu.uniquindio.proyectofinalprogramacion.model;

public class Administrador extends Usuario{
    public Administrador(String id, String nombre, String correo) {
        super(id, nombre, correo);
    }

    @Override
    public String getTipoUsuario() { return "Administrador"; }

}
