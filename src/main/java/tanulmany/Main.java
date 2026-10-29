package tanulmany;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import tanulmany.model.Beiratkozas;
import tanulmany.model.BeiratkozasiHiba;
import tanulmany.model.Felev;
import tanulmany.model.Hallgato;
import tanulmany.model.Kurzus;
import tanulmany.model.Oktato;
import tanulmany.szolgaltatas.AtiratKeszito;
import tanulmany.szolgaltatas.Lekerdezesek;

/**
 *
 * @author boldi
 */
public class Main
{
    
    public static void main(String[] args) throws IOException
    {
        Oktato kovacs = new Oktato("O1", "Kovacs Anna", "kovacs@egyetem.hu");
        Oktato szabo = new Oktato("O2", "Szabo Peter", "szabo@egyetem.hu");

        Kurzus prog1 = new Kurzus("PROG1", "Programozas 1", 5, 30, kovacs);
        Kurzus prog2 = new Kurzus("PROG2", "Programozas 2", 5, 30, kovacs, prog1);
        Kurzus mat = new Kurzus("MAT1", "Matematika 1", 4, 2, szabo);

        Hallgato bela = new Hallgato("H1", "Nagy Bela", "bela@hallgato.hu");
        Hallgato eva = new Hallgato("H2", "Toth Eva", "eva@hallgato.hu");
        Hallgato zoli = new Hallgato("H3", "Kiss Zoltan", "zoli@hallgato.hu");
        Hallgato dora = new Hallgato("H4", "Varga Dora", "dora@hallgato.hu");

        Felev osz = new Felev(2025, 1);
        Felev tavasz = new Felev(2025, 2);

        bela.beiratkozik(prog1, osz).beirJegy(9);
        bela.beiratkozik(mat, osz).beirJegy(6);
        eva.beiratkozik(prog1, osz).beirJegy(10);
        eva.beiratkozik(mat, osz).beirJegy(4);
        zoli.beiratkozik(prog1, osz).beirJegy(3);
        dora.beiratkozik(prog1, osz).beirJegy(7);
        bela.beiratkozik(prog2, tavasz).beirJegy(8);

        // ezek szandekosan hibasak, a szabalyokat mutatjak
        proba(() -> dora.beiratkozik(mat, osz));
        proba(() -> bela.beiratkozik(prog1, osz));
        proba(() -> zoli.beiratkozik(prog2, tavasz));

        Lekerdezesek lekerdezesek = new Lekerdezesek();
        System.out.println("Legjobb 3 a PROG1-en: " + lekerdezesek.legjobb3Hallgato(prog1));
        System.out.println("Kreditek oktatonkent: " + lekerdezesek.kreditekOktatonkent(List.of(prog1, prog2, mat)));
        System.out.println("PROG1 atmenesi arany: " + prog1.atmenesiArany());
        System.out.println(bela + " atlag: " + bela.sulyozottAtlag() + ", kredit: " + bela.megszerzettKreditek());

        for(Beiratkozas b : bela.getBeiratkozasok())
        {
            System.out.println(b);
        }

        Path fajl = Path.of("atirat_" + bela.getAzonosito() + ".txt");
        new AtiratKeszito().ment(bela, fajl);
        System.out.println("Atirat mentve: " + fajl.toAbsolutePath());
    }

    private static void proba(Runnable muvelet)
    {
        try
        {
            muvelet.run();
        }
        catch(BeiratkozasiHiba e)
        {
            System.out.println("Hiba: " + e.getMessage());
        }
    }
}
