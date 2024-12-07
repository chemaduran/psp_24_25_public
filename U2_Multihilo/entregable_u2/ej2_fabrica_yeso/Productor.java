package U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej2_fabrica_yeso;

public class Productor extends Thread {
  private Pila pila;
  private int cant;

  public Productor(Pila pila, int cant) {
    this.pila = pila;
    this.cant = cant;
  }

  public Productor(Pila p) {
    this.pila = p;
  }

  public void run() {
    for (int i = 1; i < cant + 1; i++) {
      Saco s = new Saco();
      System.out.println(
          "Productor-> Produzco Saco: " + i + ": " + s.getCodigo() + " " + s.getPeso() + "KG");
      try {
        Thread.sleep(s.getPeso() * 2L);
      } catch (InterruptedException e) {
        System.out.println(e.getMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
          System.out.println(ste);
        }
      }
      pila.apilar(s);
    }
    System.out.println("Productor-> He terminado de producir");
  }
}
