import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import org.opencv.core.Size;
import org.opencv.core.Core;
import org.opencv.core.Point;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.videoio.Videoio.CV_CAP_PROP_FRAME_HEIGHT;
import static org.opencv.videoio.Videoio.CV_CAP_PROP_FRAME_WIDTH;

public class PorcesamientoCarril1 extends javax.swing.JFrame 
{
    private DaemonThread myThread = null;
    int count = 0;
    VideoCapture webSource = new VideoCapture(0);
    Mat frame = new Mat();
    Mat Gray = new Mat();
    Mat Blur = new Mat();
    Mat Th = new Mat();
    Mat dila = new Mat();
    Mat Kernel = new Mat();
    Mat Openn = new Mat();
    LinkedList<MatOfPoint> boxPoints = new LinkedList<MatOfPoint>();
    LinkedList<MatOfPoint> contours  = new LinkedList<MatOfPoint>();
    MatOfPoint contour = new MatOfPoint();
    MatOfByte  mem     = new MatOfByte();
    class DaemonThread implements Runnable
    {
        protected volatile boolean runnable = false;
        @Override
        public void run()
        {
            synchronized(this)
            {
                while(runnable)
                {
                    if(webSource.grab())
                    {
                        try
                        {
                            webSource.retrieve(frame);
                            Imgproc.cvtColor(frame, Gray, Imgproc.COLOR_BGR2GRAY);
                            Imgproc.GaussianBlur(Gray, Blur, new Size(15,15), 0);
                            Imgproc.adaptiveThreshold(Blur, Th, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 15, 4);
                            Imgproc.dilate(Th, dila, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2,2)));
                            Imgproc.morphologyEx(dila, Openn, Imgproc.MORPH_OPEN, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2,2)));
                            contours.clear();
                            ArrayList<int[]>Lista = new ArrayList();
                            Imgproc.findContours(Openn, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
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
                                    Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 0, 255), 3);
                                    int prop = (rect.width * 100) / rect.height;
                                    if ( 40<prop && prop<60)
                                    {
                                        if ( 30<rect.height && rect.height<100)
                                        {
                                            Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0, 255), 3);
                                            int[] a = {rect.x, rect.x + rect.width, rect.y, rect.y + rect.height};
                                            
                                            Lista.add(a);
                                        }
                                    }
                                }
                                
                            }
                            System.out.println(Lista);
                            Imgcodecs.imencode(".bmp", frame, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                            BufferedImage buff = (BufferedImage) im;
                            //Graphics g=jPanel1.getGraphics();
                            
                            //if(g.drawImage(buff, 0, 0, getWidth(), getHeight() - 150, 0, 0, buff.getWidth(), buff.getHeight(), null));
                            
                            if(runnable == false)
                            {
                                System.out.println("Going to Wait()");
                                this.wait();
                            }
                        }
                        catch(Exception ex)
                        {
                            System.out.println("Error");
                        }
                    }
                }
            }
        }
    }
}
