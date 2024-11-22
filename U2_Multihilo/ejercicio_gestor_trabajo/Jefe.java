package U2_Multihilo.T3;

import java.util.concurrent.atomic.AtomicInteger;

public class Jefe extends Thread {
    static AtomicInteger i = new AtomicInteger(0);
    Trabajo trabajo;

    public Jefe(Trabajo trabajo, String nombre) {
        this.trabajo = trabajo;
        this.setName(nombre);
    }

    public void run() {
        while (!trabajo.quiebra && !trabajo.objetivoCumplido) {
            trabajo.asignarTarea("Tarea_" + i.incrementAndGet() + " de " + getName());
        }

        System.out.println(
                getName()
                        + " Terminado. Tareas realizadas: "
                        + trabajo.trabajoRealizado
                        + "; Tareas pendientes "
                        + trabajo.tareas.size()
                        + "; Máximo pico de trabajo acumulado: "
                        + trabajo.picoMaximoTrabajoAcumulado);
    }
}
