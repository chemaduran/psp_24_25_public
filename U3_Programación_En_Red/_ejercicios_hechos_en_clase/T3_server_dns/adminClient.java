package U3_Red.clase.tarea_3_server_dns;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class adminClient {

    private static final long code=9999999999L;
    private static final String operacion="REG";
    private static final int PUERTO = 5555;
    private static final String SERVER = "localhost";




    public static void main(String[] args) {
        Socket socket = new Socket();
        Token token = new Token(code, operacion);
        InetSocketAddress addr = new InetSocketAddress(SERVER, PUERTO);
        try {
            socket.connect(addr);
            ObjectOutputStream flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            flujoSalida.writeObject(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
