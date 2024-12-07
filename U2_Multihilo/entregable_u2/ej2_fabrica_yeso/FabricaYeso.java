package U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej2_fabrica_yeso;

public class FabricaYeso {
  private static FabricaYeso fabrica;

  private FabricaYeso() {}

  public static FabricaYeso nuevaInstancia() {
    if (fabrica == null) {
      fabrica = new FabricaYeso();
    }
    return fabrica;
  }

  public static void lanzarProductorEmpaquetador(int cantidadProductor, int cantidadEmpaquetador) {
    Pila pila = new Pila();

    Productor productor = new Productor(pila, cantidadProductor);
    Empaquetador empaquetador = new Empaquetador(pila, cantidadEmpaquetador, 250);

    productor.start();
    empaquetador.start();

    try {
      productor.join();
      empaquetador.join();

    } catch (InterruptedException ex) {
      System.err.println(ex.getMessage());
    }
    // Ahora escribimos el archivo de resumen
    empaquetador.escribirResumen();
  }

  public void fabricarSacos() {
    lanzarProductorEmpaquetador(500, 50);
  }
}
