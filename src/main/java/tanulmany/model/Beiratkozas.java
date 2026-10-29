package tanulmany.model;

import java.util.Optional;

/**
 *
 * @author boldi
 */
public class Beiratkozas 
{
    private final Hallgato hallgato;
    private final Kurzus kurzus;
    private final Felev felev;
    private Jegy jegy;
    
    //csak a hallgato hozhatja letre (kompozicio)
    
    Beiratkozas(Hallgato hallgato,Kurzus kurzus,Felev felev)
    {
        this.hallgato = hallgato;
        this.kurzus = kurzus;
        this.felev = felev;
    }
    
    public Hallgato getHallgato()
    {
        return hallgato;
    }

    public Kurzus getKurzus()
    {
        return kurzus;
    }

    public Felev getFelev()
    {
        return felev;
    }

    public Optional<Jegy> getJegy()
    {
        return Optional.ofNullable(jegy);
    }

    public void beirJegy(int ertek)
    {
        this.jegy = new Jegy(ertek);
    }

    public boolean sikeres()
    {
        return getJegy().map(Jegy::sikeres).orElse(false);
    }

    @Override
    public String toString()
    {
        String j = getJegy().map(x -> String.valueOf(x.ertek())).orElse("nincs jegy");
        return "Beiratkozas[" + hallgato.getNev() + ", " + kurzus.getKod() + ", " + felev + ", " + j + "]";
    }
}
