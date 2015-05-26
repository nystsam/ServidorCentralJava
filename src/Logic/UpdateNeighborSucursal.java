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
    
    private final String nextIp;
    private final String nextPort;
    private final String [] previousSucursal;

    public UpdateNeighborSucursal(String ip, String port, String [] previousSucursal) {
        this.nextIp = ip;
        this.nextPort = port;
        this.previousSucursal = previousSucursal;
    }
    
    @Override
    public void run(){
        
            Socket so;
        try {
            
            so = new Socket(this.previousSucursal[1], Integer.parseInt(this.previousSucursal[2]));
            DataOutputStream output = new DataOutputStream(so.getOutputStream());
            
            output.writeUTF("0 " +this.nextIp+" "+this.nextPort);
            output.flush();
            
            Util.addText(this.previousSucursal[0] + " tiene nuevo vecino: "+ this.nextIp);
            
            so.close();
            
        
        } catch (IOException ex) {
            Logger.getLogger(UpdateNeighborSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
}
