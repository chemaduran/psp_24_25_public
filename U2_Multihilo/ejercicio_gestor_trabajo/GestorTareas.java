package U2_Multihilo.resolucion.gestor_trabajo;

public class GestorTareas {
  public static void main(String[] args) {
    Trabajo trabajo = new Trabajo();
    final int nTrabajadores = 20;
    final int nJefes = 5;

    for (int i = 0; i < nTrabajadores; i++) {
      Trabajador trabajador = new Trabajador(trabajo, "Trabajador_" + i);
      trabajador.start();
    }
    for (int i = 0; i < nJefes; i++) {
      Jefe jefe = new Jefe(trabajo, "Jefe_" + i);
      jefe.start();
    }
  }
}
