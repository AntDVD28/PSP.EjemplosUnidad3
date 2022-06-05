package servidorunico_adivina;

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

/**
 * Clase Cliente
 * @author David Jiménez Riscardo
 * @version 1.0
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        String host = "localhost";
        int puerto = 6000;
        Socket cliente = null;
        OutputStream salida = null;
        DataOutputStream flujoSalida = null;
        InputStream entrada = null;
        DataInputStream flujoEntrada = null;
        //Cadena introducida por teclado
        String teclado = null;
        //Para controlar la salida del bucle
        String resultado = "INCORRECTO";
        //Cadena devuelta por el servidor
        String mensajeServer = null;
        //controlar el número de reintentos
        int reintentos = 3;
        int reintentosSegundos = 5;
            
            
        //Gestión del puerto
        if(args.length!=1){
            System.out.println("\"Parámetros erróneos, iniciando servidor en el puerto 6000\"");
        }else{
            puerto = Integer.parseInt(args[0]);
        }
            
        System.out.println("PROGRAMA CLIENTE INICIADO... Vamos a adivinar un número");
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

                while(resultado != "CORRECTO"){

                    System.out.println("Dime un número para ver si es el que piensa el Servidor (entre 1 y 100):");
                    InputStreamReader isr = new InputStreamReader(System.in);
                    BufferedReader br = new BufferedReader(isr);                
                    teclado = br.readLine();

                    //Envío algo al servidor                
                    flujoSalida.writeUTF(teclado);

                    //Recibo un mensaje del servidor                     
                    mensajeServer = flujoEntrada.readUTF();
                    System.out.println("Recibiendo del servidor: \n\t"+mensajeServer);

                    if(mensajeServer.equals("CORRECTO"))
                        resultado="CORRECTO";                
                }//fin del while

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
    }
    
}
