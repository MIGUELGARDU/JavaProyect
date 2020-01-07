

import java.io.IOException;
import javax.swing.SwingUtilities;
import org.opencv.core.Core;


public class OCR
{
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
                        try 
                        {
                            new LeerConfiguracion();
                        } 
                        catch (IOException ex) 
                        {}
                    }
                });
    }
}