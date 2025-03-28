package recu_clase.multihilos.A2_cien_metros_mod_2;

import java.util.Random;

public class Atleta extends Thread {
    private int dorsal;
    private String nombre;
    private double tiempo;
    private boolean lesionado = false;

    public Atleta(int dorsal, String nombre) {
        this.dorsal = dorsal;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(nombre);
        Random random = new Random();

        if (random.nextInt(3)!=1){
            tiempo = 8 + (3.5 * random.nextDouble());
            try {
                Thread.sleep((long) (tiempo * 1000));
            } catch (InterruptedException e) {
                System.out.println(e.getLocalizedMessage());
            }
            System.out.println("El atleta " + nombre + " con dorsal " + dorsal + " ha finalizado la carrera con un tiempo de " + tiempo);
        }else{
            lesionado=true;
            try {
                Thread.sleep(2000);
                System.out.println(nombre + " grita: Ahhh!!! Me ha dado un tirón.");
            } catch (InterruptedException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }


    }

    public boolean isLesionado() {
        return lesionado;
    }

    public void setLesionado(boolean lesionado) {
        this.lesionado = lesionado;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }
}
