import java.util.Arrays;
import java.util.Random;

public class MainClass {

    static class Letras implements Comparable<Letras> {

        public int X;
        public int XW, Y, YH;

        public Letras(int X, int XW, int Y, int YH) {
            this.X = X;
            this.XW = XW;
            this.Y = Y;
            this.YH = YH;
        }

        @Override
        public int compareTo(Letras o) {
            if (X < o.X) {
                return -1;
            }
            if (X > o.X) {
                return 1;
            }
            return 0;
        }
    }
    
    static void imprimeArrayPersonas(Letras[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println((i+1) + ". x: " + array[i].X + " - xw: " + array[i].XW + " - y: " + array[i].Y+" - yh: "+array[i].YH);
        }
    }

    public static void main(String[] args) {
        Letras[] arrayletra = new Letras[5];
        for (int i=0;i<arrayletra.length;i++)
        {
            arrayletra[i] = new Letras(new Random().nextInt(100), new Random().nextInt(100), new Random().nextInt(100), new Random().nextInt(100));    
        }

        System.out.println("Array sin ordenar por altura");
        imprimeArrayPersonas(arrayletra);
        // Ordeno el array de personas por altura (de menor a mayor).
        Arrays.sort(arrayletra);
        System.out.println("Array ordenado por altura");
        imprimeArrayPersonas(arrayletra);
    }
}