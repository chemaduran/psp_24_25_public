package U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej2_fabrica_yeso;

public class App {
  public static void main(String[] args) {
    FabricaYeso fabrica = FabricaYeso.nuevaInstancia();
    fabrica.fabricarSacos();
  }
}
