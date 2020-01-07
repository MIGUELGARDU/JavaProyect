
package redneuronalbackprop;

import java.util.Arrays;

class Recalcular {
    double[][][] pesos,errores;
    double[][] Objetivos,entradas,umbral,a,erroresumbral;
    int[] neuronas;
    double[] rho4,rho3,rho2;
    double sum1,sum2, tza_aprendizaje = 20;
    int indice;
    void setValues(double[][][] pesos, double[][] Objetivos, double[][] entradas, double[][] umbral, int[] neuronas, double[][] ValueNeuronas,int indice) 
    {
        this.pesos = pesos;
        this.Objetivos = Objetivos;
        this.entradas = entradas;
        this.umbral = umbral;
        this.a = ValueNeuronas;
        this.indice = indice;
        this.neuronas = neuronas;
    }
    public double[][][] getnewpesos()
    {
        return pesos;
    }
    public double[][] getnewumbrales()
    {
        return umbral;
    }
    void Calcularerrores() 
    {
        rho4 = new double[neuronas[3]];
        rho3 = new double[neuronas[2]];
        rho2 = new double[neuronas[1]];
        errores= new double[pesos.length][][];
        errores[0] = new double[a[1].length][a[0].length];
        errores[1] = new double[a[2].length][a[1].length];
        errores[2] = new double[a[3].length][a[2].length];
        erroresumbral = new double [3][];
        erroresumbral[0] = new double[neuronas[1]];
        erroresumbral[1] = new double[neuronas[2]];
        erroresumbral[2] = new double[neuronas[3]];
        indice = 0;
        while (indice<entradas.length)
        {
            //System.out.println(Arrays.toString(entradas[indice]));
            /*for (int k = 0; k < errores.length; k++)
            {
                if (k == 0)
                {
                    for (int entrad = 0; entrad < neuronas[0]; entrad++)
                    {
                        for (int CO1 = 0; CO1 < neuronas[1]; CO1++)
                        {
                            sum2=0;
                            for(int CO2 = 0; CO2 < neuronas[2]; CO2++)
                            {
                                sum1 = 0;
                                for(int Salidas = 0; Salidas < neuronas[3]; Salidas++)
                                {
                                    sum1 += (pesos[2][Salidas][CO2])*(a[3][Salidas])*(1-a[3][Salidas])*(-(Objetivos[indice][Salidas]-a[3][Salidas]));
                                }
                                sum2 += (pesos[1][CO1][CO2])*(a[2][CO1])*(1-a[2][CO1])*sum1;
                            }
                            errores[k][CO1][entrad] = (entradas[indice][entrad])*(a[1][CO1])*(1-a[1][CO1])*sum2;
                            erroresumbral[k][CO1] = (a[1][CO1])*(1-a[1][CO1])*sum2;
                        }
                    }
                }
                else if(k==1)
                {
                    for (int CO1 = 0; CO1 < neuronas[1]; CO1++)
                    {
                        for(int CO2 = 0; CO2 < neuronas[2]; CO2++)
                        {
                            sum1 = 0;
                            for(int Salidas = 0; Salidas < neuronas[3]; Salidas++)
                            {
                                sum1 += (pesos[2][Salidas][CO2])*(a[3][Salidas])*(1-a[3][Salidas])*(-(Objetivos[indice][Salidas]-a[3][Salidas]));
                            }
                            errores[k][CO2][CO1] = (a[1][CO1])*(a[2][CO2])*(1-a[2][CO2])*sum1;
                            erroresumbral[k][CO2] = (a[2][CO2])*(1-a[2][CO2])*sum1;
                        }
                    }
                }
                else if(k==2)
                {
                    for(int CO2 = 0; CO2 < neuronas[2]; CO2++)
                    {
                        for(int Salidas = 0; Salidas < neuronas[3]; Salidas++)
                        {
                            errores[k][Salidas][CO2] = (a[2][CO2])*(a[3][Salidas])*(1-a[3][Salidas])*(-(Objetivos[indice][Salidas]-a[3][Salidas]));
                            erroresumbral[k][Salidas] = (a[3][Salidas])*(1-a[3][Salidas])*(-(Objetivos[indice][Salidas]-a[3][Salidas]));
                        }
                    }
                    
                }
            }
            */
            for (int i = 0; i<neuronas[0]; i++)
            {
                for (int j = 0; j<neuronas[1]; j++)
                {
                    sum2 = 0.0;
                    for (int k = 0; k < neuronas[2]; k++)
                    {
                        sum1 = 0.0;
                        for (int m = 0; m<neuronas[3]; m++)
                        {
                            rho4[m] = a[3][m]*(1.0-a[3][m])*(-(Objetivos[indice][m]-a[3][m]));
                            sum1 += pesos[2][m][k]*rho4[m];
                            errores[2][m][k] = a[2][k]*rho4[m];
                            erroresumbral[2][m] = rho4[m];
                        }
                        rho3[k] = a[2][k]*(1.0-a[2][k])*sum1;
                        sum2 += pesos[1][k][j]*rho3[k];
                        errores[1][k][j] = a[1][j]*rho3[k];
                        erroresumbral[1][k] = rho3[k]; 
                    }
                    rho2[j] = a[1][j]*(1.0-a[1][j])*sum2;
                    errores[0][j][i] = a[0][i]*rho2[j];
                    erroresumbral[0][j] = rho2[j]; 
                }
            } 
            calculapesosnuevos(); 
            indice++;
        }
    }
    private void calculapesosnuevos() 
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
        for (int k = 0; k<neuronas.length-1;k++)
        {
            for (int i =0; i<neuronas[k+1]; i++)
            {
                umbral[k][i] = (umbral[k][i]) - (tza_aprendizaje*erroresumbral[k][i]);
            }
        }
        
        
    }
    
}
