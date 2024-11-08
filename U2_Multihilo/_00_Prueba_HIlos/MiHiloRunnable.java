package U2_Multihilo._01_Prueba_HIlos;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MiHiloRunnable implements Runnable {

  public void run() {

    int i = 0;
    while (i < 5) {
      System.out.println(
          "Soy el hilo Thread.currentThread().getName():"
              + Thread.currentThread().getName()
              + " y estoy en la vuelta "
              + i);
      try {
        Thread.sleep(5);
      } catch (InterruptedException ex) {
        Logger.getLogger(MiHiloRunnable.class.getName()).log(Level.SEVERE, null, ex);
      }
      i++;
    }
  }
}
