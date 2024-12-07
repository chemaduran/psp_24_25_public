package U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej1_tragabolas;

import static U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej1_tragabolas.Tragabolas.NUM_JUGADORES;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Tablero {

  private AtomicBoolean bolaEnJuego = new AtomicBoolean(false); // Indica si la bola está en juego
  private AtomicInteger turnoActual = new AtomicInteger(1); // Turno del jugador que lanza la bola
  private AtomicInteger rondaActual = new AtomicInteger(1); // Ronda actual
  private AtomicInteger totalBolasComidasRonda =
      new AtomicInteger(0); // Bolas comidas en cada ronda por los hipopótamos

  public int getRondaActual() {
    return rondaActual.get();
  }

  public synchronized void lanzarBola(int idJugador, int ronda) {

    System.out.println(
        "lanzarBola - turnoActual=" + turnoActual.get() + " - jugadorId=" + idJugador);

    // Si el turno no nos corresponde o estamos en otra ronda, nos esperamos.
    while (idJugador != turnoActual.get() || ronda != rondaActual.get()) {
      try {
        System.out.println("Soy idJugador=" + idJugador + " NO ME TOCA, ME ESPERO");
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    System.out.println(
        "---- Ronda "
            + ronda
            + ", turnoActual="
            + turnoActual
            + " - lanza la bola Jugador "
            + idJugador);

    // Se pone la bola en juego
    bolaEnJuego.set(true);
  }

  public synchronized boolean comerBola(int idHipopotamo, int ronda, int intentos) {
    // Presuponemos que la bola al principio no va a ser comida
    boolean bolaComida = false;

    // Sólo vamos a intentar comer si la bola está en juego
    if (bolaEnJuego.get()) {
      // Se intenta comer la bola
      System.out.println(
          "El hipopótamo "
              + idHipopotamo
              + " intenta comer la bola de la ronda "
              + ronda
              + " en el intento "
              + intentos);
      //  Hacemos el intento de comer la bola...
      bolaComida = Math.random() * 100 < Tragabolas.PROBABILIDAD_COMER_BOLA;
      if (bolaComida) { // ... y si ha sido comida...
        System.out.println("El hipopótamo " + idHipopotamo + " ha comido la bola");
        bolaEnJuego.set(false); // En el tablero ya no hay bolas
        // Pasamos al siguiente turno
        turnoActual.set((turnoActual.getAndIncrement() % NUM_JUGADORES) + 1);
        // Incrementamos el total de bolas comidas en la ronda.
        totalBolasComidasRonda.getAndIncrement();
        // Y si ya hemos terminado la ronda, tenemos que resetear valores.
        if (totalBolasComidasRonda.get() == NUM_JUGADORES) {
          System.out.println("Ronda " + rondaActual.get() + " - FIN DE RONDA");
          totalBolasComidasRonda.set(0);
          rondaActual.getAndIncrement();
        }
        notifyAll(); // Avisamos a los jugadores de que pueden tirar la siguiente ronda
      }
    }
    return bolaComida;
  }
}
