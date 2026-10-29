package tanulmany.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class KurzusTest
{
    private final Oktato oktato = new Oktato("O1", "Kovacs Anna", "kovacs@egyetem.hu");
    private final Felev osz = new Felev(2025, 1);

    @Test
    void atmenesiAranyCsakAJegyekbolSzamol()
    {
        Kurzus prog = new Kurzus("PROG1", "Programozas 1", 5, 30, oktato);
        new Hallgato("H1", "A", "a@x.hu").beiratkozik(prog, osz).beirJegy(8);
        new Hallgato("H2", "B", "b@x.hu").beiratkozik(prog, osz).beirJegy(5);
        new Hallgato("H3", "C", "c@x.hu").beiratkozik(prog, osz).beirJegy(2);
        new Hallgato("H4", "D", "d@x.hu").beiratkozik(prog, osz).beirJegy(4);
        new Hallgato("H5", "E", "e@x.hu").beiratkozik(prog, osz);

        // 4 jegybol 2 atmeno, a jegy nelkuli nem szamit
        assertEquals(0.5, prog.atmenesiArany(), 0.0001);
    }

    @Test
    void kurzusOktatoNelkulNemHozhatoLetre()
    {
        assertThrows(IllegalArgumentException.class, () -> new Kurzus("K", "Kurzus", 5, 30, null));
    }
}