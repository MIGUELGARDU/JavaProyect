/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redneuronalmulticapa;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Global
 */
public class Capa {
    public ArrayList<Neurona> neuronas;
    public int numeroDeNeuronas;
    public double[] salida;
    
    /**
     * 
     * @param numeroNeuronas
     * @param numeroEntradas
     * @param r
     */
    public Capa(int numeroNeuronas,int numeroEntradas,Random r){
        numeroDeNeuronas=numeroNeuronas;
        neuronas=new ArrayList<Neurona>();
        for (int i = 0; i < numeroDeNeuronas; i++) {
            neuronas.add(new Neurona(numeroEntradas, r));
        }
    }
    
    /**
     * 
     * @param entradas
     * @return 
     */
    public double[] activar(double[] entradas){
        double[] salidas = new double[numeroDeNeuronas];
        for(int i=0;i<numeroDeNeuronas;i++){
            salidas[i]=(neuronas.get(i).activar(entradas));
        }
        salida=salidas;
        return salidas;
    }
    
}
