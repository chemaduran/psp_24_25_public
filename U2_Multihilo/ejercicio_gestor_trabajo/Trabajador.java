package U2_Multihilo.T3;

public class Trabajador extends Thread {

  Trabajo trabajo;

  public Trabajador(Trabajo trabajo, String nombre) {
    this.trabajo = trabajo;
    this.setName(nombre);
  }

  public void run() {
    int i = 0;
    while (!trabajo.quiebra && !trabajo.objetivoCumplido) {
      trabajo.realizarTarea();
      i++;
    }
    System.out.println(
        Thread.currentThread().getName()
            + " ha terminado su trabajo con un total de "
            + i
            + " tareas realizadas");
  }
}
