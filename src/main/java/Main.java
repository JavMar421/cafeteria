import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    static int sillas=0;
    static int cafe=0;
    static int maxsillas=10;
    static int maxcafe=4;

    static Semaphore semasillas = new Semaphore(0);
    static Semaphore semacafe = new Semaphore(0);
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            Hilos hilo = new Hilos();
            hilo.setName("Huesped "+ i );
            hilo.start();
        }
    }
}

class Hilos extends Thread {
    public Hilos() {

    }
    @Override
    public void run() {
    Cafeteria.Sentarse(this);
    }
}
class Cafeteria{
    public static void Sentarse(Hilos hilo){
        Main.sillas++;
        try {
            if (Main.sillas == Main.maxsillas)
                Main.semasillas.release(Main.maxsillas);
            Main.semasillas.acquire();
            System.out.println("El "+ hilo.getName()+" se ha sentado");
        } catch (InterruptedException e) {
            e.printStackTrace();}
            Cafeteria.Cafetera(hilo);
        }

    public static void Cafetera(Hilos hilo){
        Main.cafe++;
        try {
            if (Main.cafe == Main.maxcafe)
                Main.semacafe.release(Main.maxcafe);
            Main.semacafe.acquire();
            System.out.println("El "+ hilo.getName()+" va a por Café");
            Thread.sleep((new Random().nextInt(3)+2) * 1000);
            Main.semacafe.release();
        } catch (InterruptedException e) {
            e.printStackTrace();}

            Cafeteria.Cafe(hilo);
        }
    public static void Cafe(Hilos hilo){
        System.out.println("El "+ hilo.getName()+" se sienta a tomar Cafe");
        try {
            Thread.sleep((new Random().nextInt(2)+2) * 1000);}
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("El "+ hilo.getName()+" se marcha");
        Main.semasillas.release();
    }
}