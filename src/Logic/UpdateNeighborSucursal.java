/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * AJUSTA EL IP VECINO DE LA SUCURSAL ANTERIOR
 * @author Daniel
 */
public class UpdateNeighborSucursal extends Thread {
    
    private String nextIp;
    private String nextPort;
    private String [] previousSucursal;
    private int size;

    public UpdateNeighborSucursal(){
        
    }
    
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
            
            output.writeUTF("0 "+this.nextIp+" "+this.nextPort);
            output.flush();
            
            Util.addText(this.previousSucursal[0] + " tiene nuevo vecino: "+ this.nextIp);
            
            so.close();
            
            this.updateSucursalList();
        
        } catch (IOException ex) {
            Logger.getLogger(UpdateNeighborSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
          catch(Exception e){
              
            System.out.println("Error al establecer conexion");  
          }
        
            
    }
    
    private void updateSucursalList(){
        
        Document doc;
        Element root,child;
        List <Element> rootChildrens;
        
        int pos = 0;
        SAXBuilder builder = new SAXBuilder();

        try
        {
          
            doc = builder.build("src/XmlFiles/Sucursales.xml");
            root = doc.getRootElement();
            rootChildrens = root.getChildren();
            Socket so;
            
                while (pos < rootChildrens.size()-1){

                    child = rootChildrens.get(pos);
                    String ip = child.getAttributeValue("Ip");
                    String port = child.getAttributeValue("Port");
                    
                    String list = this.getSucursales(ip).split(" ")[1];
                    
                    so = new Socket(ip, Integer.parseInt(port));
                    DataOutputStream output = new DataOutputStream(so.getOutputStream());

                    output.writeUTF("1 "+list);
                    output.flush();

                    Util.addText(ip + " se le envió nueva lista de sucursales");

                    so.close();
                    
                    pos++;
                }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public String getSucursales(String ip){
        
        Document doc;
        Element root,child;
        List <Element> rootChildrens;
        
        String ipLists = "";
        String success = "0 ";
        int pos = 0;
        SAXBuilder builder = new SAXBuilder();

        try
        {
          
            doc = builder.build("src/XmlFiles/Sucursales.xml");
            root = doc.getRootElement();
            rootChildrens = root.getChildren();
                
                while (pos < rootChildrens.size()){

                    child = rootChildrens.get(pos);
                    String newIp = child.getAttributeValue("Ip");
                    String newPort = child.getAttributeValue("Port");
                    
                    if(!ip.equals(newIp)) {
                        
                        ipLists = ipLists + newIp+"!"+newPort+"@";
                        success = "1 ";
                    } 
     
                    pos++;
                }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return success+ipLists;
            
    }
    
    public String getVecinos(String ip){
           
        Document doc;
        Element root,child;
        List <Element> rootChildrens;
        
        String ipPuertoVecino = "";
        int pos = 0;
        SAXBuilder builder = new SAXBuilder();

        try
        {
            boolean conseguido = false;
            String respaldo = "";
            doc = builder.build("src/XmlFiles/Sucursales.xml");
            root = doc.getRootElement();
            rootChildrens = root.getChildren();
                
                while (pos < rootChildrens.size()){

                    child = rootChildrens.get(pos);
                    String newIp = child.getAttributeValue("Ip");
                    String newPort = child.getAttributeValue("Port");
                    
                    if(pos == 0){
                        respaldo = newIp+"@"+newPort;
                    }
                    
                    if(conseguido){
                        
                        ipPuertoVecino = newIp+"@"+newPort;
                        
                    }
                    else{
                        if(ip.equals(newIp)) {
                            conseguido = true;         
                        } 
                    } 
                    pos++;
                }
                
                if(conseguido && ipPuertoVecino.equals("")) {
                    ipPuertoVecino = respaldo;
                }
                
                return ipPuertoVecino;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
        return ipPuertoVecino;
        
    }  
    
}
