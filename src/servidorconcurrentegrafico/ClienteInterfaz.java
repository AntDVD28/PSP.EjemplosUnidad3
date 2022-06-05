
package servidorconcurrentegrafico;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Clase para la interfaz de los clientes
 * @author David Jiménez Riscardo
 */
public class ClienteInterfaz extends Frame implements ActionListener{
    
    Panel panel;
    Socket conexion;
    
    //Flujos de entrada y salida
    DataOutputStream salida;
    DataInputStream entrada;
    
    //Componentes de la interfaz de usuario
    TextField textent, textsal;
    Button enviar, conectar, desconectar;
    
    ClienteInterfaz(String nombre){
        super(nombre);
        setSize(350,200);
        panel = new Panel();
        textsal = new TextField(40);
        textent = new TextField(40);
        textent.setText("Pulsa el botón \"Conectar\" para conectarte");
        textent.setEditable(false);
        enviar = new Button("Enviar");
        enviar.setEnabled(false);
        conectar = new Button("Conectar");
        desconectar = new Button("Desconectar");
        desconectar.setEnabled(false);
        //Agregamos los componentes al panel
        panel.add(new Label("Datos a enviar"));
        panel.add(textsal);
        panel.add(new Label("Datos recibidos"));
        panel.add(textent);
        panel.add(enviar);
        panel.add(conectar);
        panel.add(desconectar);
        enviar.addActionListener(this);
        conectar.addActionListener(this);
        desconectar.addActionListener(this);
        add(panel);
        setVisible(true);    
    }
    
    public void actionPerformed(ActionEvent e){
        String com = e.getActionCommand();
        //Si hemos presionado el botón Enviar
        if(com.equals("Enviar")){
            //Obtenemos los campos del textfield y los enviamos al server
            //Además recibimos del server los datos modificados           
            try {
                textent.setText("");
                salida.writeUTF(textsal.getText());
                textent.setText(entrada.readUTF());
                textsal.setText("");
            } catch (IOException ex) {
                Logger.getLogger(ClienteInterfaz.class.getName()).log(Level.SEVERE, null, ex);
            }     
        //Si hemos pulsado el botón Conectar    
        }else if(com.equals("Conectar")){
            
            try {
                //Se conectar el cliente al servidor y se crean los flujos de entrada/salida
                conexion = new Socket(InetAddress.getLocalHost(),5000);
                salida = new DataOutputStream(conexion.getOutputStream());
                entrada = new DataInputStream(conexion.getInputStream());
                conectar.setEnabled(true);
                desconectar.setEnabled(true);
                enviar.setEnabled(true);
                textent.setText("");                
            } catch (UnknownHostException ex) {
                Logger.getLogger(ClienteInterfaz.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClienteInterfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        //Hemos pulsado el botón desconectar      
        }else {
            try {
                //Enviamos al servidor la cadena "Salir"
                salida.writeUTF("Salir");
                //Cerramos la conexion con el servidor
                conexion.close();
                conectar.setEnabled(true);
                desconectar.setEnabled(false);
                enviar.setEnabled(false);
                textent.setText("Pulsa el botón \"Conectar\" para conectarte");
            } catch (IOException ex) {
                Logger.getLogger(ClienteInterfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//fin del if      
    }//fin del método actionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        new ClienteInterfaz("Cliente David");
        new ClienteInterfaz("Cliente Valme");
    }
    
}
