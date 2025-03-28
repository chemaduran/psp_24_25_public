package recu_clase.multihilos.A3_lector_escritor;

import java.util.ArrayList;
import java.util.List;

public class Buzon {
    private List<String> mensajes = new ArrayList<>();


    public synchronized void escribir (String msg){
        mensajes.add(msg);
        notify();
    }

    public synchronized String leer(){
       while (mensajes.isEmpty()){
           try {
               wait();
           }catch (Exception e){
               System.out.println(e.getLocalizedMessage());
           }

       }
       String mensaje = mensajes.get(0);
       mensajes.remove(mensaje);
        return mensaje;
    }
}
