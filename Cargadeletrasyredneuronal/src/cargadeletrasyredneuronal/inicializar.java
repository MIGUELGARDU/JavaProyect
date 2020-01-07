package cargadeletrasyredneuronal;


class inicializar {
    double[][][] pesos;
    double[][] umbral, neuronas;

    void setvalue(double[][] Neuronas) {
        this.neuronas = Neuronas;
    }

    void inicializarpesos() {
        pesos = new double [neuronas.length-1][][];
        for (int k = 0; k<pesos.length;k++)
            {
                pesos[k] = new double [neuronas[k+1].length][neuronas[k].length];
                for (int i = 0; i < pesos[k].length ; i++)
                {
                    for (int j = 0; j < pesos[k][i].length; j++)
                    {
                        pesos[k][i][j] = Math.random();
                    }
                }

            }
    }
    void inicializarumbrales() {
        umbral = new double[neuronas.length-1][];
        for (int i = 0;i<neuronas.length-1;i++)
        {
            umbral[i] = new double [neuronas[i+1].length]; 
            for (int j = 0; j < neuronas[i+1].length; j++)
            {
                umbral[i][j]=Math.random();
            }
        }
    }

    double[][][] getPesos() {
        return pesos;
    }

    double[][] getUmbrales() {
        return umbral;
    }
    
}
