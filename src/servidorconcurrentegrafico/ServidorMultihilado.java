package servidorconcurrentegrafico;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Clase principal del servidor, recibe cadenas de los clientes, las modifica y las devuelve modificadas.
 * @author David Jiménez Riscardo
 * @version 1.0
 */
public class ServidorMultihilado extends Frame {

    TextArea ta;
    ServerSocket ss;
    //Para controlar el número de clientes conectados
    int num_clientes;
    

    ServidorMultihilado(){
        setTitle("Servidor");
        setSize(350,400);
        ta = new TextArea(20,40);
        ta.setEditable(false);
        add(ta);
        setVisible(true);
        num_clientes = 0;
    }
    
    /**
     * Método con el que iniciamos nuestro servidor
     */
    void lanzarServidor(){
        String cadena;
        try {
            //Indicamos el máximo de clientes que pueden conectarse
            ss = new ServerSocket(5000,50);
        } catch (IOException ex) {
            Logger.getLogger(ServidorMultihilado.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            Socket clienteConectado = null;
            try {
                clienteConectado = ss.accept();
            } catch (IOException ex) {
                Logger.getLogger(ServidorMultihilado.class.getName()).log(Level.SEVERE, null, ex);
            }
            Cliente cliente = new Cliente(clienteConectado,this,num_clientes++);
            cliente.start();
        }   
    }
    
    public void aniadir(String texto){
        ta.append(texto);
    }
      
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ServidorMultihilado server = new ServidorMultihilado();
        server.lanzarServidor();
    }
    
}
