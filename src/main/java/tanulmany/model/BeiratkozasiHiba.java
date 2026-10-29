package tanulmany.model;

/**
 *
 * @author boldi
 */
public class BeiratkozasiHiba extends RuntimeException  
{
    public BeiratkozasiHiba(String uzenet)
    {
        super(uzenet);
    }
}
