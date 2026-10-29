package tanulmany.szolgaltatas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import tanulmany.model.Felev;
import tanulmany.model.Hallgato;
import tanulmany.model.Kurzus;
import tanulmany.model.Oktato;

class LekerdezesekTest
{
    private final Lekerdezesek lekerdezesek = new Lekerdezesek();
    private final Oktato kovacs = new Oktato("O1", "Kovacs Anna", "kovacs@x.hu");
    private final Oktato szabo = new Oktato("O2", "Szabo Peter", "szabo@x.hu");

    @Test
    void legjobb3HallgatoJegySzerint()
    {
        Kurzus prog = new Kurzus("PROG1", "Programozas 1", 5, 30, kovacs);
        Felev osz = new Felev(2025, 1);
        Hallgato a = new Hallgato("H1", "Anna", "a@x.hu");
        Hallgato b = new Hallgato("H2", "Bela", "b@x.hu");
        Hallgato c = new Hallgato("H3", "Csaba", "c@x.hu");
        Hallgato d = new Hallgato("H4", "Dora", "d@x.hu");
        a.beiratkozik(prog, osz).beirJegy(6);
        b.beiratkozik(prog, osz).beirJegy(9);
        c.beiratkozik(prog, osz).beirJegy(3);
        d.beiratkozik(prog, osz).beirJegy(10);

        assertEquals(List.of(d, b, a), lekerdezesek.legjobb3Hallgato(prog));
    }

    @Test
    void ujrafelvettKurzusnalHallgatoCsakEgyszer()
    {
        Kurzus prog = new Kurzus("PROG1", "Programozas 1", 5, 30, kovacs);
        Hallgato a = new Hallgato("H1", "Anna", "a@x.hu");
        Hallgato b = new Hallgato("H2", "Bela", "b@x.hu");
        a.beiratkozik(prog, new Felev(2025, 1)).beirJegy(2);
        a.beiratkozik(prog, new Felev(2025, 2)).beirJegy(9);
        b.beiratkozik(prog, new Felev(2025, 2)).beirJegy(7);

        assertEquals(List.of(a, b), lekerdezesek.legjobb3Hallgato(prog));
    }

    @Test
    void kreditekOktatonkentOsszegezve()
    {
        Kurzus k1 = new Kurzus("K1", "Kurzus 1", 5, 30, kovacs);
        Kurzus k2 = new Kurzus("K2", "Kurzus 2", 3, 30, kovacs);
        Kurzus k3 = new Kurzus("K3", "Kurzus 3", 4, 30, szabo);

        Map<Oktato, Integer> eredmeny = lekerdezesek.kreditekOktatonkent(List.of(k1, k2, k3));

        assertEquals(8, eredmeny.get(kovacs));
        assertEquals(4, eredmeny.get(szabo));
    }

    @Test
    void atiratFelevenkentRendezve()
    {
        Kurzus k1 = new Kurzus("K1", "Kurzus 1", 5, 30, kovacs);
        Kurzus k2 = new Kurzus("K2", "Kurzus 2", 3, 30, kovacs);
        Hallgato a = new Hallgato("H1", "Anna", "a@x.hu");
        a.beiratkozik(k2, new Felev(2025, 2)).beirJegy(8);
        a.beiratkozik(k1, new Felev(2024, 1)).beirJegy(7);

        String atirat = new AtiratKeszito().keszit(a);

        assertTrue(atirat.indexOf("2024/1") < atirat.indexOf("2025/2"));
        assertTrue(atirat.contains("Megszerzett kredit: 8"));
    }
}