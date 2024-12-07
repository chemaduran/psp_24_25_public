package U2_Multihilo.ex4_tragabolas_fabrica_yeso.ej2_fabrica_yeso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Saco {
  private static int contador = 1;
  private int peso;
  private int codigo;
  private int lote;
  private String fecha;

  public Saco() {
    peso = (int) (Math.random() * 25 + 26);
    this.codigo = contador++;
    LocalDateTime ldt = LocalDateTime.now();
    System.out.println(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(ldt));
    fecha = ldt.toString();
  }

  public int getPeso() {
    return peso;
  }

  public void setPeso(int peso) {
    this.peso = peso;
  }

  public int getCodigo() {
    return codigo;
  }

  public int getLote() {
    return lote;
  }

  public void setLote(int lote) {
    this.lote = lote;
  }

  @Override
  public String toString() {
    return "Saco{"
        + "peso="
        + peso
        + ", codigo="
        + codigo
        + ", lote="
        + lote
        + ", fecha='"
        + fecha
        + '\''
        + '}';
  }
}
