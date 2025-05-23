package co.edu.uniquindio.proyectofinalprogramacion;
import co.edu.uniquindio.proyectofinalprogramacion.model.Paciente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PacienteTest {

    @Test
    void testConstructorAndGetters() {
        Paciente paciente = new Paciente("p1", "Juan", "juan@mail.com", "Calle 1", "123");
        assertEquals("p1", paciente.getId());
        assertEquals("Juan", paciente.getNombre());
        assertEquals("juan@mail.com", paciente.getCorreo());
        assertEquals("Calle 1", paciente.getDireccion());
        assertEquals("123", paciente.getTelefono());
        assertEquals("Paciente", paciente.getTipoUsuario());
    }

    @Test
    void testSetters() {
        Paciente paciente = new Paciente("p2", "Ana", "ana@mail.com", "Calle 2", "456");
        paciente.setDireccion("Nueva Dir");
        paciente.setTelefono("999");
        assertEquals("Nueva Dir", paciente.getDireccion());
        assertEquals("999", paciente.getTelefono());
    }
}