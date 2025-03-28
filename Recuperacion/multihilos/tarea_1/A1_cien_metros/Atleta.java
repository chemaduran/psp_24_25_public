package recu_clase.multihilos.A1_cien_metros;

import java.util.Random;

public class Atleta extends Thread {
    private int dorsal;
    private String nombre;
    private double tiempo;

    public Atleta(int dorsal, String nombre) {
        this.dorsal = dorsal;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(nombre);
        Random random = new Random();
        tiempo = 8 + (3.5 * random.nextDouble());
        try {
            Thread.sleep((long) (tiempo * 1000));
        } catch (InterruptedException e) {
            System.out.println(e.getLocalizedMessage());
        }
        System.out.println("El atleta " + nombre + " con dorsal " + dorsal + " ha finalizado la carrera con un tiempo de " + tiempo);

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
