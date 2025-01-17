package U3_Red.tareas.tarea_1_adivina_numero;

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

public class Servidor {

  private static final int PUERTO = 5555;
  private static Map<String, Integer> participantes = new HashMap<>();

  public static void main(String[] args) {
    try {

      boolean finPartida = false;
      int numDeJugadas = 0;
      int numSecreto = aleatorio(1, 100);
      System.out.println("SERVIDOR: Nueva partida empezada con el número secreto: " + numSecreto);
      ServerSocket serverSocket = new ServerSocket(PUERTO);

      while (!finPartida) {

        numDeJugadas++;
        System.out.println(
            "SERVIDOR: Jugada número: "
                + numDeJugadas
                + " Esperando a que se conecten los jugadores...");
        Socket clientSocket = serverSocket.accept();

        // 1. Crear flujo de ENTRADA del cliente (recepción del nombre del jugador)
        DataInputStream flujoEntrada = new DataInputStream(clientSocket.getInputStream());
        ObjectOutputStream flujoSalida = new ObjectOutputStream(clientSocket.getOutputStream());

        // 2. RECEPCIÓN del mensaje del cliente
        String nombreJugador = flujoEntrada.readUTF();

        if (validarJugador(nombreJugador)) {
          System.out.println("SERVIDOR: Se ha conectado a la partida el jugador: " + nombreJugador);

          // 3. ENVÍO la acreditación al cliente
          Acreditacion card = new Acreditacion();
          card.setValida(true);
          card.setNomUsuario(nombreJugador.toUpperCase());
          card.setMensaje(
              "Bienvenido a la partida "
                  + nombreJugador
                  + "!! Intenta averiguar el número secreto");

          flujoSalida.writeObject(card);

          // 4. Recibo el número del cliente
          int num = Integer.parseInt(flujoEntrada.readUTF());

          String mensajeFinal = "";
          if (num == numSecreto) {
            finPartida = true;
            mensajeFinal = "Enhorabuena " + nombreJugador + " Has acertado el número";
            System.out.println(
                "SERVIDOR: El jugador: "
                    + nombreJugador
                    + " ha acertado el numero. Fin de la partida tras "
                    + numDeJugadas
                    + " intentos");
          } else {
            System.out.println(
                "SERVIDOR: El jugador: "
                    + nombreJugador
                    + " no ha acertado el numero. Continuamos jugando...");
            if (num < numSecreto) {
              mensajeFinal = "No has acertado!! El número que buscas es mayor";
            } else {
              mensajeFinal = "No has acertado!! El número que buscas es menor";
            }

            if (numDeJugadas == 10) {
              finPartida = true;
              System.out.println(
                  "SERVIDOR: Se han realizado 2 jugadas y no ha habido ningún acertante. Fin de la partida");
              mensajeFinal =
                  mensajeFinal + "\n - Se han agotado el número de jugadas. Servidor cerrado";
            }
          }

          DataOutputStream flujoSalida2 = new DataOutputStream(clientSocket.getOutputStream());
          flujoSalida2.writeUTF(mensajeFinal);

          // ESTO es para probar que se recibe directamente el 12
          //                    int num2 = Integer.parseInt(flujoEntrada.readUTF());
          //                    flujoSalida2.writeUTF("Me has enviado de nuevo el número " + num2);

          // 5. Cierre de flujos y conexión del cliente (en orden)
          flujoEntrada.close();
          flujoSalida.close();
          flujoSalida2.close();
          clientSocket.close();

        } else {
          System.out.println(
              "SERVIDOR: Se ha comprobado que "
                  + nombreJugador
                  + " ha agotado los 3 intentos y no puede volver a jugar");

          // 3. Crear flujo de SALIDA hacia el cliente (envío del mensaje)
          Acreditacion card = new Acreditacion();
          card.setValida(false);
          card.setNomUsuario(nombreJugador.toUpperCase());
          card.setMensaje(
              "Lo siento  "
                  + nombreJugador
                  + " has agotado los 3 intentos posibles y no puedes volver a jugar");

          // 4. ENVIO del mensaje hacia el cliente
          flujoSalida.writeObject(card);

          flujoEntrada.close();
          flujoSalida.close();
          clientSocket.close();
        }
      }
      // 6. Cierre de conexión principal del servidor
      serverSocket.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } catch (Exception ex) {
      Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private static int aleatorio(int min, int max) {
    Random r = new Random();
    return min + r.nextInt(max - min + 1); // Entre min y max -> [min,max];
  }

  private static boolean validarJugador(String nombreJugador) {
    boolean jugadorValido = true;
    String key = nombreJugador.toUpperCase();
    if (participantes.containsKey(key)) {
      if (participantes.get(key) > 2) {
        jugadorValido = false;
      } else {
        participantes.replace(key, participantes.get(key) + 1);
      }
    } else {
      participantes.put(key, 1);
    }
    return jugadorValido;
  }
}
