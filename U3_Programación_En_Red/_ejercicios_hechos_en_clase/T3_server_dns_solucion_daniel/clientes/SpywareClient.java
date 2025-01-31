package U3_Red.clase.tarea_3_dns_daniel.clientes;

import U3_Red.clase.tarea_3_dns_daniel.server.Token;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

public class SpywareClient {

    private static final String SERVER = "localhost";
    private static final int PUERTO = 5555;

    public static void main(String[] args) {
        Token token = new Token(new Random().nextLong(1111111112L, 9999999998L), "ALL");

        try (Socket conexion = new Socket()) {
            InetSocketAddress addr = new InetSocketAddress(SERVER, PUERTO);
            conexion.connect(addr);

            /// Generación de flujos de datos y envío del token para la validación
            ObjectOutputStream flujoSalidaDatos = new ObjectOutputStream(conexion.getOutputStream());
            flujoSalidaDatos.writeObject(token);
            flujoSalidaDatos.flush();

            /// Recibir la respuesta de parte del servidor (denegada)
            ObjectInputStream flujoEntradaDatos = new ObjectInputStream(conexion.getInputStream());
            System.out.println(flujoEntradaDatos.readUTF());

            flujoEntradaDatos.close();
            flujoSalidaDatos.close();
        } catch (ConnectException ce) {
            System.err.println("El servidor está apagado");
        } catch (IOException e) {
            System.err.println("Error de conexión con el servidor");
        }
    }

}
