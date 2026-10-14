package ejercicio1;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        Tuberia padre = new Tuberia();
        padre.iniciarProceso1();
        padre.iniciarProceso2();

        //extraemos las líneas de tasklist del proceso 1 con un getInputStream;
        // creamos la línea de acción para recibir del hijo 1 la lista de stream de tasklist y guardarlo momentáneamente en un buffer
        InputStream inputProceso1 = padre.proceso1.getInputStream();
        InputStreamReader reader1 = new InputStreamReader(inputProceso1);
        BufferedReader bufferLector1 = new BufferedReader(reader1);

        //creamos la línea de acción para mandar de string en string almacenados en el buffer al hijo 2
        OutputStream outputProceso2 = padre.proceso2.getOutputStream();
        OutputStreamWriter writer2 = new OutputStreamWriter(outputProceso2);
        BufferedWriter bufferEscritor2 = new BufferedWriter(writer2);


        String lineaDeTexto;

        // mandamos las líneas de tasklist al getOutputStream de proceso 2 para que las filtre los '.txt'
        // hacemos el bucle para procesar los inputs y enviarlos en forma de outputs
        try {
            while ((lineaDeTexto = bufferLector1.readLine()) != null) {
                bufferEscritor2.write(lineaDeTexto);
                bufferEscritor2.newLine();
            }
        } catch (IOException e) {
            System.out.println("ERROR EL BUFFER: " + e.getMessage());
        }
        //cerramos el proceso de lectura
        try {
            bufferLector1.close();
        } catch (IOException e) {
            System.out.println("ERROR AL CERRAR EL BUFFER DE LECTURA DEL PROCESO TASKLIST: " + e.getMessage());
        }
        //cerramos el proceso de escritura
        try {
            bufferEscritor2.close();
        } catch (IOException e) {
            System.out.println("ERROR AL CERRAR EL BUFFER DE ESCRITURA DEL PROCESO FINDSTR: " + e.getMessage());
        }

        // Reciclamos el getInputStream y el bucle para recoger la salida de proceso findstr y mandarla por pantalla
        InputStream inputProceso2 = padre.proceso2.getInputStream();
        InputStreamReader reader2 = new InputStreamReader(inputProceso2);
        BufferedReader bufferLector2 = new BufferedReader(reader2);

        try {
            while ((lineaDeTexto = bufferLector2.readLine()) != null) {
                System.out.println(lineaDeTexto);
            }
        } catch (IOException e) {
            System.out.println("ERROR AL OBTENER LA SALIDA DEL PROCESO FINDSTR: " + e.getMessage());
        }

        try {
            bufferLector2.close();
        } catch (IOException e) {
            System.out.println("ERROR AL CERRAR EL BUFFER DE ESCRITURA DEL PROCESO FINDSTR: " + e.getMessage());
        }


        try {
            int codigo = padre.proceso2.waitFor();   // Java obliga a capturar InterruptedException
            if ((codigo == 0) || (codigo == 1)) {
                System.out.println("Finalizada la ejecución");//Ejecución tiene tilde asi que no se reconoce el carácter en UTF-8
            } else {
                System.out.println("EL PROCESO HA TERMINADO CON CÓDIGO DE ERROR: " + codigo);
            }
        } catch (InterruptedException e) {
            System.out.println("ERROR DURANTE EL PROCESO FINDSTR: " + e.getMessage());
        }

    }
}
