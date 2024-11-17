package U2_Multihilo.resolucion.gestor_trabajo;

public class Trabajador extends Thread {
  private Trabajo trabajo;

  public Trabajador(Trabajo trabajo, String nombre) {
    this.trabajo = trabajo;
    setName(nombre);
  }

  @Override
  public void run() {
    int contador = 0;
    while (!trabajo.isObjetivo_alcanzado() && !trabajo.isQuebrado()) {
      trabajo.hacerTarea();
      contador++;
    }
    System.out.println(
        Thread.currentThread().getName() + " ha terminado un número total de tareas: " + contador);
  }
}
