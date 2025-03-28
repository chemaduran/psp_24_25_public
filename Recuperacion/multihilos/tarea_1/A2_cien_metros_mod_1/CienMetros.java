package recu_clase.multihilos.A2_cien_metros_mod_1;

public class CienMetros {
    public static void main(String[] args) {
        Thread.currentThread().setName("Carrera Legendaria");

        Atleta[] atletas = new Atleta[5];
        atletas[0]= new Atleta(1, "Usain Bolt");
        atletas[1]= new Atleta(2, "Tyson Gay");
        atletas[2]= new Atleta(3, "Carl Lewis");
        atletas[3]= new Atleta(4, "Asafa Powell");
        atletas[4]= new Atleta(5, "Justin Gutlin");

        System.out.println("Inicio de carrera de 100m de nombre" + Thread.currentThread().getName());
        try {
            System.out.println("Preparados...");
            Thread.sleep(1000);
            System.out.println("Listos?");
            Thread.sleep(1000);
            System.out.println("YA!!");
        } catch (InterruptedException e) {
            System.out.println(e.getLocalizedMessage());
        }
        for (Atleta atleta : atletas){
            atleta.start();
        }
        for (Atleta atleta : atletas){
            try {
                atleta.join();
            } catch (InterruptedException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

        Atleta ganador = fotoFinish(atletas);
        System.out.println("\nEl ganador de la carrera es " + ganador.getNombre() + " con un tiempo de " + ganador.getTiempo());
        System.out.println("Fin de la carrera de 100 metros de nombre " + Thread.currentThread().getName() + ". Gracias por asistir.");
    }

    private static Atleta fotoFinish(Atleta[] atletas){
        Atleta ganador = atletas[0];
        for (Atleta atleta : atletas){
            if (atleta.getTiempo()<ganador.getTiempo()){
                ganador = atleta;
            }
        }
        return ganador;
    }
}
