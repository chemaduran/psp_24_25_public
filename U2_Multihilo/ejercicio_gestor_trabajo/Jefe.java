package U2_Multihilo.resolucion.gestor_trabajo;

public class Jefe extends Thread {
  private Trabajo trabajo;

  public Jefe(Trabajo trabajo, String nombre) {
    this.trabajo = trabajo;
    setName(nombre);
  }

  @Override
  public void run() {
    int contador = 0;

    while (!trabajo.isObjetivo_alcanzado() && !trabajo.isQuebrado()) {
      trabajo.insertarTarea("Tarea" + contador++);
    }
    System.out.println(Thread.currentThread().getName() + " ha terminado");
  }
}
