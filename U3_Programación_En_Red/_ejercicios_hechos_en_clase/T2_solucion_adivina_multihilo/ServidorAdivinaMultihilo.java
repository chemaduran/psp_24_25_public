package U3_Red.clase.tarea_2_adivina_multihilo;

import U3_Red.clase._05_TCPMultihilo.HiloCliente;
import U3_Red.tareas.tarea_1_adivina_numero.Acreditacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorAdivinaMultihilo {

  private static final int PUERTO = 5555;

  public static void main(String[] args) {
    try {
      Partida partida = new Partida();
      System.out.println("SERVIDOR: Nueva partida empezada con el número secreto: " + partida.getNumSecreto());
      ServerSocket serverSocket = new ServerSocket(PUERTO);

      while (!partida.isFinPartida().get()) {
        System.out.println(
                "SERVIDOR: Jugada número: "
                        + partida.getNumDeJugadas()
                        + " Esperando a que se conecten los jugadores...");

        // 1. Espera/escucha del cliente (LISTEN) y aceptación de
        // conexión (ACCEPT)
        Socket clientSocket = serverSocket.accept();

        // 2. Se crea el hilo dedicado al cliente
        HiloServidor hilo = new HiloServidor(clientSocket, partida);

        // 3. El hilo pasa a estado RUNNABLE
        hilo.start();
      }
      // 6. Cierre de conexión principal del servidor
      serverSocket.close();
    } catch (IOException e) {
      System.out.println("Error en la conexión del socket");
      System.out.println(e.getMessage());
    } catch (Exception ex) {
      Logger.getLogger(ServidorAdivinaMultihilo.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
