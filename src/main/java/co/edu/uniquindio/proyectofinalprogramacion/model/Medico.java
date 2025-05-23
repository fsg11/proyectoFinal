package co.edu.uniquindio.proyectofinalprogramacion.model;

public class Medico extends Usuario implements GestionCitas, AccesoHistorial{
    private String especialidad;

    public Medico(String id, String nombre, String correo, String especialidad) {
        super(id, nombre, correo);
        this.especialidad = especialidad;
    }

    @Override
    public String getTipoUsuario() { return "Medico"; }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public void solicitarCita() {
        // Implementación para solicitar una cita
        System.out.println("Cita solicitada por el médico " + nombre);
    }

    @Override
    public void cancelarCita() {
        // Implementación para cancelar una cita
        System.out.println("Cita cancelada por el médico " + nombre);
    }

    @Override
    public void accederHistorialPaciente(String pacienteId) {
        // Implementación para acceder al historial médico
        System.out.println("Accediendo al historial médico del paciente por el médico " + nombre);
    }

    @Override
    public void registrarDiagnostico(String pacienteId, String diagnostico) {
        // Implementación para registrar un diagnóstico
        System.out.println("Registrando diagnóstico para el paciente " + pacienteId + " por el médico " + nombre);
    }
}
