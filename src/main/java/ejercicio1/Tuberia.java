package ejercicio1;

import java.io.IOException;

public class Tuberia {
    ProcessBuilder pb1 = new ProcessBuilder("cmd","/c","tasklist");
    ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", "findstr", "exe");

    public Process proceso1;
    public Process proceso2;

    public void iniciarProceso1() {
        try {
            proceso1 = pb1.start();

        } catch (IOException e) {
            System.out.println("ERROR AL INICIAR EL PROCESO 1: " + e.getMessage());
        }
    }

    public void iniciarProceso2() {
        try {
            proceso2 = pb2.start();
        } catch (IOException e) {
            System.out.println("ERROR AL INICIAR EL PROCESO 2: " + e.getMessage());
        }
    }


}
