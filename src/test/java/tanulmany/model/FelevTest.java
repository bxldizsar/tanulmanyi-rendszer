package tanulmany.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class FelevTest
{
    @Test
    void evEsSzamSzerintRendez()
    {
        List<Felev> lista = new ArrayList<>(List.of(new Felev(2025, 2), new Felev(2024, 2), new Felev(2025, 1)));

        Collections.sort(lista);

        assertEquals(List.of(new Felev(2024, 2), new Felev(2025, 1), new Felev(2025, 2)), lista);
    }

    @Test
    void hibasFelevSzam()
    {
        assertThrows(IllegalArgumentException.class, () -> new Felev(2025, 3));
    }
}