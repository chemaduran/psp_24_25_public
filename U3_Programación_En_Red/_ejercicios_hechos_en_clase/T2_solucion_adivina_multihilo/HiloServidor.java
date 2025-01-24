package U3_Red.clase.tarea_2_adivina_multihilo;

import U3_Red.tareas.tarea_1_adivina_numero.Acreditacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class HiloServidor extends Thread {

  private Socket cliente = null;
  private Partida partida = null;

  public HiloServidor(Socket cliente, Partida partida) {
    this.cliente = cliente;
    this.partida = partida;

  }

  public void run() {
    try {

        // 1. Crear flujo de ENTRADA del cliente (recepción del nombre del jugador)
        DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
        ObjectOutputStream flujoSalida = new ObjectOutputStream(cliente.getOutputStream());

        // 2. RECEPCIÓN del mensaje del cliente
        String nombreJugador = flujoEntrada.readUTF();

        if (Partida.validarJugador(nombreJugador)) {
          partida.getNumDeJugadas().getAndIncrement();
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
          if (num == partida.getNumSecreto()) {
            partida.setFinPartida(true);
            mensajeFinal = "Enhorabuena " + nombreJugador + " Has acertado el número";
            System.out.println(
                    "SERVIDOR: El jugador: "
                            + nombreJugador
                            + " ha acertado el numero. Fin de la partida tras "
                            + partida.getNumDeJugadas()
                            + " intentos");
          } else {
            System.out.println(
                    "SERVIDOR: El jugador: "
                            + nombreJugador
                            + " no ha acertado el numero. Continuamos jugando...");
            if (num < partida.getNumSecreto()) {
              mensajeFinal = "No has acertado!! El número que buscas es mayor";
            } else {
              mensajeFinal = "No has acertado!! El número que buscas es menor";
            }

            if (partida.getNumDeJugadas().get() == 10) {
              partida.setFinPartida(true);
              System.out.println(
                      "SERVIDOR: Se han realizado 2 jugadas y no ha habido ningún acertante. Fin de la partida");
              mensajeFinal =
                      mensajeFinal + "\n - Se han agotado el número de jugadas. Servidor cerrado";
            }
          }

          DataOutputStream flujoSalida2 = new DataOutputStream(cliente.getOutputStream());
          flujoSalida2.writeUTF(mensajeFinal);

          // ESTO es para probar que se recibe directamente el 12
          //                    int num2 = Integer.parseInt(flujoEntrada.readUTF());
          //                    flujoSalida2.writeUTF("Me has enviado de nuevo el número " + num2);

          // 5. Cierre de flujos y conexión del cliente (en orden)
          flujoEntrada.close();
          flujoSalida.close();
          flujoSalida2.close();
          cliente.close();

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
          cliente.close();
        }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
