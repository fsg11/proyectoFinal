package co.edu.uniquindio.proyectofinalprogramacion.model;

import java.time.LocalDateTime;

public class ArchivoMedico {
    private String id;
    private String pacienteId;
    private String nombreArchivo;
    private String tipoArchivo; // Ejemplo: "Examen", "Receta"
    private byte[] contenido;
    private LocalDateTime fechaSubida;

    public ArchivoMedico(String id, String pacienteId, String nombreArchivo, String tipoArchivo, byte[] contenido, LocalDateTime fechaSubida) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo;
        this.contenido = contenido;
        this.fechaSubida = fechaSubida;
    }

    public String getId() { return id; }
    public String getPacienteId() { return pacienteId; }
    public String getNombreArchivo() { return nombreArchivo; }
    public String getTipoArchivo() { return tipoArchivo; }
    public byte[] getContenido() { return contenido; }
    public LocalDateTime getFechaSubida() { return fechaSubida; }

    public void setId(String id) { this.id = id; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }
    public void setTipoArchivo(String tipoArchivo) { this.tipoArchivo = tipoArchivo; }
    public void setContenido(byte[] contenido) { this.contenido = contenido; }
    public void setFechaSubida(LocalDateTime fechaSubida) { this.fechaSubida = fechaSubida; }
}