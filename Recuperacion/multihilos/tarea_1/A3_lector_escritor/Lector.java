package recu_clase.multihilos.A3_lector_escritor;

public class Lector extends Thread{
    private Buzon buzon;
    private String nombre;

    public Lector(Buzon buzon, String nombre) {
        this.buzon = buzon;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(nombre);
        while (true){
            String mensaje = buzon.leer();
            System.out.println("Soy el lector " + nombre + " leo el mensaje " + mensaje);
        }

    }
}
