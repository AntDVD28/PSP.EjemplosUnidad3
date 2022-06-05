
package servidorunico;

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
 * Servidor único, atiende a una única petición de cliente
 * @author David Jiménez Riscardo
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
        
        //Gestión del puerto
        int puerto = 6000;
        if(args.length!=1){
            System.out.println("Parámetros erróneos. Iniciando servidor en el puerto 6000.");
        }else {
            puerto = Integer.parseInt(args[0]);
        }
        
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
            System.out.println("Recibiendo del cliente: "+flujoEntrada.readUTF());
            flujoSalida.writeUTF("Bienvenido!!");
            
            //Cierro streams y sockets
            flujoSalida.close();
            salida.close();
            flujoEntrada.close();
            entrada.close();
            clienteConectado.close();
            servidor.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
