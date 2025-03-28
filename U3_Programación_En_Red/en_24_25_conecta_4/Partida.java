package U3_Red.tareas.en_24_25_conecta_4;

import java.io.Serializable;
import java.util.Arrays;

public class Partida implements Serializable {
  private Character[][] tablero;
  private ClientID.Ficha turnoActual;
  private boolean partidaTerminada;
  private int idPartida;
  private ClientID.Ficha ganador = null;

  public Partida(int idPartida) {
    this.idPartida = idPartida;
    this.tablero = new Character[6][7];
    for (Character[] fila : tablero) {
      Arrays.fill(fila, '.');
    }
    this.turnoActual = ClientID.Ficha.ROJO;
    this.partidaTerminada = false;
  }

  public ClientID.Ficha getGanador() {
    return ganador;
  }

  public void setGanador(ClientID.Ficha ganador) {
    this.ganador = ganador;
  }

  public int getIdPartida() {
    return idPartida;
  }

  public void setIdPartida(int idPartida) {
    this.idPartida = idPartida;
  }

  public Character[][] getTablero() {
    return tablero;
  }

  public void setTablero(Character[][] tablero) {
    this.tablero = tablero;
  }

  public boolean isPartidaTerminada() {
    return partidaTerminada;
  }

  public void setPartidaTerminada(boolean partidaTerminada) {
    this.partidaTerminada = partidaTerminada;
  }

  public ClientID.Ficha getTurnoActual() {
    return turnoActual;
  }

  public void setTurnoActual(ClientID.Ficha turnoActual) {
    this.turnoActual = turnoActual;
  }

  public void cambiarTurno() {
    if (turnoActual == ClientID.Ficha.ROJO) {
      turnoActual = ClientID.Ficha.VERDE;
    } else {
      turnoActual = ClientID.Ficha.ROJO;
    }
  }

  public int getID() {
    return idPartida;
  }

  public synchronized boolean realizarMovimiento(int columna, ClientID.Ficha fichaRecibida) {
    char ficha = (fichaRecibida == ClientID.Ficha.ROJO) ? 'X' : '0';
    if (partidaTerminada || columna < 0 || columna >= 7) return false;
    for (int fila = 5; fila >= 0; fila--) {
      if (tablero[fila][columna] == '.') {
        tablero[fila][columna] = ficha;
        if (verificarVictoria(fila, columna, ficha)) {
          setPartidaTerminada(true);
          setGanador(turnoActual);
        }
        cambiarTurno();
        return true;
      }
    }
    return false;
  }

  public String getColorCelda(char c) {
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String RESET = "\u001B[0m";

    if (c == '.') return ".";

    return (c == 'X') ? RED + "X" + RESET : GREEN + "0" + RESET;
  }

  public void imprimirTablero() {
    for (Character[] fila : tablero) {
      for (char celda : fila) {
        System.out.print(getColorCelda(celda) + " ");
      }
      System.out.println();
    }
  }

  private boolean verificarVictoria(int fila, int col, char ficha) {
    return (contarEnDireccion(fila, col, 1, 0, ficha) + contarEnDireccion(fila, col, -1, 0, ficha)
            >= 3
        || contarEnDireccion(fila, col, 0, 1, ficha) + contarEnDireccion(fila, col, 0, -1, ficha)
            >= 3
        || contarEnDireccion(fila, col, 1, 1, ficha) + contarEnDireccion(fila, col, -1, -1, ficha)
            >= 3
        || contarEnDireccion(fila, col, 1, -1, ficha) + contarEnDireccion(fila, col, -1, 1, ficha)
            >= 3);
  }

  private int contarEnDireccion(int fila, int col, int df, int dc, char ficha) {
    int contador = 0;
    for (int i = 1; i < 4; i++) {
      int nf = fila + df * i, nc = col + dc * i;
      if (nf < 0 || nf >= 6 || nc < 0 || nc >= 7 || tablero[nf][nc] != ficha) break;
      contador++;
    }
    return contador;
  }
}
