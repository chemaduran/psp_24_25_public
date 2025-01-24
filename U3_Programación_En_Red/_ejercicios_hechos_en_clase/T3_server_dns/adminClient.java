package U3_Red.clase.tarea_3_server_dns;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class adminClient {

    private static final long code = 9999999999L;
    private static final String operacionREG = "REG";
    private static final String operacionDEL = "DEL";
    private static final int PUERTO = 5555;
    private static final String SERVER = "localhost";


    public static void main(String[] args) {
        Token token = null;
        Socket socket = new Socket();
        Scanner sc = new Scanner(System.in);

        System.out.println("¿Qué operación quieres realizar?");
        String operacionIntr = sc.nextLine();

        if(operacionIntr.equals(operacionREG)){
            token = new Token(code, operacionREG);
        }else if(operacionIntr.equals(operacionDEL)){
            token = new Token(code, operacionDEL);
        }else{
            System.err.println("Operación no valida.");
        }

        sc.close();
        InetSocketAddress addr = new InetSocketAddress(SERVER, PUERTO);
        try {
            socket.connect(addr);
            ObjectOutputStream flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            flujoSalida.writeObject(token);
            flujoSalida.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
