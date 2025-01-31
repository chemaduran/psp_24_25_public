package dbrusev.server;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicInteger;

public class DnsServer {

    private static final int PUERTO = 5555;
    private static final int MAX_INTENTOS_ATAQUE = 3;

    private static final AtomicInteger intentosAtaque = new AtomicInteger(1);

    public static void main(String[] args) {

        try (ServerSocket socketServidor = new ServerSocket(PUERTO)){
            GestorDominios gestorDominios = new GestorDominios();
            System.out.println("SERVIDOR INICIADO EN EL PUERTO " + PUERTO + "\n");

            while (intentosAtaque.get() != MAX_INTENTOS_ATAQUE)
                new Thread(new HiloConexion(socketServidor.accept(), gestorDominios, intentosAtaque)).start();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        if (intentosAtaque.get() == MAX_INTENTOS_ATAQUE) {
            System.err.println("Se ha detectado un posible ataque al servidor, con lo que se procederá a su finalización");
            System.exit(1);
        }
    }




    /// Clase gestora para la conexión, registro y eliminación de dominios


    /// Generación de informe de los dominios registrados


    /// Plantilla de token de conexión para los clientes


}
