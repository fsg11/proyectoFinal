package co.edu.uniquindio.proyectofinalprogramacion.model;

import java.time.LocalDateTime;

public class Cita {
    private String id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private String salaId;
    private String estado; // Ejemplo: "Programada", "Cancelada", "Realizada"

    public Cita(String id, Paciente paciente, Medico medico, LocalDateTime fechaHora, String salaId, String estado) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.salaId = salaId;
        this.estado = estado;
    }

    public String getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getSalaId() { return salaId; }
    public String getEstado() { return estado; }
    public void setId(String id) { this.id = id; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public void setSalaId(String salaId) { this.salaId = salaId; }


    public void setEstado(String estado) { this.estado = estado; }
}