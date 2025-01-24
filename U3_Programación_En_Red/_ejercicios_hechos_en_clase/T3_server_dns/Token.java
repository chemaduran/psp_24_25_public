package U3_Red.clase.tarea_3_server_dns;

import java.io.Serializable;

public class Token implements Serializable {
    private long codCliente;
    private String operacion;

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public long getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(long codCliente) {
        this.codCliente = codCliente;
    }

    public Token(long codCliente, String operacion) {
        this.codCliente = codCliente;
        this.operacion = operacion;
    }

    @Override
    public String toString() {
        return "Token{" +
                "Codigo=" + codCliente +
                "Operacion='" + operacion + '\'' +
                '}';
    }
}
