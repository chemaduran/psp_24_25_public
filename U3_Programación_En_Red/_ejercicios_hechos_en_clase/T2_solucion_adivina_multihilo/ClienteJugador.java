package U3_Red.clase.tarea_2_adivina_multihilo;

import U3_Red.tareas.tarea_1_adivina_numero.Acreditacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteJugador {

  private static final int PUERTO = 5555;
  private static final String SERVER = "localhost";

  public static void main(String[] args) {
    try {

      Socket clientSocket = new Socket();
      InetSocketAddress addr = new InetSocketAddress(SERVER, PUERTO);
      clientSocket.connect(addr);

      Scanner sc = new Scanner(System.in);
      System.out.println("CLIENTE: Introduce tu nombre");
      String nombreJugador = sc.nextLine();

      // 1. Crear flujo de SALIDA hacia el servidor (envío del mensaje)
      DataOutputStream flujoSalida = new DataOutputStream(clientSocket.getOutputStream());

      // 2. ENVÍO del mensaje hacia el servidor

      flujoSalida.writeUTF(nombreJugador);

      // 3. Crear flujo de ENTRADA al servidor (recepción del mensaje)
      ObjectInputStream flujoEntrada = new ObjectInputStream(clientSocket.getInputStream());

      // 4. RECEPCIÓN del mensaje del servidor
      Acreditacion card = (Acreditacion) flujoEntrada.readObject();
      System.out.println("CLIENTE: Recibiendo del SERVIDOR: " + card.getMensaje());

      if (card.isValida()) {

        // 5. ENVÍO del OBJETO hacia el cliente
        System.out.println("CLIENTE: Introduce el número");
        String numeroIntroducido = sc.nextLine();
        while (!esNumero(numeroIntroducido)) {
          System.out.println(
              "CLIENTE: El valor introducido no es numérico. Por favor introdúcelo de nuevo");
          numeroIntroducido = sc.nextLine();
        }
        int numero = Integer.parseInt(numeroIntroducido);

        // 6. Envío el número al cliente
        flujoSalida.writeUTF(String.valueOf(numero));

        // 7. Crear flujo de ENTRADA del mensaje del servidor (recepción del mensaje)
        DataInputStream flujoEntrada2 = new DataInputStream(clientSocket.getInputStream());
        System.out.println("CLIENTE: Recibiendo del SERVIDOR: " + flujoEntrada2.readUTF());

        // 6. Esta prueba no vale
        //                System.out.println("CLIENTE: Enviando el 12 directemente");
        //                flujoSalida.writeUTF("12");
        //                System.out.println("CLIENTE: Recibiendo del SERVIDOR: "
        //                        + flujoEntrada2.readUTF());
        // 8. Cierre de flujos de datos y conexión (en orden)

        flujoSalida.close();
        flujoEntrada.close();
        flujoEntrada2.close();
        clientSocket.close();

      } else {
        System.out.println("***    GAME OVER  :-(   ***");
      }

    } catch (IOException e) {
      System.out.println("Error en la conexión del socket");
      System.out.println(e.getMessage());
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(ClienteJugador.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private static boolean esNumero(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}
