/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redneuronalmulticapa;

import java.util.Random;

/**
 *
 * @author Global
 */
public class Neurona {
    public double [] pesos;
    public double ultimaActivación;
    public double bias;// Umbral
    
    /**
     *
     * @param numeroDeEntradas
     * @param r
    */
    public Neurona(int numeroDeEntradas,Random r){
        bias=10*r.nextDouble()-5;
        pesos=new double[numeroDeEntradas];
        for (int i = 0; i < numeroDeEntradas; i++) {
            pesos[i]=10*r.nextDouble()-5;
        }
    }
    
    /**
     * 
     * @param entradas
     * @return 
    */
    public double activar(double[] entradas){
        double activacion=bias;
        for(int i=0;i<pesos.length;i++){
            activacion = activacion+ (pesos[i] * entradas[i]);
        }
        ultimaActivación=activacion;
        return sigmoide(activacion);
    }
    
    /**
     * 
     * @param entrada
     * @return 
     */
    public static double sigmoide(double entrada){
        return 1/(1+Math.exp(-entrada));
    }
    
    /**
    * @param entrada
    * @return 
    */
    public static double derivadaSigmoide(double entrada){
        double y=sigmoide(entrada);
        return y*(1-y);
    }
    
}
