package U3_Red.tareas.en_24_25_conecta_4;

import java.io.Serializable;

public class ClientID implements Serializable {
  private static long contadorClientes = 0;
  private Ficha colorFicha;
  private long idCliente;
  private int idPartida;

  public ClientID() {
    contadorClientes++;
    idCliente = contadorClientes;
  }

  public int getIdPartida() {
    return idPartida;
  }

  public void setIdPartida(int idPartida) {
    this.idPartida = idPartida;
  }

  public long getIdCliente() {
    return idCliente;
  }

  public void setIdCliente(int idCliente) {
    this.idCliente = idCliente;
  }

  public Ficha getColorFicha() {
    return colorFicha;
  }

  public void setColorFicha(Ficha colorFicha) {
    this.colorFicha = colorFicha;
  }

  public enum Ficha {
    ROJO,
    VERDE
  }
}
