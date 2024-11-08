package U2_Multihilo._01_Prueba_HIlos;

public class MiHiloThread extends Thread {
  @Override
  public void run() {
    int i = 0;
    while (i < 5) {
      System.out.println(
          "Soy el hilo " + Thread.currentThread().getName() + " y estoy en la vuelta " + i);
      i++;
    }
    /*for (int i = 0; i < 500; i++) {
           System.out.println("Soy un hilo y esto es lo que hago");
    }*/
  }
}
