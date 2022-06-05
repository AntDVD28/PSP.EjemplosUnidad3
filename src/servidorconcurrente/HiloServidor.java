/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorconcurrente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hilo Servidor
 * @author David Jiménez Riscardo
 */
class HiloServidor extends Thread{
    
    private Socket clienteConectado;

    // Flujo de salida a través del cual enviaremos información al cliente
    private DataOutputStream flujoSalida;

    // Flujo de entrada a través del cual recibiremos información desde el cliente
    private DataInputStream flujoEntrada;

    private int numCliente;
    

    HiloServidor(Socket clienteConectado, int numCliente) {
        try {
            this.clienteConectado = clienteConectado;
            this.numCliente = numCliente;      
            this.flujoSalida = new DataOutputStream(this.clienteConectado.getOutputStream());
            this.flujoEntrada = new DataInputStream(this.clienteConectado.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        
        String peticionCliente = null;
        
        System.out.printf("Iniciado hilo servidor %d.\n", this.numCliente);
        
        try{
            
            // En espera de la recepción de peticiones por parte del cliente
            peticionCliente = flujoEntrada.readUTF();
            if (!peticionCliente.isEmpty()) {
                System.out.println("Recibiendo del CLIENTE: \n\t" + peticionCliente);
            }
            // Devuelvo al cliente el resultado de la operación
            flujoSalida.writeUTF("Bienvenido cliente "+this.numCliente+"!!");

            // Cerramos la comunicación con el cliente
            clienteConectado.close();
            
            // Cerramos flujos de entrada y salida
            flujoSalida.close();
            flujoEntrada.close();
            
        } catch (SocketException ex) {
            System.out.printf("Error de socket: %s\n", ex.getMessage());
        } catch (IOException ex) {
            System.out.printf("Error de E/S: %s\n", ex.getMessage());
        }
        
        System.out.printf("Hilo servidor %d: Fin de la conexión con el cliente.\n", this.numCliente);

    }
    
}
