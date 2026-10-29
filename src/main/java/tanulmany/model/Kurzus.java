package tanulmany.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author boldi
 */
public class Kurzus 
{
    private final String kod;
    private final String nev;
    private final int kredit;
    private final int maxLetszam;
    private final Oktato oktato;
    private final Kurzus elofeltetel;
    private final List<Beiratkozas> beiratkozasok = new ArrayList<>();

    public Kurzus(String kod, String nev, int kredit, int maxLetszam, Oktato oktato)
    {
        this(kod, nev, kredit, maxLetszam, oktato, null);
    }

    public Kurzus(String kod, String nev, int kredit, int maxLetszam, Oktato oktato, Kurzus elofeltetel)
    {
        if(kod == null || kod.isBlank() || nev == null || nev.isBlank())
        {
            throw new IllegalArgumentException("a kod es a nev nem lehet ures");
        }
        if(kredit <= 0)
        {
            throw new IllegalArgumentException("a kredit pozitiv kell legyen: " + kredit);
        }
        if(maxLetszam <= 0)
        {
            throw new IllegalArgumentException("a max letszam pozitiv kell legyen: " + maxLetszam);
        }
        if(oktato == null)
        {
            throw new IllegalArgumentException("a kurzusnak kell oktato");
        }
        this.kod = kod;
        this.nev = nev;
        this.kredit = kredit;
        this.maxLetszam = maxLetszam;
        this.oktato = oktato;
        this.elofeltetel = elofeltetel;
    }

    public String getKod()
    {
        return kod;
    }

    public String getNev()
    {
        return nev;
    }

    public int getKredit()
    {
        return kredit;
    }

    public int getMaxLetszam()
    {
        return maxLetszam;
    }

    public Oktato getOktato()
    {
        return oktato;
    }

    public Optional<Kurzus> getElofeltetel()
    {
        return Optional.ofNullable(elofeltetel);
    }

    public List<Beiratkozas> getBeiratkozasok()
    {
        return Collections.unmodifiableList(beiratkozasok);
    }

    public long letszam(Felev felev)
    {
        return beiratkozasok.stream()
                .filter(b -> b.getFelev().equals(felev))
                .count();
    }

    public boolean telt(Felev felev)
    {
        return letszam(felev) >= maxLetszam;
    }

    // a jeggyel lezart beiratkozasok kozul hany sikeres
    public double atmenesiArany()
    {
        List<Jegy> jegyek = beiratkozasok.stream()
                .flatMap(b -> b.getJegy().stream())
                .toList();
        if(jegyek.isEmpty())
        {
            return 0.0;
        }
        long sikeres = jegyek.stream().filter(Jegy::sikeres).count();
        return (double) sikeres / jegyek.size();
    }

    // csak a Hallgato hivja, ezert package-private
    void hozzaad(Beiratkozas beiratkozas)
    {
        beiratkozasok.add(beiratkozas);
    }

    @Override
    public String toString()
    {
        return "Kurzus[" + kod + ", " + nev + ", " + kredit + " kredit, max " + maxLetszam
                + ", oktato: " + oktato.getNev() + "]";
    }
}
