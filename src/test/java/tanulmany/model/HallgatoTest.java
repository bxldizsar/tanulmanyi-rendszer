package tanulmany.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HallgatoTest
{
    private Oktato oktato;
    private Kurzus prog;
    private Kurzus mat;
    private Hallgato bela;
    private Felev osz;

    @BeforeEach
    void setUp()
    {
        oktato = new Oktato("O1", "Kovacs Anna", "kovacs@egyetem.hu");
        prog = new Kurzus("PROG1", "Programozas 1", 5, 2, oktato);
        mat = new Kurzus("MAT1", "Matematika 1", 3, 30, oktato);
        bela = new Hallgato("H1", "Nagy Bela", "bela@hallgato.hu");
        osz = new Felev(2025, 1);
    }

    @Test
    void teltKurzusraNemLehetBeiratkozni()
    {
        new Hallgato("H2", "Toth Eva", "eva@x.hu").beiratkozik(prog, osz);
        new Hallgato("H3", "Kiss Zoli", "zoli@x.hu").beiratkozik(prog, osz);

        assertThrows(BeiratkozasiHiba.class, () -> bela.beiratkozik(prog, osz));
    }

    @Test
    void masikFelevbenUjraVanHely()
    {
        new Hallgato("H2", "Toth Eva", "eva@x.hu").beiratkozik(prog, osz);
        new Hallgato("H3", "Kiss Zoli", "zoli@x.hu").beiratkozik(prog, osz);

        bela.beiratkozik(prog, new Felev(2025, 2));

        assertEquals(1, bela.getBeiratkozasok().size());
    }

    @Test
    void egyFelevbenCsakEgyszerLehetBeiratkozni()
    {
        bela.beiratkozik(prog, osz);

        assertThrows(BeiratkozasiHiba.class, () -> bela.beiratkozik(prog, osz));
    }

    @Test
    void bukasUtanKovetkezoFelevbenUjraFelveheto()
    {
        bela.beiratkozik(prog, osz).beirJegy(3);
        bela.beiratkozik(prog, new Felev(2025, 2)).beirJegy(8);

        assertEquals(5, bela.megszerzettKreditek());
    }

    @Test
    void elofeltetelNelkulNemLehetBeiratkozni()
    {
        Kurzus prog2 = new Kurzus("PROG2", "Programozas 2", 5, 30, oktato, prog);

        assertThrows(BeiratkozasiHiba.class, () -> bela.beiratkozik(prog2, osz));

        bela.beiratkozik(prog, osz).beirJegy(6);
        bela.beiratkozik(prog2, new Felev(2025, 2));
        assertEquals(2, bela.getBeiratkozasok().size());
    }

    @Test
    void sulyozottAtlagKredittelSulyoz()
    {
        bela.beiratkozik(prog, osz).beirJegy(10);
        bela.beiratkozik(mat, osz).beirJegy(2);

        // (5*10 + 3*2) / 8 = 7.0
        assertEquals(7.0, bela.sulyozottAtlag(), 0.0001);
    }

    @Test
    void jegyNelkulAzAtlagNulla()
    {
        bela.beiratkozik(prog, osz);

        assertEquals(0.0, bela.sulyozottAtlag(), 0.0001);
    }

    @Test
    void megszerzettKreditCsakAtmenoJegyert()
    {
        bela.beiratkozik(prog, osz).beirJegy(5);
        bela.beiratkozik(mat, osz).beirJegy(4);

        assertEquals(5, bela.megszerzettKreditek());
    }

    @Test
    void beiratkozasokListajaNemModosithato()
    {
        bela.beiratkozik(prog, osz);

        assertThrows(UnsupportedOperationException.class, () -> bela.getBeiratkozasok().clear());
    }
}