package ejercicio2;

import java.io.IOException;

public class Escritor {

    // NOTA (no está en el PDF): el proceso hijo tiene que ser un programa que lea un número de su
    // entrada estándar y escriba los 10 siguientes por su salida estándar. Como esta clase solo crea
    // el proceso (igual que Tuberia en el ejercicio 1), usamos un comando del sistema: PowerShell,
    // que viene con Windows. El script es UN solo argumento (PDF pág. 8 y 9):
    //   [Console]::In.ReadLine()           -> lee una línea de la entrada estándar
    //   [int]                              -> la convierte en número entero
    //   1..10 | ForEach-Object { $n + $_ } -> para cada i de 1 a 10 escribe número + i
    // El PDF explica cómo lanzar un comando con ProcessBuilder, pero no el contenido de este.
    ProcessBuilder pb = new ProcessBuilder("powershell", "-NoProfile", "-Command",
            "$n = [int][Console]::In.ReadLine(); 1..10 | ForEach-Object { $n + $_ }");

    public Process proceso;

    public void iniciarProceso() {
        try {
            proceso = pb.start();   // PDF pág. 8: start() puede lanzar IOException
        } catch (IOException e) {
            System.out.println("ERROR AL INICIAR EL PROCESO ESCRITOR: " + e.getMessage());
        }
    }
}