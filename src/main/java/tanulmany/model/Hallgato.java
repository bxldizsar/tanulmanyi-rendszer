package tanulmany.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author boldi
 */
public class Hallgato extends Szemely
{
    private final List<Beiratkozas> beiratkozasok = new ArrayList<>();

    public Hallgato(String azonosito, String nev, String email)
    {
        super(azonosito, nev, email);
    }

    public Beiratkozas beiratkozik(Kurzus kurzus, Felev felev)
    {
        if(kurzus == null || felev == null)
        {
            throw new IllegalArgumentException("a kurzus es a felev kotelezo");
        }
        if(kurzus.telt(felev))
        {
            throw new BeiratkozasiHiba("a kurzus megtelt: " + kurzus.getKod());
        }
        if(beiratkozva(kurzus, felev))
        {
            throw new BeiratkozasiHiba("mar beiratkozott erre a kurzusra ebben a felevben: " + kurzus.getKod());
        }
        if(teljesitette(kurzus))
        {
            throw new BeiratkozasiHiba("ezt a kurzust mar teljesitette: " + kurzus.getKod());
        }
        Optional<Kurzus> elofeltetel = kurzus.getElofeltetel();
        if(elofeltetel.isPresent() && !teljesitette(elofeltetel.get()))
        {
            throw new BeiratkozasiHiba("nincs meg az elofeltetel: " + elofeltetel.get().getKod());
        }
        Beiratkozas uj = new Beiratkozas(this, kurzus, felev);
        beiratkozasok.add(uj);
        kurzus.hozzaad(uj);
        return uj;
    }

    public List<Beiratkozas> getBeiratkozasok()
    {
        return Collections.unmodifiableList(beiratkozasok);
    }

    public boolean teljesitette(Kurzus kurzus)
    {
        return beiratkozasok.stream()
                .anyMatch(b -> b.getKurzus() == kurzus && b.sikeres());
    }

    // kredittel sulyozott atlag a jeggyel rendelkezo beiratkozasokbol
    public double sulyozottAtlag()
    {
        int osszKredit = 0;
        int osszSuly = 0;
        for(Beiratkozas b : beiratkozasok)
        {
            if(b.getJegy().isPresent())
            {
                int kredit = b.getKurzus().getKredit();
                osszKredit += kredit;
                osszSuly += kredit * b.getJegy().get().ertek();
            }
        }
        if(osszKredit == 0)
        {
            return 0.0;
        }
        return (double) osszSuly / osszKredit;
    }

    public int megszerzettKreditek()
    {
        return beiratkozasok.stream()
                .filter(Beiratkozas::sikeres)
                .mapToInt(b -> b.getKurzus().getKredit())
                .sum();
    }

    private boolean beiratkozva(Kurzus kurzus, Felev felev)
    {
        return beiratkozasok.stream()
                .anyMatch(b -> b.getKurzus() == kurzus && b.getFelev().equals(felev));
    }
}
