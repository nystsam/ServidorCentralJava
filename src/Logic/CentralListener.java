/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Daniel
 */
public class CentralListener extends Thread {
    
    @Override
    public void run(){
        
        ServerSocket ss;
        Socket so;
        try{
            
            ss = new ServerSocket(Util.port);
            while (true){
                System.out.println("Servidor centralizado esperando una conexión por el puerto: " + Util.port);
                so = ss.accept();
                System.out.println( "Conexión recibida de: " +  so.getInetAddress().getHostAddress());
                
                // Atender solicitud
                

            }
        }catch(Exception e ){
            this.start();
        }
    
    
        }
    
    
}
