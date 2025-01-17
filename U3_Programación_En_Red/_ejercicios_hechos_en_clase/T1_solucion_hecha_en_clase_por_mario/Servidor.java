package mm.U3.T1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Servidor {
    public static final int PUERTO = 5555;

    public static void main(String[] args) {
        int nIntentosMax = 0;
        boolean acertado = false;
        ArrayList<Jugador> listaJugadores = new ArrayList<>();//ALMACENO TODOS LOS JUGADORES QUE HAN ACCEDIDO

        Random rd = new Random();
        int numSecreto = rd.nextInt(0, 11);
        System.out.println("El numero secreto es: " + numSecreto);

        Socket clientSocket;
        ServerSocket server=null;



        while (nIntentosMax < 10 && !acertado) {
            try {
                server = new ServerSocket(PUERTO);
                System.out.println("Escuchando por el puerto: " + PUERTO);

                clientSocket = server.accept();

                //CREAMOS EL FLUJO DE SALIDA (MENSAJES)
                DataOutputStream flujoSalidaMensajes = new DataOutputStream(clientSocket.getOutputStream());

                //CREAR FLUJO DE SALIDA DE (OBJETOS)
                ObjectOutputStream flujoSalidaObjetos = new ObjectOutputStream(clientSocket.getOutputStream());

                //CREAR FLUJO DE ENTRADA DE MENSAJES DEL CLIENTE
                DataInputStream flujoEntradaMensajes = new DataInputStream(clientSocket.getInputStream());

                //1ºCOMPROBAR SI YA EXISTE EL JUGADOR
                String nombreJugador = flujoEntradaMensajes.readUTF();
                Jugador j = new Jugador(0, "");
                boolean existe = false;


                for (Jugador pjAux : listaJugadores) {
                    if (pjAux.getNombre().equals(nombreJugador)) {
                        j = pjAux;//EN EL CASO DE QUE SEA EL JUGADOR ASIGNO A MI J ESE OBJETO
                        existe = true;
                    }
                }
                if (!existe) {
                    j.setNombre(nombreJugador);
                    listaJugadores.add(j);
                }

                if (j.getnIntentos() < 3) {
                    flujoSalidaObjetos.writeObject(new Acreditacion("Acceso concedido", true));
                    nIntentosMax++;
                    //RECOJO EL NUMERO PASDO POR EL CLIENTE
                    String numero = flujoEntradaMensajes.readUTF();

                    if (String.valueOf(numSecreto).equals(numero)) {
                        System.out.println("El jugador ha acertado el numero");
                        flujoSalidaMensajes.writeUTF("HAS ACERTADO EL NUMERO: || NUMERO INTENTOS: " + nIntentosMax);
                        acertado = true;
                        clientSocket.close();

                    } else {
                        j.setnIntentos(j.getnIntentos() + 1);//EN EL CASO DE QUE FALLE LE SUMO UN INTENTO
                        if (numSecreto > Integer.valueOf(numero)) {
                            flujoSalidaMensajes.writeUTF("El numero secreto es mayor");
                        } else
                            flujoSalidaMensajes.writeUTF("El numero secreto es menor");
                    }
                } else {
                    flujoSalidaObjetos.writeObject(new Acreditacion("Acceso denegado", false));
                }
                clientSocket.close();
                server.close();
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
                try {
                    if(server !=null){
                        server.close();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

    }
}
