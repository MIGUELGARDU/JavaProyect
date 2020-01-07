
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;




public class Entrena 
{
    private final int minima_altura = 30;
    private final int maxima_altura = 100;
    private final int minima_prop = 20;
    private final int maxima_prop = 60;
    private final int DIMENSION_ANCHO = 20;
    private final int DIMENSION_ALTO = 30;
    private char[] intValidChars;
    private JFrame Ventana;
    private JPanel Ventanaa;
    private Paint1 image;
    private Paint1p imagep;
    static
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public Entrena() throws IOException
    {
        buildGUI();
        
    }

    private void buildGUI() throws IOException
    {
        Ventana = new JFrame("Entrenamiento");
        Ventanaa = new JPanel();
        Ventanaa.setLayout(new GridBagLayout());
        image = new Paint1(new ImageIcon("figs/320x240.gif").getImage());
        imagep = new Paint1p(new ImageIcon("figs/320x240.gif").getImage());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        Ventanaa.add(image,c);
        c.gridx = 1;
        c.gridy = 0;
        Ventanaa.add(imagep,c);
        Ventana.setContentPane(Ventanaa);
        Ventana.setSize(1280,480);
        Ventana.setLocation(750,100);
        Ventana.setResizable(false);
        Ventana.setVisible(true);
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        start();

    }
    private Boolean beging = false;
    private VideoCapture video = null;
    private CaptureThread thread = null;

    private void start() 
    {
        if(beging == false)
        {
            video = new VideoCapture(0);
            
            if (video.isOpened())
            {
                thread = new CaptureThread();
                thread.start();
                beging = true;
            }
        }
    }

    private MatOfByte matOfByte = new MatOfByte();
    private BufferedImage bufImage = null;
    private InputStream in;
    private Mat auxFrame = new Mat();
    private Mat frame = new Mat(480,640,CvType.CV_8UC3);
    private Mat proces = new Mat();
    private Mat imageROI = new Mat();
    private int c;
    ArrayList<Rect> lista;
    public int X;
    public int XW;
    public int Y;
    public int YH;
    class CaptureThread extends Thread
    {
        @Override
        public void run()
        {
            if(video.isOpened())
            {
                while(beging==true)
                {
                    video.retrieve(frame);
                    Imgcodecs.imencode(".jpg",frame, matOfByte);
                    byte[] byteArray = matOfByte.toArray();
                    try 
                    {
                        in = new ByteArrayInputStream(byteArray);
                        bufImage = ImageIO.read(in);
                    }
                    catch(IOException ex)
                    {
                    }
                    image.updateImage(bufImage);
                    Imgproc.cvtColor(frame, proces, Imgproc.COLOR_BGR2GRAY);
                    Imgproc.GaussianBlur(proces, proces, new Size(15, 15), 0);
                    Imgproc.adaptiveThreshold(proces, proces, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 15, 4);
                    Imgproc.dilate(proces, proces, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2,2)));
                    Imgproc.morphologyEx(proces, proces, Imgproc.MORPH_OPEN,Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2,2)));
                    LinkedList<MatOfPoint> boxPoints = new LinkedList<>();
                    LinkedList<MatOfPoint> contours  = new LinkedList<>();
                    contours.clear();
                    lista = new ArrayList<>();
                    Imgproc.findContours(proces, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
                    for (int i = 0; i < contours.size(); i++)
                            {
                                if (Imgproc.contourArea(contours.get(i))>1)
                                {
                                    MatOfPoint2f aprox = new MatOfPoint2f();
                                    MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
                                    double appro = Imgproc.arcLength(contour2f, true)*0.02;
                                    Imgproc.approxPolyDP(contour2f, aprox, appro, true);
                                    MatOfPoint points = new MatOfPoint(aprox.toArray());
                                    Rect rect = Imgproc.boundingRect(points);
                                    int prop = (rect.width * 100) / rect.height;
                                    if ( 40<prop && prop<60)
                                    {
                                        if ( 30<rect.height && rect.height<100)
                                        {   
                                            Imgproc.rectangle(frame, rect.br(),rect.tl(), new Scalar(255,0,0,255) ,3);
                                            Rect a = new Rect(rect.x,rect.x+rect.width, rect.y,rect.y+rect.height);
                                            lista.add(a);
                                            System.out.println(rect);
                                        }
                                    }
                                }
                            }
                    Imgcodecs.imencode(".jpg",proces,matOfByte);
                    byte[] byteArray2 = matOfByte.toArray();
                    try
                    {
                        in = new ByteArrayInputStream(byteArray2);
                        bufImage = ImageIO.read(in);
                    }
                    catch(IOException ex)
                    {
                    }
                    imagep.updateImage(bufImage);
                    
                }
            }
        }
    }
    public static void main(String[] args) throws IOException
    {
        new Entrena();
    }
    
    
}
