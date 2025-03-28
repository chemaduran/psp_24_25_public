package recu_clase.multihilos.A3_lector_escritor;

public class Escritor extends Thread{
    private Buzon buzon;
    private String nombre;



    public Escritor(Buzon buzon, String nombre) {
        this.buzon = buzon;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(nombre);
        int contador = 0;
        while (true){
            System.out.println("Soy el escritor " + nombre + " Te envio el mensaje " + " msg " + contador );
            buzon.escribir("msg " + contador );
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println(e.getLocalizedMessage());
            }
            contador++;
        }

    }
}
