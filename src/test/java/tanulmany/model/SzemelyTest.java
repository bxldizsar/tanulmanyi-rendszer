package tanulmany.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SzemelyTest
{
    @Test
    void azonosAzonositoEgyenlo()
    {
        Hallgato a = new Hallgato("H1", "Nagy Bela", "bela@x.hu");
        Hallgato b = new Hallgato("H1", "Mas Nev", "mas@x.hu");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void halmazbanCsakEgyszerSzerepel()
    {
        Set<Szemely> halmaz = new HashSet<>();
        halmaz.add(new Hallgato("H1", "Nagy Bela", "bela@x.hu"));
        halmaz.add(new Hallgato("H1", "Nagy Bela", "bela@x.hu"));

        assertEquals(1, halmaz.size());
    }

    @Test
    void kulonbozoAzonositoNemEgyenlo()
    {
        assertNotEquals(new Hallgato("H1", "Nagy Bela", "bela@x.hu"), new Hallgato("H2", "Nagy Bela", "bela@x.hu"));
    }

    @Test
    void hibasAdatokraKivetel()
    {
        assertThrows(IllegalArgumentException.class, () -> new Hallgato(null, "Nev", "a@x.hu"));
        assertThrows(IllegalArgumentException.class, () -> new Hallgato("H1", " ", "a@x.hu"));
        assertThrows(IllegalArgumentException.class, () -> new Hallgato("H1", "Nev", "rossz-email"));
    }

    @Test
    void toStringTartalmazzaAzAdatokat()
    {
        String s = new Oktato("O1", "Kovacs Anna", "kovacs@x.hu").toString();

        assertTrue(s.contains("Oktato"));
        assertTrue(s.contains("O1"));
        assertTrue(s.contains("Kovacs Anna"));
    }
}