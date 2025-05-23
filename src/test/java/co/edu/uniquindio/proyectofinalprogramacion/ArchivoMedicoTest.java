package co.edu.uniquindio.proyectofinalprogramacion;

import co.edu.uniquindio.proyectofinalprogramacion.model.ArchivoMedico;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ArchivoMedicoTest {

    @Test
    void testConstructorAndGetters() {
        byte[] contenido = {1,2,3};
        LocalDateTime fecha = LocalDateTime.now();
        ArchivoMedico archivo = new ArchivoMedico("id1", "p1", "examen.pdf", "Examen", contenido, fecha);

        assertEquals("id1", archivo.getId());
        assertEquals("p1", archivo.getPacienteId());
        assertEquals("examen.pdf", archivo.getNombreArchivo());
        assertEquals("Examen", archivo.getTipoArchivo());
        assertArrayEquals(contenido, archivo.getContenido());
        assertEquals(fecha, archivo.getFechaSubida());
    }
}