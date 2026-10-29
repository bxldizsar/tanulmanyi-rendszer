package tanulmany.model;
import java.util.Objects;

/**
 *
 * @author boldi
 */
public abstract class Szemely 
{
    private final String azonosito;
    private String nev;
    private String email;
 
    protected Szemely(String azonosito, String nev, String email)
{
    this.azonosito = ellenorizSzoveg(azonosito, "azonosito");
    this.nev = ellenorizSzoveg(nev, "nev");
    this.email = ellenorizEmail(email);
}
    // TODO: getterek, equals, hashCode, toString
    
    
    public String getAzonosito()
    {
        return azonosito;
    }

    public String getNev()
    {
        return nev;
    }

    public void setNev(String nev)
    {
        this.nev = ellenorizSzoveg(nev, "nev");
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = ellenorizEmail(email);
    }

    private static String ellenorizSzoveg(String ertek, String mezoNev)
    {
        if(ertek == null || ertek.isBlank())
        {
            throw new IllegalArgumentException("a(z) " + mezoNev + " nem lehet ures");
        }
        return ertek.trim();
    }

    private static String ellenorizEmail(String email)
    {
        String e = ellenorizSzoveg(email, "email");
        if(!e.contains("@"))
        {
            throw new IllegalArgumentException("hibas email: " + email);
        }
        return e;
    }

    // csak az azonosito szamit, a nev es az email valtozhat
    @Override
    public final boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof Szemely))
        {
            return false;
        }
        Szemely masik = (Szemely) o;
        return azonosito.equals(masik.azonosito);
    }

    @Override
    public final int hashCode()
    {
        return Objects.hash(azonosito);
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "[" + azonosito + ", " + nev + ", " + email + "]";
    }
}
