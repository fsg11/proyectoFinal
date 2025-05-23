package co.edu.uniquindio.proyectofinalprogramacion.model;

import java.time.LocalDateTime;

public class Notificacion {
    private String id;
    private String usuarioId;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private boolean leida;

    public Notificacion(String id, String usuarioId, String mensaje, LocalDateTime fechaEnvio) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.leida = false;
    }

    public String getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public boolean isLeida() { return leida; }
    public void setId(String id) { this.id = id; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }


    public void marcarComoLeida() { this.leida = true; }
}