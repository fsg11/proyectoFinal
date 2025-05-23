package co.edu.uniquindio.proyectofinalprogramacion.model;

import java.util.ArrayList;
import java.util.List;

public class HistorialMedico {
    private String pacienteId;
    private List<String> diagnosticos;

    public HistorialMedico(String pacienteId) {
        this.pacienteId = pacienteId;
        this.diagnosticos = new ArrayList<>();
    }

    public String getPacienteId() { return pacienteId; }
    public List<String> getDiagnosticos() { return diagnosticos; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public void setDiagnosticos(List<String> diagnosticos) { this.diagnosticos = diagnosticos; }


    public void agregarDiagnostico(String diagnostico) {
        diagnosticos.add(diagnostico);
    }
}