package U2_Multihilo.T3;

public class GestionTareas {
  public static void main(String[] args) {

    int numJefes = 5;
    int numTrabajadores = 20;
    Trabajo trabajo = new Trabajo(5000, 500);

    for (int i = 1; i <= numJefes; i++) {
      new Jefe(trabajo, "JEFE_" + i).start();
    }

    for (int i = 1; i <= numTrabajadores; i++) {
      new Trabajador(trabajo, "TRABAJADOR_" + i).start();
    }
  }
}
