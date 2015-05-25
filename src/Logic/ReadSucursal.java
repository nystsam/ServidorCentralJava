/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.FileOutputStream;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Daniel
 */
public class ReadSucursal {

    /**
     * Busca en el archivo XML las sucursales vivas
     * @param name nueva sucursal que saluda
     * @param ip ip de la neuva sucursal que saluda
     * @param port puerto de la nueva sucursal que saluda
     * @return Devuelve el Ip y el puerto de la sucursal vecina
     */
    public String findSucursal(String name, String ip, String port){
    
        Document doc;
        Element root,child;
        List <Element> rootChildrens;
        
        String [] lastSucursal = new String[3];
        String nextIpPort = "";
        boolean alredyExist = false;
        
        int pos = 0;
        SAXBuilder builder = new SAXBuilder();

        try
        {
            doc = builder.build("src/XmlFiles/Sucursales.xml");
            root = doc.getRootElement();
            rootChildrens = root.getChildren();

            // Cuando no hay ninguna sucursal viva
            if(rootChildrens.isEmpty()){
                
                if(this.saveSucursal(name, ip, port)){
                
                    nextIpPort = ip+" "+port+" "+name;
                }
            }
            else{
                // Recorrido de sucursales vivas para obtener la vecina
                String firstSucursal = "";
                
                while (pos < rootChildrens.size() && !alredyExist){

                    child = rootChildrens.get(pos);
                    lastSucursal[0] = child.getAttributeValue("Name");
                    lastSucursal[1] = child.getAttributeValue("Ip");
                    lastSucursal[2] = child.getAttributeValue("Port"); 

                    if(lastSucursal[0] != null && lastSucursal[0].equals(name)) 
                        alredyExist = true;  
                   
                    if(pos == 0){
                        firstSucursal = lastSucursal[1]+" "+lastSucursal[2]+" "+lastSucursal[0];
                    }
                    
                    pos++;
                }
                
                if(!alredyExist){
                    
                    if(this.saveSucursal(name, ip, port)){
                        
                        // Ajusta el ip vecino de la sucursal anterior
                        UpdateNeighborSucursal updater = new UpdateNeighborSucursal(ip, Integer.parseInt(port));
                        updater.start();
                        
                        return firstSucursal;
                        
                    }
                    
                }
                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return nextIpPort;
        
    }
    
    private String sendNextSucursal(){
    
        String nextIpPort = "";
        
        
        
        return nextIpPort;
    }
    
    
    private boolean saveSucursal(String name, String ip, String port){
        
        Document    doc;
        Element     root, newChild;
        SAXBuilder  builder = new SAXBuilder();

        try
        {
            doc = builder.build("src/XmlFiles/Sucursales.xml");
            root = doc.getRootElement();
            // Creamos una nueva etiqueta
            newChild = new Element("Sucursal");

            // Añadimos un atributo
            newChild.setAttribute("Name", name);
            newChild.setAttribute("Ip", ip);   
            newChild.setAttribute("Port", port);   

            // La añadimos como hija a una etiqueta ya existente
            root.addContent(newChild);
            
            try
            {
                Format format = Format.getPrettyFormat();
                // Se genera un flujo de salida de datos XML
                XMLOutputter out = new XMLOutputter(format);
                // Se asocia el flujo de salida con el archivo donde se guardaran los datos
                FileOutputStream file = new FileOutputStream("src/XmlFiles/Sucursales.xml");
                // Se manda el documento generado hacia el archivo XML 
                out.output(doc,file);
                // Se limpia el buffer ocupado por el objeto file y se manda a cerrar el archivo
                file.flush();
                file.close();
            }
            catch(Exception e)
            {
                throw e;
            }
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return false;
    } 
    
}
