/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocrplacassystemas;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.opencv.core.Core;

/**
 *
 * @author Placas
 */
public class main {
    static
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        try {
                            new Ventana();
                        } catch (IOException ex) {
                            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
    }
    
}
