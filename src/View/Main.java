package View;

import Logic.CentralListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        CentralListener listener = new CentralListener();
        listener.start();
        
        MainWindow window = new MainWindow();
        window.setVisible(true);
        
    }
    
}
