package co.edu.uniquindio.proyectofinalprogramacion;

import co.edu.uniquindio.proyectofinalprogramacion.model.Farmacia;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FarmaciaTest {

    @Test
    void testConstructorAndSetters() {
        Farmacia farmacia = new Farmacia("f1", "Farmacia UQ", "Calle 1", "12345");
        assertEquals("f1", farmacia.getId());
        assertEquals("Farmacia UQ", farmacia.getNombre());
        assertEquals("Calle 1", farmacia.getDireccion());
        assertEquals("12345", farmacia.getTelefono());

        farmacia.setNombre("Nueva Farmacia");
        assertEquals("Nueva Farmacia", farmacia.getNombre());
    }
}