package co.edu.uniquindio.proyectofinalprogramacion;

import co.edu.uniquindio.proyectofinalprogramacion.model.Administrador;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdministradorTest {

    @Test
    void testConstructorAndTipoUsuario() {
        Administrador admin = new Administrador("a1", "Admin", "admin@mail.com");
        assertEquals("a1", admin.getId());
        assertEquals("Admin", admin.getNombre());
        assertEquals("admin@mail.com", admin.getCorreo());
        assertEquals("Administrador", admin.getTipoUsuario());
    }
}