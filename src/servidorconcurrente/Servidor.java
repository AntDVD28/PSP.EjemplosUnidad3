
package servidorconcurrente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servidor concurrente, atiende a varios clientes de forma concurrente
 * @author David Jiménez Riscardo
 */
public class Servidor {
    
    // Límite de conexiones con clientes
    public static final int LIMITE_CONEXIONES = 3;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Gestión del puerto
        int puerto = 6000;
        if(args.length!=1){
            System.out.println("Parámetros erróneos. Iniciando servidor en el puerto 6000.");
        }else {
            puerto = Integer.parseInt(args[0]);
        }
        
        //Declaro un array "hilosServidor" con 3 hilos
        ArrayList<Thread> hilosServidor = new ArrayList<>(LIMITE_CONEXIONES);
           
        //Declaro socket del servidor y cliente    
        ServerSocket servidor = null;
        Socket clienteConectado = null;
        
        //Cadena recibida del cliente
        String peticionCliente = null;
        //Variable para controlar el numero de clientes que se conectan
        int numCliente=0;
            

        try { 
            //Instanciamos el servidor
            servidor = new ServerSocket(puerto);
            
            System.out.println("Esperando al cliente...");
            while (numCliente < LIMITE_CONEXIONES) {
                
                //Aceptamos la petición de conexión del cliente 
                clienteConectado = servidor.accept();
                
                if(clienteConectado!=null){
                    System.out.println("Conexión establecida con cliente.");
                    // Creamos un nuevo hilo de ejecución para servir a este nuevo cliente conectado y lo almaceno en el array de hilos
                    HiloServidor hilo = new HiloServidor(clienteConectado, numCliente++);
                    hilosServidor.add(hilo);
                    
                    // Lanzamos la ejecución de ese nuevo hilo
                    hilo.start();
                    
                }        
            }//fin del while
            // Finaliza la interacción con posibles clientes. Cerramos el socket de servidor
            servidor.close();

        } catch (SocketException ex) {
            System.out.printf("Error de socket: %s\n", ex.getMessage());
        } catch (IOException ex) {
            System.out.printf("Error de E/S: %s\n", ex.getMessage());
        }
        // Esperamos a que todos los hilos del servidor finalicen su ejecución
        for (Thread hilo : hilosServidor){
                    
            try {
                hilo.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }             
        }       
        System.out.println("Fin de ejecución del servidor alcanzado el número máximo de conexiones.");
            
     

    }//fin del main
    
}//fin de la clase

