package tanulmany.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class JegyTest
{
    @ParameterizedTest
    @ValueSource(ints = {0, -1, 11, 100})
    void tartomanyonKivuliJegyHibas(int ertek)
    {
        assertThrows(IllegalArgumentException.class, () -> new Jegy(ertek));
    }

    @Test
    void hatarertekekRendben()
    {
        assertDoesNotThrow(() -> new Jegy(1));
        assertDoesNotThrow(() -> new Jegy(10));
    }

    @Test
    void otTolSikeres()
    {
        assertTrue(new Jegy(5).sikeres());
        assertFalse(new Jegy(4).sikeres());
    }
}