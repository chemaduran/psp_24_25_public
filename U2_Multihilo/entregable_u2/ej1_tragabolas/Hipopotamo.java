package U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej1_tragabolas;

import static U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej1_tragabolas.Tragabolas.NUM_RONDAS;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Hipopotamo extends Thread {
  private final Tablero tablero;
  private final int idHipopotamo;
  private int bolasComidas = 0;

  public Hipopotamo(int idHipopotamo, Tablero tablero) {
    this.tablero = tablero;
    this.idHipopotamo = idHipopotamo;
  }

  public int getIdHipopotamo() {
    return idHipopotamo;
  }

  public int getBolasComidas() {
    return bolasComidas;
  }

  @Override
  public void run() {

    // Hacemos esperar a todos los hipopótamos al menos un segundo, para que nos aseguremos de que
    // la primera bola se ha lanzado
    try {
      sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    System.out.println("Hipopótamo " + idHipopotamo + " empieza a comer bolas");
    int ronda = 1;

    // Aquí hacemos un bucle "while" y no un "for", para poder controlar las rondas en las que come
    // un hipopótamo.
    while (ronda <= NUM_RONDAS) {
      int intentos = 0; // Se contabilizan los intentos por ronda
      boolean bolaComida = false; // Al iniciar la ronda, el hipopótamo no ha comido la bola

      // Nos ponemos a intentar comer en esta ronda. Terminaremos la ronda cuando ESTE hipopótamo se
      // coma la bola, o se acabe la ronda porque otro hipopótamo se ha comido la bola
      while (!bolaComida && tablero.getRondaActual() == ronda) {
        intentos++;
        bolaComida = tablero.comerBola(idHipopotamo, ronda, intentos);
        espera(); // Tiempo de espera aleatorio de como máximo 100 ms.
      }

      if (bolaComida) { // Aquí no sabemos si hemos salido del "while" anterior por comer bola o por
                        // haberse terminado la ronda
        bolasComidas++;
      } else {
        ronda++;
        System.out.println(
            "Hipopótamo " + idHipopotamo + " termina la ronda " + ronda + " sin comer");
      }
    }

    System.out.println("Hipopótamo " + idHipopotamo + " ha terminado de comer bolas");
  }

  public void espera() {
    try {
      sleep((long) (Math.random() * 100));
    } catch (InterruptedException ex) {
      Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
