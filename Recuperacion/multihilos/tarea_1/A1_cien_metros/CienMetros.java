package recu_clase.multihilos.A1_cien_metros;

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


    }
}
