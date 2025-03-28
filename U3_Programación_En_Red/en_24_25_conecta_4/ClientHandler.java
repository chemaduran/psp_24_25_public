package U3_Red.tareas.en_24_25_conecta_4;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Partida partida;
    private ClientID.Ficha ficha;
    private ClientHandler oponente;
    private ObjectInputStream entradaObj;
    private ObjectOutputStream salidaObj;
    private DataInputStream entradaData;
    private DataOutputStream salidaData;
    private ClientID cliente;

    public ClientHandler(
            Socket socket, Partida partida, ClientID.Ficha ficha, ClientHandler oponente) {
        this.socket = socket;
        this.partida = partida;
        this.ficha = ficha;
        this.oponente = oponente;
        try {
            this.entradaObj = new ObjectInputStream(socket.getInputStream());
            this.salidaObj = new ObjectOutputStream(socket.getOutputStream());
            this.entradaData = new DataInputStream(socket.getInputStream());
            this.salidaData = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(
                "Cliente conectado. Partida: " + partida.getID() + ". Color: " + gCol(ficha));

        try {
            cliente = (ClientID) entradaObj.readObject();
            cliente.setColorFicha(ficha);
            cliente.setIdPartida(partida.getID());
            // El cliente recibe el color de ficha asignado y el ID de la partida.
            salidaObj.writeObject(cliente);
            salidaObj.flush();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void setOponente(ClientHandler oponente) {
        this.oponente = oponente;
    }

    public void p_colorMsg(ClientID.Ficha c, String msg) {
        System.out.println("[" + gCol(c) + "] " + msg);
    }

    public String gCol(ClientID.Ficha c) {
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";

        return (c == ClientID.Ficha.ROJO) ? RED + "ROJO (X)" + RESET : GREEN + "VERDE (0)" + RESET;
    }

    @Override
    public void run() {
        try {
            // Se le envía el estado de la partida al cliente
            salidaObj.writeObject(partida);
            salidaObj.flush();

            while (!partida.isPartidaTerminada()) {
                // Leemos la columna que envía el cliente (si toca).
//                if (entradaData.available() == 0) continue;
                System.out.println("Esperando movimiento del cliente...");
                int columna = entradaData.readInt();
                if (partida.realizarMovimiento(columna, ficha)) {
                    // Se envía el estado de la partida.
                    if (partida.isPartidaTerminada()) {
                        p_colorMsg(this.ficha, "Partida terminada.");
                        p_colorMsg(this.ficha, "Ha ganado: " + gCol(partida.getGanador()));
                        avisarCliente();
                        oponente.avisarCliente();
                        continue;
                    }
                    p_colorMsg(this.ficha, "Movimiento válido. Enviando estado de la partida.");
                    p_colorMsg(this.ficha, "Es el turno de: " + gCol(partida.getTurnoActual()));

                    avisarCliente();
                    oponente.avisarCliente();
                } else {
                    // Si el movimiento no es válido, se le indica al cliente que debe intentar de nuevo.
                    p_colorMsg(this.ficha, "Movimiento no válido o partida terminada. Intenta de nuevo.");
                }
            }

            System.out.println("Partida terminada.");

        } catch (IOException e) {
            System.err.println("Error al leer el objeto del cliente:" + e.getLocalizedMessage());
        }
    }

    public void avisarCliente() {
        try {
            salidaObj.reset(); // Borra la caché de salidaObj y envía el obj con los datos cambiados
            salidaObj.writeObject(partida);
            salidaObj.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
