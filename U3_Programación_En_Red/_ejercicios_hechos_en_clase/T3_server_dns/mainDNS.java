package U3_Red.clase.tarea_3_server_dns;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class mainDNS {
    private static final int PUERTO = 5555;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO);
            System.out.println("SERVIDOR: Escuchando por el puerto " + PUERTO + " ...");

            while (true) {
                // 1. Espera/escucha del cliente (LISTEN) y aceptación de
                // conexión (ACCEPT)
                Socket clientSocket = serverSocket.accept();

                // 2. Se crea el hilo dedicado al cliente
                HiloMainDNS hilo = new HiloMainDNS(clientSocket);

                // 3. El hilo pasa a estado RUNNABLE
                hilo.start();


            }

            // Cierre de conexión principal del servidor
//       serverSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
