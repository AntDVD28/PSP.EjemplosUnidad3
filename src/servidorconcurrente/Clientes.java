
package servidorconcurrente;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para implementar los hilos de cliente
 * @author @author David Jiménez Riscardo
 */
public class Clientes extends Thread{
    
    private static final int MAX_CLIENTES = 3;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String hostServidor="localhost";
        int puertoServidor=6000;
        
        //Gestion del puerto
        if(args.length!=1){
            System.out.println("Parámetros erróneos. Iniciando servidor en el puerto 6000.");
        }else {
            puertoServidor = Integer.parseInt(args[0]);
        }
        
        //Estructura de datos dónde guardaremos los hilos clientes
        ArrayList<Thread> listaHilosClientes = new ArrayList<>();
        
        //Variable para controlar el número de clientes
        int numCliente=0;
        
        System.out.println("HILOS CLIENTES");
        System.out.println("--------------");
        System.out.println("Clientes de David Jiménez Riscardo");
        
        while(numCliente < MAX_CLIENTES){
            
            Cliente cliente = new Cliente(hostServidor, puertoServidor);
            listaHilosClientes.add(cliente);
            numCliente++;
        }
        
        System.out.printf("Cantidad de clientes: %d\n", listaHilosClientes.size());
            System.out.println();
            System.out.println("Ejecución de clientes concurrentes");
            System.out.println("-------------------------------");
            
            //Lanzo los hilos
            for(Thread hilo : listaHilosClientes){
                
                hilo.start();
            }
            
            //Me aseguro que todos los hilos hayan terminado
            for(Thread hilo : listaHilosClientes){
                
                try {
                    hilo.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                        
            System.out.println("\nTodos los clientes han finalizado su ejecución.");
            System.out.println("Fin del programa principal.");
        
        
    }
    
}
