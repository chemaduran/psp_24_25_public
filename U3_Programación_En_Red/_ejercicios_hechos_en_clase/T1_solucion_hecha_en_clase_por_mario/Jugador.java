package mm.U3.T1;

import java.io.Serial;
import java.io.Serializable;

public class Jugador implements Serializable {
    private String nombre;
    private int nIntentos;

    public Jugador(int nIntentos, String nombre) {
        this.nIntentos = nIntentos;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getnIntentos() {
        return nIntentos;
    }

    public void setnIntentos(int nIntentos) {
        this.nIntentos = nIntentos;
    }
}
