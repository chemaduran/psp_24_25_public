package U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej2_fabrica_yeso;

import java.util.ArrayList;

public class Empaquetador extends Thread {
  private Pila pila;
  private int cantidadSacos, ms;
  private ArrayList<Lote> lotes = new ArrayList<>();

  public Empaquetador(Pila pila, int cant, int ms) {
    this.pila = pila;
    this.cantidadSacos = cant;
    this.ms = ms;
  }

  public void escribirResumen() {
    for (Lote l : lotes) {
      System.out.println(l);
    }
  }

  public void run() {

    for (int i = 1; i < cantidadSacos + 1; i++) {
      // Empaquetador saca 10 sacos
      double pesoTotal = 0;
      double pesoMedio = 0;
      double pesoMax = 0;
      double pesoMin = Double.MAX_VALUE;
      for (int j = 0; j < 10; j++) {
        Saco s = pila.empaquetar();
        s.setLote(i);
        System.out.println(
            "Empaquetador-> Paquete Lote: "
                + i
                + ": empaqueto Saco: "
                + s.getCodigo()
                + " "
                + s.getPeso()
                + "KG");
        pesoTotal += s.getPeso();
        if (s.getPeso() > pesoMax) {
          pesoMax = s.getPeso();
        }
        if (s.getPeso() < pesoMin) {
          pesoMin = s.getPeso();
        }
      }
      pesoMedio = pesoTotal / 10;
      Lote lote = new Lote(i, 10, pesoTotal, pesoMedio, pesoMax, pesoMin);
      lotes.add(lote);
      System.out.println("Empaquetador-> " + lote);
      try {
        Thread.sleep(ms);
      } catch (InterruptedException e) {
        System.out.println(e.getMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
          System.out.println(ste);
        }
      }
    }
  }
}
