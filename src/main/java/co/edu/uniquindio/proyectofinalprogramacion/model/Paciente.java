package co.edu.uniquindio.proyectofinalprogramacion.model;

public class Paciente extends Usuario implements GestionCitas, ConsultaHistorial{
    private String direccion;
    private String telefono;

    public Paciente(String id, String nombre, String correo, String direccion, String telefono) {
        super(id, nombre, correo);
        this.direccion = direccion;
        this.telefono = telefono;
    }

    @Override
    public String getTipoUsuario() { return "Paciente"; }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public void solicitarCita() {
        // Implementación de la gestión de citas
        System.out.println("Gestión de citas para el paciente " + getNombre());
    }

    @Override
    public void cancelarCita(){
        // Implementación de la cancelación de citas
        System.out.println("Cancelación de cita para el paciente " + getNombre());
    }

    @Override
    public void consultarHistorial() {
        // Implementación de la consulta del historial médico
        System.out.println("Consulta del historial médico para el paciente " + getNombre());
    }
}
