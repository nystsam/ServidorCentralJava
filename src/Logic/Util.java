/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author Daniel
 */
public class Util {
    
    public static int port = 10000;
    
    // Consola de la central
    public static JList console;
    
    /**
     * Agregar texto a la consola de la central
     * @param text texto que se quiere colocar
     */
    public static void addText(String text){
        
        DefaultListModel newText = new DefaultListModel();
        if(Util.console.getModel().getSize() > 0)
            newText = (DefaultListModel) Util.console.getModel();

        String timeStamp = new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime());
        newText.addElement(text+" - " + timeStamp);
        Util.console.setModel(newText);
        
    }
    
}
