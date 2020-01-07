package cargadeletrasyredneuronal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class Cargadeletras{
    private static double S=0,tza_aprendizaje = 0.01;
    private static double[][][] pesos,errores;
    private static double[][] umbrales,Neuronas= new double[4][],erroresumbral;
    private static int tamañoletra = 390, tamañoObjetivo = 34, neuronasCapaoculta1 = 64, neuronasCapaoculta2 = 64, aprendizage = 1;
    private static double error =0, errorGeneral=0;
    private static double[] letra = new double[tamañoletra];
    private static double[] Objetivo =new double[tamañoObjetivo];
    public static void main(String[] args) throws IOException {
        String csvFile = "C:/Users/Vostro Placas/Desktop/letrasynumeros.csv";
        String line= "";
        Neuronas[0] = new double[tamañoletra];
        Neuronas[1] = new double[neuronasCapaoculta1];
        Neuronas[2] = new double[neuronasCapaoculta2];
        Neuronas[3] = new double[tamañoObjetivo];
        
        inicializar I = new inicializar();


        I.setvalue(Neuronas);
        I.inicializarpesos();
        I.inicializarumbrales();
        pesos = I.getPesos();
        umbrales = I.getUmbrales();
        
        errores= new double[pesos.length][][];
        errores[0] = new double[Neuronas[1].length][Neuronas[0].length];
        errores[1] = new double[Neuronas[2].length][Neuronas[1].length];
        errores[2] = new double[Neuronas[3].length][Neuronas[2].length];
        
        erroresumbral = new double [3][];
        erroresumbral[0] = new double[Neuronas[1].length];
        erroresumbral[1] = new double[Neuronas[2].length];
        erroresumbral[2] = new double[Neuronas[3].length];

        while (aprendizage>0)
        {
            BufferedReader br = null;    
            errorGeneral = 0.0;
            try  
            {
                br = new BufferedReader(new FileReader(csvFile));
                int prueba = 1;
                while((line = br.readLine())!=null)
                {
                    String[] leer = line.split(",");
                    for (int i = 0; i<Neuronas[0].length; i++)
                    {
                        letra[i] = Double.parseDouble(leer[i]);
                    }
                    for (int i = 0; i<Neuronas[3].length; i++)
                    {
                        Objetivo[i] = Double.parseDouble(leer[Neuronas[0].length+i]);
                    }
                    for (int k = 0; k<Neuronas.length; k++)
                    {
                        if(k==0)
                        {
                            Neuronas[0] = letra;
                        }
                        else
                        {
                            //System.out.println(Neuronas[k].length);
                            for(int i=0; i<Neuronas[k].length; i++)
                            {
                                double suma =0.0;
                                for (int j = 0; j<Neuronas[k-1].length; j++)
                                {
                                    suma += Neuronas[k-1][j]*pesos[k-1][i][j];
                                }
                                Neuronas[k][i] = suma*umbrales[k-1][i];
                                sigmoide(Neuronas[k][i]);
                                Neuronas[k][i] = S;
                            }
                        }
                    }
                    for (int k = 0; k < errores.length; k++)
                    {
                        if (k == 0)
                        {
                            for (int entrad = 0; entrad < Neuronas[0].length; entrad++)
                            {
                                for (int CO1 = 0; CO1 < Neuronas[1].length; CO1++)
                                {
                                    double sum2 = 0.0;
                                    for(int CO2 = 0; CO2 < Neuronas[2].length; CO2++)
                                    {
                                        double sum1 = 0.0;
                                        for(int Salidas = 0; Salidas < Neuronas[3].length; Salidas++)
                                        {
                                            sum1 += (pesos[2][Salidas][CO2])*(Neuronas[3][Salidas])*(1-Neuronas[3][Salidas])*(-(Objetivo[Salidas]-Neuronas[3][Salidas]));
                                        }
                                        sum2 += (pesos[1][CO1][CO2])*(Neuronas[2][CO1])*(1-Neuronas[2][CO1])*sum1;
                                    }
                                    errores[k][CO1][entrad] = (letra[entrad])*(Neuronas[1][CO1])*(1-Neuronas[1][CO1])*sum2;
                                    erroresumbral[k][CO1] = (Neuronas[1][CO1])*(1-Neuronas[1][CO1])*sum2;
                                }
                            }
                        }
                        else if(k==1)
                        {
                            for (int CO1 = 0; CO1 < Neuronas[1].length; CO1++)
                            {
                                for(int CO2 = 0; CO2 < Neuronas[2].length; CO2++)
                                {
                                    double sum1 = 0.0;
                                    for(int Salidas = 0; Salidas < Neuronas[3].length; Salidas++)
                                    {
                                        sum1 += (pesos[2][Salidas][CO2])*(Neuronas[3][Salidas])*(1-Neuronas[3][Salidas])*(-(Objetivo[Salidas]-Neuronas[3][Salidas]));
                                    }
                                    errores[k][CO2][CO1] = (Neuronas[1][CO1])*(Neuronas[2][CO2])*(1-Neuronas[2][CO2])*sum1;
                                    erroresumbral[k][CO2] = (Neuronas[2][CO2])*(1-Neuronas[2][CO2])*sum1;
                                }
                            }
                        }
                        else if(k==2)
                        {
                            for(int CO2 = 0; CO2 < Neuronas[2].length; CO2++)
                            {
                                for(int Salidas = 0; Salidas < Neuronas[3].length; Salidas++)
                                {
                                    errores[k][Salidas][CO2] = (Neuronas[2][CO2])*(Neuronas[3][Salidas])*(1-Neuronas[3][Salidas])*(-(Objetivo[Salidas]-Neuronas[3][Salidas]));
                                    erroresumbral[k][Salidas] = (Neuronas[3][Salidas])*(1-Neuronas[3][Salidas])*(-(Objetivo[Salidas]-Neuronas[3][Salidas]));
                                }
                            }
                        }
                    }
                    calculapesosnuevos();
                    error = 0.0;
                    for (int i =0; i<Neuronas[3].length;i++)
                    {
                        error += Math.pow(Objetivo[i]-Neuronas[3][i],2);
                    }
                    errorGeneral += error/2;
                    
                    //System.out.println("Deseado "+Arrays.toString(Objetivo)+ " Resultado: "+Arrays.toString(Neuronas[3]));
                    prueba++;
                }
            }catch (IOException e) {
            e.printStackTrace();
        }
            br.close();
            
            System.out.println(errorGeneral);
            if (errorGeneral > 0.01)
            {
                aprendizage++;
            }
            aprendizage--;
        }
    }

    private static void sigmoide(double d) {
        Cargadeletras.S = 1.0/(1.0+Math.exp(-(d/10)));  
    }
    
    
    private static void calculapesosnuevos() 
    {
        for (int k =0; k<pesos.length;k++)
        {
            for (int i = 0; i<pesos[k].length;i++)
            {
                for (int j = 0; j<pesos[k][i].length; j++)
                {
                    pesos[k][i][j] = pesos[k][i][j]-(tza_aprendizaje*errores[k][i][j]) ;
                }
            }
        }
        for (int k = 0; k<Neuronas.length-1;k++)
        {
            for (int j =0; j<Neuronas[k+1].length; j++)
            {
                umbrales[k][j] = (umbrales[k][j]) + (tza_aprendizaje*erroresumbral[k][j]);
            }
        }
        
        
    }
}
