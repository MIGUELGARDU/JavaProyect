import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
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


public class Carril3
{
    private JPanel Carrill3;
    private Paint3 image;
    private Paint3p imagep;
    
    public Carril3() throws IOException
    {
        buildGUI() ;
    }
    private void buildGUI() throws IOException
    {
        Carrill3 = new JPanel();
        Carrill3.setPreferredSize(new Dimension(400,550));
        image = new Paint3(new ImageIcon("figs/320x240.gif").getImage());
        imagep = new Paint3p(new ImageIcon("figs/320x240.gif").getImage());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 6;
        c.gridheight = 8;
        Carrill3.add(image,c);
        c.gridx = 1;
        c.gridy = 20;
        c.gridwidth = 8;
        c.gridheight = 6;
        Carrill3.add(imagep,c);
        VentanaPrincipal.Carriles.addTab("Carril 3",Carrill3);
        
        start();
    }
    
    private Boolean beging = false;
    private VideoCapture video = null;
    private CaptureThread thread = null;
    
    private void start()
    {
        if(beging ==false)
        {
            video = new VideoCapture(LeerConfiguracion.Carril3);
            
            if (video.isOpened())
            {
                thread = new CaptureThread();
                thread.start();
                beging = true;
            }
        }
    }
    private final MatOfByte matOfByte1 = new MatOfByte();
    private final MatOfByte matOfByte2 = new MatOfByte();
    private BufferedImage bufImage1 = null;
    private BufferedImage bufImage2 = null;
    private InputStream in1;
    private InputStream in2;
    private final Mat frameaux =new Mat();
    private final Mat proces = new Mat();
    private final Mat Improces = new Mat();
    private final Mat frame = new Mat(240,320,CvType.CV_8UC3);
    ArrayList<int[]> lista;
    class CaptureThread extends Thread
    {
        @Override
        public void run()
        {
            if(video.isOpened())
            {
                while(beging == true)
                {
                    video.retrieve(frameaux);
                    Imgproc.resize(frameaux, frame, frame.size());
                    Imgcodecs.imencode(".jpg", frame, matOfByte1);
                    byte[] byteArray1 = matOfByte1.toArray();
                    try
                    {
                        in1 = new ByteArrayInputStream(byteArray1);
                        bufImage1 = ImageIO.read(in1);
                    }
                    catch(IOException ex)
                    {
                    }
                    image.updateImage(bufImage1);
                    Imgproc.cvtColor(frameaux, proces, Imgproc.COLOR_BGR2GRAY);
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
                                            Imgproc.rectangle(Improces, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0, 255), 3);
                                            int[] a = {rect.x, rect.x + rect.width, rect.y, rect.y + rect.height};
                                            lista.add(a);
                                        }
                                    }
                                }
                                
                            }
                    if (lista.size()>5)
                    {
                    Imgproc.resize(frameaux, Improces, frame.size());
                    Imgcodecs.imencode(".jpg",Improces,matOfByte2);
                    byte[] byteArray2 = matOfByte2.toArray();
                    try
                    {
                        in2 = new ByteArrayInputStream(byteArray2);
                        bufImage2 = ImageIO.read(in2);
                    }
                    catch(IOException ex)
                    {
                    }
                    imagep.updateImage(bufImage2);
                        
                    }
                    try{Thread.sleep(5);}catch(InterruptedException ex){}
                    
                }
            }
        }
    }
    public static void main() throws IOException
    {
        new Carril3();
    }
}