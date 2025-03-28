package recu_clase.multihilos.A3_lector_escritor;

public class MailBox {
    public static void main(String[] args) {

        Buzon buzon = new Buzon();
        Lector lector = new Lector(buzon, "Lector 1");
        Lector lector1 = new Lector(buzon, "Lector 2");
        Escritor escritor = new Escritor(buzon, "Escritor 1");
        Escritor escritor1 = new Escritor(buzon, "Escritor 2");
        Escritor escritor2 = new Escritor(buzon, "Escritor 3");
        Escritor escritor3 = new Escritor(buzon, "Escritor 4");

        lector.setPriority(Thread.MAX_PRIORITY);
        lector.start();
        lector1.setPriority(Thread.MIN_PRIORITY);
        lector1.start();
        escritor.start();
        escritor1.start();
        escritor2.start();
        escritor3.start();



    }
}
