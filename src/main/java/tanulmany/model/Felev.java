package tanulmany.model;

import java.util.Comparator;

/**
 *
 * @author boldi
 */
public record Felev(int ev, int szam) implements Comparable<Felev> {

    private static final Comparator<Felev> RENDEZES = Comparator.comparingInt(Felev::ev)
            .thenComparingInt(Felev::szam);

    public Felev {
        if (ev < 1900) {
            throw new IllegalArgumentException("hibas ev: " + ev);
        }
        if (szam != 1 && szam != 2) {
            throw new IllegalArgumentException("a felev szama 1 vagy 2 lehet: " + szam);
        }
    }

    @Override
    public int compareTo(Felev masik) {
        return RENDEZES.compare(this, masik);
    }

    @Override
    public String toString() {
        return ev + "/" + szam;
    }
}
