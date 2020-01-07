package convertir;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Convertir {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        String classif = "C:/Users/Vostro Placas/Desktop/xppython/instalado en sistemas/ArchivosEN/classifications.txt";
        String images = "C:/Users/Vostro Placas/Desktop/xppython/instalado en sistemas/ArchivosEN/flattened_images.txt";
        String line = null;
        BufferedReader reader = null;
        int i = 0;
        try
        {
          reader =  new BufferedReader(new FileReader(classif));
          
          while ((line = reader.readLine())!=null)
          {
              String[] partes = line.split("e");
              if (partes.length>1)
              {
                  double valor;
                  valor = Double.parseDouble(line);
                  //System.out.println(partes[0]+" , " + partes[1]);
                  //System.out.println(valor);
                  i++;
              }
          }
          System.out.println(i);
          i = 0;
        }catch(IOException e)
        {
            System.out.println(e);
        }finally{
                    if (reader!=null)
                    {
                        try{
                            reader.close();
                        }catch(IOException e){
                            System.out.println(e);
                        }
                    }
                    
                }
        try 
        {
           reader =  new BufferedReader(new FileReader(images));
           while((line = reader.readLine())!=null)
           {
               String[] separado = line.split(" ");
               if (separado.length>1)
               {
                   for(int j=0; separado.length>j; j++)
                   {
                       line = separado[j];
                       double val = Double.parseDouble(line);
                       System.out.println(val);
                       i++;
                   }
               }
           }
           System.out.println(i);
        }catch(IOException e)
        {
            System.out.println(e);
        }finally
        {
            if(reader!=null)
            {
                try
                {
                    reader.close();
                }catch(IOException e)
                {
                    System.out.println(e);
                }
            }
        }
            
    }
    
}
