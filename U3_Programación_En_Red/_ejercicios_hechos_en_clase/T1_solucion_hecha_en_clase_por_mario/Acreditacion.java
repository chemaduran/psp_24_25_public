package mm.U3.T1;

import java.io.Serializable;

public class Acreditacion implements Serializable {
    private String mensaje;
    private boolean flag;

    public Acreditacion(String mensaje, boolean flag) {
        this.mensaje = mensaje;
        this.flag = flag;
    }

    @Override
    public String toString() {
        return  mensaje+" "+flag;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
