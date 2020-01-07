
package neuronal;

import java.util.Arrays;

public class Perceptron {
 
    private double[] pesos;
    private double[][] objetivos;
    private double[][] entradas;
    private int numeroEntradas;
    private int numeroSalidas;
    private int numeroResultados;
    private static final double TASA_APRENDIZAJE = 0.5d;
 
    public double[][] getEntradas() {
        return entradas;
    }
 
    public void setEntradas(double[][] entradas) {
        this.entradas = entradas;
        this.numeroEntradas = entradas[0].length;
    }
 
    public double[][] getObjetivos() {
        return objetivos;
    }
 
    public void setObjetivos(double[][] objetivos) {
        this.objetivos = objetivos;
        this.numeroSalidas = objetivos[0].length;
    }
 
    public double[] getPesos() {
        return pesos;
    }
 
    public void setPesos(double[] pesos) {
        this.pesos = pesos;
    }
 
    /**
     * Inicializar los pesos sinápticos con números aleatorios del intervalo [-1, 1]
     */
    public void inicializarPesos() {
        pesos = new double[numeroEntradas];
        for (int i = 0; i < numeroEntradas; i++) {
            pesos[i] = Math.random();
        }
    }
 
    public void imprimirPesos() {
        for (int i = 0; i < numeroEntradas; i++) {
            System.out.println("pesos[" + i + "]=" + pesos[i]+";");
        }
    }
 
    /**
     * wj(k+1)=wj(k)+&#951;[z(k)&#8722;y(k)]xj(k), j =1,2,...,n+1
     */
    public void recalcularPesos(int posicionEntrada, double y,int salida) {
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] = pesos[i] + TASA_APRENDIZAJE * (objetivos[posicionEntrada][salida] - y) * entradas[posicionEntrada][i];
        }
    }
 
    public void entrenar() {
        int indice = 0;
        boolean es = false;
        double[] y = {0,0,0};
        //while (indice < entradas.length) {
            double suma = 0;
            

            for (int salidas = 0; salidas<numeroSalidas;salidas++)
            {
                for (int i = 0; i < numeroEntradas; i++) {
                    suma += (pesos[i] * entradas[indice][i]);//&#8721; x[i] * W[i] 
                }
                y[salidas] = suma >=0 ? 1 : 0;
            }////////
            System.out.println("Deseo: "+Arrays.toString(objetivos[0])+" y: "+Arrays.toString(y));
            
            for (int yes = 0; yes<numeroSalidas; yes++)
            {
                if (y[yes]==objetivos[indice][yes])
                {
                    es=true;
                }
                else
                {
                    es=false;
                }
                System.out.println("es: "+es);
            }
            
            /*                
                if ( y[salidas] == objetivos[indice][salidas])
                {
                    for(int i = 0; i < numeroEntradas; i++)
                    {
                        System.out.print(entradas[indice][i] + "\t");
                    }
                    System.out.print(" => Esperada = " + objetivos[indice][salidas] + ", Calculada = " + y[salidas] + "\n");
                }
                else
                {
                    for (int i = 0; i < numeroEntradas; i++) {
                    System.out.print(entradas[indice][i] + "\t");
                }
                    System.out.print(" => Esperada = " + objetivos[indice][salidas] + ", Calculada = " + y[salidas] + " [Error]\n");
                    System.out.println("Corrección de pesos");
                    recalcularPesos(indice, y[salidas],salidas);
                    imprimirPesos();
                    System.out.println("--");
                    salidas = salidas-1;
                }
            }
            indice++;*/
        }
    }
//}
    
