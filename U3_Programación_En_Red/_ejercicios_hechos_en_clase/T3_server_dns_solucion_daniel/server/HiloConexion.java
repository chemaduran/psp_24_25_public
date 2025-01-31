package U3_Red.clase.tarea_3_dns_daniel.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

class HiloConexion implements Runnable {

    private static GestorDominios gestorDominios;
    private final Socket conexion;
    private AtomicInteger intentosAtaque;

    public HiloConexion(Socket conexion, GestorDominios gd, AtomicInteger intentosAtaque) {
        this.conexion = conexion;
        gestorDominios = gd;
        this.intentosAtaque = intentosAtaque;
    }

    @Override
    public void run() {
        Token tokenConexion;

        try {
            /// Generación de flujos datos y aviso de conexión al cliente
            ObjectOutputStream flujoSalidaDatos = new ObjectOutputStream(conexion.getOutputStream());
            flujoSalidaDatos.writeUTF("- Conexión realizada correctamente -");
            flujoSalidaDatos.flush();

            ObjectInputStream flujoEntradaDatos = new ObjectInputStream(conexion.getInputStream());
            tokenConexion = (Token) flujoEntradaDatos.readObject();

            // LA CONEXIÓN ENTRANTE ES DEL USUARIO
            if (tokenConexion.getID() == 1111111111L) { //
                switch (tokenConexion.getOperacion().toUpperCase()) {
                    case "CON": // Conectarse a dominio
                        flujoSalidaDatos.writeUTF("¿A qué dominio se desea conectar? ");
                        flujoSalidaDatos.flush();

                        String respuestaConexion = gestorDominios.conectarADominio(flujoEntradaDatos.readUTF());
                        flujoSalidaDatos.writeUTF(respuestaConexion);
                        flujoSalidaDatos.flush();
                        break;
                    case "ALL": // Listar dominios
                        System.out.println(gestorDominios.getLISTA_DOMINIOS());
                        flujoSalidaDatos.writeObject(new InformeDominios(gestorDominios.getLISTA_DOMINIOS()));
                        flujoSalidaDatos.flush();
                        break;
                }
                // LA CONEXIÓN ENTRANTE ES DEL ADMINISTRADOR
            } else if (tokenConexion.getID() == 9999999999L) {
                switch (tokenConexion.getOperacion().toUpperCase()) {
                    case "REG": // Registrar dominio
                        flujoSalidaDatos.writeUTF("Introduzca el nombre del dominio a registrar: ");
                        flujoSalidaDatos.flush();

                        String ipDeDominio = gestorDominios.registrarDominio(flujoEntradaDatos.readUTF());
                        if (ipDeDominio != null)
                            flujoSalidaDatos.writeUTF("¡Dominio registrado! La IP es: " + ipDeDominio);
                        else
                            flujoSalidaDatos.writeUTF("Dominio no registrado: Falta de espacio o nombre duplicado");
                        flujoSalidaDatos.flush();
                        break;
                    case "DEL": // Borrar dominio
                        flujoSalidaDatos.writeUTF("¿Qué dominio desea eliminar? ");
                        flujoSalidaDatos.flush();
                        boolean dominioEliminado = gestorDominios.eliminarDominio(flujoEntradaDatos.readUTF());

                        if (dominioEliminado)
                            flujoSalidaDatos.writeUTF("¡Dominio eliminado con éxito!");
                        else
                            flujoSalidaDatos.writeUTF("Dominio no eliminado: no existian dominios con ese nombre");
                        flujoSalidaDatos.flush();
                        break;
                }
                // LA CONEXIÓN ENTRANTE ES FALSIFICADA (SPYWARECLIENT)
            } else {
                flujoSalidaDatos.writeUTF("Usted no es un cliente autorizado para el uso del servidor.");
                flujoSalidaDatos.flush();
                intentosAtaque.incrementAndGet();
            }

            /// Cierre de flujo de datos
            flujoEntradaDatos.close();
            flujoSalidaDatos.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error de clase recibida");
        }
    }
}
