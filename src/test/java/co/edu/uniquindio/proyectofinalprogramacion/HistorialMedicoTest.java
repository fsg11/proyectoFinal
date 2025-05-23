package co.edu.uniquindio.proyectofinalprogramacion;

import co.edu.uniquindio.proyectofinalprogramacion.model.HistorialMedico;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HistorialMedicoTest {

    @Test
    void testAgregarDiagnostico() {
        HistorialMedico historial = new HistorialMedico("p1");
        historial.agregarDiagnostico("Gripe");
        List<String> diagnosticos = historial.getDiagnosticos();
        assertEquals(1, diagnosticos.size());
        assertEquals("Gripe", diagnosticos.get(0));
    }

    @Test
    void testSettersAndGetters() {
        HistorialMedico historial = new HistorialMedico("p2");
        historial.setPacienteId("p3");
        assertEquals("p3", historial.getPacienteId());
    }
}