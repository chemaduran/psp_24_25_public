package U3_Red.clase.tarea_3_server_dns;


import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HiloMainDNS extends Thread{
    private Socket cliente = null;
    DataOutputStream flujoSalida = null;

    public HiloMainDNS(Socket cliente) {
        try {
            this.cliente = cliente;
            flujoSalida = new DataOutputStream(cliente.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try{
            Token token = null;
            ObjectInputStream flujoEntrada = new ObjectInputStream(cliente.getInputStream());
            token = (Token) flujoEntrada.readObject();
            System.out.println(token);

            if(token.isAdmind()){
                procesarAdministrador(token);
            } else if (token.isUser()) {

            }else{
                //Spyware
            }

        } catch (IOException  e) {
            System.out.println(e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void procesarAdministrador(Token token){
        if(token.isAdminREG()){

        }else if(token.isAdminDEL()){

        }

    }
}
