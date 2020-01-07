/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redneuronalmulticapa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

/**
 * Simulación y entrenamiento de una RNA: Perceptron Multicapa
 * @author Global-Cruz Jesús
 * Bias= Umbral
 */
public class RedNeuronalMulticapa {
    //private double entradaMaxima,entradaMinima,salidaMaxima,salidaMinima;
    private ArrayList<double[]> entradas=new ArrayList<>();
    private ArrayList<double[]> salidas=new ArrayList<>();
    private int numeroDatos,numeroEntradas,numeroSalidas;
       
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String archivoEntrenamiento="C:\\Users\\Vostro Placas\\Desktop\\letrasynumeros.csv";
        //String archivoEntrenamiento="C:\\Users\\Global\\Documents\\Cruz_Jesus\\Letras.csv";
        RedNeuronalMulticapa rnm=new RedNeuronalMulticapa();
        rnm.asignarEntradas(390);//Difine el número de entradas
        rnm.asignarSalidas(34);//Asigna el número de salidas/respuestas
        rnm.cargarInfo(archivoEntrenamiento);
        
        //El tamaño del entero, define el tamaño de la red: ejem. tamaño=4-> 4 capas de la red.
        int[] estructuraRed={
            rnm.entradas.get(0).length,
            32,
            32,
            rnm.salidas.get(0).length
        };
        
        Perceptron p=new Perceptron(estructuraRed);
        
        /*while (!p.aprendizaje(rnm.entradas, rnm.salidas, 0.1, 0.01, 50000)) {            
            p=new Perceptron(estructuraRed);
        }
        System.out.println("Numero total de pesos: "+p.guardarPesos() );
        p.guardarBias();
        rnm.respuestaSalida(p);*/
        Hashtable<String, Double> datos = new Hashtable<String, Double>();
        datos.put("Hola", 0.00005);
        System.out.println(datos.get("Hola"));
        datos.replace("Hola", 5.55555);
        System.out.println(datos.get("Hola"));
        
    }   
    
    /**
     * 
     */
    public void cargarInfo(String nombreArchivo){
        String linea="";
        String separador=",";
        BufferedReader br=null;
        
        try {
            br=new BufferedReader(new FileReader(nombreArchivo));
            while( (linea=br.readLine())  != null){
                double[] entradasArchivo=new double[numeroEntradas];
                double[] salidasArchivo=new double[numeroSalidas];
                String[] datos=linea.split(separador);
                numeroDatos++;
                for(int i=0;i<datos.length;i++){
                    if(i<numeroEntradas){
                        entradasArchivo[i]=Double.parseDouble(datos[i]);
                    }
                    else{
                        salidasArchivo[i-numeroEntradas]=Double.parseDouble(datos[i]);
                    }
                }
                this.entradas.add(entradasArchivo);
                this.salidas.add(salidasArchivo);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } 
    }
    /**
     * 
     * @param valor
     * @param minimo
     * @param maximo
     * @return 
     */
    public double normalizar(double valor,double minimo,double maximo){
        return (valor-minimo)/(maximo/minimo);
    }
    
    /**
     * 
     * @param valor
     * @param minimo
     * @param maximo
     * @return 
     */
    public double desNormalizar(double valor,double minimo,double maximo){
        return valor*(maximo-minimo)+minimo;
    }
    
    /**
     * Método utilizado 
     * @param p
     */
    public void respuestaSalida(Perceptron p){
        System.out.println("");
        while(true){
            System.out.println("Inserta un valor menor a : "+ numeroDatos);
            double[] valor=new double[numeroDatos];
            String entradaTeclado="";
            Scanner scannerTeclado=new Scanner(System.in);
            entradaTeclado=scannerTeclado.nextLine();
            valor=this.entradas.get(Integer.parseInt(entradaTeclado));
            System.out.println(Arrays.toString(valor));
            
            double[] sal=p.activar(valor);
            System.out.println(Arrays.toString(sal));
            System.out.println("Tamaño salidas: "+sal.length);
            for(int i=0;i<numeroSalidas;i++){
                if(sal[i]>0.5){
                    System.out.println("Respuesta: 1");
                }
                else{
                    System.out.println("Respuesta: 0");
                }
            }
        }
    }
    
    public void asignarEntradas(int valor){
        numeroEntradas=valor;
    }
    
    public void asignarSalidas(int valor){
        numeroSalidas=valor;
    }
    
}
