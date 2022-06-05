
package servidorunico;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cliente
 * @author David Jiménez Riscardo
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Socket cliente = null;
        InputStream entrada = null;
        DataInputStream flujoEntrada = null;
        OutputStream salida = null;
        DataOutputStream flujoSalida = null;
        //Cadena introducida por teclado
        String teclado = null;
        
        //Gestión del puerto
        int puerto = 6000;
        if(args.length!=1){
            System.out.println("Parámetros erróneos. Iniciando servidor en el puerto 6000.");
        }else {
            puerto = Integer.parseInt(args[0]);
        }
        
        //Gestión de la conexión
        String host = "localhost";
        int reintentos = 3;
        int reintentosSegundos = 5;
        System.out.println("Programa cliente iniciado");
        
        try {
            
            while(cliente == null && reintentos>0){               
                try{
                    //Instanciamos el cliente
                    cliente = new Socket(host, puerto);
                } catch (IOException ex) {
                    System.err.println("Fallo de conexión."
                        + "\nAseguresé de que el servidor está activo."
                        + "\nNuevo intento de conexión en " + reintentosSegundos + " segundos...\n");
                    reintentos--;
                    try {
                        Thread.sleep(reintentosSegundos*1000);
                    } catch (InterruptedException ex1) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex1);
                    }    
                }    
            }//fin del while
                
            if(cliente!=null){
                
                //Creo un flujo de salida al servidor
                salida = cliente.getOutputStream();           
                flujoSalida = new DataOutputStream(salida);

                //Creo un flujo de entrada del cliente           
                entrada = cliente.getInputStream();
                flujoEntrada = new DataInputStream(entrada);
                
                //Operamos....enviamos mensaje al servidor y mostramos mensaje del servidor
                flujoSalida.writeUTF("Hola!!");
                System.out.println("Recibiendo del servidor: "+flujoEntrada.readUTF());
                
                //Cierro streams y conexiones           
                entrada.close();
                flujoEntrada.close();
                salida.close();
                flujoSalida.close();
                cliente.close();
            }else{
                System.out.println("Intentos de conexión agotados.\nCliente terminado sin recibir respuesta.\n");
            }    
        
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }//fin del main
    
}//fin de la clase
