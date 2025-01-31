package U3_Red.clase.tarea_3_dns_daniel.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/// Clase ejecutable encargada de gestionar cada petición de conexión
public class GestorDominios {
    private final String IP_DEFECTO = "192.168.0.0";

    private static final int MAX_DIRECCIONES = 14;
    private static final AtomicInteger numDominiosRegistrados = new AtomicInteger(0);

    private final ConcurrentHashMap<String, String> LISTA_DOMINIOS = new ConcurrentHashMap<>();

    public String conectarADominio(String nombreDominio) {
        String respuesta;
        if (LISTA_DOMINIOS.containsKey(nombreDominio))
            respuesta = "Conexión correcta a " + nombreDominio + " en la dirección IP: <" + LISTA_DOMINIOS.get(nombreDominio) + ">";
        else
            respuesta = "ERROR HTTP 404 (Page Not Found): " + nombreDominio + " no encontrado en el servidor DNS";

        return respuesta;
    }

    public synchronized String registrarDominio(String nombreDominio) {
        String ipDeDominio = null;
        if (numDominiosRegistrados.get() < MAX_DIRECCIONES && !LISTA_DOMINIOS.containsKey(nombreDominio)) {
            numDominiosRegistrados.incrementAndGet();
            ipDeDominio = IP_DEFECTO.substring(0, IP_DEFECTO.length() - 1) + numDominiosRegistrados;

            LISTA_DOMINIOS.put(nombreDominio, ipDeDominio);
        }

        return ipDeDominio;
    }

    public synchronized boolean eliminarDominio(String nombreDominio) {
        boolean dominioEliminado = false;
        if (!LISTA_DOMINIOS.isEmpty() && LISTA_DOMINIOS.containsKey(nombreDominio)) {
            numDominiosRegistrados.decrementAndGet();
            LISTA_DOMINIOS.remove(nombreDominio);
            dominioEliminado = true;
        }

        return dominioEliminado;
    }

    public ConcurrentHashMap<String, String> getLISTA_DOMINIOS() {
        return LISTA_DOMINIOS;
    }

}