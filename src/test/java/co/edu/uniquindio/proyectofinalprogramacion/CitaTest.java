package co.edu.uniquindio.proyectofinalprogramacion;

import co.edu.uniquindio.proyectofinalprogramacion.model.Cita;
import co.edu.uniquindio.proyectofinalprogramacion.model.Paciente;
import co.edu.uniquindio.proyectofinalprogramacion.model.Medico;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CitaTest {

    @Test
    void testConstructorAndSetters() {
        Paciente paciente = new Paciente("p1", "Juan", "juan@mail.com", "Calle 1", "123");
        Medico medico = new Medico("m1", "Dra. Ana", "ana@mail.com", "456");
        LocalDateTime fecha = LocalDateTime.now();
        Cita cita = new Cita("c1", paciente, medico, fecha, "sala1", "Programada");

        assertEquals("c1", cita.getId());
        assertEquals(paciente, cita.getPaciente());
        assertEquals(medico, cita.getMedico());
        assertEquals(fecha, cita.getFechaHora());
        assertEquals("sala1", cita.getSalaId());
        assertEquals("Programada", cita.getEstado());

        cita.setEstado("Realizada");
        assertEquals("Realizada", cita.getEstado());
    }
}