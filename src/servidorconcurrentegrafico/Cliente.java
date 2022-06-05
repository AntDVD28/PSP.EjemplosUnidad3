package servidorconcurrentegrafico;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para la gestión de los hilos clientes
 * @author David Jiménez Riscardo
 */
public class Cliente extends Thread{
    
    Socket conexion;
    ServidorMultihilado server;
    int cliente;
    
    //Flujos de entrada y salida
    DataInputStream entrada;
    DataOutputStream salida;
    
    //Constructor
    Cliente(Socket s, ServidorMultihilado sms, int numero){
        conexion = s;
        server = sms;
        cliente = numero;
        
        //Obtenemos los flujos de entrada y salida
        try {
            entrada = new DataInputStream(conexion.getInputStream());
            salida = new DataOutputStream(conexion.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método que inicia el hilo cliente
     */
    public void run(){
        
        boolean salir = false;
        server.aniadir("Cliente "+cliente+" se ha conectado.\n");
        
        while(!salir){
            
            try {
                String cadena = entrada.readUTF();
                if(cadena.equals("Salir")){
                    salir = true;
                }else {
                    server.aniadir("El cliente "+cliente+" ha enviado:\n"+"      "+cadena+'\n');
                    salida.writeUTF("Cadena \""+cadena+"\" recibida");
                }
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        server.aniadir("Se ha ido el cliente "+cliente+"\n");
        try {
            conexion.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
