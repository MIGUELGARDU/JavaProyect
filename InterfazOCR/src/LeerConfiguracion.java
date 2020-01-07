

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;


public class LeerConfiguracion {
    public static String ruta = "configuracion.Json";
    private final File archivo = new File(ruta);
    public static int Camarasexistentes;
    public static int Sistemaactual;
    public static int[] Sistemasvalidos;
    public static String DimensionVentana;
    public static int Carril1;
    public static int Carril2;
    public static int Carril3;
    public static int Carril4;
    private int i = 0;
    private String[] linea;
    private String[] lineas;
    private String lina; 
    
    public LeerConfiguracion() throws IOException
    {
        
        if(archivo.exists())
        {
            String cadena;
            FileReader f = new FileReader(archivo);
            try (BufferedReader b = new BufferedReader(f)) {
                while((cadena = b.readLine())!=null)
                {
                    linea = cadena.split("=");
                    if (i==0)
                        Camarasexistentes = Integer.parseInt(linea[1]);
                    if (i==1)
                        Sistemaactual = Integer.parseInt(linea[1]);
                    if (i==2)
                    {
                        
                        lina = linea[1].substring(1, linea[1].length()-1);
                        linea = lina.split(",");
                        int tamaño = linea.length;
                        Sistemasvalidos = new int[tamaño];
                        lina = linea[0];
                        Sistemasvalidos[0]=Integer.parseInt(lina);
                        for (int j=1; j<tamaño; j++)
                        {
                            lineas = linea[j].split(" ");
                            lina = lineas[1];
                            Sistemasvalidos[j] = Integer.parseInt(lina);
                        }
                    }
                    if (i==3)
                        Carril1 = Integer.parseInt(linea[1]);
                    if (i==4)
                        Carril2 =Integer.parseInt(linea[1]);
                    if (i==5)
                        Carril3 = Integer.parseInt(linea[1]);
                    if (i==6)
                        Carril4 = Integer.parseInt(linea[1]);
                    if (i==7)
                        DimensionVentana = linea[1];
                    i++;
                }
            }
            SwingUtilities.invokeLater(() -> {
                try {
                    new VentanaPrincipal();
                } catch (IOException ex) {
                    Logger.getLogger(LeerConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        else
        {
            SwingUtilities.invokeLater(() -> {
                try
                {
                    new Configuracioninicial();
                }
                catch(IOException ex)                
                {}
            });
        }
    }
    
}
