package U3_Red.tareas.en_24_25_conecta_4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorConecta4 {
  private static final int PUERTO = 12345;
  private static int idPartida = 0;
  private static int contadorClientes = 0;
  private static Partida nuevaPartida;

  public static void main(String[] args) {
    try (ServerSocket servidor = new ServerSocket(PUERTO)) {
      System.out.println("Servidor en ejecución...");
      ClientHandler clienteRojo = null;
      ClientHandler clienteVerde = null;
      while (true) {
        Socket socket = servidor.accept();
        // A cada cliente que se conecta se le asigna un hilo para manejarlo, con el recurso
        // compartido de la partida, además de asignarle un color de ficha (ROJO o VERDE). Siempre
        // se asigna primero el color VERDE, luego el ROJO, que será siempre el que inicie la
        // partida.

        if (contadorClientes % 2 == 0) {
          idPartida++;
          nuevaPartida = new Partida(idPartida);
          clienteVerde = new ClientHandler(socket, nuevaPartida, ClientID.Ficha.VERDE, null);
        } else {
          clienteRojo = new ClientHandler(socket, nuevaPartida, ClientID.Ficha.ROJO, clienteVerde);
          clienteVerde.setOponente(clienteRojo);
          new Thread(clienteRojo).start();
          new Thread(clienteVerde).start();
        }
        contadorClientes++;
      }
    } catch (IOException e) {
      System.err.println("Error en el servidor: " + e.getLocalizedMessage());
    }
  }
}
