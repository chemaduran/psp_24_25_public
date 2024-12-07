package U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej1_tragabolas;

import java.util.ArrayList;
import java.util.Comparator;

public class Tragabolas {

  // Constantes donde vamos a guardar el número de jugadores, el número de rondas por jugador y la
  // probabilidad de comer bola
  public static final int NUM_JUGADORES = 4;
  public static final int NUM_RONDAS = 5;
  public static final int PROBABILIDAD_COMER_BOLA = 20;

  public static void main(String[] args) {

    // Los ArrayList donde guardamos las instancias de los diferentes hilos que son, por un lado,
    // jugadores, y por otro lado, hipopótamos.
    ArrayList<Jugador> jugadores = new ArrayList<>();
    ArrayList<Hipopotamo> hipopotamos = new ArrayList<>();

    // Creo la instancia del objeto Tablero, que utilizaremos como recurso compartido
    Tablero tablero = new Tablero();

    // Creamos los 4 jugadores y los 4 hipopótamos y los añadimos a un ArrayList
    for (int i = 0; i < NUM_JUGADORES; i++) {
      Jugador jugador = new Jugador(i + 1, tablero);
      jugador.setName("Jugador " + (i + 1));
      jugadores.add(jugador);
      Hipopotamo hipopotamo = new Hipopotamo(i + 1, tablero);
      hipopotamo.setName("Hipopotamo " + (i + 1));
      hipopotamos.add(hipopotamo);
    }

    // Iniciamos los hilos
    for (int i = 0; i < NUM_JUGADORES; i++) {
      jugadores.get(i).start();
      hipopotamos.get(i).start();
    }

    // Esperamos a que terminen los jugadores
    for (int i = 0; i < NUM_JUGADORES; i++) {
      try {
        jugadores.get(i).join();
      } catch (InterruptedException e) {
        System.out.println(e.getLocalizedMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
          System.out.println(ste);
        }
      }
    }
    System.out.println("Terminaron los jugadores");

    // Esperamos a que terminen los hipopótamos
    for (int i = 0; i < NUM_JUGADORES; i++) {
      try {
        hipopotamos.get(i).join();
      } catch (InterruptedException e) {
        System.out.println(e.getLocalizedMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
          System.out.println(ste);
        }
      }
    }
    System.out.println("Terminaron los hipopótamos");

    // Resumen final
    System.out.println("\n===== Resumen de la partida =====");
    hipopotamos.sort(Comparator.comparingInt(Hipopotamo::getBolasComidas).reversed());
    for (Hipopotamo h : hipopotamos) {
      System.out.println(
          "Hipopótamo " + h.getIdHipopotamo() + " comió " + h.getBolasComidas() + " bolas");
    }
  }
}
