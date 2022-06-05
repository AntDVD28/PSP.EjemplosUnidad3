
package servidorunico_primo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    public static void main(String[] args){
        
        Socket cliente = null;
        InputStream entrada = null;
        DataInputStream flujoEntrada = null;
        OutputStream salida = null;
        DataOutputStream flujoSalida = null;
        //Cadena introducida por teclado
        String teclado = null;
        
        //Gestión del puerto
        int puerto = 6000;
        
        System.out.println("CLIENTE PARA EVALUAR LA PRIMALIDAD DE NUMEROS");
        System.out.println("=============================================");
        
        
        //Gestión de la conexión
        String host = "localhost";
        int reintentos = 3;
        int reintentosSegundos = 5;
        System.out.println("Cliente del alumno David Jiménez Riscardo conectado a localhost e iniciado en el puerto 6000");
        
        try {
            
            while(cliente == null && reintentos>0){               
                try{
                    //Instanciamos el cliente
                    cliente = new Socket(host, puerto);
                } catch (IOException ex) {
                    System.err.println("Error: No se ha podido establecer la conexión con el servidor"
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
                System.out.println("Dame un número positivo para comprobar su primalidad:");
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                String cadena = br.readLine();
                flujoSalida.writeUTF(cadena);
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
            
            System.out.println("FIN DE EJECUCIÓN DEL CLIENTE");
        
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
    }
}
