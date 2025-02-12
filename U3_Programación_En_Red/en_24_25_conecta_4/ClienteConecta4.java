package U3_Red.tareas.en_24_25_conecta_4;

import java.io.*;
import java.net.Socket;

public class ClienteConecta4 {
  public static void main(String[] args) {
    try {
      Socket socket = new Socket("localhost", 12345);

      ObjectOutputStream salidaObj = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream entradaObj = new ObjectInputStream(socket.getInputStream());
      DataInputStream entradaData = new DataInputStream(socket.getInputStream());
      DataOutputStream salidaData = new DataOutputStream(socket.getOutputStream());

      ClientID cliente = new ClientID();
      salidaObj.writeObject(cliente);
      salidaObj.flush();

      ClientID clientID = (ClientID) entradaObj.readObject();
      System.out.println("Color de ficha asignado: " + getColorString(clientID.getColorFicha()));
      System.out.println("ID de la partida: " + clientID.getIdPartida());

      while (true) {
        Partida partida = (Partida) entradaObj.readObject();
        if (partida.isPartidaTerminada()) {
          System.out.println(
              "Partida terminada. El ganador es " + getColorString(partida.getGanador()));
          partida.imprimirTablero();
          break;
        }
        System.out.println("Es el turno de: " + getColorString(partida.getTurnoActual()));
        System.out.println("Tablero:");
        partida.imprimirTablero();

        if (clientID.getColorFicha() == partida.getTurnoActual()) {
          System.out.println("Es tu turno. Ingresa la columna:");
          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          int columna = Integer.parseInt(br.readLine());
          salidaData.writeInt(columna);
          salidaData.flush();
        }
      }
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Error en el cliente: " + e.getLocalizedMessage());
    }
  }

  public static String getColorString(ClientID.Ficha c) {
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String RESET = "\u001B[0m";

    return (c == ClientID.Ficha.ROJO) ? RED + "ROJO (X)" + RESET : GREEN + "VERDE (0)" + RESET;
  }
}
