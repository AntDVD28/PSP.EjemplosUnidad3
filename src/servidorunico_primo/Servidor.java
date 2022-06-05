
package servidorunico_primo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Servidor único
 * @author David Jiménez Riscardo
 */
public class Servidor {
    public static void main(String[] args){
        
        ServerSocket servidor = null;
        Socket clienteConectado = null;
        InputStream entrada = null;
        DataInputStream flujoEntrada = null;
        OutputStream salida = null;
        DataOutputStream flujoSalida = null;
        //Cadena recibida del cliente
        String peticionCliente = null;
        
        System.out.println("SERVIDOR NO CONCURRENTE DE NUMEROS PRIMOS");
        System.out.println("=========================================");
        
        //Gestión del puerto
        int puerto = 6000;       
        System.out.println("Servidor del alumno David Jiménez Riscardo iniciado en el puerto 6000");

  
        try { 
            //Instanciamos el servidor
            servidor = new ServerSocket(puerto);

            //Aceptamos la petición de conexión del cliente
            System.out.println("Esperando al cliente...");
            clienteConectado = servidor.accept();

            //Creo flujo de entrada del cliente
            entrada = clienteConectado.getInputStream();
            flujoEntrada = new DataInputStream(entrada);

            //Creo flujo de salida hacia el cliente
            salida = clienteConectado.getOutputStream();
            flujoSalida = new DataOutputStream(salida);

            //Operamos... mostramos mensaje enviado por el cliente y enviamos mensaje al cliente
            String cadena = flujoEntrada.readUTF();
            System.out.println("Recibiendo del cliente: "+cadena);
            
            if(isLong(cadena)){
                
                Long numero = Long.valueOf(cadena);
                //Compruebo si es primo
                String mensaje = esPrimo(numero);
                flujoSalida.writeUTF(mensaje);
                System.out.println("Respuesta al cliente: "+mensaje);
            }else {
                flujoSalida.writeUTF("El valor introducido no es numérico");
                System.out.println("Respuesta al cliente: El valor introducido no es numérico");
            }

            //Cierro streams y sockets
            flujoSalida.close();
            salida.close();
            flujoEntrada.close();
            entrada.close();
            clienteConectado.close();
            servidor.close();
            
            System.out.println("FIN DE EJECUCIÓN DEL SERVIDOR");
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    /**
     * Método para comprobar si una cadena recibida es un entero largo
     * @param cadena Cadena recibida
     * @return Valor booleano, true si la cadena es un entero largo, false en caso contrario
     */
    public static boolean isLong(String cadena) {

        boolean resultado;

        try {
            Long.valueOf(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
    
    public static String esPrimo(long numero){
        boolean primo= true;
        long candidatoDivisor= 3;
        if (numero % 2 == 0) {
            primo= false;
        }
        while (candidatoDivisor < (int) Math.sqrt(numero) && !primo) {
            if (numero % candidatoDivisor == 0)
                primo= false;
            else
                candidatoDivisor +=2;                       
        }        
        if(primo)
            return "El número "+numero+" es primo.";
        else
            return "El número "+numero+" NO es primo.";
    }
}
