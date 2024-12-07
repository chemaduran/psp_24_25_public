package U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej2_fabrica_yeso;

import java.util.LinkedList;

public class Pila extends LinkedList<Saco> {
  boolean sacosDisponible = false;

  public synchronized Saco empaquetar() {
    while (!sacosDisponible && isEmpty()) {
      try {
        // Si no hay hay empaquetar, esperamos
        wait();
      } catch (InterruptedException e) {
        System.out.println(e.getMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
          System.out.println(ste);
        }
      }
    }

    Saco s = this.removeFirst();
    sacosDisponible = false;
    // Activamos
    notifyAll();

    return s;
  }

  public synchronized void apilar(Saco s) {
    while (sacosDisponible && !isEmpty()) {
      try {
        // Si no hay que producir esperamos
        wait();
      } catch (InterruptedException e) {
        System.out.println(e.getMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
          System.out.println(ste);
        }
      }
    }
    add(s);
    sacosDisponible = true;
    // Ya hay cantidad a consumir, activamos.
    notifyAll();
  }
}
