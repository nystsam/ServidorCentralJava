/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CLASE PARA ATENDER TODAS LAS SOLICITUDES ENVIADAS A LA CENTRAL
 * @author Daniel
 */
public class SucursalRequest extends Thread {
    
    private final Socket so;

    public SucursalRequest(Socket so) {
        this.so = so;
    }
    
    @Override
    public void run(){
        
        try {
            
            DataOutputStream output = new DataOutputStream(this.so.getOutputStream());
            DataInputStream input = new DataInputStream(this.so.getInputStream());
            
            // Lee la peticion de la sucursal
            String[] sucursalRequest = input.readUTF().split(" ");
            
            // Respuesta a la sucursal
            String sendResponse = "";
            
            switch(sucursalRequest[0]){
                
                // Saludo de una sucursal
                case "0":
                
                    ReadSucursal finder = new ReadSucursal();
                    sendResponse = finder.findSucursal(sucursalRequest[1], this.so.getInetAddress().getHostAddress(), sucursalRequest[2]);
                    
                    break;
                // Fin Saludo de una sucursal    
            }
            
        
            
            output.writeUTF(sendResponse);
            output.flush();
            this.so.close();
            
        } catch (IOException ex) {
            Logger.getLogger(SucursalRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
