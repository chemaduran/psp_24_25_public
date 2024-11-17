package U2_Multihilo._02b_ProductorConsumidorSincronizado;

public class PCSincronizado {
  public static void main(String[] args) {
    BufferSincronizado buffer = new BufferSincronizado(5);

    Thread productor = new Thread(new ProductorSeguro(buffer));
    Thread consumidor = new Thread(new ConsumidorSeguro(buffer));

    productor.start();
    consumidor.start();
  }
}
