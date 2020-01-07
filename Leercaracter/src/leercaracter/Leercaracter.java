/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leercaracter;


import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Placas
 */
public class Leercaracter extends JFrame{
    static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
    private static List<MatOfPoint> contours;
    public static void main(String[] args) throws IOException, InterruptedException {
        Leercaracter frame = new Leercaracter();
        Container c = frame.getContentPane();
        Rect rectltra;
        Mat imagen,gray=new Mat(),blur = new Mat(), th = new Mat(),hierarchy= new Mat(),negro, letra ;
        imagen = Imgcodecs.imread("C:/Users/Vostro Placas/Pictures/V-Zcam.png");
        negro = new Mat(imagen.rows(),imagen.cols(),CvType.CV_8UC4,new Scalar(255,255,255));
        Imgproc.cvtColor(imagen, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(gray, blur, new Size(15,15), 0);
        Imgproc.adaptiveThreshold(blur, th, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 29, 5);
        contours = new ArrayList<MatOfPoint>();
        final ArrayList contornos = new ArrayList();
        int immms =0;
        Imgproc.findContours(th, contours, hierarchy,Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_NONE, new Point(0, 0));
        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
            if (Imgproc.contourArea(contours.get(contourIdx)) > 1) {
                MatOfPoint2f aprox = new MatOfPoint2f();
                MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(contourIdx).toArray());
                double appro = Imgproc.arcLength(contour2f, true) * 0.02;
                Imgproc.approxPolyDP(contour2f, aprox, appro, true);
                MatOfPoint points = new MatOfPoint(aprox.toArray());
                Rect rect = Imgproc.boundingRect(points);
                double prop = (rect.width * 100) / rect.height;
                if (prop > 10 && prop < 100) {
                    if (rect.height > 100) {
                        Imgproc.rectangle(imagen, new Point(rect.x-5,rect.y-5), new Point(rect.x+rect.width+5,rect.y+rect.height+5), new Scalar(0,255,0));
                        //rectltra = new Rect(((rect.x)-5),((rect.y)-5),((rect.x+rect.width)+5),((rect.y+rect.height)+5));
                        letra = new Mat(gray, rect);
                        Imgproc.resize(letra, letra, new Size(130,300));
                        Imgcodecs.imwrite("C:/Users/Vostro Placas/Pictures/Imagenes entrenadas/"+immms+"Imagen.png", letra);
                        immms++;
                    }
                }
            }
        }
        MatOfByte buf=new MatOfByte();
        Imgcodecs.imencode(".png", imagen, buf);
        byte ba[]=buf.toArray();
        JPanel panel = new JPanel();
        panel.setSize(imagen.width(),imagen.height());
        panel.setBackground(Color.BLACK);
        ImageIcon icon = new ImageIcon(ba);
        JLabel label = new JLabel();
        label.setIcon(icon);
        panel.add(label);
        frame.add(panel);
        frame.pack();
        frame.show();

        
        
        
        
    }
    public Leercaracter()
    {
        this.getContentPane();
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent evt)
            {
                System.exit(0);
            }
        });
    }
    
}
