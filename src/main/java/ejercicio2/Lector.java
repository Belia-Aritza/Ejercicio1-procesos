package ejercicio2;

import org.iesalandalus.programacion.utilidades.Entrada;

import java.io.*;

public class Lector {
    public static void main(String[] args) {

        // Pedimos el número por teclado
        // NOTA (librería Entrada, no está en el PDF): entero() lee por teclado, convierte el texto a int
        // y, si el usuario se equivoca, vuelve a pedirlo.
        // (Los textos que se imprimen van sin tilde porque la consola no reconoce el carácter en UTF-8)
        System.out.print("Introduce un numero: ");
        int numero = Entrada.entero();

        Escritor escritor = new Escritor();
        escritor.iniciarProceso();
        if (escritor.proceso == null) {
            return;   // si no se pudo crear el proceso no tiene sentido seguir
        }

        // Creamos la línea de acción para mandar el número al proceso (getOutputStream, PDF pág. 11)
        OutputStream outputProceso = escritor.proceso.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputProceso);
        BufferedWriter bufferEscritor = new BufferedWriter(writer);

        try {
            // NOTA (no está en el PDF): la tubería solo transporta texto, así que convertimos el int
            // a String con String.valueOf. newLine() añade el fin de línea que el proceso espera.
            bufferEscritor.write(String.valueOf(numero));
            bufferEscritor.newLine();
        } catch (IOException e) {
            System.out.println("ERROR AL ESCRIBIR EL NUMERO: " + e.getMessage());
        }

        //cerramos el proceso de escritura (close() hace el flush y cierra la entrada del proceso)
        try {
            bufferEscritor.close();
        } catch (IOException e) {
            System.out.println("ERROR AL CERRAR EL BUFFER DE ESCRITURA DEL PROCESO ESCRITOR: " + e.getMessage());
        }

        // recogemos la salida del proceso (los 10 números) con getInputStream y la mandamos por pantalla
        InputStream inputProceso = escritor.proceso.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputProceso);
        BufferedReader bufferLector = new BufferedReader(reader);

        String lineaDeTexto;

        try {
            while ((lineaDeTexto = bufferLector.readLine()) != null) {
                System.out.println(lineaDeTexto);
            }
        } catch (IOException e) {
            System.out.println("ERROR AL OBTENER LA SALIDA DEL PROCESO ESCRITOR: " + e.getMessage());
        }

        try {
            bufferLector.close();
        } catch (IOException e) {
            System.out.println("ERROR AL CERRAR EL BUFFER DE LECTURA DEL PROCESO ESCRITOR: " + e.getMessage());
        }

        // Recogemos los posibles errores del proceso (getErrorStream, PDF pág. 11 y 14),
        // después de la salida normal para evitar un bloqueo
        InputStream errorProceso = escritor.proceso.getErrorStream();
        InputStreamReader readerError = new InputStreamReader(errorProceso);
        BufferedReader bufferError = new BufferedReader(readerError);

        try {
            while ((lineaDeTexto = bufferError.readLine()) != null) {
                System.out.println("ERROR DEL PROCESO ESCRITOR: " + lineaDeTexto);
            }
            bufferError.close();
        } catch (IOException e) {
            System.out.println("ERROR AL LEER LOS ERRORES DEL PROCESO ESCRITOR: " + e.getMessage());
        }

        try {
            int codigo = escritor.proceso.waitFor();   // Java obliga a capturar InterruptedException
            if (codigo == 0) {
                System.out.println("Finalizada la ejecucion");
            } else {
                System.out.println("EL PROCESO HA TERMINADO CON CODIGO DE ERROR: " + codigo);
            }
        } catch (InterruptedException e) {
            System.out.println("ERROR DURANTE EL PROCESO ESCRITOR: " + e.getMessage());
        }
    }
}
