package co.edu.uniquindio.proyectofinalprogramacion;

import co.edu.uniquindio.proyectofinalprogramacion.model.Notificacion;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class NotificacionTest {

    @Test
    void testConstructorAndGetters() {
        LocalDateTime fecha = LocalDateTime.now();
        Notificacion notif = new Notificacion("n1", "u1", "Mensaje", fecha);
        assertEquals("n1", notif.getId());
        assertEquals("u1", notif.getUsuarioId());
        assertEquals("Mensaje", notif.getMensaje());
        assertEquals(fecha, notif.getFechaEnvio());
        assertFalse(notif.isLeida());
    }

    @Test
    void testMarcarComoLeida() {
        Notificacion notif = new Notificacion("n2", "u2", "Otro", LocalDateTime.now());
        notif.marcarComoLeida();
        assertTrue(notif.isLeida());
    }
}