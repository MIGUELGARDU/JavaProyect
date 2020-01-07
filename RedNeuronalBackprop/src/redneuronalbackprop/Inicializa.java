
package redneuronalbackprop;


class Inicializa {
 
    private double [][][] pesos;
    private double[][] umbral,entradas,Objetivos;
    private int[] neuronas;
    private int numEntradas, numObjetivos, numCapa1 = 32, numCapa2 = 32;
    
    Entrena E = new Entrena();
    

    void setEntradas(double[][] entradas) 
    {
        this.entradas = entradas;
        this.numEntradas = entradas[0].length;
    }
    

    void setObjetivos(double[][] objetivos) 
    {
        this.Objetivos = objetivos;
        this.numObjetivos = objetivos[0].length;
    }

    void inicializar() 
    {
        neuronas = new int[4];
        neuronas[0] = numEntradas;
        neuronas[1] = numCapa1;
        neuronas[2] = numCapa2;
        neuronas[3] = numObjetivos;
        pesos = new double [neuronas.length-1][][];
        umbral = new double[neuronas.length-1][];
        for (int k = 0; k<pesos.length;k++)
        {
            pesos[k] = new double [neuronas[k+1]][neuronas[k]];
            for (int i = 0; i < pesos[k].length ; i++)
            {
                for (int j = 0; j < pesos[k][i].length; j++)
                {
                    pesos[k][i][j] = Math.random();
                    //System.out.println("Peso en la Capa " +k+" de la Neurona "+i+" a "+j+": "+ pesos[k][i][j]);
                }
            }
            
        }
        for (int i = 0;i<neuronas.length-1;i++)
        {
            umbral[i] = new double [neuronas[i]]; 
            for (int j = 0; j < neuronas[i+1]; j++)
            {
                umbral[i][j]=Math.random();
                //System.out.println("Umbral en la Capa " +i+" de la Neurona "+j+": "+ umbral[i][j]);
            }
        }
        
        E.setValores(pesos,umbral,entradas,Objetivos,neuronas);
        E.entrena();
        
    }
    
}
