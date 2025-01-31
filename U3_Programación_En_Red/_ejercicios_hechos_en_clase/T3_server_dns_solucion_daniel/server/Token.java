package dbrusev.server;

import java.io.Serializable;

public class Token implements Serializable {

    private final long ID;
    private final String operacion;

    public String getOperacion() {
        return operacion;
    }

    public long getID() {
        return ID;
    }

    public Token(long id, String operacion) {
        this.ID = id;
        this.operacion = operacion;
    }

}
