package tanulmany.szolgaltatas;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import tanulmany.model.Beiratkozas;
import tanulmany.model.Hallgato;
import tanulmany.model.Kurzus;
import tanulmany.model.Oktato;
/**
 *
 * @author boldi
 */
public class Lekerdezesek
{
    // a legjobb jegyet kapott hallgatok egy kurzuson, egy hallgato csak egyszer
    public List<Hallgato> legjobbHallgatok(Kurzus kurzus, int darab)
    {
        return kurzus.getBeiratkozasok().stream()
                .filter(b -> b.getJegy().isPresent())
                .sorted(Comparator.comparingInt((Beiratkozas b) -> b.getJegy().get().ertek()).reversed()
                        .thenComparing(b -> b.getHallgato().getNev()))
                .map(Beiratkozas::getHallgato)
                .distinct()
                .limit(darab)
                .toList();
    }

    public List<Hallgato> legjobb3Hallgato(Kurzus kurzus)
    {
        return legjobbHallgatok(kurzus, 3);
    }

    public Map<Oktato, Integer> kreditekOktatonkent(Collection<Kurzus> kurzusok)
    {
        return kurzusok.stream()
                .collect(Collectors.groupingBy(Kurzus::getOktato,
                        Collectors.summingInt(Kurzus::getKredit)));
    }
    
}
