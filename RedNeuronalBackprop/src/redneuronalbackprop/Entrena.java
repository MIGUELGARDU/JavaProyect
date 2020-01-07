package redneuronalbackprop;

import java.util.Arrays;


class Entrena {
    double[][][] pesos = new double[3][][];
    double[][] umbral,entradas,Objetivos,ValueNeuronas;  
    double S,error, sum1,sum2,sumerror,anteerror,erro,iteracion=1;
    int[] neuronas;
    int numCapas,numEntradas,numObjetivos,indice;
    Recalcular R = new Recalcular();

    void setValores( double[][][] pesos, double[][] umbral, double[][] entradas, double[][] Objetivos, int[] neuronas) 
    {
        this.pesos = pesos;
        this.umbral = umbral;
        this.entradas = entradas;
        this.Objetivos = Objetivos;
        this.neuronas = neuronas;
        this.numCapas = neuronas.length;
        this.numEntradas = entradas[0].length;
        this.numObjetivos = Objetivos[0].length;
    }

    void entrena()
    {
        
        ValueNeuronas = new double[numCapas][];
        int epocas = 1;
        
        for (int i = 0; i<entradas.length;i++)
        {
            for ( int j = 0; j<numEntradas;j++)
            {
                entradas[i][j] = entradas[i][j]/255; 
            }
        }
        
        while (epocas>0)
        {
            indice = 0;
            error = 0.0;
            while (indice<entradas.length)
            {
                for (int k =0; k< numCapas;k++)
                {
                    ValueNeuronas[k] = new double[neuronas[k]];
                    if(k == 0)
                    {
                        ValueNeuronas[0] = entradas[indice];
                    }
                    else
                    {    
                        for(int i=0; i<neuronas[k]; i++)
                        {
                            double suma =0.0;
                            for (int j = 0; j<neuronas[k-1]; j++)
                            {
                                suma += ValueNeuronas[k-1][j]*pesos[k-1][i][j];
                            }
                            ValueNeuronas[k][i] = suma*umbral[k-1][i];
                            sigmoide(ValueNeuronas[k][i]);
                            ValueNeuronas[k][i] = Math.abs(S);
                        }
                    }
                }
                //System.out.println("Objetivos: "+Arrays.toString(Objetivos[indice])+" Resultados: "+Arrays.toString(ValueNeuronas[3]));
                sumerror = 0.0;
                for ( int i = 0; i<neuronas[3]; i++)
                {
                    sumerror += Math.pow(Objetivos[indice][i]-ValueNeuronas[3][i],2);
                }
                error += sumerror/2;
                indice++;
            }
            erro = error;
            //System.out.println("**************************************************************************************************");
            System.out.println(erro);
            if (error>0.01)
            {
                R.setValues(pesos, Objetivos, entradas, umbral, neuronas, ValueNeuronas,indice);
                R.Calcularerrores();
                pesos = R.getnewpesos();
                umbral = R.getnewumbrales();
                epocas++;
            }
            epocas--;
        }
        
    }
    

    void sigmoide(double d) 
    {
        //this.S = Math.tanh(d/10);
        this.S = 1.0/(1.0+Math.exp(-(d/10000)));   
        
    }
    
}
