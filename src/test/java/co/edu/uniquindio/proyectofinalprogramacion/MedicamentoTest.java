package co.edu.uniquindio.proyectofinalprogramacion;

import co.edu.uniquindio.proyectofinalprogramacion.model.Medicamento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MedicamentoTest {

    @Test
    void testConstructorAndGetters() {
        Medicamento med = new Medicamento("m1", "Paracetamol", "Dolor", 10);
        assertEquals("m1", med.getId());
        assertEquals("Paracetamol", med.getNombre());
        assertEquals("Dolor", med.getDescripcion());
        assertEquals(10, med.getCantidadDisponible());
    }

    @Test
    void testSetters() {
        Medicamento med = new Medicamento("m2", "Ibuprofeno", "Fiebre", 5);
        med.setNombre("Nuevo Nombre");
        med.setDescripcion("Nueva Desc");
        med.setCantidadDisponible(20);
        assertEquals("Nuevo Nombre", med.getNombre());
        assertEquals("Nueva Desc", med.getDescripcion());
        assertEquals(20, med.getCantidadDisponible());
    }
}