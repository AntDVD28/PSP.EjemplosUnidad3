package servidorunico_adivina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Clase Aleatorio
 * @author David Jiménez Riscardo
 * @version 1.0
 */
public class Aleatorio {
    
    private int numeroAleatorio;
    private int limiteInferior;
    private int limiteSuperior;
    
    public Aleatorio(){
        
        this.limiteInferior = 0;
        this.limiteSuperior = 100;
        numeroAleatorio =  (int) Math.floor(Math.random()*(this.limiteSuperior-this.limiteInferior+1)+this.limiteInferior); 
    }
    
    public int getNumero(){
        return this.numeroAleatorio;
    }
    
    public String comprobarNumero(int numero){
        
        String cadena = null;
        
        if(numero>this.numeroAleatorio)           
            cadena = "Número demasiado grande";           
        else if(numero<this.numeroAleatorio)
            cadena = "Número demasiado pequeño";
        else
            cadena = "CORRECTO"; 
        
        return cadena;
    }
      
    /*
    //Para comprobar que la clase funciona correctamente
    public static void main(String[] args) throws IOException{
        Aleatorio numero = new Aleatorio();
        System.out.println(numero.getNumero());
        System.out.println("Introduzca un número:");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        int num = Integer.parseInt(s);
        String resultado = numero.comprobarNumero(num);
        System.out.println(resultado);
    }*/
}
