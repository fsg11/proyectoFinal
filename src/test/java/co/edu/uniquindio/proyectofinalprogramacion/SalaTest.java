package co.edu.uniquindio.proyectofinalprogramacion;


import co.edu.uniquindio.proyectofinalprogramacion.model.Sala;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SalaTest {

    @Test
    void testConstructorAndGetters() {
        Sala sala = new Sala("s1", "Sala A", 20);
        assertEquals("s1", sala.getId());
        assertEquals("Sala A", sala.getNombre());
        assertEquals(20, sala.getCapacidad());
    }

    @Test
    void testSetters() {
        Sala sala = new Sala("s2", "Sala B", 10);
        sala.setNombre("Sala C");
        sala.setCapacidad(30);
        assertEquals("Sala C", sala.getNombre());
        assertEquals(30, sala.getCapacidad());
    }
}