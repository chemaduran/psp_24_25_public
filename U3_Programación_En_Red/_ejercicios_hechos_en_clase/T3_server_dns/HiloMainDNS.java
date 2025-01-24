package U3_Red.clase.tarea_3_server_dns;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HiloMainDNS extends Thread{
    private Socket cliente = null;

    public HiloMainDNS(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try{
            Token token = null;
            ObjectInputStream flujoEntrada = new ObjectInputStream(cliente.getInputStream());
            token = (Token) flujoEntrada.readObject();
            System.out.println(token);

        } catch (IOException  e) {
            System.out.println(e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
