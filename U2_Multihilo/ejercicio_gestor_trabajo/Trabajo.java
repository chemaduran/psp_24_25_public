package U2_Multihilo.T3;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Trabajo {

  public static int trabajoSoportado;
  public static int objetivo;
  public int trabajoRealizado = 0;
  public int picoMaximoTrabajoAcumulado = 0;
  public boolean quiebra = false;
  public boolean objetivoCumplido = false;
  List<String> tareas = new ArrayList<>();

  public Trabajo(int objetivo, int maximoSoportado) {
    trabajoSoportado = maximoSoportado;
    Trabajo.objetivo = objetivo;
  }

  public synchronized void asignarTarea(String t) {
    if (tareas.size() < trabajoSoportado) {
      tareas.add(t);
      System.out.println(
          Thread.currentThread().getName()
              + " ha anadido la tarea "
              + t
              + " Hay un total de "
              + tareas.size()
              + " pendientes");
      notify();
      if (tareas.size() > picoMaximoTrabajoAcumulado) {
        picoMaximoTrabajoAcumulado = tareas.size();
      }
    } else {
      quiebra = true;
      // System.out.println(Thread.currentThread().getName() + " ha añadido la tarea " + t + " Se
      // han s " + tareas.size() + " pendientes" );
      System.out.println(
          "QUIEBRA: Se han superado las "
              + trabajoSoportado
              + " tareas pendientes la empresa necesita contratar mas trabajadores");
    }
  }

  public synchronized void realizarTarea() {

    if (!tareas.isEmpty()) {
      String t = tareas.get(0);
      tareas.remove(0);
      trabajoRealizado++;
      System.out.println(
          Thread.currentThread().getName()
              + " ha realizado la tarea "
              + t
              + " Quedan ahora "
              + tareas.size()
              + " pendientes");
      if (this.trabajoRealizado >= objetivo) {
        this.objetivoCumplido = true;
        System.out.println(
            "OBJETIVO CUMPLIDO: La empresa ha realizado un total de "
                + this.trabajoRealizado
                + " tareas");
      }
    } else {
      // System.out.println(Thread.currentThread().getName() + " ha añadido la tarea " + t + " Se
      // han s " + tareas.size() + " pendientes" );
      // System.out.println("SITUACIÓN IDEAL: No hay tareas pendientes de realizar espero a que haya
      // trabajo");
      try {
        wait();
      } catch (Exception ex) {
        Logger.getLogger(Trabajo.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
