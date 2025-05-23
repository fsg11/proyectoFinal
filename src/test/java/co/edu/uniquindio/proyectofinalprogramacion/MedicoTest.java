package co.edu.uniquindio.proyectofinalprogramacion;


import co.edu.uniquindio.proyectofinalprogramacion.model.Medico;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MedicoTest {

    @Test
    void testConstructorAndGetters() {
        Medico medico = new Medico("m1", "Dra. Ana", "ana@mail.com", "Pediatría");
        assertEquals("m1", medico.getId());
        assertEquals("Dra. Ana", medico.getNombre());
        assertEquals("ana@mail.com", medico.getCorreo());
        assertEquals("Pediatría", medico.getEspecialidad());
        assertEquals("Medico", medico.getTipoUsuario());
    }

    @Test
    void testSetEspecialidad() {
        Medico medico = new Medico("m2", "Dr. Luis", "luis@mail.com", "General");
        medico.setEspecialidad("Cardiología");
        assertEquals("Cardiología", medico.getEspecialidad());
    }
}