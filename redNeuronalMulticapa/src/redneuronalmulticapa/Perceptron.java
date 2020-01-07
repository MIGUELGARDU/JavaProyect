/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redneuronalmulticapa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Global
 */
public class Perceptron {
    private ArrayList<Capa> capas;
    private ArrayList<double[]> sigmas;
    private ArrayList<double[][]> deltas;
    
    /**
     * 
     * @param neuronasPorCapa 
     */
    public Perceptron(int[] neuronasPorCapa){
        capas=new ArrayList<>();
        Random r=new Random();
        for(int i=0;i<neuronasPorCapa.length;i++){
            if (i==0) {
                capas.add(new Capa(neuronasPorCapa[i], neuronasPorCapa[i], r));    
            }
            else{
                capas.add(new Capa(neuronasPorCapa[i], neuronasPorCapa[i-1], r));//?
            }
        }
    }
    
    /**
     * 
     * @param entradas
     * @return 
     */
    public double[] activar(double[] entradas){
        double[] salidas = new double[0];
        for(int i=1;i<capas.size();i++){
            salidas=capas.get(i).activar(entradas);
            entradas=salidas;
        }
        return salidas;
    }
    
    /**
     * 
     * @param entrada  ArrayList con los valores de entrada paar la red.
     * @param salidaDeseada ArrayListi con los valores de salida de la reed.
     * @param alpha Valor del umbral
     * @param errorMaximo Valor maximo permitido
     * @param iteraciones Número máximo de Iteraciones para el aprendizaje
     * @return 
     */
    public boolean aprendizaje(ArrayList<double[]> entrada,ArrayList<double[]> salidaDeseada,double alpha,double errorMaximo,int iteraciones){
        double err=99999;
        while(err>errorMaximo){
            iteraciones--;
            if(iteraciones<=0){
                System.out.println("-------Minimo Local--------");
                return false;
            }
            aplicarBackpropagation(entrada, salidaDeseada, alpha);
            err=errorGeneral(entrada, salidaDeseada);
            System.out.println(err);
            //System.out.println("Error General " + err);
        }
        return true;
    }
    
    /**
     * 
     * @param entrada
     * @param salidaDeseada
     * @param alpha
     */
    public void aplicarBackpropagation(ArrayList<double[]> entrada,ArrayList<double[]> salidaDeseada,double alpha){
        setDeltas();
        sigmas=new ArrayList<>();
        for(int i=0;i<entrada.size();i++){
            activar(entrada.get(i));
            setSigmas(salidaDeseada.get(i));
            actualizarBias(alpha);
            addDeltas();
        }
        actualizarPesos(alpha);
    }
    
    /**
     * 
     * @param salidaReal
     * @param salidaDeseada
     * @return 
     */
    public double errorIndivual(double [] salidaReal,double[] salidaDeseada){
        double error=0;
        for(int i=0;i<salidaReal.length;i++){
            error+= 0.5*Math.pow(salidaReal[i]-salidaDeseada[i],2);
        }
        return error;
    }
    
    /**
     * Error general del sistema con respecto a las entradas
     * @param entrada valores que ingresan al sistema
     * @param salidaDeseada valores que se desean
     * @return 
     */
    public double errorGeneral(ArrayList<double[]> entrada,ArrayList<double[]> salidaDeseada){
        double error=0;
        for(int i=0;i<entrada.size();i++){
            error+= errorIndivual(activar(entrada.get(i)), salidaDeseada.get(i));
        }
        return error;
    }
        
    /**
     * Sumatoria
     * @param salidasDeseada
     */
    public void setSigmas(double[] salidasDeseada){
        for(int i=0;i<capas.size();i++){
            sigmas.add(new double[capas.get(i).numeroDeNeuronas]);
        }
        
        for(int i=capas.size()-1;i>=0;i--){
            for(int j=0;j<capas.get(i).numeroDeNeuronas;j++){
                if(i==capas.size()-1){
                    //Codigo del Video
                    double y=capas.get(i).salida[j];
                    sigmas.get(i)[j]=(y-salidasDeseada[j])*Neurona.derivadaSigmoide(y);
                    //Codigo de la pag.
                    //double y=capas.get(i).neuronas.get(j).ultimaActivación;
                    //sigmas.get(i)[j]=(Neurona.sigmoide(y) - salidasDeseada[j]) * Neurona.derivadaSigmoide(y);
                }
                else{
                    double suma=0;
                    for(int k=0;k<capas.get(i+1).numeroDeNeuronas;k++){
                        suma+=capas.get(i+1).neuronas.get(k).pesos[j] * sigmas.get(i+1)[k];
                    }
                    sigmas.get(i)[j]=Neurona.derivadaSigmoide(capas.get(i).neuronas.get(j).ultimaActivación) * suma;
                }
            }
        }
    }
    
    /**
     * Asigna Deltas y resetea valores 
     */
    public void setDeltas(){
        deltas=new ArrayList<>();
        for(int i=0;i<capas.size();i++){
            deltas.add(new double[capas.get(i).numeroDeNeuronas][capas.get(i).neuronas.get(0).pesos.length]);
            
        }
    }
    
    /**
     * Direccion de la pendiente el decenso de gradiente
     */
    public void addDeltas(){
        for(int i=1;i<capas.size();i++){
            for(int j=0;j<capas.get(i).numeroDeNeuronas;j++){
                for(int k=0;k<capas.get(i).neuronas.get(j).pesos.length;k++){
                    //Codigo de la Pag
                    deltas.get(i)[j][k] += (sigmas.get(i)[j] * Neurona.sigmoide(capas.get(i-1).neuronas.get(k).ultimaActivación));
                }
            }
        }
    }
       
    /**
     * Actualización de los Umbrales de cada neurona
     * @param alpha
     */
    public void actualizarBias(double alpha){
        for(int i=0;i<capas.size();i++){
            for(int j=0;j<capas.get(i).numeroDeNeuronas;j++){
                capas.get(i).neuronas.get(j).bias-= alpha * sigmas.get(i)[j];
            }
        }
    }
    
    /**
     * 
     * @param alpha
     */
    public void actualizarPesos(double alpha){
        for(int i=0;i<capas.size();i++){
            for(int j=0;j<capas.get(i).numeroDeNeuronas;j++){
                for(int k=0;k<capas.get(i).neuronas.get(j).pesos.length;k++){
                    capas.get(i).neuronas.get(j).pesos[k] -= alpha * deltas.get(i)[j][k];
                }
            }
        }
    }
    
    /**
     * Crea un archivo CSV que contiene los pesos de la red.
     * @return Numero de pesos en la red
     */
    public int guardarPesos(){
        int total=0;
        String separador=",",sigLinea="\n";
        
        String rutaArchivo="C:\\Users\\Vostro Placas\\Desktop\\pesosPesados.csv";
        File file=new File(rutaArchivo);
        try{
            FileWriter fw=new FileWriter(file);
            for(int i=0; i<capas.size(); i++){
                for(int j=0; j<capas.get(i).numeroDeNeuronas; j++){
                    for(int k=0; k<capas.get(i).neuronas.get(j).pesos.length; k++){
                        fw.write( capas.get(i).neuronas.get(j).pesos[k]+separador );
                        total++;
                    }
                    fw.write(sigLinea);
                }
            }
            fw.flush();
            fw.close();
            System.out.println("¡Pesos Guardados Correctamente!");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return total;
    }
    
    /**
     * Crea un archivo CSV que contiene los valores de las Bias(Umbrales)
     * 
     */
    public void guardarBias(){
        String separador=",",sigLinea="\n";
        String rutaArchivo="C:\\Users\\Vostro Placas\\Desktop\\bias.csv";
        File file=new File(rutaArchivo);
        try{
            FileWriter fw=new FileWriter(file);
            for(int i=0; i<capas.size(); i++){
                for(int j=0; j<capas.get(i).numeroDeNeuronas; j++){
                    //System.out.println("Bias: "+j+" "+ capas.get(i).neuronas.get(j).bias);
                    fw.write( capas.get(i).neuronas.get(j).bias+separador);
                }
                fw.write(sigLinea);
            }
            fw.flush();
            fw.close();
            System.out.println("¡Bias Guardados Correctamente!");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
