package ocrplacassystemas;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Ventana {
    
    private JFrame Ventana;
    private JPanel Ventanaa, Carril;
    private Paint image;
    private Paintp imagep;
    private PaintPla imagepla;
    private PaintPlac imageplac;
    private PaintPlaca imageplaca;
    private PaintPlacaa imageplacaa;
    
    public Ventana() throws IOException
    {
        buildGUI();
    }

    private void buildGUI() {
        Ventana = new JFrame("OCRPlacas");
        Ventanaa = new JPanel();
        Carril = new JPanel();
        Carril.setPreferredSize(new Dimension(900,900));
        Ventanaa.setLayout(new GridBagLayout());
        image = new Paint(new ImageIcon("figs/320x240.gif").getImage());
        imagep = new Paintp(new ImageIcon("figs/320x240.gif").getImage());
        imagepla = new PaintPla(new ImageIcon("figs/320x240.gif").getImage());
        imageplac = new PaintPlac(new ImageIcon("figs/320x240.gif").getImage());
        imageplaca = new PaintPlaca(new ImageIcon("figs/320x240.gif").getImage());
        imageplacaa = new PaintPlacaa(new ImageIcon("figs/320x240.gif").getImage());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        Carril.add(image, c);
        c.gridx = 1;
        c.gridy = 0;
        Carril.add(imagep,c);
        c.gridx = 0;
        c.gridy = 1;
        Carril.add(imagepla,c);
        c.gridx = 1;
        c.gridy = 1;
        Carril.add(imageplac, c);
        c.gridx = 0;
        c.gridy = 2;
        Carril.add(imageplaca,c);
        c.gridx = 1;
        c.gridy = 2;
        Carril.add(imageplacaa,c);
        c.gridx =0;
        c.gridy = 0;
        Ventanaa.add(Carril,c);
        
        Ventana.setContentPane(Ventanaa);
        Ventana.setSize(1000,1000);
        Ventana.setLocation(400,20);
        Ventana.setResizable(false);
        Ventana.setVisible(true);
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        start();   
    }
    
    private Boolean beging = false;
    private VideoCapture video = null;
    private CaptureThread thread = null;
    private Procesar tread = null;
    private void start() {
        if(beging ==false)
        {
            video = new VideoCapture(0);
            
            if (video.isOpened())
            {
                video.set(3, 320);
                video.set(4, 240);
                thread = new CaptureThread();
                tread = new Procesar();
                thread.start();
                tread.start();
                beging = true;
            }
        }
    }
        
    
    private MatOfByte matOfByte1 = new MatOfByte();
    private MatOfByte matOfByte2 = new MatOfByte();
    private MatOfByte matOfByte3 = new MatOfByte();
    private MatOfByte matOfByte4 = new MatOfByte();
    private MatOfByte matOfByte5 = new MatOfByte();
    private MatOfByte matOfByte6 = new MatOfByte();
    private BufferedImage bufImage1 = null;
    private BufferedImage bufImage2 = null;
    private BufferedImage bufImage3 = null;
    private BufferedImage bufImage4 = null;
    private BufferedImage bufImage5 = null;
    private BufferedImage bufImage6 = null;
    private InputStream in1;
    private InputStream in2;
    private InputStream in3;
    private InputStream in4;
    private InputStream in5;
    private InputStream in6;
    private Mat frameaux =new Mat();
    private Mat proces = new Mat();
    private Mat Improces = new Mat();
    private Mat frame = new Mat(240,320,CvType.CV_8UC3);
    ArrayList<int[]> lista;
    private boolean Termine = true,mandoimagen = false;
    class CaptureThread extends Thread
    {
        @Override
        public void run()
        {
            while(beging)
            {
                if(video.isOpened())
                {
                    video.read(frame);
                    //Imgproc.resize(frame, frame, new Size(720,240));
                    Imgcodecs.imencode(".jpg", frame, matOfByte1);
                    byte[] byteArray1 = matOfByte1.toArray();
                    try
                    {
                        in1 = new ByteArrayInputStream(byteArray1);
                        bufImage1 = ImageIO.read(in1);
                    }
                    catch(IOException ex){}
                    image.updateImage(bufImage1);
                    if (Termine)
                    {
                        mandoimagen = true;
                    }
                }
            }
        }
    }
    
    
    //Mat gray = new Mat(),Sobelx = new Mat(),Sobely = new Mat(),Sobel = new Mat(), blur = new Mat(), adaptiveth = new Mat(), adaptiveth1 = new Mat(), canny = new Mat(),hierarchy,rectangu,box = new Mat(),rotado;
    Mat gray = new Mat(),Sobelx= new Mat(),Sobely = new Mat(), Sobel= new Mat(), canny = new Mat(),canny2 = new Mat(),adaptiveth = new Mat(), box, rectangu,hierarchy,hierarch,rotado,trasladado,color= new Mat(),grayy = new Mat(),laplacian = new Mat();
    Mat sum1= new Mat(),sum2 = new Mat(), sum3=new Mat(),sum4 = new Mat(), sum5 = new Mat();
    Rect placaaa;
    Point puntodegiro;
    ArrayList<MatOfPoint> contours,contour;
    class Procesar extends Thread
    {
        @Override
        public void run()
        {
            while(beging)
            {     
                if(mandoimagen)
                {
                    mandoimagen = false;
                    Termine = false;
                    rectangu = new Mat();
                    box = frame.clone();
                    color = frame.clone();
                    Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);
                    Imgproc.Sobel(gray, Sobelx, 3, 1, 0,3,1,0,Core.BORDER_CONSTANT);
                    Imgproc.Sobel(gray, Sobely, 3, 0, 1,3,1,0,Core.BORDER_CONSTANT);
                    Imgproc.Laplacian(gray, laplacian, 10);
                    Core.convertScaleAbs(Sobelx, Sobelx);
                    Core.convertScaleAbs(Sobely, Sobely);
                    Core.convertScaleAbs(laplacian, laplacian);
                    //Core.addWeighted(Sobelx, 0.9, Sobely, 0.9, 1.0, Sobel);
                    Core.add(Sobelx,Sobely,Sobel);
                    Core.add(Sobel, new Scalar(0), sum1);
                    Core.subtract(sum1, Core.mean(sum1),sum2);
                    Core.add(sum2, new Scalar(100), sum2);
                    Imgproc.GaussianBlur(sum2,sum2,new Size(7,7),1);
                    Imgproc.Canny(sum2,canny, 0, 255);
                    Imgproc.Canny(laplacian,canny2, 0, 255);
                    Imgproc.threshold(Sobel, sum3, 0,255, Imgproc.THRESH_TRIANGLE);
                    Imgproc.GaussianBlur(sum3, sum3, new Size(5,5), 0);
                    Imgcodecs.imencode(".jpg",Sobelx,matOfByte2);
                    byte[] byteArray2 = matOfByte2.toArray();
                    try
                    {
                        in2 = new ByteArrayInputStream(byteArray2);
                        bufImage2 = ImageIO.read(in2);

                    }
                    catch(IOException ex)
                    {} 

                    imagep.updateImage(bufImage2); 
                    Imgcodecs.imencode(".jpg",Sobel,matOfByte3);
                    byte[] byteArray3 = matOfByte3.toArray();
                    try
                    {
                        in3 = new ByteArrayInputStream(byteArray3);
                        bufImage3 = ImageIO.read(in3);

                    }
                    catch(IOException ex)
                    {}
                    imagepla.updateImage(bufImage3);
                    
                    Imgcodecs.imencode(".jpg",canny,matOfByte4);
                    byte[] byteArray4 = matOfByte4.toArray();
                    try
                    {
                        in4 = new ByteArrayInputStream(byteArray4);
                        bufImage4 = ImageIO.read(in4);

                    }
                    catch(IOException ex)
                    {}
                    imageplac.updateImage(bufImage4);
                    
                    Imgcodecs.imencode(".jpg",sum3,matOfByte5);
                    byte[] byteArray5 = matOfByte5.toArray();
                    try
                    {
                        in5 = new ByteArrayInputStream(byteArray5);
                        bufImage5 = ImageIO.read(in5);

                    }
                    catch(IOException ex)
                    {}
                    imageplaca.updateImage(bufImage5);
                    
                    
                    Imgcodecs.imencode(".jpg",canny2,matOfByte6);
                    byte[] byteArray6 = matOfByte6.toArray();
                    try
                    {
                        in6 = new ByteArrayInputStream(byteArray6);
                        bufImage6 = ImageIO.read(in6);

                    }
                    catch(IOException ex)
                    {}
                    imageplacaa.updateImage(bufImage6);
                    
                    /*contours = new ArrayList<MatOfPoint>();
                    Imgproc.findContours(canny, contours, hierarchy = new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
                    for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++)
                    {
                        if(Imgproc.contourArea(contours.get(contourIdx))>1)
                        {
                            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(contourIdx).toArray());
                            RotatedRect rect = Imgproc.minAreaRect(contour2f);
                            Point[] points = new Point[4];
                            rect.points(points);
                            Imgproc.boxPoints(rect,rectangu);
                            double[][] arreglo = new double[4][2];
                            double[] aux = new double[2];
                            for (int i =0; i<rectangu.rows(); i++)
                                for (int j = 0; j < rectangu.cols(); j++)
                                    arreglo[i][j] = rectangu.get(i, j)[0];
                            for (int i = 0 ; i<arreglo.length-1;i++)
                            {
                                for(int j=0; j<arreglo.length-i-1;j++)
                                    if(arreglo[j+1][0]<arreglo[j][0])
                                    {
                                        aux = arreglo[j+1];
                                        arreglo[j+1] = arreglo[j];
                                        arreglo[j] = aux;
                                    }
                            }
                            if(arreglo[1][1]<arreglo[0][1])
                            {
                                aux = arreglo[1];
                                arreglo[1] = arreglo[0];
                                arreglo[0] = aux;
                            }
                            if(arreglo[3][1]<arreglo[2][1])
                            {
                                aux = arreglo[3];
                                arreglo[3] = arreglo[2];
                                arreglo[2] = aux;
                            }
                            double largo13 = 0,ancho12 = 0,ancho34 = 0,largo24 = 0,Prop = 0,angulo = 0;
                            boolean placa = false;
                            largo13 = Math.sqrt(Math.pow(arreglo[2][0]-arreglo[0][0],2)+Math.pow(arreglo[2][1]-arreglo[0][1],2));
                            ancho34 = Math.sqrt(Math.pow(arreglo[3][0]-arreglo[2][0],2)+Math.pow(arreglo[3][1]-arreglo[2][1],2));
                            largo24 = Math.sqrt(Math.pow(arreglo[3][0]-arreglo[1][0],2)+Math.pow(arreglo[3][1]-arreglo[1][1],2));
                            ancho12 = Math.sqrt(Math.pow(arreglo[1][0]-arreglo[0][0],2)+Math.pow(arreglo[1][1]-arreglo[0][1],2));
                            Prop = (ancho12*100)/largo13;
                            rotado = new Mat();
                            trasladado = new Mat();
                            
                            //if(largo13<=largo24+30 && largo13>=largo24-30 && ancho34<=ancho12+30 && ancho34>=ancho12-30 && largo13>100 && largo13<400 && Prop>30 && Prop<100)
                            if( largo13>50 && largo13<300 && Prop>30 && Prop<100)
                                placa = true;
                            
                            if (placa)
                            {
                                angulo = Math.toDegrees(Math.asin((arreglo[2][1]-arreglo[0][1])/largo13));
                                puntodegiro = new Point(arreglo[0][0],arreglo[0][1]);
                                Mat rotacion = Imgproc.getRotationMatrix2D(puntodegiro, angulo, 1.0);
                                Imgproc.warpAffine(color, rotado, rotacion, new Size(gray.cols(),gray.rows()));
                                placaaa = new Rect(puntodegiro,new Size(largo13,ancho12));
                                try
                                {
                                    trasladado = new Mat(rotado,placaaa);
                                    Imgproc.resize(trasladado, trasladado, new Size(300,150));
                                    //System.out.println(rect.angle);
                                    Imgproc.line(box, new Point(arreglo[0][0], arreglo[0][1]), new Point(arreglo[2][0], arreglo[2][1]), new Scalar(0, 0, 255));
                                    Imgproc.line(box, new Point(arreglo[2][0], arreglo[2][1]), new Point(arreglo[3][0], arreglo[3][1]), new Scalar(0, 0, 255));
                                    Imgproc.line(box, new Point(arreglo[3][0], arreglo[3][1]), new Point(arreglo[1][0], arreglo[1][1]), new Scalar(0, 0, 255));
                                    Imgproc.line(box, new Point(arreglo[1][0], arreglo[1][1]), new Point(arreglo[0][0], arreglo[0][1]), new Scalar(0, 0, 255));
                                    Imgproc.circle(box, new Point(arreglo[0][0],arreglo[0][1]), 5, new Scalar(255,0,0),-1);
                                    Imgproc.circle(box, new Point(arreglo[1][0],arreglo[1][1]), 5, new Scalar(255,255,255),-1);
                                    Imgproc.circle(box, new Point(arreglo[2][0],arreglo[2][1]), 5, new Scalar(0,0,0),-1);
                                    Imgproc.circle(box, new Point(arreglo[3][0],arreglo[3][1]), 5, new Scalar(0,255,0),-1);
                                    Imgproc.cvtColor(trasladado, grayy, Imgproc.COLOR_BGR2GRAY);
                                    Imgproc.Sobel(grayy, Sobelx, 3, 1, 0,3,1,0,Core.BORDER_CONSTANT);
                                    Imgproc.Sobel(grayy, Sobely, 3, 0, 1,3,1,0,Core.BORDER_CONSTANT);
                                    Core.convertScaleAbs(Sobelx, Sobelx);
                                    Core.convertScaleAbs(Sobely, Sobely);
                                    Core.addWeighted(Sobelx, 0.9, Sobely, 0.9, 1.0, Sobel);
                                    //Core.subtract(Sobel, Core.mean(Sobel),Sobel);
                                    //Imgproc.adaptiveThreshold(Sobel, Sobel, 255.0, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 35, 1);
                                    //Imgproc.dilate(Sobel, Sobel, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(7, 7)));
                                    Imgproc.morphologyEx(Sobel, Sobel, Imgproc.MORPH_CLOSE, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5,5)));
                                    Imgproc.morphologyEx(Sobel, Sobel, Imgproc.MORPH_GRADIENT, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(7,7)));
                                    Imgproc.adaptiveThreshold(Sobel, Sobel, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 29, 1);

                                    contour = new ArrayList<MatOfPoint>();
                                    
                                    Imgproc.findContours(Sobel, contour, hierarch,  Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
                                    for ( int contourIdxx = 0; contourIdxx<contour.size(); contourIdxx++)
                                    {
                                        if(Imgproc.contourArea(contour.get(contourIdxx))>1)
                                        {
                                            MatOfPoint2f aprox = new MatOfPoint2f();
                                            MatOfPoint2f contour2ff = new MatOfPoint2f(contour.get(contourIdxx).toArray());
                                            double appro = Imgproc.arcLength(contour2ff, true)*0.02;
                                            Imgproc.approxPolyDP(contour2ff, aprox, appro, true);
                                            MatOfPoint pointss = new MatOfPoint(aprox.toArray());
                                            Rect rectt = Imgproc.boundingRect(pointss);
                                            System.out.println(rectt.height);
                                            if(rectt.height>40)
                                            {
                                                Imgcodecs.imencode(".jpg",trasladado,matOfByte5);
                                                byte[] byteArray5 = matOfByte5.toArray();
                                                try
                                                {
                                                    in5 = new ByteArrayInputStream(byteArray5);
                                                    bufImage5 = ImageIO.read(in5);

                                                }
                                                catch(IOException ex)
                                                {}
                                                imageplaca.updateImage(bufImage5);

                                                Imgcodecs.imencode(".jpg",Sobel,matOfByte6);
                                                byte[] byteArray6 = matOfByte6.toArray();
                                                try
                                                {
                                                    in6 = new ByteArrayInputStream(byteArray6);
                                                    bufImage6 = ImageIO.read(in6);

                                                }
                                                catch(IOException ex)
                                                {}
                                                imageplacaa.updateImage(bufImage6);
                                            }
                                            
                                        }
                                    }
                                }catch(Exception ex){}
                            }
                        }
                    }
                    Imgproc.GaussianBlur(Sobel,Sobel,new Size(5,5),1);
                    Imgproc.adaptiveThreshold(Sobel, adaptiveth1, 255.0, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 25, 5);
                    Imgproc.Canny(Sobel,canny, 0, 255);
                    Core.add(adaptiveth1, canny, Sobely);
                    Core.normalize(Sobely, Sobely, 0.0, 255.0, Core.NORM_MINMAX);
                    contours = new ArrayList<MatOfPoint>();
                    Imgproc.findContours(Sobely, contours, hierarchy = new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
                    for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++)
                    {
                        if(Imgproc.contourArea(contours.get(contourIdx))>1)
                        {
                            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(contourIdx).toArray());
                            RotatedRect rect = Imgproc.minAreaRect(contour2f);
                            Point[] points = new Point[4];
                            rect.points(points);
                            Imgproc.boxPoints(rect,rectangu);
                            double[][] arreglo = new double[4][2];
                            double[] aux = new double[2];
                            for (int i =0; i<rectangu.rows(); i++)
                                for (int j = 0; j < rectangu.cols(); j++)
                                    arreglo[i][j] = rectangu.get(i, j)[0];
                            for (int i = 0 ; i<arreglo.length-1;i++)
                            {
                                for(int j=0; j<arreglo.length-i-1;j++)
                                    if(arreglo[j+1][0]<arreglo[j][0])
                                    {
                                        aux = arreglo[j+1];
                                        arreglo[j+1] = arreglo[j];
                                        arreglo[j] = aux;
                                    }
                            }
                            if(arreglo[1][1]<arreglo[0][1])
                            {
                                aux = arreglo[1];
                                arreglo[1] = arreglo[0];
                                arreglo[0] = aux;
                            }
                            if(arreglo[3][1]<arreglo[2][1])
                            {
                                aux = arreglo[3];
                                arreglo[3] = arreglo[2];
                                arreglo[2] = aux;
                            }
                            double largo13 = 0,ancho12 = 0,ancho34 = 0,largo24 = 0,Prop = 0,angulo = 0;
                            boolean placa = false;
                            largo13 = Math.sqrt(Math.pow(arreglo[2][0]-arreglo[0][0],2)+Math.pow(arreglo[2][1]-arreglo[0][1],2));
                            ancho34 = Math.sqrt(Math.pow(arreglo[3][0]-arreglo[2][0],2)+Math.pow(arreglo[3][1]-arreglo[2][1],2));
                            largo24 = Math.sqrt(Math.pow(arreglo[3][0]-arreglo[1][0],2)+Math.pow(arreglo[3][1]-arreglo[1][1],2));
                            ancho12 = Math.sqrt(Math.pow(arreglo[1][0]-arreglo[0][0],2)+Math.pow(arreglo[1][1]-arreglo[0][1],2));
                            Prop = (ancho12*100)/largo13;
                            rotado = new Mat();
                            
                            //if(largo13<=largo24+30 && largo13>=largo24-30 && ancho34<=ancho12+30 && ancho34>=ancho12-30 && largo13>100 && largo13<400 && Prop>30 && Prop<100)
                            if( largo13>50 && largo13<300 && Prop>30 && Prop<100)
                                placa = true;
                            
                            if (placa)
                            {
                                angulo = Math.toDegrees(Math.asin((arreglo[2][1]-arreglo[0][1])/largo13));
                                Mat rotacion = Imgproc.getRotationMatrix2D(rect.center, angulo, 1.0);
                                Imgproc.warpAffine(gray, rotado, rotacion, new Size(largo13,ancho12));
                                Imgproc.resize(rotado, rotado, new Size(300,150));
                                System.out.println(rect.angle);
                                Imgproc.line(box, new Point(arreglo[0][0], arreglo[0][1]), new Point(arreglo[2][0], arreglo[2][1]), new Scalar(0, 0, 255));
                                Imgproc.line(box, new Point(arreglo[2][0], arreglo[2][1]), new Point(arreglo[3][0], arreglo[3][1]), new Scalar(0, 0, 255));
                                Imgproc.line(box, new Point(arreglo[3][0], arreglo[3][1]), new Point(arreglo[1][0], arreglo[1][1]), new Scalar(0, 0, 255));
                                Imgproc.line(box, new Point(arreglo[1][0], arreglo[1][1]), new Point(arreglo[0][0], arreglo[0][1]), new Scalar(0, 0, 255));
                                Imgproc.circle(box, new Point(arreglo[0][0],arreglo[0][1]), 5, new Scalar(255,0,0),-1);
                                Imgproc.circle(box, new Point(arreglo[1][0],arreglo[1][1]), 5, new Scalar(255,255,255),-1);
                                Imgproc.circle(box, new Point(arreglo[2][0],arreglo[2][1]), 5, new Scalar(0,0,0),-1);
                                Imgproc.circle(box, new Point(arreglo[3][0],arreglo[3][1]), 5, new Scalar(0,255,0),-1);
                                Imgcodecs.imencode(".jpg",box,matOfByte3);
                                byte[] byteArray3 = matOfByte3.toArray();
                                try
                                {
                                    in3 = new ByteArrayInputStream(byteArray3);
                                    bufImage3 = ImageIO.read(in3);

                                }
                                catch(IOException ex)
                                {}
                                imagepla.updateImage(bufImage3);
                                

                                
                            }
                             
                                
                            
                                
                            
                        }
                    }
                    
                    
                    
                    
                      //talvez funcione para reconocimiento de rostros
                    
                    //Imgproc.adaptiveThreshold(gray, adaptiveth, 255.0, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 25, 1.0);
                    //Core.divide(adaptiveth, canny,  Sobelx);
                    //Core.add(canny,adaptiveth,Sobelx);
                    //Core.normalize(Sobelx, Sobelx, 0.0, 255.0, Core.NORM_MINMAX);
                    //Imgproc.erode(Sobely, Sobely, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(3, 3)));
                    //Imgproc.GaussianBlur(Sobel,Sobel,new Size(7,7),1);
                    //Imgproc.adaptiveThreshold(Sobel, Sobel, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 25, 3);
                    
                    
                    
                    */
                    Termine = true;
                }
            }
        }
    }
}
    
