package U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej2_fabrica_yeso;

// Genero esta clase por comodidad. Podría haberse hecho sin esta clase igualmente

public class Lote {
  private double pesoTotal;
  private double pesoMedio;
  private double pesoMax;
  private double pesoMin;
  private int numSacos;
  private int lote;

  public Lote(
      int lote, int numSacos, double pesoTotal, double pesoMedio, double pesoMax, double pesoMin) {
    this.lote = lote;
    this.numSacos = numSacos;
    this.pesoTotal = pesoTotal;
    this.pesoMedio = pesoMedio;
    this.pesoMax = pesoMax;
    this.pesoMin = pesoMin;
  }

  @Override
  public String toString() {
    return "Lote "
        + lote
        + " {pesoTotal="
        + pesoTotal
        + ", pesoMedio="
        + pesoMedio
        + ", pesoMax="
        + pesoMax
        + ", pesoMin="
        + pesoMin
        + ", numSacos="
        + numSacos
        + '}';
  }
}
