package dbrusev.clientes;

import dbrusev.server.InformeDominios;
import dbrusev.server.Token;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class UserClient {

    private static final Scanner lector = new Scanner(System.in);
    private static final String SERVER = "localhost";
    private static final int PUERTO = 5555;

    public static void main(String[] args) {
        Token token = new Token(1111111111L, "ALL");

        if (token.getOperacion().equalsIgnoreCase("CON") || token.getOperacion().equalsIgnoreCase("ALL")) {
            try (Socket conexion = new Socket()) {
                InetSocketAddress addr = new InetSocketAddress(SERVER, PUERTO);
                conexion.connect(addr);

                /// Generación de flujos de datos y envío del token para la validación
                ObjectOutputStream flujoSalidaDatos = new ObjectOutputStream(conexion.getOutputStream());
                flujoSalidaDatos.writeObject(token);
                flujoSalidaDatos.flush();

                /// Recibir la respuesta de parte del servidor (aceptada)
                ObjectInputStream flujoEntradaDatos = new ObjectInputStream(conexion.getInputStream());
                System.out.println(flujoEntradaDatos.readUTF() + "\n");

                switch (token.getOperacion().toUpperCase()) {
                    case "CON": /// Conexión con dominio ya creado
                        System.out.print(flujoEntradaDatos.readUTF());
                        String nombreDominio = "";
                        while (nombreDominio.isEmpty())
                            nombreDominio = lector.nextLine();

                        flujoSalidaDatos.writeUTF(nombreDominio);
                        flujoSalidaDatos.flush();
                        System.out.println(flujoEntradaDatos.readUTF());

                        break;
                    case "ALL": /// Recibir objeto con el informe de dominios
                        InformeDominios informe = (InformeDominios) flujoEntradaDatos.readObject();
                        System.out.println(informe.informeOrdenado());
                        break;
                }

                /// Cerrar flujos de datos
                flujoEntradaDatos.close();
                flujoSalidaDatos.close();
            } catch (ConnectException ce) {
                System.err.println("Error de conexión con el servidor");
            } catch (IOException e) {
                System.err.println("Error de flujo de datos con el servidor");
            } catch (ClassNotFoundException e) {
                System.err.println("Clase recibida no encontrada");
            }
        } else
            System.err.println("Tipo de operación no admitida para el usuario");
    }

}
