
package servidorconcurrente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Cliente
 * @author David Jiménez Riscardo
 */
public class Cliente extends Thread{
    
    private String hostServidor;
    private int puertoServidor;
    
    public Cliente(String hostServidor, int puertoServidor){
        this.hostServidor = hostServidor;
        this.puertoServidor = puertoServidor;
    }
    
    public void run(){
        System.out.println("PROGRAMA CLIENTE INICIADO ...");
        Socket cliente = null;
        
        try {
            //CREO EL SOCKET CON EL PUERTO Y EL HOST DEL SERVIDOR
            cliente = new Socket(this.hostServidor, this.puertoServidor);
            
            //CREO FLUJO DE SALIDA AL SERVIDOR
            DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());
            
            
            //ENVÍO AL SERVIDOR UNA CADENA CON LA OPERACIÓN QUE DEBE REALIZAR
            flujoSalida.writeUTF("Hola!!");
            
            //CREO FLUJO DE ENTRADA AL SERVIDOR
            DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
                       
            //RECIBO EL MENSAJE CON LA CADENA DE RESPUESTA DEL SERVIDOR
            System.out.println(flujoEntrada.readUTF());
            
            //CERRAR STREAMS Y SOCKETS
            flujoSalida.close();
            flujoEntrada.close();
            cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
