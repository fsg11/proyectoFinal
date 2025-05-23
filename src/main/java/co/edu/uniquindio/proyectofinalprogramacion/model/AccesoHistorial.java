package co.edu.uniquindio.proyectofinalprogramacion.model;

public interface AccesoHistorial {
    void accederHistorialPaciente(String pacienteId);
    void registrarDiagnostico(String pacienteId, String diagnostico);
}
