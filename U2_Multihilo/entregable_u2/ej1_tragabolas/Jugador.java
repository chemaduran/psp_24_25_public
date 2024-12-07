package U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej1_tragabolas;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Jugador extends Thread {
  private final Tablero tablero;
  private final int idJugador;

  public Jugador(int idJugador, Tablero tablero) {
    this.tablero = tablero;
    this.idJugador = idJugador;
  }

  @Override
  public void run() {
    // Cada jugador lanzará una bola, y sólo una, en cada ronda.
    for (int ronda = 1; ronda <= Tragabolas.NUM_RONDAS; ronda++) {
      tablero.lanzarBola(idJugador, ronda);

      // Simula tiempo entre lanzamientos
      espera();
    }

    System.out.println("Soy el jugador " + idJugador + " y he terminado de lanzar las bolas");
  }

  public void espera() {
    try {
      sleep((long) (Math.random() * 500));
    } catch (InterruptedException ex) {
      Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
