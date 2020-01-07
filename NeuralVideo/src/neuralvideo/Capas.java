
package neuralvideo;

import java.util.Arrays;


public class Capas {
    private double[][] pesosW1, pesosW2, pesosW3;
    private int[] neuronas;
    private int numEntradas,numObjetivos, numCapa1 = 16, numCapa2 = 16, Capa, neuro;
    private static double[][][] pesos = new double[3][][];
    private static double[][] umbral= new double[4][],a = new double[4][],entradas, Objetivos,y;
    private double x,errorr;
    
    
    
    
    public double[][] getEntradas()
    {
        return entradas;
    }
    
    public void  setEntradas(double[][] entradas)
    {
        this.entradas = entradas;
        this.numEntradas = entradas[0].length;
        
    }
    
    
    public double[][] getObjetivos()
    {
        return Objetivos;
    }
    
    
    public void setObjetivos(double[][] objetivos)
    {
        this.Objetivos = objetivos;
        this.numObjetivos = objetivos[0].length;
    }
    
    public void inicializarPesos()
    {
        pesosW1 = new double[numEntradas][numCapa1];
        pesosW2 = new double[numCapa1][numCapa2];
        pesosW3 = new double[numCapa2][numObjetivos];
        umbral[0] = new double[numEntradas];
        umbral[1] = new double[numCapa1];
        umbral[2] = new double[numCapa2];
        umbral[3] = new double[numObjetivos];
        neuronas = new int[4];
        neuronas[0] = numEntradas;
        neuronas[1] = numCapa1;
        neuronas[2] = numCapa2;
        neuronas[3] = numObjetivos;
        for (int i = 0;i<3;i++)
        {
            for (int j = 0; j < neuronas[i]; j++)
            {
                umbral[i][j]=Math.random();
            }
        }
        for(int i = 0; i<numEntradas; i++)
        {
            for (int j = 0; j < numCapa1; j++)
                pesosW1[i][j] = Math.random();
        }
        for(int i = 0; i<numCapa1; i++)
        {
            for (int j = 0; j < numCapa2; j++)
                pesosW2[i][j] = Math.random();
        }
        for(int i = 0; i<numCapa2; i++)
        {
            for (int j = 0; j < numObjetivos; j++)
                pesosW3[i][j] = Math.random();
        }
        pesos[0]=pesosW1;
        pesos[1]=pesosW2;
        pesos[2]=pesosW3;
    }
    
    
    public void recalcular(int indicee, int Capa, int neuro)
    {
        this.Capa = Capa;
        this.neuro = neuro;
        double suma1 = 0, suma2 = 0,error;
        for (int i = 0; i<neuronas[3]; i++)
        {
            suma1 += pesosW2[0][i]*a[2][i]*(1-a[2][i]);
        }
        for (int i = 0; i<neuronas[3]; i++)
        {
            suma2 += pesosW3[i][0]*y[0][0]*(1-y[0][0])*(-(Objetivos[0][0]-y[0][0]));
        }
        error = a[0][0]*(a[1][0]*(1-a[1][0])*suma1*suma2);
        System.out.println(error);

    }
    
    public void sigmoide(double x)
    {
        this.x = 1/(1+Math.exp(-0.3*x));
    }
    public void error(double esperado, double resultado)
    {
        this.errorr = esperado-resultado;
    }
    
    public void entrenar()
    {
        int indice = 0;
        a[0] = new double[neuronas[0]];
        a[1] = new double[neuronas[1]];
        a[2] = new double[neuronas[2]];
        a[3] = new double[neuronas[3]];
        y = new double[entradas.length][numObjetivos];
        //double[][]salidas= new double[entradas.length][numObjetivos];
        for (int k = 0; k < neuronas.length; k++)
        {
            if(k==0)
            {
                for (int i =0; i<entradas[indice].length;i++)
                {
                    a[k][i]=entradas[indice][i]/255;
                }
            }
            else
            {
                for (int i = 0; i < neuronas[k]; i++)
                {
                    double suma = 0;
                    for (int j = 0; j < neuronas[k-1]; j++)
                    {
                        suma += a[k-1][j]*pesos[k-1][j][i];
                    }
                    a[k][i] = umbral[k][i] + suma;
                    sigmoide(a[k][i]);
                    a[k][i] = x;
                    if (k==3)
                    {
                        y[indice][i] = a[k][i];
                    }
                }
            }
        }
        System.out.println(Arrays.toString(y[indice]));
        for (int posicion = 0; posicion<numObjetivos; posicion++)
        {
            error(Objetivos[0][posicion],y[0][posicion]);
            if (errorr>0.05)
            {
                recalcular(indice,3,posicion);
            }
            System.out.println(errorr);
        }
       /* for(int indica = 0; indica<entradas.length; indica++)
        {
            for (int j = 0; j < numObjetivos; j++)
            {
                System.out.println("Deseado: "+Objetivos[indica][j]+" Resultados: "+y[indica][j]);
                if (Objetivos[indica][j]!=y[indica][j])
                {
                    recalcular(indica,j);
                }
            }
            for(int i = 0; i<numObjetivos; i++)
            {
                salidas[indica][i] = y[indica][i] >= 0 ? 1 : 0;
                if(salidas[indica][i]!=Objetivos[indica][i])
                {
                    recalcular(indica,i);
                    break;
                }
                
            }
           
            
        }*/
        /*while(indice<entradas.length)
        {
            for (int k =0; k<4; k++)
            {
                if (k == 0)
                {
                    a[k]=entradas[indice];
                }
                else
                {
                    for (int i =0;i<neuronas[k];i++)
                    {        
                        double suma = 0;
                        for ( int j = 0; j < neuronas[k-1]; j++)
                        {
                            
                            suma += a[k-1][j]*pesos[k-1][j][i];
                        }
                        a[k][i] = umbral[k][i]+suma;
                        sigmoide(a[k][i]);
                        a[k][i] = x;
                        if (k==3)
                        {
                            y[indice][i]=a[k][i];
                        }
                    }
                }
            }
            indice++;
        } 
        for(int indica = 0; indica<entradas.length; indica++)
        {
            for (int j = 0; j < numObjetivos; j++)
            {
                salidas[indica][j] = y[indica][j] >= 0.5 ? 1 : 0;
                System.out.println("Deseado: "+Objetivos[indica][j]+" Resultados: "+salidas[indica][j]);
                if (Objetivos[indica][j]!=salidas[indica][j])
                {
                    recalcular(indica,j);
                }
            }
            for(int i = 0; i<numObjetivos; i++)
            {
                salidas[indica][i] = y[indica][i] >= 0 ? 1 : 0;
                if(salidas[indica][i]!=Objetivos[indica][i])
                {
                    recalcular(indica,i);
                    break;
                }
                
            }
           
            
        }*/
        
    }
}
