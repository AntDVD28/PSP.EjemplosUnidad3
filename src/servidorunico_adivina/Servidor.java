/*
Cliente-Servidor único
Tenemos que implementar un modelo cliente-servidor único (es decir, un solo cliente y un único servidor, 
no se admiten conexiones múltiples, ni secuencial ni concurrente).
Una vez realiza la conexión, el cliente tendrá que adivinar un número que el servidor ha generado de forma 
aleatoria (entre 1 y 100).
De esta forma el cliente pedirá un número al usuario, lo enviará al servidor y este le responderá si es 
correcto, demasiado pequeño y demasiado grande (mensaje que se mostrará por pantalla).
Cuando se haya adivinado el número ambos procesos se cerrarán.
*/
package servidorunico_adivina;

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
 * Clase Servidor Único
 * @author David Jiménez
 * @version 1.0
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ServerSocket servidor = null;
        Socket clienteConectado = null;
        InputStream entrada = null;
        DataInputStream flujoEntrada = null;
        OutputStream salida = null;
        DataOutputStream flujoSalida = null;
        //Cadena recibida del cliente
        String peticionCliente = null;
        //Para controlar la salida del bucle
        boolean encontrado = false;
        
        //Gestión del puerto
        int puerto = 6000;       
        if(args.length!=1){
            System.out.println("Parámetros erróneos, iniciando servidor en el puerto 6000");
        }else{
            puerto = Integer.parseInt(args[0]);
        }
        
        //Instanciamos el servidor
        try {
            servidor = new ServerSocket(puerto);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Aceptamos la petición de conexión del cliente
        System.out.println("Esperando al cliente...");
        try {
            clienteConectado = servidor.accept();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Creo flujo de entrada del cliente
        try {
            entrada = clienteConectado.getInputStream();          
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        flujoEntrada = new DataInputStream(entrada);
        
        //Creo un flujo de salida hacia el cliente
        try {            
            salida = clienteConectado.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        flujoSalida = new DataOutputStream(salida);
        
        //Genero un número aleatorio
        Aleatorio numAleatorio = new Aleatorio();
        //System.out.println("Número generado aleatorio: "+String.valueOf(numAleatorio.getNumero()));
        
        while(!encontrado){
            
            //Mostramos por pantalla el mensaje enviado por el cliente
            try {
                peticionCliente = flujoEntrada.readUTF();
                System.out.println("Recibiendo del cliente: \n\t"+peticionCliente);
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Compruebo si coincide el número enviado por el cliente
            String resultado = numAleatorio.comprobarNumero(Integer.parseInt(peticionCliente));

            //Envío resultado al cliente
            try {          
                flujoSalida.writeUTF(resultado);
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(resultado=="CORRECTO")
                encontrado = true;
        }
              
        //Cierro streams y sockets
        try {    
            entrada.close();
            flujoEntrada.close();
            salida.close();
            flujoSalida.close();
            clienteConectado.close();
            servidor.close();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }                   
    }   
}
