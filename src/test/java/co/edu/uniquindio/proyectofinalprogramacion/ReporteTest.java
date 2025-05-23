package co.edu.uniquindio.proyectofinalprogramacion;


import co.edu.uniquindio.proyectofinalprogramacion.model.Reporte;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReporteTest {

    @Test
    void testConstructorAndGetters() {
        Reporte reporte = new Reporte("r1", "Descripción", "Citas");
        assertEquals("r1", reporte.getId());
        assertEquals("Descripción", reporte.getDescripcion());
        assertEquals("Citas", reporte.getTipo());
    }

    @Test
    void testSetters() {
        Reporte reporte = new Reporte("r2", "Desc", "Ocupación");
        reporte.setDescripcion("Nueva Desc");
        reporte.setTipo("Nuevo Tipo");
        assertEquals("Nueva Desc", reporte.getDescripcion());
        assertEquals("Nuevo Tipo", reporte.getTipo());
    }
}