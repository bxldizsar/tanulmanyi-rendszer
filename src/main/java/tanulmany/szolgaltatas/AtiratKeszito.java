package tanulmany.szolgaltatas;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import tanulmany.model.Beiratkozas;
import tanulmany.model.Felev;
import tanulmany.model.Hallgato;

/**
 *
 * @author boldi
 */
public class AtiratKeszito 
{
     public String keszit(Hallgato hallgato)
    {
        // a TreeMap a Felev compareTo-ja szerint rendez
        Map<Felev, List<Beiratkozas>> felevenkent = hallgato.getBeiratkozasok().stream()
                .collect(Collectors.groupingBy(Beiratkozas::getFelev, TreeMap::new, Collectors.toList()));

        StringBuilder sb = new StringBuilder();
        sb.append("Atirat: ").append(hallgato.getNev())
                .append(" (").append(hallgato.getAzonosito()).append(")\n\n");
        for(Map.Entry<Felev, List<Beiratkozas>> e : felevenkent.entrySet())
        {
            sb.append(e.getKey()).append(" felev\n");
            for(Beiratkozas b : e.getValue())
            {
                String jegy = b.getJegy().map(j -> String.valueOf(j.ertek())).orElse("-");
                sb.append(String.format("  %-8s %-30s %2d kredit  jegy: %s%n",
                        b.getKurzus().getKod(), b.getKurzus().getNev(), b.getKurzus().getKredit(), jegy));
            }
            sb.append("\n");
        }
        sb.append(String.format(Locale.ROOT, "Sulyozott atlag: %.2f%n", hallgato.sulyozottAtlag()));
        sb.append("Megszerzett kredit: ").append(hallgato.megszerzettKreditek()).append("\n");
        return sb.toString();
    }

    public void ment(Hallgato hallgato, Path fajl) throws IOException
    {
        Files.writeString(fajl, keszit(hallgato), StandardCharsets.UTF_8);
    }
}
