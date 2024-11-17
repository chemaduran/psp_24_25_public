package U2_Multihilo.resolucion.gestor_trabajo;

import java.util.ArrayList;

public class Trabajo {
  private ArrayList<String> tareasPendientes = new ArrayList<>();
  private int limite_quiebra = 250;
  private int objetivo = 5000;
  private int pico_maximo = 0;
  private boolean quebrado = false;
  private boolean objetivo_alcanzado = false;
  private int tareas_realizadas = 0;

  public synchronized void insertarTarea(String tarea) {
    if (tareasPendientes.size() < limite_quiebra) {
      tareasPendientes.add(tarea);
      notifyAll();
      if (tareasPendientes.size() > pico_maximo) {
        pico_maximo = tareasPendientes.size();
      }
    } else {
      quebrado = true;
      System.out.println(
          "QUIEBRA: Se han superado las tareas pendientes. La empresa necesita contratar más trabajadores.");
    }
  }

  public synchronized void hacerTarea() {
    if (!tareasPendientes.isEmpty()) {
      String tarea = tareasPendientes.get(0);
      tareasPendientes.remove(0);
      System.out.println("Tarea " + tarea + " realizada por " + Thread.currentThread().getName());
      tareas_realizadas++;
      if (tareas_realizadas >= objetivo) {
        objetivo_alcanzado = true;
      }
      notifyAll();

    } else {
      try {
        wait();
      } catch (InterruptedException e) {
        System.err.println(e.getLocalizedMessage());
      }
    }
  }

  public boolean isQuebrado() {
    return quebrado;
  }

  public void setQuebrado(boolean quebrado) {
    this.quebrado = quebrado;
  }

  public boolean isObjetivo_alcanzado() {
    return objetivo_alcanzado;
  }

  public void setObjetivo_alcanzado(boolean objetivo_alcanzado) {
    this.objetivo_alcanzado = objetivo_alcanzado;
  }
}
