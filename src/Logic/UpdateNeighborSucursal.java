/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AJUSTA EL IP VECINO DE LA SUCURSAL ANTERIOR
 * @author Daniel
 */
public class UpdateNeighborSucursal extends Thread {
    
    private final String ip;
    private final int port;

    public UpdateNeighborSucursal(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    
    @Override
    public void run(){
        
            Socket so;
        try {
            
            so = new Socket(this.ip, this.port);
            DataOutputStream output = new DataOutputStream(so.getOutputStream());
            
            output.writeUTF("0 "+this.ip+" "+this.port);
            output.flush();
            
            so.close();
            
        
        } catch (IOException ex) {
            Logger.getLogger(UpdateNeighborSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
}
