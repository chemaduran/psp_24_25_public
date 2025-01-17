package mm.U3.T1;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private static final int PUERTO = 5555;
    private static final String SERVER = "localhost";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            Socket clientSocket = new Socket();
            InetSocketAddress addr = new InetSocketAddress(SERVER,PUERTO);
            clientSocket.connect(addr);

            //CREAR FLUJO DE LECTURA DE MENSAJES
            DataInputStream flujoEntradaMensajes = new DataInputStream(clientSocket.getInputStream());

            //CREAR FLUJO DE LECTURA DE OBJETOS
            ObjectInputStream flujoEntradaObjetos = new ObjectInputStream(clientSocket.getInputStream());

            //CREAR FLUJO SALIDA MENSAJES
            DataOutputStream flujoSalida = new DataOutputStream(clientSocket.getOutputStream());

            System.out.println("Introduce tu nombre:");
            flujoSalida.writeUTF(sc.nextLine());

            Acreditacion acreditacion = (Acreditacion) flujoEntradaObjetos.readObject();

            if(acreditacion.isFlag()){
                System.out.println(acreditacion);
                System.out.println("Introduce el numero secreto:");
                flujoSalida.writeUTF(sc.nextLine());
                //LEO SI SE HA ACERTADO El NUMERO
                System.out.println(flujoEntradaMensajes.readUTF());
            }
            else {
                System.out.println(acreditacion);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
