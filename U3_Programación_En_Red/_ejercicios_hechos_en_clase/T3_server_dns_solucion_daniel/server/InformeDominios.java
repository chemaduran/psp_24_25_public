package U3_Red.clase.tarea_3_dns_daniel.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class InformeDominios implements Serializable {

    private List<String> informeDominios;
    private ConcurrentHashMap<String, String> LISTA_DOMINIOS;

    public InformeDominios(ConcurrentHashMap<String, String> listaDominios) {
        LISTA_DOMINIOS = listaDominios;
        this.informeDominios = recogerInformacion();
    }

    public List<String> getInformeDominios() {
        return informeDominios;
    }

    private List<String> recogerInformacion() {
        List<String> informeDominios = null;
        if (!LISTA_DOMINIOS.isEmpty()) {
            informeDominios = new ArrayList<>();
            for (String dominio : LISTA_DOMINIOS.keySet())
                informeDominios.add(dominio + ";" + LISTA_DOMINIOS.get(dominio));
        }

        return informeDominios;
    }

    public String informeOrdenado() {
        Collections.sort(informeDominios);

        StringBuilder informeOrdenado = new StringBuilder();
        for (String dominio : informeDominios) {
            informeOrdenado.append(dominio.replace(";", " => ") + "\n");
        }

        return informeOrdenado.toString();
    }
}
